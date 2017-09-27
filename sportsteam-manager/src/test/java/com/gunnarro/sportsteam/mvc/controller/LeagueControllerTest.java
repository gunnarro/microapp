package com.gunnarro.sportsteam.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.league.League;
import com.gunnarro.sportsteam.domain.statistic.MatchStatistic;
import com.gunnarro.sportsteam.service.SportsTeamService;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

public class LeagueControllerTest extends SpringTestSetup {

    @Mock
    private SportsTeamService sportsTeamServiceMock;

    private LeagueController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new LeagueController();
        controller.setSportsTeamService(sportsTeamServiceMock);
    }

    @Test
    public void listLeagues() throws Exception {
        when(sportsTeamServiceMock.getLeagues()).thenReturn(Arrays.asList(new League()));
        ModelAndView modelAndView = controller.listLeagues();
        Assert.assertEquals("league/list-leagues", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        List<League> list = (List<League>) modelAndView.getModel().get("leagueList");
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void viewLeague() {
        int leagueId = 2;
        when(sportsTeamServiceMock.getLeague(leagueId)).thenReturn(new League(leagueId, "lillegutt"));
        ModelAndView modelAndView = controller.viewLeague(leagueId);
        Assert.assertEquals("league/view-league", modelAndView.getViewName());
        League league = (League) modelAndView.getModel().get("league");
        Assert.assertNotNull(league);
    }

    @Test
    public void viewLeagueTable() {
        int leagueId = 22;
        List<MatchStatistic> list = new ArrayList<MatchStatistic>();
        when(sportsTeamServiceMock.getLeague(leagueId)).thenReturn(new League());
        when(sportsTeamServiceMock.getLeagueTable(leagueId, "current")).thenReturn(list);
        ModelAndView modelAndView = controller.viewLeagueTable(leagueId, "current");
        Assert.assertEquals("league/view-league-table", modelAndView.getViewName());
        List<MatchStatistic> leagueStat = (List<MatchStatistic>) modelAndView.getModel().get("leagueStatistics");
        Assert.assertNotNull(leagueStat);
    }

    @Test(expected = ApplicationException.class)
    public void viewLeagueError() {
        int leagueId = 22;
        when(sportsTeamServiceMock.getLeague(leagueId)).thenReturn(null);
        controller.viewLeague(222);
    }

}