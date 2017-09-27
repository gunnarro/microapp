package com.gunnarro.sportsteam.domain.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.gunnarro.sportsteam.domain.activity.Activity.ActivityTypesEnum;

public class CupTest {

    @Test
    public void testConstructor() {
        Cup cup = new Cup(new Season(System.currentTimeMillis(), System.currentTimeMillis()), new Date(System.currentTimeMillis()), "cup name", "club name", "venue", new Date());
        assertEquals(ActivityTypesEnum.Cup.name(), cup.getName());
        assertEquals(ActivityTypesEnum.Cup.name(), cup.getType());
        assertEquals("2016-2016", cup.getSeason().getPeriod());
        assertEquals("club name", cup.getClubName());
        assertEquals("cup name", cup.getCupName());
        assertEquals("venue", cup.getVenue());
        assertNotNull(cup.getStartDate());
        assertNotNull(cup.getEndDate());
        assertNull(cup.getEmail());
        assertNull(cup.getHomePage());
        assertEquals(0, cup.getNumberOfSignedPlayers().intValue());
        assertEquals(0, cup.getNumberOfRegistreredTeams().intValue());
        // assertEquals(ActivityStatusEnum.BEGIN, cup.getStatus());
        assertFalse(cup.isFinished());
    }

    @Test
    public void startDateInit() {
        Cup cup = new Cup();
        assertNotNull(cup.getStartDate());
        assertNotNull(cup.getEndDate());
        assertNotNull(cup.getDeadlineDate());
    }

    @Test
    public void generateMatchPermutations() {
        List<String> teamNames = new ArrayList<String>();
        teamNames.add("lyn");
        teamNames.add("skeid");
        teamNames.add("røa");
        teamNames.add("sagene");
        teamNames.add("stabekk");
        teamNames.add("høvik");

        List<String> permutations = new ArrayList<String>();
        // Product role
        int numberOfProducts = teamNames.size() * (teamNames.size() - 1);
        int numberOfPermutations = fakultet(teamNames.size());
        System.out.println("Fakultet:" + numberOfPermutations);
        int n = 0;
        for (int i = 0; i < numberOfPermutations; i++) {
            if (i < teamNames.size()) {
                for (int j = 0; j < teamNames.size(); j++) {
                    // Skip when name are equals
                    if (teamNames.get(i) != teamNames.get(j)) {
                        // System.out.println("generated match: " +
                        // teamNames.get(i) + teamNames.get(j));
                        n++;
                        // One way permutation only, ie if contains AB
                        // combination, then skip BA combination
                        if (!permutations.contains(flip(teamNames.get(i), teamNames.get(j)))) {
                            permutations.add(teamNames.get(i) + "-" + teamNames.get(j));
                            System.out.println("generated match nr. " + (permutations.size() - 1) + " " + permutations.get(permutations.size() - 1));
                        } else {// to way

                        }
                    }
                }
            }
        }
        System.out.println("n=" + n);
        System.out.println("f=" + numberOfPermutations);
        System.out.println("p=" + numberOfProducts);

        System.out.println("permutations=" + permutations);
        System.out.println("permutations=" + permutations.size());

        if (verifyNumberOfMatches(permutations, teamNames.size())) {
            generateGroups(permutations, teamNames.size());
        }

        // Split into number of
        // in minutes
        int period = 600;
    }

    private boolean verifyNumberOfMatches(List<String> matches, int numberOfTeams) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String match : matches) {
            String[] m = match.split("-");
            // Home team
            updateMap(map, m[0]);
            // Away team
            updateMap(map, m[1]);
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() != numberOfTeams - 1) {
                System.out.println("ERROR Validation failed, number of matches per team is not equal to " + (numberOfTeams - 1) + ": " + entry.toString());
            }
        }
        System.out.println("Number of matches         : " + matches.size());
        System.out.println("Number of Teams           : " + map.size());
        System.out.println("Number of matches per Team: " + map.entrySet().iterator().next().getValue());
        System.out.println("Number of matches per team verified OK");
        return true;
    }

    private void updateMap(Map<String, Integer> map, String key) {
        if (!map.containsKey(key)) {
            map.put(key, new Integer(1));
        } else {
            Integer value = map.get(key);
            map.put(key, new Integer(value + 1));
        }
    }

    public void generateGroups(List<String> matches, int numberOfTeams) {
        Map<String, List<String>> groupMap = new HashMap<String, List<String>>();
        int numberOfGroups = 2;
        int numberOfMatchesPerGroup = matches.size() / numberOfGroups;
        for (int n = 0; n < numberOfGroups; n++) {
            List<String> gm = matches.subList(n * numberOfMatchesPerGroup, numberOfMatchesPerGroup * (n + 1));
            // for (int i=0; i<numberOfMatchesPerGroup; i++) {
            // gm.add(matches.get(i));
            // }
            groupMap.put("group_" + n, gm);
        }

        for (Map.Entry<String, List<String>> entry : groupMap.entrySet()) {
            System.out.println(entry.toString());
        }
    }

    public String flip(String a, String b) {
        return b + "-" + a;
    }

    public static int fakultet(int maxTall) {
        int nFak = 1;
        for (int tall = 1; tall <= maxTall; tall++) {
            nFak = nFak * tall;
        }
        return nFak;
    }

    public Map<String, String> decode(List<String> names) {
        String[] alpabeth = new String[] { "A", "B", "C", "D", "E", "F" };
        Map<String, String> decode = new HashMap<String, String>();
        int i = 0;
        for (String name : names) {
            decode.put(alpabeth[i], name);
            i++;
        }
        return decode;
    }
}
