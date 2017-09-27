package com.gunnarro.sportsteam.utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UtilityTest {

    @Ignore
    @Test
    public void datePeriodeInfo() {
        assertEquals("Week 19, Mon 9 - Sun 15 May 2016", Utility.getPeriodInfo("week", 0));
    }
    
    @Ignore
    @Test
    public void readDietImageFiles() {
        try {
//            File folder = new ClassPathResource("static/diet").getFile();
//            File[] fileList = folder.listFiles();
            
//            final DefaultResourceLoader loader = new DefaultResourceLoader(); 
//            Resource resource = loader.getResource("src/main/webapp/resources/static/diet");        
            
            File file = new File(new File(".").getAbsolutePath() + "resources/static/diet");
            FileSystemResource resource = new FileSystemResource("resources/static/diet");
            System.out.println(resource.getFile().list());
            System.out.println(file.getPath());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void validateInput() {
        assertTrue(Utility.validateValue("23:59", Utility.REGEXP_TIME_24));
        assertFalse(Utility.validateValue("2359", Utility.REGEXP_TIME_24));
        assertFalse(Utility.validateValue("23:5", Utility.REGEXP_TIME_24));
        assertTrue(Utility.validateValue("02:59", Utility.REGEXP_TIME_24));
        assertTrue(Utility.validateValue("00:00", Utility.REGEXP_TIME_24));
        // assertTrue(Utility.validateValue("10.10.2014", Utility.REGEXP_DATE));
        // assertTrue(Utility.validateValue("10.10.2014 23:59",
        // Utility.REGEXP_DATE_TIME));
    }

    @Test
    public void timeToDate() {
//        assertEquals("Sunday, 17. Jan 2016", Utility.formatTime(System.currentTimeMillis(), "EEEE, dd. MMM yyyy"));
        assertNotNull(Utility.timeToDate("23.12.2014 23:00", "dd.MM.yyyy HH:mm"));
        assertNotNull(Utility.timeToDate("23.12.2014 23:00", null));
        assertNotNull(Utility.timeToDate("2014-11-01", "yyyy-MM-dd"));
        assertNull(Utility.timeToDate("23.12.2014", null));
        assertNull(Utility.timeToDate(null, null));
        assertNull(Utility.timeToDate("", null));
        assertNull(Utility.timeToDate("343434", null));
        assertNotNull(Utility.timeToDate("20150513184500", "yyyyMMddhhmmss"));
        // assertEquals("",Utility.formatTime(System.currentTimeMillis(),
        // "E d MMM yyyy"));
        // assertEquals("23.12.2014 23:00",Utility.timeToDate("23.12.2014 23:00",
        // "dd.MM.yyyy HH:mm"));
        // assertEquals("23.12.2014 23:00",Utility.formatTime(System.currentTimeMillis(),
        // "EEE dd.MM"));
    }

    @Test
    public void roundToTime() {
        String dateAndTime = Utility.formatTime(Utility.roundToClosestHour(getFixedDate()).getTime(), Utility.DATE_TIME_PATTERN);
        assertEquals("23.04.2014 09:00", dateAndTime);
    }

    @Test
    public void addMinutes() {
        String dateAndTime = Utility.formatTime(Utility.addMinutes(getFixedDate(), 91).getTime(), Utility.DATE_TIME_PATTERN);
        assertEquals("23.04.2014 09:31", dateAndTime);

        dateAndTime = Utility.formatTime(Utility.addMinutes(getFixedDate(), 58).getTime(), Utility.DATE_TIME_PATTERN);
        assertEquals("23.04.2014 08:58", dateAndTime);

        dateAndTime = Utility.formatTime(Utility.addMinutes(getFixedDate(), 61).getTime(), Utility.DATE_TIME_PATTERN);
        assertEquals("23.04.2014 09:01", dateAndTime);
    }

    private Date getFixedDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2014);
        cal.set(Calendar.MONTH, 3);
        cal.set(Calendar.DAY_OF_MONTH, 23);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.HOUR_OF_DAY, 8);
        return cal.getTime();
    }

    /**
     * Permutations without Repetition: Pick 7 out of 9 players Permutations =
     * 9*8*7*6*5*4*3 =
     */
    // @Ignore
    @Test
    public void distributePlayersPrMatch() {
        int numberOfPlayers = 7;
        String[] players = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
        String[] matches = new String[] { "1", "2", "3", "4" };
        int playerMaxMatches = 0;

        for (String player : players) {
            playerMatchDistribution.put(player, new Integer(0));
        }

        System.out.println("Players total m x p = " + (matches.length * numberOfPlayers));
        System.out.println("Rest m mod t = " + (matches.length * numberOfPlayers) % players.length);

        if (((matches.length * numberOfPlayers) % players.length) == 0) {
            playerMaxMatches = (matches.length * numberOfPlayers) / players.length;
        } else {
            // error
        }
        playerMaxMatches = (matches.length * numberOfPlayers) / players.length;
        System.out.println("Player Max matches: " + playerMaxMatches);

        Random randPlayer = new Random();
        for (int j = 0; j < matches.length; j++) {
            Match match = new Match(matches[j]);
            for (int i = 0; i < numberOfPlayers; i++) {
                String playerName = findPlayerLowMacth();// players[randPlayer.nextInt(players.length)];
                // check number of matches for this player
                Integer m = getPlayerSignMatches(playerName);
                int iterations = 0;
                while (iterations <= players.length && !match.addPlayer(playerName, m < playerMaxMatches)) {
                    playerName = players[randPlayer.nextInt(players.length)];
                    m = getPlayerSignMatches(playerName);
                    // Need a break out
                    iterations++;
                }
                updatePlayer(playerName);
                // System.out.println("added:" + playerName + " " + m);
            }
            System.out.println(match.toString());
        }
        System.out.print(playerMatchDistribution);
    }

    private Map<String, Integer> playerMatchDistribution = new HashMap<String, Integer>();

    public String findPlayerLowMacth() {
        int low = 1000;
        String name = null;
        for (String key : playerMatchDistribution.keySet()) {
            Integer m = playerMatchDistribution.get(key);
            if (m < low) {
                low = m;
                name = key;
            }
        }
        return name;
    }

    private Integer getPlayerSignMatches(String name) {
        return playerMatchDistribution.containsKey(name) ? playerMatchDistribution.get(name) : 0;
    }

    private boolean updatePlayer(String name) {
        if (!playerMatchDistribution.containsKey(name)) {
            playerMatchDistribution.put(name, new Integer(1));
        } else {
            Integer v = playerMatchDistribution.get(name);
            Integer n = v + 1;
            playerMatchDistribution.put(name, n);
        }
        return true;
    }

    @Test
    public void getFistDayInCurrentWeek() {
        Iterator<Calendar> itr = DateUtils.iterator(new Date(), DateUtils.RANGE_WEEK_MONDAY);
        System.out.println(((Calendar) itr.next()).getTime());

        Date lastDay = null;
        while (itr.hasNext()) {
            Calendar gCal = (Calendar) itr.next();
            lastDay = gCal.getTime();
        }
        System.out.println(lastDay);
    }

    @Test
    public void getWeekInfo() {
        System.out.println(Utility.getWeekInfo(new Date()));
        assertNotNull(Utility.getWeekInfo(new Date()));
    }

    @Test
    public void validate24H() {
        assertFalse(Utility.validateTime24H(null));
        assertFalse(Utility.validateTime24H(""));
        assertTrue(Utility.validateTime24H("23:45"));
        assertTrue(Utility.validateTime24H("3:45"));
        assertTrue(Utility.validateTime24H("01:45"));
        assertTrue(Utility.validateTime24H("00:00"));
        assertFalse(Utility.validateTime24H("23-45"));
        assertFalse(Utility.validateTime24H("23:45:34"));
    }

    @Test
    public void hashPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        System.out.println("pwd for pappa: " + passwordEncoder.encode("guro66"));
        System.out.println("pwd for mamma: " + passwordEncoder.encode("sipa66"));
        System.out.println("pwd for pepilie: " + passwordEncoder.encode("empa02"));
        String encodedPassword = passwordEncoder.encode("ABcd1212");
        assertTrue(!encodedPassword.equals("ABcd1212"));
        assertTrue(passwordEncoder.matches("ABcd1212", encodedPassword));
    }

    @Ignore
    @Test
    public void encodePwd() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(13);
        System.out.println("Encoded: " + passwordEncoder.encode("guest"));
    }

    private class Match {
        String name;
        List<String> players = new ArrayList<String>();

        public Match(String name) {
            this.name = name;
        }

        public boolean addPlayer(String name, boolean notMaxNumberOfMatches) {
            // System.out.println(name + " " + notMaxNumberOfMatches);
            if (!players.contains(name) && notMaxNumberOfMatches) {
                return players.add(name);
            }
            return false;
        }

        @Override
        public String toString() {
            Collections.sort(players);
            return name + ": " + players;
        }
    }
}
