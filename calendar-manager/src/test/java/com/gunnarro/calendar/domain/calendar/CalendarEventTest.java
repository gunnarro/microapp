package com.gunnarro.calendar.domain.calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.gunnarro.calendar.utility.Utility;

public class CalendarEventTest {

    @Test
    public void startDateOnly() {
        CalendarEvent event = new CalendarEvent();
        assertNotNull(event.getStartDate());
        assertNotNull(event.getStartDateOnly());
        assertNotNull(event.getStartDateTime());
        assertNotNull(event.getEndDateTime());
    }

    @Test
    public void startTimeDate() {
        CalendarEvent event = new CalendarEvent();
        event.setStartDate(new Date());
        event.setStartTime("18:30");
        assertNotNull(event.getStartDateTime());
        String startDate = Utility.formatTime(event.getStartDate().getTime(), "dd.MM.yyyy HH:mm");
        String startDateTime = Utility.formatTime(event.getStartDateTime().getTime(), "dd.MM.yyyy HH:mm");
        assertEquals("18:30", event.getStartTime());
        assertTrue(startDateTime.contains("18:30"));
    }

    @Test
    public void endTimeDate() {
        CalendarEvent event = new CalendarEvent();
        event.setEndDate(new Date());
        event.setEndTime("20:00");
        assertNotNull(event.getStartDateTime());
        String endDate = Utility.formatTime(event.getEndDate().getTime(), "dd.MM.yyyy HH:mm");
        String endDateTime = Utility.formatTime(event.getEndDateTime().getTime(), "dd.MM.yyyy HH:mm");
        assertEquals("20:00", event.getEndTime());
        assertTrue(endDateTime.contains("20:00"));
    }

    @Test
    public void copyEvent() {
        CalendarEvent event = new CalendarEvent();
        event.setDescription("description");
        event.setSummary("Gutter 12 år, Norway Cup 2015");
        event.setName("norway cup");
        event.setLocation("Ekeberg");
        event.setType("Gutter 12 år avd 13 (runde 5)");
        assertEquals("Gutter 12 år avd 13", event.getShortType());
        assertNotNull(CalendarEvent.copyEvent(event, new Date()));
        assertNull(CalendarEvent.copyEvent(event, new Date()).getId());
    }

//    @Ignore
//    @Test
//    public void newEvent() {
//        CalendarEvent event = new CalendarEvent();
//        event.setStartDate(Utility.roundToClosestHour(event.getStartDate()));
//        event.setEndDate(Utility.addMinutes(event.getStartDate(), Training.DEFAULT_TRAINING_TIME_MINUTES));
//        event.setStartTime(Utility.formatTime(event.getStartDate().getTime(), "HH:mm"));
//        event.setEndTime(Utility.formatTime(event.getEndDate().getTime(), "HH:mm"));
//
//        assertEquals("17:00", event.getStartTime());
//        assertEquals("18:30", event.getEndTime());
//    }
}
