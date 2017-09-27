package com.gunnarro.sportsteam.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Cup;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.view.list.Item;
import com.gunnarro.sportsteam.service.SportsTeamService;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

public class CupControllerTest extends SpringTestSetup {

    private CupController controller;

    @Mock
    private SportsTeamService sportsTeamServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new CupController();
        controller.setSportsTeamService(sportsTeamServiceMock);
    }

    @Test
    public void listCups() throws Exception {
        when(sportsTeamServiceMock.getCups(1, "2014")).thenReturn(Arrays.asList(new Cup()));
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season(1, 1, 1));
        // assertNotNull(
        ModelAndView modelAndView = controller.listCups("2014");
        Assert.assertEquals("cup/list-cups-view", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        Assert.assertNotNull(modelAndView.getModel().get("season"));
        Assert.assertNotNull(modelAndView.getModel().get("cupList"));
    }

    @Test
    public void deleteCup() {
        when(sportsTeamServiceMock.deleteCup(1)).thenReturn(1);
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season(3, 1, 1));
        String view = controller.deleteCup(1);
        Assert.assertEquals("redirect:/listcups/3", view);
    }

    @Test
    public void viewCupForTeam() {
        Cup cup = new Cup();
        cup.setId(2);
        Team team = new Team();
        team.setName("team name");
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season());
        when(sportsTeamServiceMock.getTeam(1)).thenReturn(team);
        when(sportsTeamServiceMock.getCup(1)).thenReturn(cup);
        when(sportsTeamServiceMock.getCupSignedPlayerList(1, 1)).thenReturn(new ArrayList<Item>());
        ModelAndView view = controller.viewCupForTeam(1, 1, null);
        Assert.assertEquals("cup/view-cup-team", view.getViewName());
        Assert.assertEquals("team name", view.getModel().get("teamName"));
        Assert.assertNotNull(view.getModel().get("cup"));
        Assert.assertNotNull(view.getModel().get("playerList"));
    }

    @Test
    public void viewCup() {
        Cup cup = new Cup();
        cup.setId(2);
        Team team = new Team();
        team.setName("team name");
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season());
        when(sportsTeamServiceMock.getCup(1)).thenReturn(cup);
        when(sportsTeamServiceMock.getCupRegistreredTeamList(1)).thenReturn(new ArrayList<Item>());
        ModelAndView view = controller.viewCup(1, null);
        Assert.assertEquals("cup/view-cup", view.getViewName());
        Assert.assertNotNull(view.getModel().get("cup"));
        Assert.assertNotNull(view.getModel().get("teamList"));
    }

    @Test(expected = ApplicationException.class)
    public void viewCupError() {
        when(sportsTeamServiceMock.getCup(1)).thenReturn(null);
        controller.viewCup(1, null);
    }

    @Test
    public void initNewCupForm() {
        int teamId = 1;
        Team team = new Team();
        team.setId(teamId);
        team.setFkClubId(1);
        when(sportsTeamServiceMock.getTeam(teamId)).thenReturn(team);
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season());
        when(sportsTeamServiceMock.getTeams(1)).thenReturn(new ArrayList<Team>());
        String view = controller.initNewCupForm(new HashMap<String, Object>());
        Assert.assertEquals("cup/edit-cup", view);
    }
}