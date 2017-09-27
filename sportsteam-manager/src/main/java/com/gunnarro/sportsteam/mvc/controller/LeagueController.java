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

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.league.League;
import com.gunnarro.sportsteam.domain.party.Contact.GenderEnum;
import com.gunnarro.sportsteam.domain.statistic.MatchStatistic;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

@Controller
@SessionAttributes(types = Team.class)
public class LeagueController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(LeagueController.class);

    @RequestMapping(value = "/listleagues", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView listLeagues() {
        List<League> leagues = sportsTeamService.getLeagues();
        ModelAndView modelView = new ModelAndView("league/list-leagues");
        modelView.addObject("leagueList", leagues);
        return modelView;
    }

    @RequestMapping(value = "/league/{leagueId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewLeague(@PathVariable("leagueId") int leagueId) {
        League league = sportsTeamService.getLeague(leagueId);
        if (league == null) {
            throw new ApplicationException("Object Not Found, leagueId=" + leagueId);
        }
        List<MatchStatistic> leagueTable = sportsTeamService.getLeagueTable(league.getId(), "current");
        ModelAndView modelView = new ModelAndView("league/view-league");
        modelView.addObject("league", league);
         modelView.addObject("leagueStatistics", leagueTable);
        return modelView;
    }

    @RequestMapping(value = "/league/table/{leagueId}/{season}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewLeagueTable(@PathVariable("leagueId") int leagueId, @PathVariable("season") String seasonPeriod) {
        League league = sportsTeamService.getLeague(leagueId);
        if (league == null) {
            throw new ApplicationException("Object Not Found, leagueId=" + leagueId);
        }
        List<MatchStatistic> leagueTable = sportsTeamService.getLeagueTable(leagueId, seasonPeriod);
        ModelAndView modelView = new ModelAndView("league/view-league-table");
        modelView.addObject("league", league);
        modelView.addObject("leagueStatistics", leagueTable);
        return modelView;
    }

    // ---------------------------------------------
    // New and update league
    // ---------------------------------------------

    @RequestMapping(value = "/league/new", method = RequestMethod.GET)
    public String initNewLeagueForm(Map<String, Object> model) {
        League league = new League();
        model.put("league", league);
        model.put("genderOptions", GenderEnum.asArray());
        return "league/edit-league";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/league/new", method = RequestMethod.POST)
    public String processNewLeagueForm(League league, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(league.toString());
        }
        if (result.hasErrors()) {
            return "league/edit-league";
        } else {
            this.sportsTeamService.saveLeague(league);
            status.setComplete();
            return "redirect:/listleague/";
        }
    }

    @RequestMapping(value = "/league/edit/{leagueId}", method = RequestMethod.GET)
    public String initUpdateLeagueForm(@PathVariable("leagueId") int leagueId, Model model) {
        League league = sportsTeamService.getLeague(leagueId);
        if (league == null) {
            throw new ApplicationException("Object Not Found, leagueId=" + leagueId);
        }
        model.addAttribute("league", league);
        return "league/edit-league";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/league/edit/{leagueId}", method = RequestMethod.PUT)
    public String processUpdateLeagueForm(League league, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "league/edit-league";
        } else {
            sportsTeamService.saveLeague(league);
            status.setComplete();
            return "redirect:/listleague";
        }
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/league/delete/{leagueId}", method = RequestMethod.GET)
    public String deleteLeague(@PathVariable("leagueId") int leagueId) {
        League league = sportsTeamService.getLeague(leagueId);
        if (league == null) {
            throw new ApplicationException("Object Not Found, leagueId=" + leagueId);
        }
        sportsTeamService.deleteLeague(leagueId);
        return "redirect:/listleagues";
    }
}
