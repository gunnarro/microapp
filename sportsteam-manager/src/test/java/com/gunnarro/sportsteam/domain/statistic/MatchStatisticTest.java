package com.gunnarro.sportsteam.domain.statistic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gunnarro.sportsteam.domain.activity.Type.MatchTypesEnum;

public class MatchStatisticTest {

    @Test
    public void leagueStatistic() {
        MatchStatistic stat = new MatchStatistic(MatchTypesEnum.LEAGUE);
//        assertEquals("LEAGUE", stat.getMatchType().name());
        assertEquals(0, stat.getWon());
        assertEquals(0, stat.getDraw());
        assertEquals(0, stat.getLoss());
        assertEquals(0, stat.getGoalsAgainst());
        assertEquals(0, stat.getGoalsScored());
        assertEquals(0, stat.getNumberOfPlayedMatches());
        assertEquals(0, stat.getScores());
    }

}
