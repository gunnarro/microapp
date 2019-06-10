package com.gunnarro.dietmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gunnarro.tournament.domain.Team;
import com.gunnarro.tournament.domain.activity.Group;
import com.gunnarro.tournament.domain.activity.Match;
import com.gunnarro.tournament.domain.activity.MatchStatus;
import com.gunnarro.tournament.domain.activity.Tournament;
import com.gunnarro.tournament.domain.view.FinalSetup;
import com.gunnarro.tournament.domain.view.TournamentInput;
import com.gunnarro.tournament.service.TournamentPlannerService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
public class TournamentPlannerServiceTest {

    @Autowired
    @Qualifier("tournamentPlannerService")
    protected TournamentPlannerService service;

    @Test
    public void generateTournamentSingelCup6Teams2Groups() {
        List<String> teamNames = new ArrayList<String>();
        teamNames.add("lyn");
        teamNames.add("skeid");
        teamNames.add("røa");
        teamNames.add("sagene");
        teamNames.add("stabekk");
        teamNames.add("høvik");
        TournamentInput tournamentInput = new TournamentInput("andreas-tournament-2015", "single", teamNames);
        tournamentInput.setNumberOfGroups(2);
        Tournament tournament = service.generateTournament(tournamentInput);
        Assert.assertEquals(2, tournament.getGroups().size());
        Assert.assertEquals(3, tournament.getGroups().get(0).getTeamNames().size());
        Assert.assertEquals(3, tournament.getGroups().get(0).getMatches().size());
        Assert.assertEquals("winner-semi-final-1 - winner-semi-final-2", tournament.getFinalsSetup().getGoldFinal().getTeamVersus());
        // Update results
        tournament.getFinalsSetup().getQuarterFinal1().setNumberOfGoalsHome(1);
        tournament.getFinalsSetup().getQuarterFinal1().setNumberOfGoalsAway(2);
        tournament.getFinalsSetup().getQuarterFinal1().setMatchStatus(MatchStatus.createPlayed());

        tournament.getFinalsSetup().getQuarterFinal2().setNumberOfGoalsHome(3);
        tournament.getFinalsSetup().getQuarterFinal2().setNumberOfGoalsAway(2);
        tournament.getFinalsSetup().getQuarterFinal2().setMatchStatus(MatchStatus.createPlayed());

        tournament.getFinalsSetup().getQuarterFinal3().setNumberOfGoalsHome(0);
        tournament.getFinalsSetup().getQuarterFinal3().setNumberOfGoalsAway(2);
        tournament.getFinalsSetup().getQuarterFinal3().setMatchStatus(MatchStatus.createPlayed());

        tournament.getFinalsSetup().getQuarterFinal4().setNumberOfGoalsHome(2);
        tournament.getFinalsSetup().getQuarterFinal4().setNumberOfGoalsAway(4);
        tournament.getFinalsSetup().getQuarterFinal4().setMatchStatus(MatchStatus.createPlayed());
        
        tournament.getFinalsSetup().getSemiFinal1().setNumberOfGoalsHome(3);
        tournament.getFinalsSetup().getSemiFinal1().setNumberOfGoalsAway(4);
        tournament.getFinalsSetup().getSemiFinal1().setMatchStatus(MatchStatus.createPlayed());

        tournament.getFinalsSetup().getSemiFinal2().setNumberOfGoalsHome(1);
        tournament.getFinalsSetup().getSemiFinal2().setNumberOfGoalsAway(3);
        tournament.getFinalsSetup().getSemiFinal2().setMatchStatus(MatchStatus.createPlayed());

        service.updateFinalSetup(tournament.getFinalsSetup());

       // Assert.assertEquals("gr2_2 - gr1_2", tournament.getFinalsSetup().getBronseFinal().getTeamVersus());
       // Assert.assertEquals("gr1_1 - gr2_2", tournament.getFinalsSetup().getGoldFinal().getTeamVersus());

    }

    @Test
    public void generateTournamentSingelCup12Teams4Groups() {
        List<String> teamNames = new ArrayList<String>();
        teamNames.add("lyn");
        teamNames.add("skeid");
        teamNames.add("røa");
        teamNames.add("sagene");
        teamNames.add("stabekk");
        teamNames.add("høvik");
        teamNames.add("lyn2");
        teamNames.add("skeid2");
        teamNames.add("røa2");
        teamNames.add("sagene2");
        teamNames.add("stabekk2");
        teamNames.add("høvik2");
        TournamentInput tournamentInput = new TournamentInput("andreas-tournament-2015", "single", teamNames);
        tournamentInput.setNumberOfGroups(4);
        Tournament tournament = service.generateTournament(tournamentInput);
        Assert.assertEquals(4, tournament.getGroups().size());
        Assert.assertEquals(3, tournament.getGroups().get(0).getTeamNames().size());
        Assert.assertEquals(3, tournament.getGroups().get(0).getMatches().size());
        Assert.assertEquals("winner-semi-final-1 - winner-semi-final-2", tournament.getFinalsSetup().getGoldFinal().getTeamVersus());
    }

