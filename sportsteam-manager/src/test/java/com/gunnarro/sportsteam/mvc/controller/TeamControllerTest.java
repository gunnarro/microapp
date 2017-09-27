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

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.activity.Type;
import com.gunnarro.sportsteam.service.SportsTeamService;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

public class TeamControllerTest extends SpringTestSetup {

    @Mock
    private SportsTeamService sportsTeamServiceMock;

    private TeamController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new TeamController();
        controller.setSportsTeamService(sportsTeamServiceMock);
    }

    @Test
    public void listTeams() throws Exception {
        int clubId = 1;
        when(sportsTeamServiceMock.getClub(clubId)).thenReturn(new Club(clubId, "Uil", "Bandy"));
        when(sportsTeamServiceMock.getTeams(clubId)).thenReturn(Arrays.asList(new Team(1, "2003 team")));
        ModelAndView modelAndView = controller.listTeams(1);
        Assert.assertEquals("team/list-teams", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        String clubName = (String) modelAndView.getModel().get("clubName");
        Assert.assertEquals("Uil Bandy", clubName);
        List<Team> list = (List<Team>) modelAndView.getModel().get("teamList");
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("2003 team", list.get(0).getName());
    }

    @Test
    public void viewTeam() {
        int teamId = 22;
        when(sportsTeamServiceMock.getTeam(teamId)).thenReturn(new Team(teamId, "2003 team"));
        ModelAndView modelAndView = controller.viewTeam(teamId);
        Assert.assertEquals("team/view-team", modelAndView.getViewName());
        Team team = (Team) modelAndView.getModel().get("team");
        Assert.assertNotNull(team);
        Assert.assertEquals("2003 team", team.getName());
    }

    @Test(expected = ApplicationException.class)
    public void viewTeamError() {
        int teamId = 22;
        when(sportsTeamServiceMock.getTeam(teamId)).thenReturn(null);
        controller.viewTeam(222);
    }

    @Test
    public void newTeam() {
        int teamId = 1;
        Team team = new Team();
        team.setId(teamId);
        when(sportsTeamServiceMock.getTeam(teamId)).thenReturn(team);
        when(sportsTeamServiceMock.getPlayerStatusTypes()).thenReturn(Arrays.asList(new Type()));
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season(1,System.currentTimeMillis(), System.currentTimeMillis()));
        String url = controller.initNewTeamForm(new HashMap<String, Object>());
        Assert.assertEquals("team/edit-team", url);
    }

//    @Test(expected = ApplicationException.class)
//    public void newPlayerError() {
//        int teamId = 1;
//        Team team = new Team();
//        team.setId(teamId);
//        when(sportsTeamServiceMock.getPlayerStatusTypes()).thenReturn(Arrays.asList(new Type()));
//        controller.initNewTeamForm(new HashMap<String, Object>());
//    }

    @Ignore
    @Test
    public void updateTeam() {
        int teamId = 1;
        Team team = new Team();
        team.setId(teamId);
        when(sportsTeamServiceMock.getTeam(teamId)).thenReturn(team);
        String url = controller.initUpdateTeamForm(teamId, null);
        Assert.assertEquals("team/edit-team", url);
    }

    @Test
    public void deleteTeam() {
        int teamId = 1;
        Team team = new Team();
        team.setId(teamId);
        team.setFkClubId(2);
        when(sportsTeamServiceMock.getTeam(teamId)).thenReturn(team);
        when(sportsTeamServiceMock.deleteTeam(teamId)).thenReturn(1);
        String url = controller.deleteTeam(teamId);
        Assert.assertEquals("redirect:/listteams/" + team.getFkClubId(), url);
    }

    @Test(expected = ApplicationException.class)
    public void deleteTeamError() {
        int teamId = 11;
        when(sportsTeamServiceMock.deleteTeam(teamId)).thenReturn(1);
        controller.deleteTeam(teamId);
    }
}