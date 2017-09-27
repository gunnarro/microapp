package com.gunnarro.sportsteam.domain.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Type.MatchTypesEnum;
import com.gunnarro.sportsteam.domain.party.Referee;

public class MatchTest {

    @SuppressWarnings("unchecked")
    @Test
    public void matchMultiValueMap() {
        // TEST MULTI VALUE MAP
        MultiMap matchMultiMap = new MultiValueMap();
        Assert.assertNotNull(matchMultiMap);

        List<Match> matchList = new ArrayList<Match>();
        matchList.add(new Match(DateUtils.addDays(new Date(), 1), "11"));
        matchList.add(new Match(DateUtils.addDays(new Date(), 1), "12"));
        matchList.add(new Match(DateUtils.addDays(new Date(), 1), "13"));
        matchList.add(new Match(DateUtils.addDays(new Date(), 1), "14"));

        matchList.add(new Match(DateUtils.addDays(new Date(), 2), "21"));
        matchList.add(new Match(DateUtils.addDays(new Date(), 2), "22"));
        matchList.add(new Match(DateUtils.addDays(new Date(), 2), "23"));
        matchList.add(new Match(DateUtils.addDays(new Date(), 2), "24"));

        for (Match m : matchList) {
            matchMultiMap.put(m.getStartDateEEEEDDMMMYYYY(), m);
        }

        // get all the set of keys
        Set<Map.Entry<String, List<Match>>> entrySet = matchMultiMap.entrySet();

        // Sort the entry set
        List<Map.Entry<String, List<Match>>> list = new ArrayList<Entry<String, List<Match>>>(entrySet);
        Collections.sort(list, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                String e1Key = ((Map.Entry<String, List<Match>>) o1).getKey();
                String e2Key = ((Map.Entry<String, List<Match>>) o2).getKey();
                return e1Key.compareTo(e2Key);
            }
        });

        // iterate through the key set and display key and values
        for (Map.Entry<String, List<Match>> entry : entrySet) {
            System.out.println("Key = " + entry.getKey());
            for (Match m : entry.getValue()) {
                System.out.println("Values = " + m.toString());
            }
        }
    }

    @Test
    public void testConstructor() {
        Match match = new Match(new Season(0, 0), new Date(System.currentTimeMillis() - 60000), new Team(""), new Team("homeTeam"), new Team("awayTeam"), "venue", new Referee(
                "firstname", "middlename", "lastname"));
        match.setMatchStatus(new MatchStatus("PLAYED"));
        assertNotNull(match.getStartDate());
        assertNotNull(match.getStartWeekDayName());
        // assertEquals("Sat",match.getStartWeekDayName());
        assertNull(match.getNumberOfGoalsHome());
        assertNull(match.getNumberOfGoalsAway());
        assertNull(match.getResult());
        assertEquals("PLAYED", match.getMatchStatus().getName());
//        assertTrue(match.isPlayed());
        assertEquals(0, match.getNumberOfSignedPlayers().intValue());
        assertTrue(match.isStartDateBeforeToDay());
    }

    @Test
    public void testConstructorPlayed() {
        Match match = new Match(1, new Season(0, 0), new Date(System.currentTimeMillis() - 60000), new Team(""), new Team("homeTeam"), new Team("awayTeam"), 4, 5, "venue",
                new Referee("firstname", "middlename", "lastname"), MatchTypesEnum.CUP);
        match.setMatchStatus(new MatchStatus("PLAYED"));
        assertEquals("PLAYED", match.getMatchStatus().getName());
        assertTrue(match.isPlayed());
        assertEquals(1, match.getId().intValue());
        assertEquals("homeTeam", match.getHomeTeam().getName());
        assertEquals("awayTeam", match.getAwayTeam().getName());
        assertEquals("homeTeam - awayTeam", match.getTeamVersus());
        assertEquals(4, match.getNumberOfGoalsHome().intValue());
        assertEquals(5, match.getNumberOfGoalsAway().intValue());
        assertEquals("4 - 5", match.getResult());
        assertEquals("CUP", match.getMatchType().getName());
        assertEquals(3, match.getMatchType().getId());
        assertEquals("Firstname Middlename Lastname".toUpperCase(), match.getReferee().getFullName());
    }

    @Ignore
    @Test
    public void testConstructorNotPlayedDateBefore() {
        Match match = new Match(1, new Season(0, 0), new Date(System.currentTimeMillis() - 60000), new Team(""), new Team("homeTeam"), new Team("awayTeam"), null, null, "venue",
                new Referee("firstname", "middlename", "lastname"), MatchTypesEnum.CUP);
        match.setStatus(new Status(null, "PLAYED"));
        assertTrue(match.isFinished());
        assertNull(match.getNumberOfGoalsHome());
        assertNull(match.getNumberOfGoalsAway());
        assertNull(match.getResult());
        assertFalse(match.isPlayed());
    }

    @Test
    public void testConstructorNotPlayedDateAfter() {
        Match match = new Match(1, new Season(0, 0), new Date(System.currentTimeMillis() + 60000), new Team(""), new Team("homeTeam"), new Team("awayTeam"), null, null, "venue",
                new Referee("firstname", "middlename", "lastname"), MatchTypesEnum.CUP);
        match.setStatus(new Status(null, "PLAYED"));
        assertFalse(match.isFinished());
        assertNull(match.getNumberOfGoalsHome());
        assertNull(match.getNumberOfGoalsAway());
        assertNull(match.getResult());
        assertFalse(match.isPlayed());
    }

    @Test
    public void getWinner() {
        Match match = new Match(1, new Season(0, 0), new Date(System.currentTimeMillis() + 60000), new Team(""), new Team("homeTeam"), new Team("awayTeam"), null, null, "venue",
                new Referee("firstname", "middlename", "lastname"), MatchTypesEnum.CUP);
        match.setNumberOfGoalsHome(2);
        match.setNumberOfGoalsAway(1);
        assertEquals("homeTeam", match.getWinnerTeamName());
        assertEquals("awayTeam", match.getLooserTeamName());
        match.setNumberOfGoalsHome(2);
        match.setNumberOfGoalsAway(3);
        assertEquals("awayTeam", match.getWinnerTeamName());
        assertEquals("homeTeam", match.getLooserTeamName());
        match.setNumberOfGoalsHome(1);
        match.setNumberOfGoalsAway(1);
        assertEquals("homeTeam/awayTeam", match.getWinnerTeamName());
        assertEquals("homeTeam/awayTeam", match.getLooserTeamName());
        
    }
}
