package com.gunnarro.sportsteam.mvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Type;
import com.gunnarro.sportsteam.domain.party.Address;
import com.gunnarro.sportsteam.domain.party.Contact;
import com.gunnarro.sportsteam.domain.party.Contact.GenderEnum;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

@Controller
@SessionAttributes(types = Contact.class)
public class ContactController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(ContactController.class);

    @RequestMapping(value = "/listcontacts/{teamId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView listContacts(@PathVariable("teamId") int teamId) {
        ModelAndView modelView = new ModelAndView("contact/list-contacts");
        Team team = sportsTeamService.getTeam(teamId);
        if (team == null) {
            throw new ApplicationException("Object Not Found, teamId=" + teamId);
        }
        List<Contact> contacts = sportsTeamService.getContacts(teamId);
        modelView.addObject("team", team);
        modelView.addObject("contactList", contacts);
        return modelView;
    }

    @RequestMapping(value = "/contact/{contactId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewContact(@PathVariable("contactId") int contactId) {
        Contact contact = sportsTeamService.getContact(contactId);
        if (contact == null) {
            throw new ApplicationException("Object Not Found, contactId=" + contactId);
        }
        return new ModelAndView("contact/view-contact", "contact", contact);
    }

    // ---------------------------------------------
    // New and update contact
    // ---------------------------------------------

    @RequestMapping(value = "/contact/new/{teamId}", method = RequestMethod.GET)
    public String initNewContactForm(@PathVariable("teamId") int teamId, Map<String, Object> model) {
        Team team = sportsTeamService.getTeam(teamId);
        if (team == null) {
            throw new ApplicationException("Object Not Found, teamId=" + teamId);
        }
        Contact contact = new Contact();
        contact.setAddress(new Address());
        contact.setFkTeamId(team.getId());
        model.put("contact", contact);
        model.put("statusOptions", sportsTeamService.getContactStatusTypes());
        model.put("team", team);
        // model.put("teamList", sportsTeamService.getTeams(1));
        model.put("genderOptions", GenderEnum.asArray());
        List<String> list = new ArrayList<String>();
        for (Type t : sportsTeamService.getTeamRoleTypes()) {
            list.add(t.getName());
        }
        model.put("teamRoleOptions", list);
        return "contact/edit-contact";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/contact/new/{teamId}", method = RequestMethod.POST)
    public String processNewContactForm(Contact contact, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "contact/edit-contact";
        } else {
            // Map assigned team or removed team roles, if any.
            List<Type> teamRoles = Contact.mapNewTeamRolesNameToTypeList(contact.getTeamRolesStr());
            contact.setTeamRoleList(teamRoles);
            this.sportsTeamService.saveContact(contact);
            status.setComplete();
            return "redirect:/listcontacts/" + contact.getFkTeamId();
        }
    }

    @RequestMapping(value = "/contact/edit/{contactId}", method = RequestMethod.GET)
    public String initUpdateContactForm(@PathVariable("contactId") int contactId, Model model) {
        Contact contact = sportsTeamService.getContact(contactId);
        if (contact == null) {
            throw new ApplicationException("Object Not Found, contactId=" + contactId);
        }
        List<String> list = new ArrayList<String>();
        for (Type t : sportsTeamService.getTeamRoleTypes()) {
            list.add(t.getName());
        }
        model.addAttribute("statusOptions", sportsTeamService.getContactStatusTypes());
        model.addAttribute("genderOptions", GenderEnum.asArray());
        model.addAttribute("teamRoleOptions", list);
        model.addAttribute("team", contact.getTeam());
        model.addAttribute("contact", contact);
        return "contact/edit-contact";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/contact/edit/{contactId}", method = RequestMethod.PUT)
    public String processUpdateContactForm(Contact contact, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "contact/edit-contact";
        } else {
            if (contact.getTeamRolesStr() != null) {
                List<Type> list = new ArrayList<Type>();
                for (String s : contact.getTeamRolesStr()) {
                    Type type = new Type();
                    type.setName(s);
                    list.add(type);
                }
                contact.setTeamRoleList(list);
            }
            sportsTeamService.saveContact(contact);
            status.setComplete();
            return "redirect:/listcontacts/" + contact.getFkTeamId();
        }
    }

    /**
     * Use DELETE for deletes
     * 
     */
    @RequestMapping(value = "/contact/delete/{contactId}", method = RequestMethod.GET)
    public String deleteContact(@PathVariable("contactId") int contactId) {
        Contact contact = sportsTeamService.getContact(contactId);
        if (contact == null) {
            throw new ApplicationException("Object Not Found, contactId=" + contactId);
        }
        sportsTeamService.deleteContact(contactId);
        return "redirect:/listcontacts/" + contact.getFkTeamId();
    }
}
