package com.gunnarro.sportsteam.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Match;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.activity.Status;
import com.gunnarro.sportsteam.domain.activity.Type;
import com.gunnarro.sportsteam.domain.league.League;
import com.gunnarro.sportsteam.domain.party.Player;
import com.gunnarro.sportsteam.service.SportsTeamService;
import com.gunnarro.sportsteam.utility.Utility;

public class MatchControllerTest extends SpringTestSetup {

    @Mock
    private SportsTeamService sportsTeamServiceMock;

    private MatchController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new MatchController();
        controller.setSportsTeamService(sportsTeamServiceMock);
    }

    @Test
    public void listMatchesForClub() throws Exception {
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season());
        when(sportsTeamServiceMock.getMatchesForClub(202, "2014-2015")).thenReturn(Arrays.asList(new Match()));
        ModelAndView modelAndView = controller.listMatchesForClub(202, "2014-2015");
        Assert.assertEquals("match/list-matches-view", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        Assert.assertNotNull(modelAndView.getModel().get("season"));
        List<Match> list = (List<Match>) modelAndView.getModel().get("matchList");
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void listMatchesForTeam() throws Exception {
        League league = new League(1, "Lillegutt");
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season());
        when(sportsTeamServiceMock.getLeague("BANDY", "Lillegutt")).thenReturn(league);
        when(sportsTeamServiceMock.getMatchesByTeamName("ullevål", league, "2014")).thenReturn(Arrays.asList(new Match()));
        when(sportsTeamServiceMock.getLeagueTypes(1)).thenReturn(Arrays.asList(new Type()));
        ModelAndView modelAndView = controller.listMatchesByTeamName("ullevål", league.getName(), "2014");
        Assert.assertEquals("match/list-matches-view", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        Assert.assertNotNull(modelAndView.getModel().get("season"));
        List<Match> list = (List<Match>) modelAndView.getModel().get("matchList");
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void listMatchesByLeague() throws Exception {
        League league = new League(1, "Lillegutt");
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season());
        when(sportsTeamServiceMock.getLeague(1)).thenReturn(league);
        when(sportsTeamServiceMock.getMatchesByLeague(league, "2014")).thenReturn(new Match[] { new Match() }); // .thenReturn(Arrays.asList(new
                                                                                                                // Match()));
        ModelAndView modelAndView = controller.listMatchesByLeague(1, "2014");
        Assert.assertEquals("match/list-matches-view", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        Assert.assertNotNull(modelAndView.getModel().get("season"));
        Assert.assertNotNull(modelAndView.getModel().get("leagueTypes"));
        Assert.assertEquals("Lillegutt", modelAndView.getModel().get("selectedLeagueName"));
        Match[] list = (Match[]) modelAndView.getModel().get("matchList");
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.length);
    }

    @Test
    public void searchMatchesAll() throws Exception {
        String fromDate = "2015-01-01";
        String toDate = "2015-01-30";
        League league = new League(1, "Lillegutt");
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season());
        when(sportsTeamServiceMock.getLeague(1)).thenReturn(league);
        when(sportsTeamServiceMock.searchMatches("2014-2015", Utility.timeToDate(fromDate, "yyyy-MM-dd"), Utility.timeToDate(toDate, "yyyy-MM-dd")))
                .thenReturn(new Match[] { new Match() });// .thenReturn(Arrays.asList(new
                                                         // Match()));
        ModelAndView modelAndView = controller.searchMatches(0, "2014-2015", null, null, fromDate, toDate);
        Assert.assertEquals("match/list-matches-view", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        Assert.assertNotNull(modelAndView.getModel().get("season"));
        Assert.assertNotNull(modelAndView.getModel().get("leagueTypes"));
        Assert.assertEquals("All Leagues", modelAndView.getModel().get("selectedLeagueName"));
        Assert.assertEquals(null, modelAndView.getModel().get("selectedfilterBy"));
        Assert.assertEquals(fromDate, modelAndView.getModel().get("selectedFromDate"));
        Assert.assertEquals(toDate, modelAndView.getModel().get("selectedToDate"));
        Match[] list = (Match[]) modelAndView.getModel().get("matchList");
        Assert.assertNotNull(list);
        // Assert.assertEquals(1, list.size());
    }

    @Test
    public void searchMatchesUpcoming() throws Exception {
        League league = new League(1, "Lillegutt");
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season());
        when(sportsTeamServiceMock.getLeague(1)).thenReturn(league);

        Date fromDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.add(Calendar.DATE, 7);
        Date toDate = cal.getTime();
        when(sportsTeamServiceMock.searchMatches("2014-2015", fromDate, toDate)).thenReturn(new Match[] { new Match() });// thenReturn(Arrays.asList(new
                                                                                                                         // Match()));
        ModelAndView modelAndView = controller.searchMatches(0, "2014-2015", null, "upcoming", null, null);
        Assert.assertEquals("match/list-matches-view", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        Assert.assertNotNull(modelAndView.getModel().get("season"));
        Assert.assertNotNull(modelAndView.getModel().get("leagueTypes"));
        Assert.assertEquals("All Leagues", modelAndView.getModel().get("selectedLeagueName"));
        Assert.assertEquals("upcoming", modelAndView.getModel().get("selectedfilterBy"));
        Assert.assertNotNull(modelAndView.getModel().get("selectedFromDate"));
        Assert.assertNotNull(modelAndView.getModel().get("selectedToDate"));
        Match[] list = (Match[]) modelAndView.getModel().get("matchList");
        // Assert.assertNotNull(list);
        // Assert.assertEquals(1, list.size());
    }

    @Test
    public void viewMatch() {
        int matchId = 11;
        Match match = new Match();
        match.setId(matchId);
        match.setStatus(new Status(1, "NOT PLAYED"));
        match.setTeam(new Team(1, "2003 team"));
        when(sportsTeamServiceMock.getMatch(matchId)).thenReturn(match);
        when(sportsTeamServiceMock.getMatchSignedPlayerList(match.getTeam().getId(), matchId)).thenReturn(null);
        ModelAndView modelAndView = controller.viewMatch(matchId, new HashMap<String, Object>());
        Assert.assertEquals("match/view-match", modelAndView.getViewName());
        Match m = (Match) modelAndView.getModel().get("match");
        Assert.assertNotNull(m);
        Assert.assertEquals("NOT PLAYED", m.getStatus().getName());
        List<Player> list = (List<Player>) modelAndView.getModel().get("playerList");
        Assert.assertNull(list);
    }

    @Test
    public void sortDateString() {
        List<String> list = new ArrayList<String>();
        list.add("Friday, 09. Oct 2015");
        list.add("Friday, 12. Jun 2015");
        list.add("Friday, 24. Apr 2015");
        list.add("Friday, 04. Sep 2015");
        list.add("Friday, 11. Sep 2015");
        list.add("Friday, 18. Sep 2015");
        Collections.sort(list);
        for (String date : list) {
            System.out.println(date);
        }
    }

}