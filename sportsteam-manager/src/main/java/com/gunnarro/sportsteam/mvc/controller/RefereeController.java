package com.gunnarro.sportsteam.mvc.controller;

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

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.party.Referee;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

//import javax.validation.Valid;

@Controller
@SessionAttributes(types = Referee.class)
public class RefereeController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(RefereeController.class);

    @RequestMapping(value = "/referee/new", method = RequestMethod.GET)
    public String initNewRefereeForm(Map<String, Object> model) {
        Referee referee = new Referee();
        model.put("referee", referee);
        model.put("clubList", sportsTeamService.getClubs());
        return "referee/edit-referee";
    }

    /**
     * User POST for new
     * 
     * @param referee
     * @param result
     * @param status
     * @return
     */
    @RequestMapping(value = "/referee/new", method = RequestMethod.POST)
    // public String processCreationForm(@Valid Referee referee, BindingResult
    // result, SessionStatus status) {
    public String processCreationForm(Referee referee, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "referee/edit-referee";
        } else {
            this.sportsTeamService.saveReferee(referee);
            status.setComplete();
            return "redirect:/listreferees/" + referee.getFkClubId();
        }
    }

    @RequestMapping(value = "/listreferees/{clubId}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView listReferees(@PathVariable("clubId") int clubId) {
        ModelAndView modelView = new ModelAndView("referee/list-referees");
        Club club = sportsTeamService.getClub(clubId);
        if (club == null) {
            throw new ApplicationException("Object Not Found, clubId=" + clubId);
        }
        List<Referee> referees = sportsTeamService.getReferees(clubId);
        modelView.addObject("clubName", club.getFullName());
        modelView.addObject("refereeList", referees);
        return modelView;
    }

    @RequestMapping(value = "/referee/{refereeId}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView viewReferee(@PathVariable("refereeId") int refereeId) {
        Referee referee = sportsTeamService.getReferee(refereeId);
        if (referee == null) {
            throw new ApplicationException("Object Not Found, refereeId=" + refereeId);
        }
        return new ModelAndView("referee/view-referee", "referee", referee);
    }

    @RequestMapping(value = "/referee/edit/{refereeId}", method = RequestMethod.GET)
    public String initUpdateRefereeForm(@PathVariable("refereeId") int refereeId, Model model) {
        Referee referee = sportsTeamService.getReferee(refereeId);
        if (referee == null) {
            throw new ApplicationException("Object Not Found, refereeId=" + refereeId);
        }
        model.addAttribute(referee);
        return "referee/edit-referee";
    }

    /**
     * Use PUT for updates
     * 
     * @param referee
     * @param result
     * @param status
     * @return
     */
    @RequestMapping(value = "/referee/edit/{refereeId}", method = RequestMethod.PUT)
    public String processUpdateRefereeForm(Referee referee, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "referee/edit-referee";
        } else {
            sportsTeamService.saveReferee(referee);
            status.setComplete();
            return "redirect:/listreferees/" + referee.getFkClubId();
        }
    }

    /**
     * Use DELETE for delete object
     * 
     * @param referee
     * @param result
     * @param status
     * @return
     */
    @RequestMapping(value = "/referee/delete/{refereeId}", method = RequestMethod.GET)
    public String deleteReferee(@PathVariable("refereeId") int refereeId) {
        Referee referee = sportsTeamService.getReferee(refereeId);
        if (referee == null) {
            throw new ApplicationException("Object Not Found, refereeId=" + refereeId);
        }
        sportsTeamService.deleteReferee(refereeId);
        return "redirect:/listreferees/" + referee.getFkClubId();
    }
}
