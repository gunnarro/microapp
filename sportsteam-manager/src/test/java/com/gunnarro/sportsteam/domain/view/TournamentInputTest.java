package com.gunnarro.sportsteam.domain.view;

import org.junit.Assert;
import org.junit.Test;

public class TournamentInputTest {

    @Test
    public void parseTeamsTextToList() {
        TournamentInput input = new TournamentInput();
        Assert.assertEquals(2, input.getNumberOfGroups().intValue());
        input.setTeams("Team1\nTeam2\nTeam3\nTeam4");
        Assert.assertEquals("[Team1, Team2, Team3, Team4]", input.getTeamsAsList().toString());
        input.setTeamNames(input.getTeamsAsList());
        System.out.println(input.toString());
    }
}
