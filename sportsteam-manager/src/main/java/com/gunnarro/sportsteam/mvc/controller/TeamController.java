package com.gunnarro.sportsteam.mvc.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.party.Contact.GenderEnum;
import com.gunnarro.sportsteam.domain.view.ActivityView;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

@Controller
@SessionAttributes(types = Team.class)
public class TeamController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(TeamController.class);

    /**
     * Converts empty strings into null when a form is submitted
     * 
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = "/listteams/{clubId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView listTeams(@PathVariable("clubId") int clubId) {
        Club club = sportsTeamService.getClub(clubId);
        if (club == null) {
            throw new ApplicationException("Object Not Found, clubId=" + clubId);
        }
        List<Team> teams = sportsTeamService.getTeams(club.getId());
        ModelAndView modelView = new ModelAndView("team/list-teams");
        modelView.addObject("clubName", club.getFullName());
        modelView.addObject("teamList", teams);
        return modelView;
    }

    @RequestMapping(value = "/team/{teamId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewTeam(@PathVariable("teamId") int teamId) {
        Team team = sportsTeamService.getTeam(teamId);
        if (team == null) {
            throw new ApplicationException("Object Not Found, teamId=" + teamId);
        }
        List<ActivityView> activities = sportsTeamService.getActivitiesUpcomingWeek(teamId);
        ModelAndView modelView = new ModelAndView("team/view-team");
        modelView.addObject("team", team);
        modelView.addObject("upcomingActivityList", activities);
        return modelView;
    }

    // ---------------------------------------------
    // New and update team
    // ---------------------------------------------

    @RequestMapping(value = "/team/new/{clubId}", method = RequestMethod.GET)
    public String initNewTeamForm(@PathVariable("clubId") int clubId, Map<String, Object> model) {
        Team team = new Team();
        team.setFkClubId(clubId);
        model.put("team", team);
        model.put("clubList", sportsTeamService.getClubs());
        model.put("contactList", sportsTeamService.getContacts(1));
        model.put("leagueOptions", sportsTeamService.getLeagueTypes(sportsTeamService.getCurrentSeason().getId()));
        model.put("genderOptions", GenderEnum.asArray());
        return "team/edit-team";
    }
    
    @RequestMapping(value = "/team/new", method = RequestMethod.GET)
    public String initNewTeamForm(Map<String, Object> model) {
        Team team = new Team();
        model.put("team", team);
        model.put("clubList", sportsTeamService.getClubs());
        model.put("contactList", sportsTeamService.getContacts(1));
        model.put("leagueOptions", sportsTeamService.getLeagueTypes(sportsTeamService.getCurrentSeason().getId()));
        model.put("genderOptions", GenderEnum.asArray());
        return "team/edit-team";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/team/new", method = RequestMethod.POST)
    public String processNewTeamForm(Team team, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(team.toString());
        }
        if (result.hasErrors()) {
            return "team/edit-team";
        } else {
            this.sportsTeamService.saveTeam(team);
            status.setComplete();
            return "redirect:/listteams/" + team.getFkClubId();
        }
    }

    @RequestMapping(value = "/team/edit/{teamId}", method = RequestMethod.GET)
    public String initUpdateTeamForm(@PathVariable("teamId") int teamId, Model model) {
        Team team = sportsTeamService.getTeam(teamId);
        if (team == null) {
            throw new ApplicationException("Object Not Found, teamId=" + teamId);
        }
        // Set fk id's in order to preselect the dropdowns
        // if (team.hasTeamLead()) {
        // team.setFkTeamleadId(team.getTeamLead().getId());
        // }
        // if (team.hasCoach()) {
        // team.setFkCoachId(team.getCoach().getId());
        // }
        model.addAttribute("team", team);
        model.addAttribute("contactList", sportsTeamService.getContacts(team.getId()));
        model.addAttribute("leagueOptions", sportsTeamService.getLeagueTypes(sportsTeamService.getCurrentSeason().getId()));
        model.addAttribute("genderOptions", GenderEnum.asArray());
        return "team/edit-team";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/team/edit/{teamId}", method = RequestMethod.PUT)
    public String processUpdateTeamForm(Team team, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "team/edit-team";
        } else {
            sportsTeamService.saveTeam(team);
            status.setComplete();
            return "redirect:/listteams/" + team.getFkClubId();
        }
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/team/delete/{teamId}", method = RequestMethod.GET)
    public String deleteTeam(@PathVariable("teamId") int teamId) {
        Team team = sportsTeamService.getTeam(teamId);
        if (team == null) {
            throw new ApplicationException("Object Not Found, teamId=" + teamId);
        }
        sportsTeamService.deleteTeam(teamId);
        return "redirect:/listteams/" + team.getFkClubId();
    }
}
