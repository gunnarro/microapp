package com.gunnarro.sportsteam.domain.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Activity.ActivityStatusEnum;
import com.gunnarro.sportsteam.domain.activity.Activity.ActivityTypesEnum;
import com.gunnarro.sportsteam.utility.Utility;

public class TrainingTest {

    @Test
    public void testConstructor() {
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.YEAR, 2014);
        startTime.set(Calendar.MONTH, 1);
        startTime.set(Calendar.DAY_OF_MONTH, 1);
        startTime.set(Calendar.HOUR_OF_DAY, 16);
        startTime.set(Calendar.MILLISECOND, 0);
        startTime.set(Calendar.SECOND, 0);
        startTime.set(Calendar.MINUTE, 0);
        long endTime = startTime.getTimeInMillis() + 2 * 60 * 60 * 1000;
        Training training = new Training(new Season(System.currentTimeMillis(), System.currentTimeMillis()), startTime.getTime(), new Date(endTime), new Team(
                "team name"), "venue");
        assertEquals(ActivityTypesEnum.Training.name(), training.getName());
        assertEquals(ActivityTypesEnum.Training.name(), training.getType());
        assertEquals("2016-2016", training.getSeason().getPeriod());
        assertEquals("01.02.2014 04:00", Utility.formatTime(training.getStartDate().getTime(), "dd.MM.yyyy hh:mm"));
        assertEquals("01.02.2014 06:00", Utility.formatTime(training.getEndDate().getTime(), "dd.MM.yyyy hh:mm"));
        assertNull(training.getStatus());
        assertEquals("team name", training.getTeam().getName());
        assertEquals("venue", training.getVenue());
        assertTrue(training.isFinished());
        assertEquals(0, training.getNumberOfSignedPlayers().intValue());
    }

    @Test
    public void notFinished() {
        Calendar startTime = Calendar.getInstance();
//        startTime.set(Calendar.YEAR, 2016);
//        startTime.set(Calendar.MONTH, 1);
//        startTime.set(Calendar.DAY_OF_MONTH, 1);
//        startTime.set(Calendar.HOUR_OF_DAY, 16);
//        startTime.set(Calendar.MILLISECOND, 0);
//        startTime.set(Calendar.SECOND, 0);
//        startTime.set(Calendar.MINUTE, 0);
        long endTime = startTime.getTimeInMillis() + 2 * 60 * 60 * 1000;
        Training training = new Training(new Season(0, 0), startTime.getTime(), new Date(endTime), new Team("team name"), "venue");
        assertFalse(training.isFinished());
    }

    @Test
    public void finished() {
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.YEAR, 2014);
        startTime.set(Calendar.MONTH, 1);
        startTime.set(Calendar.DAY_OF_MONTH, 1);
        startTime.set(Calendar.HOUR_OF_DAY, 16);
        startTime.set(Calendar.MILLISECOND, 0);
        startTime.set(Calendar.SECOND, 0);
        startTime.set(Calendar.MINUTE, 0);
        long endTime = startTime.getTimeInMillis() + 2 * 60 * 60 * 1000;
        Training training = new Training(new Season(0, 0), startTime.getTime(), new Date(endTime), new Team("team name"), "venue");
        assertTrue(training.isFinished());
        training.setStatus(new Status(1, ActivityStatusEnum.NOT_STARTED.name()));
        assertFalse(training.isFinished());
        training.setStatus(new Status(1, ActivityStatusEnum.COMPLETED.name()));
        assertTrue(training.isFinished());
    }

    @Test
    public void numberOfPlayers() {
        Training training = new Training(new Season(0, 0), new Date(2014, 6, 23), new Date(), new Team("team name"), "venue");
        assertFalse(training.isFinished());
        assertEquals(0, training.getNumberOfSignedPlayers().intValue());
        training.setNumberOfSignedPlayers(11);
        assertEquals(11, training.getNumberOfSignedPlayers().intValue());
    }

    @Test
    public void appendTimeToDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2014);
        cal.set(Calendar.MONTH, 6);
        cal.set(Calendar.DAY_OF_MONTH, 23);
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.MINUTE, 0);
        Training training = new Training(new Season(0, 0), cal.getTime(), cal.getTime(), new Team("team name"), "venue");
        assertEquals("24.07.2014 00:00", Utility.formatTime(training.getStartDate().getTime(), "dd.MM.yyyy HH:mm"));
        assertEquals("24.07.2014 00:00", Utility.formatTime(training.getEndDate().getTime(), "dd.MM.yyyy HH:mm"));
        training.setStartTime("18:00");
        training.setEndTime("19:30");
        assertEquals("24.07.2014 18:00", Utility.formatTime(training.getStartDate().getTime(), "dd.MM.yyyy HH:mm"));
        assertEquals("24.07.2014 19:30", Utility.formatTime(training.getEndDate().getTime(), "dd.MM.yyyy HH:mm"));
    }

    @Test
    public void validate() {
        Training training = new Training();
        training.setStartTime("18:0");
        training.setEndTime("19:30");
    }
}
