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
import com.gunnarro.sportsteam.domain.activity.Type;
import com.gunnarro.sportsteam.domain.party.Referee;
import com.gunnarro.sportsteam.service.SportsTeamService;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

public class RefereeControllerTest extends SpringTestSetup {

    @Mock
    private SportsTeamService sportsTeamServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void listReferees() throws Exception {
        int clubId = 1;
        RefereeController controller = new RefereeController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.getClub(clubId)).thenReturn(new Club(clubId, "UIL", "Bandy"));
        when(sportsTeamServiceMock.getReferees(clubId)).thenReturn(Arrays.asList(new Referee(1010)));
        ModelAndView modelAndView = controller.listReferees(clubId);
        Assert.assertEquals("referee/list-referees", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        String clubName = (String) modelAndView.getModel().get("clubName");
        Assert.assertEquals("UIL Bandy", clubName);
        List<Referee> list = (List<Referee>) modelAndView.getModel().get("refereeList");
        Assert.assertNotNull(list);
        Assert.assertEquals(1010, list.get(0).getId().intValue());
    }

    @Test
    public void viewRefree() {
        int refereeId = 11;
        RefereeController controller = new RefereeController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        Referee referee = new Referee();
        referee.setId(refereeId);
        when(sportsTeamServiceMock.getReferee(refereeId)).thenReturn(referee);
        ModelAndView modelAndView = controller.viewReferee(refereeId);
        Assert.assertEquals("referee/view-referee", modelAndView.getViewName());
        Referee r = (Referee) modelAndView.getModel().get("referee");
        Assert.assertNotNull(r);
        Assert.assertEquals(11, r.getId().intValue());
    }

    @Test
    public void newReferee() {
        int teamId = 1;
        Team team = new Team();
        team.setId(teamId);
        RefereeController controller = new RefereeController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.getTeam(teamId)).thenReturn(team);
        when(sportsTeamServiceMock.getTeamRoleTypes()).thenReturn(Arrays.asList(new Type()));
        String url = controller.initNewRefereeForm(new HashMap<String, Object>());
        Assert.assertEquals("referee/edit-referee", url);
    }

    @Ignore
    @Test
    public void updateReferee() {
        int teamId = 1;
        int refereeId = 11;
        Referee referee = new Referee(refereeId);
        referee.setFkTeamId(teamId);
        RefereeController controller = new RefereeController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.getReferee(refereeId)).thenReturn(referee);
        String url = controller.initUpdateRefereeForm(refereeId, null);
        Assert.assertEquals("referee/edit-referee", url);
    }

    @Test
    public void deleteReferee() {
        int refereeId = 11;
        int clubId = 1;
        Referee referee = new Referee(refereeId);
        referee.setFkClubId(clubId);
        RefereeController controller = new RefereeController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.getReferee(refereeId)).thenReturn(referee);
        when(sportsTeamServiceMock.deleteReferee(refereeId)).thenReturn(1);
        String url = controller.deleteReferee(refereeId);
        Assert.assertEquals("redirect:/listreferees/" + clubId, url);
    }

    @Test(expected=ApplicationException.class)
    public void deleteRefereeError() {
        int refereeId = 11;
        int teamId = 1;
        Referee referee = new Referee(refereeId);
        referee.setFkTeamId(teamId);
        RefereeController controller = new RefereeController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.deleteReferee(refereeId)).thenReturn(1);
        controller.deleteReferee(refereeId);
    }
}