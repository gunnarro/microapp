package com.gunnarro.sportsteam.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.activity.Type;
import com.gunnarro.sportsteam.domain.party.Player;
import com.gunnarro.sportsteam.domain.statistic.Statistic;
import com.gunnarro.sportsteam.service.SportsTeamService;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

public class PlayerControllerTest extends SpringTestSetup {

    @Mock
    private SportsTeamService sportsTeamServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void listPlayers() throws Exception {
        int teamId = 1;
        PlayerController controller = new PlayerController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.getPlayers(teamId)).thenReturn(Arrays.asList(new Player(1010)));
        when(sportsTeamServiceMock.getTeam(teamId)).thenReturn(new Team());
        ModelAndView modelAndView = controller.listPlayers(teamId);
        Assert.assertEquals("player/list-players", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        Assert.assertNotNull(modelAndView.getModel().get("team"));
        List<Player> list = (List<Player>) modelAndView.getModel().get("playerList");
        Assert.assertNotNull(list);
        Assert.assertEquals(1010, list.get(0).getId().intValue());
    }

    @Test
    public void viewPlayer() {
        int playerId = 11;
        int teamId = 1;
        Player player = new Player(playerId);
        player.setFkTeamId(teamId);
        PlayerController controller = new PlayerController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.getPlayer(playerId)).thenReturn(player);
        when(sportsTeamServiceMock.getPlayerStatistic(teamId, playerId, 1)).thenReturn(new Statistic(null));
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season(1, 1, 1));
        ModelAndView modelAndView = controller.viewPlayer(playerId);
        Assert.assertEquals("player/view-player", modelAndView.getViewName());
        Player p = (Player) modelAndView.getModel().get("player");
        Assert.assertNotNull(p);
        Assert.assertEquals(playerId, p.getId().intValue());
        Assert.assertNotNull(modelAndView.getModel().get("playerStatistic"));
    }

    @Test
    public void newPlayer() {
        int teamId = 1;
        Team team = new Team();
        team.setId(teamId);
        PlayerController controller = new PlayerController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.getTeam(teamId)).thenReturn(team);
        when(sportsTeamServiceMock.getPlayerStatusTypes()).thenReturn(Arrays.asList(new Type()));
        String url = controller.initNewPlayerForm(teamId, new HashMap<String, Object>());
        Assert.assertEquals("player/edit-player", url);
    }

    @Test(expected = ApplicationException.class)
    public void newPlayerError() {
        int teamId = 1;
        Team team = new Team();
        team.setId(teamId);
        PlayerController controller = new PlayerController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.getPlayerStatusTypes()).thenReturn(Arrays.asList(new Type()));
        controller.initNewPlayerForm(teamId, new HashMap<String, Object>());
    }

    @Ignore
    @Test
    public void updatePlayer() {
        int teamId = 1;
        int playerId = 11;
        Player player = new Player(playerId);
        player.setFkTeamId(teamId);
        PlayerController controller = new PlayerController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.getPlayer(playerId)).thenReturn(player);
        String url = controller.initUpdatePlayerForm(playerId, null);
        Assert.assertEquals("player/edit-player", url);
    }

    @Test
    public void deletePlayer() {
        int playerId = 11;
        int teamId = 1;
        Player player = new Player(playerId);
        player.setFkTeamId(teamId);
        PlayerController controller = new PlayerController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.getPlayer(playerId)).thenReturn(player);
        when(sportsTeamServiceMock.deletePlayer(playerId)).thenReturn(1);
        String url = controller.deletePlayer(playerId);
        Assert.assertEquals("redirect:/listplayers/" + teamId, url);
    }

    @Test(expected = ApplicationException.class)
    public void deletePlayerError() {
        int playerId = 11;
        int teamId = 1;
        Player player = new Player(playerId);
        player.setFkTeamId(teamId);
        PlayerController controller = new PlayerController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.deletePlayer(playerId)).thenReturn(1);
        controller.deletePlayer(playerId);
    }
}