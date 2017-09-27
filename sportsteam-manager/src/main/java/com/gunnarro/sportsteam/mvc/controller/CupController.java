package com.gunnarro.sportsteam.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Cup;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.view.list.Item;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

@Controller
public class CupController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(CupController.class);

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
    }

    @RequestMapping(value = "/listcups/{seasonPeriod}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView listCups(@PathVariable("seasonPeriod") String seasonPeriod) {
        ModelAndView modelView = new ModelAndView("cup/list-cups-view");
        Season season = sportsTeamService.getSeason(seasonPeriod);
        if (season == null) {
            season = sportsTeamService.getCurrentSeason();
        }
        List<Cup> cups = sportsTeamService.getCups(-1, seasonPeriod);
        modelView.addObject("season", season);
        modelView.addObject("cupList", cups);
        return modelView;
    }

    // ---------------------------------------------
    // New and update cup
    // ---------------------------------------------

    @RequestMapping(value = "/cup/new", method = RequestMethod.GET)
    public String initNewCupForm(Map<String, Object> model) {
        // Team team = sportsTeamService.getTeam(teamId);
        // if (team == null) {
        // throw new ApplicationException("ERROR: Team not found, teamId=" +
        // teamId);
        // }
        Cup cup = new Cup();
        cup.setSeason(sportsTeamService.getCurrentSeason());
        cup.setFkSeasonId(cup.getSeason().getId());
        model.put("cup", cup);
        // model.put("teamList",
        // sportsTeamService.getTeams(team.getFkClubId()));
        if (LOG.isDebugEnabled()) {
            LOG.debug(cup.toString());
        }
        return "cup/edit-cup";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/cup/new", method = RequestMethod.POST)
    public String processNewCupForm(@Valid @ModelAttribute("cup") Cup cup, BindingResult validationResult, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(cup.toString());
        }
        if (validationResult.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(validationResult.toString());
            }
            return "cup/edit-cup";
        } else {
            this.sportsTeamService.saveCup(cup);
            status.setComplete();
            return "redirect:/listcups/" + cup.getFkSeasonId();
        }
    }

    @RequestMapping(value = "/cup/edit/{cupId}", method = RequestMethod.GET)
    public String initUpdateCupForm(@PathVariable("cupId") int cupId, Model model) {
        Cup cup = sportsTeamService.getCup(cupId);
        if (cup == null) {
            throw new ApplicationException("Object Not Found, cupId=" + cupId);
        }
        model.addAttribute(cup);
        if (LOG.isDebugEnabled()) {
            LOG.debug(cup.toString());
        }
        return "cup/edit-cup";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/cup/edit/{cupId}", method = RequestMethod.PUT)
    public String processUpdateCupForm(@Valid @ModelAttribute("cup") Cup cup, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(cup.toString());
        }
        if (result.hasErrors()) {
            LOG.error(null, cup);
            return "cup/edit-cup";
        } else {
            sportsTeamService.saveCup(cup);
            Cup updatedCup = sportsTeamService.getCup(cup.getId());
            status.setComplete();
            return "redirect:/listcups/" + updatedCup.getFkSeasonId();
        }
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/cup/delete/{cupId}", method = RequestMethod.GET)
    public String deleteCup(@PathVariable("cupId") int cupId) {
        sportsTeamService.deleteCup(cupId);
        return "redirect:/listcups/" + sportsTeamService.getCurrentSeason().getId();
    }

    /**
     * 
     * @param cupId
     * @param teamId
     * @param model
     * @return
     */
    @RequestMapping("/cup/{cupId}/{teamId}")
    public ModelAndView viewCupForTeam(@PathVariable("cupId") int cupId, @PathVariable("teamId") int teamId, Map<String, Object> model) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("cupId=" + cupId + ", teamId=" + teamId);
        }

        Cup cup = sportsTeamService.getCup(cupId);
        if (cup == null) {
            throw new ApplicationException("Object Not Found,  cupId:" + cupId);
        }
        Team team = sportsTeamService.getTeam(teamId);
        if (team == null) {
            throw new ApplicationException("Object Not Found, teamId=" + teamId);
        }
        cup.setFkTeamId(team.getId());
        List<Item> playerList = sportsTeamService.getCupSignedPlayerList(teamId, cup.getId());
        ModelAndView modelView = new ModelAndView("cup/view-cup-team");
        modelView.getModel().put("teamName", team.getName());
        modelView.getModel().put("cup", cup);
        modelView.getModel().put("playerList", playerList);
        return modelView;
    }

    /**
     * 
     * @param cupId
     * @param teamId
     * @param model
     * @return
     */
    @RequestMapping("/cup/{cupId}")
    public ModelAndView viewCup(@PathVariable("cupId") int cupId, Map<String, Object> model) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("cupId=" + cupId);
        }

        Cup cup = sportsTeamService.getCup(cupId);
        if (cup == null) {
            throw new ApplicationException("Object Not Found,  cupId:" + cupId);
        }

        List<Item> teamList = sportsTeamService.getCupRegistreredTeamList(cup.getId());
        ModelAndView modelView = new ModelAndView("cup/view-cup");
        modelView.getModel().put("cup", cup);
        modelView.getModel().put("teamList", teamList);
        return modelView;
    }

}
