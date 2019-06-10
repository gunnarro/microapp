package com.gunnarro.dietmanager.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.support.SimpleSessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.tournament.domain.activity.Group;
import com.gunnarro.tournament.domain.activity.Tournament;
import com.gunnarro.tournament.domain.view.FinalSetup;
import com.gunnarro.tournament.domain.view.TournamentInput;
import com.gunnarro.tournament.mvc.controller.TournamentPlannerController;
import com.gunnarro.tournament.service.TournamentPlannerService;

public class TournamentControllerTest extends SpringTestSetup {

    private TournamentPlannerController controller;

    @Mock
    private TournamentPlannerService tournamentPlannerServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new TournamentPlannerController();
        controller.setTournamentPlannerService(tournamentPlannerServiceMock);
    }

    @Test
    public void listTournaments() throws Exception {
        when(tournamentPlannerServiceMock.getTournaments("token")).thenReturn(Arrays.asList(new Tournament("test")));
        ModelAndView modelAndView = controller.listTournaments("token");
        Assert.assertEquals("tournament/list-tournaments-view", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        Assert.assertNotNull(modelAndView.getModel().get("tournamentList"));
    }

    @Test
    public void initGenerateTournamentForm() {
        String view = controller.initGenerateTournamentForm(new HashMap<String, Object>());
        Assert.assertEquals("tournament/generate-tournament", view);
    }

    @Test
    public void prosessGenerateTournament() {
        TournamentInput tournamentInput = new TournamentInput();
        tournamentInput.setName("unittesttournament");
        tournamentInput.setTeams("t1\nt2\nt3\nt4\nt5\nt6\nt7\nt8");
        tournamentInput.setNumberOfGroups(2);
        tournamentInput.setPauseTimeBetweenMatches(5);
        tournamentInput.setNumberOfFields(2);
        tournamentInput.setPlayTime(20);
        tournamentInput.setType("single");
        BindingResult validationResult = new  MapBindingResult(new HashMap<String,String>(), "name");
        Tournament tournament = new Tournament("unittestid");
        when(tournamentPlannerServiceMock.generateTournament(tournamentInput)).thenReturn(tournament);
        String view = controller.processGenerateTournamentForm(tournamentInput, validationResult, new SimpleSessionStatus());
        Assert.assertEquals("redirect:/tournament/unittestid", view);
    }
    
    @Test
    public void deleteTournament() {
        when(tournamentPlannerServiceMock.deleteTournament("1")).thenReturn(true);
        String view = controller.deleteTournament("1");
        Assert.assertEquals("redirect:/listtournaments/1", view);
    }

    @Test
    public void viewTournament() {
        Tournament tournament = new Tournament("unittestId");
        tournament.setFinalsSetup(new FinalSetup());
        when(tournamentPlannerServiceMock.getTournament("unittestId")).thenReturn(tournament);
        ModelAndView view = controller.viewTournament("unittestId", null);
        Assert.assertEquals("tournament/view-tournament", view.getViewName());
        Assert.assertNotNull(view.getModel().get("tournament"));
        Tournament t = (Tournament) view.getModel().get("tournament");
        Assert.assertNotNull(t.getFinalsSetup());
    }

    @Test
    public void initGroupStageUpdateTournamentForm() {
        Model model = new ExtendedModelMap();
        Tournament tournament = new Tournament("unittestId");
        tournament.setGroups(new ArrayList<Group>());
        when(tournamentPlannerServiceMock.getTournament("unittestId")).thenReturn(tournament);
        String view = controller.initGroupStageUpdateTournamentForm("unittestId", "33", model);
        Assert.assertEquals("tournament/edit-groupstage-tournament", view);
    }

    @Test
    public void initSecondStageUpdateTournamentForm() {
        Model model = new ExtendedModelMap();
        Tournament tournament = new Tournament("unittestId");
        tournament.setFinalsSetup(new FinalSetup());
        when(tournamentPlannerServiceMock.getTournament("unittestId")).thenReturn(tournament);
        String view = controller.initSecondStageUpdateTournamentForm("unittestId", model);
        Assert.assertEquals("tournament/edit-secondstage-tournament", view);
    }
    
}