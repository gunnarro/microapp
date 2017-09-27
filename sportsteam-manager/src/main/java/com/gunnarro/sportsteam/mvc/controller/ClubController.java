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
import com.gunnarro.sportsteam.domain.party.Address;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

@Controller
@SessionAttributes(types = Club.class)
public class ClubController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(ClubController.class);

    @RequestMapping(value = "/listclubs", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView listClubs() {
        ModelAndView modelView = new ModelAndView("club/list-clubs");
        List<Club> clubs = sportsTeamService.getClubs();
        modelView.addObject("clubList", clubs);
        return modelView;
    }

    @RequestMapping(value = "/club/{clubId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewClub(@PathVariable("clubId") int clubId) {
        Club club = sportsTeamService.getClub(clubId);
        if (club == null) {
            throw new ApplicationException("Object Not Found, clubId=" + clubId);
        }
        ModelAndView modelView = new ModelAndView("club/view-club");
        modelView.addObject("club", club);
        return modelView;
    }

    // ---------------------------------------------
    // New and update club
    // ---------------------------------------------

    @RequestMapping(value = "/club/new", method = RequestMethod.GET)
    public String initNewClubForm(Map<String, Object> model) {
        Club club = new Club();
        club.setAddress(new Address());
        model.put("club", club);
        return "club/edit-club";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/club/new", method = RequestMethod.POST)
    public String processNewClubForm(Club club, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(club.toString());
        }
        if (result.hasErrors()) {
            return "club/edit-club";
        } else {
            int clubId = this.sportsTeamService.saveClub(club);
            status.setComplete();
            return "redirect:/club/" + clubId;
        }
    }

    @RequestMapping(value = "/club/edit/{clubId}", method = RequestMethod.GET)
    public String initUpdateClubForm(@PathVariable("clubId") int clubId, Model model) {
        Club club = sportsTeamService.getClub(clubId);
        if (club == null) {
            throw new ApplicationException("Object Not Found, clubId=" + clubId);
        }
        // have to set an empty address in order to get it set from the ui.
        if (!club.hasAddress()) {
            club.setAddress(new Address());
        }
        model.addAttribute("club", club);
        return "club/edit-club";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/club/edit/{clubId}", method = RequestMethod.PUT)
    public String processUpdateClubForm(Club club, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "club/edit-club";
        } else {
            int clubId = sportsTeamService.saveClub(club);
            status.setComplete();
            return "redirect:/club/" + clubId;
        }
    }

    @RequestMapping(value = "/club/delete/{clubId}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteClub(@PathVariable("clubId") int clubId) {
        Club club = sportsTeamService.getClub(clubId);
        if (club == null) {
            throw new ApplicationException("Object Not Found, clubId=" + clubId);
        }
        sportsTeamService.deleteClub(clubId);
        return "redirect:/listclubs";
    }
}
