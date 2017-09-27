package com.gunnarro.tournament.mvc.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.tournament.domain.activity.Group;
import com.gunnarro.tournament.domain.activity.Tournament;
import com.gunnarro.tournament.domain.view.FinalSetup;
import com.gunnarro.tournament.domain.view.TournamentInput;
import com.gunnarro.tournament.service.TournamentPlannerService;
import com.gunnarro.tournament.service.exception.ApplicationException;

@Controller
public class TournamentPlannerController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(TournamentPlannerController.class);

    @Autowired
    @Qualifier("tournamentPlannerService")
    private TournamentPlannerService tournamentPlannerService;

    @RequestMapping(value = "/listtournaments/{token}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView listTournaments(@PathVariable("token") String token) {
        List<Tournament> tournaments = tournamentPlannerService.getTournaments(token);
        ModelAndView modelView = new ModelAndView("tournament/list-tournaments-view");
        modelView.addObject("tournamentList", tournaments);
        return modelView;
    }

    @RequestMapping(value = "/tournament/{tournamentId}", method = RequestMethod.GET)
    public ModelAndView viewTournament(@PathVariable("tournamentId") String tournamentId, Map<String, Object> model) {
        Tournament tournament = tournamentPlannerService.getTournament(tournamentId);
        if (tournament == null) {
            throw new ApplicationException("No tournament found for Id:" + tournamentId);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(tournament.toString());
        }
        ModelAndView modelView = new ModelAndView("tournament/view-tournament");
        modelView.getModel().put("tournament", tournament);
        return modelView;
    }

    // ---------------------------------------------
    // New and update tournament
    // ---------------------------------------------

    @RequestMapping(value = "/tournament/generate", method = RequestMethod.GET)
    public String initGenerateTournamentForm(Map<String, Object> model) {
        TournamentInput tournament = new TournamentInput();
        model.put("tournament", tournament);
        model.put("tournamentTypeOptions", TournamentInput.getTournamentTypeOptions());
        if (LOG.isDebugEnabled()) {
            LOG.debug(tournament.toString());
        }
        return "tournament/generate-tournament";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/tournament/generate", method = RequestMethod.POST)
    public String processGenerateTournamentForm(@Valid @ModelAttribute("tournament") TournamentInput tournament, BindingResult validationResult, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(tournament.toString());
        }
        if (validationResult.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(validationResult.toString());
            }
            return "tournament/generate-tournament";
        } else {
            // Convert textaera to list for team names
            tournament.setTeamNames(tournament.getTeamsAsList());
            if (LOG.isDebugEnabled()) {
                LOG.debug(tournament.toString());
            }
            Tournament generatedTournament = this.tournamentPlannerService.generateTournament(tournament);
            this.tournamentPlannerService.saveTournament(generatedTournament);
            status.setComplete();
            return "redirect:/tournament/" + generatedTournament.getTournamentName();
        }
    }

    @RequestMapping(value = "/tournament/groupstage/edit/{tournamentId}/{groupId}", method = RequestMethod.GET)
    public String initGroupStageUpdateTournamentForm(@PathVariable("tournamentId") String tournamentId, @PathVariable("groupId") String groupId, Model model) {
        Tournament tournament = tournamentPlannerService.getTournament(tournamentId);
        if (tournament == null) {
            throw new ApplicationException("Object Not Found, tournamentId=" + tournamentId);
        }
        Group group = tournament.getGroupById(new Integer(groupId));
        model.addAttribute("group", group);
        if (LOG.isDebugEnabled()) {
            LOG.debug("" + group);
        }
        return "tournament/edit-groupstage-tournament";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/tournament/groupstage/edit/{tournamentId}/{groupId}", method = RequestMethod.POST)
    public String processGroupStageUpdateTournamentForm(@Valid @ModelAttribute("group") Group group, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(group.toString());
        }
        if (result.hasErrors()) {
            LOG.error(null, group);
            return "tournament/edit-groupstage-tournament";
        } else {
            Tournament tournament = tournamentPlannerService.getTournament(group.getTournamentId());
            Group groupById = tournament.getGroupById(group.getId());
            groupById = group;
            tournamentPlannerService.saveTournament(tournament);
            status.setComplete();
            return "redirect:/tournament/" + group.getTournamentId();
        }
    }

    @RequestMapping(value = "/tournament/secondstage/edit/{tournamentId}", method = RequestMethod.GET)
    public String initSecondStageUpdateTournamentForm(@PathVariable("tournamentId") String tournamentId, Model model) {
        Tournament tournament = tournamentPlannerService.getTournament(tournamentId);
        if (tournament == null) {
            throw new ApplicationException("Object Not Found, tournamentId=" + tournamentId);
        }
        model.addAttribute("finalsetup", tournament.getFinalsSetup());
        if (LOG.isDebugEnabled()) {
            LOG.debug(tournament.getFinalsSetup().toString());
        }
        return "tournament/edit-secondstage-tournament";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/tournament/secondstage/edit/{tournamentId}", method = RequestMethod.POST)
    public String processSecondStageUpdateTournamentForm(@Valid @ModelAttribute("finalsetup") FinalSetup finalSetup, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(finalSetup.toString());
        }
        if (result.hasErrors()) {
            LOG.error(null, finalSetup);
            return "tournament/edit-secondstage-tournament";
        } else {
            Tournament tournament = tournamentPlannerService.getTournament(finalSetup.getTournamentId());
            tournament.setFinalsSetup(finalSetup);
            tournamentPlannerService.updateFinalSetup(tournament.getFinalsSetup());
            tournamentPlannerService.saveTournament(tournament);
            status.setComplete();
            return "redirect:/tournament/" + finalSetup.getTournamentId();
        }
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/tournament/delete/{tournamentId}", method = RequestMethod.GET)
    public String deleteTournament(@PathVariable("tournamentId") String tournamentId) {
        tournamentPlannerService.deleteTournament(tournamentId);
        return "redirect:/listtournaments/" + tournamentId;
    }

    public void setTournamentPlannerService(TournamentPlannerService tournamentPlannerService) {
        this.tournamentPlannerService = tournamentPlannerService;
    }

}