    @Test
    public void generateTournamentSingelCup16Teams4Groups() {
        List<String> teamNames = new ArrayList<String>();
        teamNames.add("lyn");
        teamNames.add("skeid");
        teamNames.add("røa");
        teamNames.add("sagene");
        teamNames.add("stabekk");
        teamNames.add("høvik");
        teamNames.add("lyn2");
        teamNames.add("skeid2");
        teamNames.add("røa2");
        teamNames.add("sagene2");
        teamNames.add("stabekk2");
        teamNames.add("høvik2");
        teamNames.add("solberg");
        teamNames.add("drammen");
        teamNames.add("hamar");
        teamNames.add("skiold");
        TournamentInput tournamentInput = new TournamentInput("andreas-tournament-2015", "single", teamNames);
        tournamentInput.setNumberOfGroups(4);
        Tournament tournament = service.generateTournament(tournamentInput);
        Assert.assertEquals(4, tournament.getGroups().size());
        Assert.assertEquals(4, tournament.getGroups().get(0).getTeamNames().size());
        Assert.assertEquals(6, tournament.getGroups().get(0).getMatches().size());
        Assert.assertEquals("winner-semi-final-1 - winner-semi-final-2", tournament.getFinalsSetup().getGoldFinal().getTeamVersus());

        for (Group g : tournament.getGroups()) {
            System.out.println(g.getName());
            System.out.println(g.getTeamNames());
            for (int j = 0; j < g.getMatches().size(); j++) {
                System.out.println("    " + j + ". " + g.getMatches().get(j).getTeamVersus());
            }
            System.out.print("");
        }
    }

    @Test
    public void scheduleMatches() {
        List<Match> matches = new ArrayList<Match>();
        matches.add(new Match(null, new Team("team_1"), new Team("tam_2"), "venue_1"));
        matches.add(new Match(null, new Team("team_1"), new Team("tam_3"), "venue_1"));
        matches.add(new Match(null, new Team("team_1"), new Team("tam_4"), "venue_1"));
        matches.add(new Match(null, new Team("team_2"), new Team("tam_3"), "venue_1"));
        service.scheduleMatches(matches, 20, 5);
        for (Match m : matches) {
            System.out.println(m.getStartDate() + " " + m.getTeamVersus());
        }
    }

    @Test
    public void generateFinalsSetup() {
        FinalSetup finalSetup = service.generateFinalsSetup("tournamentId", 4);
        Assert.assertEquals("tournamentId", finalSetup.getTournamentId());
        Assert.assertNotNull(finalSetup.getQuarterFinal1().getTeamVersus());
        Assert.assertEquals("QUARTER_FINAL_1", finalSetup.getQuarterFinal1().getTournamentPhase());
        Assert.assertNotNull(finalSetup.getQuarterFinal2().getTeamVersus());
        Assert.assertEquals("QUARTER_FINAL_2", finalSetup.getQuarterFinal2().getTournamentPhase());
        Assert.assertNotNull(finalSetup.getQuarterFinal3().getTeamVersus());
        Assert.assertEquals("QUARTER_FINAL_3", finalSetup.getQuarterFinal3().getTournamentPhase());
        Assert.assertNotNull(finalSetup.getQuarterFinal4().getTeamVersus());
        Assert.assertEquals("QUARTER_FINAL_4", finalSetup.getQuarterFinal4().getTournamentPhase());
        Assert.assertNotNull(finalSetup.getSemiFinal1().getTeamVersus());
        Assert.assertEquals("SEMI_FINAL_1", finalSetup.getSemiFinal1().getTournamentPhase());
        Assert.assertNotNull(finalSetup.getSemiFinal2().getTeamVersus());
        Assert.assertEquals("SEMI_FINAL_2", finalSetup.getSemiFinal2().getTournamentPhase());
        Assert.assertNotNull(finalSetup.getBronseFinal().getTeamVersus());
        Assert.assertEquals("BRONSE_FINAL", finalSetup.getBronseFinal().getTournamentPhase());
        Assert.assertNotNull(finalSetup.getGoldFinal().getTeamVersus());
        Assert.assertEquals("winner-semi-final-1 - winner-semi-final-2", finalSetup.getGoldFinal().getTeamVersus());
        Assert.assertEquals("GOLD_FINAL", finalSetup.getGoldFinal().getTournamentPhase());
    }
}
