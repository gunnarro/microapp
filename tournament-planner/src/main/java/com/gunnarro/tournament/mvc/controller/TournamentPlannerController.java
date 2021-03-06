package com.gunnarro.tournament.mvc.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class TournamentPlannerController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(TournamentPlannerController.class);

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@GetMapping("/tournaments/{token}")
	@ResponseBody
	public ModelAndView listTournaments(@PathVariable("token") String token) {
		List<Tournament> tournaments = tournamentPlannerService.getTournaments(token);
		ModelAndView modelView = new ModelAndView("tournament/view-tournaments");
		modelView.addObject("tournaments", tournaments);
		return modelView;
	}

	@GetMapping("/tournament/{tournamentId}")
	@ResponseBody
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

	@GetMapping("/tournament/generate")
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
	@PostMapping("/tournament/generate")
	public String processGenerateTournamentForm(@Valid @ModelAttribute("tournament") TournamentInput tournament, BindingResult validationResult, SessionStatus status) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(tournament.toString());
		}

		if (validationResult.hasErrors()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(validationResult.toString());
			}
			return "tournament/generate-tournament";
		}

		// custom validation
		if (tournament.getTeamNames().size() % 2 != 0 || tournament.getTeamNames().size() < 4) {
			FieldError error = new FieldError("tournament", "teamNames", "Number of teams must a even number and minimum 4 teams");
			validationResult.addError(error);
			return "tournament/generate-tournament";
		}

		Tournament generatedTournament = this.tournamentPlannerService.generateTournament(tournament);
		this.tournamentPlannerService.saveTournament(generatedTournament);
		status.setComplete();
		return "redirect:/tournament/" + generatedTournament.getTournamentName();
	}

	@GetMapping("/tournament/groupstage/edit/{tournamentId}/{groupId}")
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
	@PostMapping("/tournament/groupstage/edit/{tournamentId}/{groupId}")
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

	@GetMapping("/tournament/secondstage/edit/{tournamentId}")
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
	@PostMapping("/tournament/secondstage/edit/{tournamentId}")
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
	@DeleteMapping("/tournament/{tournamentId}")
	public String deleteTournament(@PathVariable("tournamentId") String tournamentId) {
		tournamentPlannerService.deleteTournament(tournamentId);
		return "redirect:/listtournaments/" + tournamentId;
	}

	public void setTournamentPlannerService(TournamentPlannerService tournamentPlannerService) {
		this.tournamentPlannerService = tournamentPlannerService;
	}

}
