package com.gunnarro.calendar.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.calendar.domain.calendar.CalendarConfig;
import com.gunnarro.calendar.domain.calendar.CalendarEvent;
import com.gunnarro.calendar.domain.view.list.Item;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
// @Ignore
public class CalendarRepositoryTest {

    @Autowired
    @Qualifier("calendarRepository")
    private CalendarRepository calendarRepository;
  

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getCalendarUrls() {
        List<Item> calendarUrls = calendarRepository.getCalendarUrls();
        assertEquals(1, calendarUrls.size());
        // assertEquals("Gutter 12 år avd 13", calendarUrls.get(0).getType());
        // assertEquals("http://www.fotball.no/templates/portal/pages/GenerateICalendar.aspx?teamId=8679",
        // calendarUrls.get(0).getValue());
    }

    @Test
    public void getCalendarConfig() {
        List<CalendarConfig> calendarConfigurations = calendarRepository.getCalendarConfigurations();
        assertEquals(3, calendarConfigurations.size());
        assertEquals("lyn", calendarConfigurations.get(0).getTeamName());
        assertEquals("sagene", calendarConfigurations.get(1).getTeamName());
    }

    @Ignore
    @Test
    public void getCalendarUrlByName() {
        String url = calendarRepository.getCalendarUrl("Gutter 12 år avd 13");
        assertEquals("http://www.fotball.no/templates/portal/pages/GenerateICalendar.aspx?teamId=8679", url);
    }

    @Test
    public void getCalendarEvents() {
        List<CalendarEvent> events = calendarRepository.getCalendarEvents();
        assertEquals(0, events.size());
    }

    /**
     * Use date function which is not supported by h2 db
     */
    @Ignore
    @Test
    public void getCalendarEventStartDates() {
        List<Item> events = calendarRepository.getCalendarEventStartDates();
        assertEquals(0, events.size());
    }

    /**
     * Use date function which is not supported by h2 db
     */
    @Ignore
    @Test
    public void findCalendarEventsForDate() {
        CalendarEvent event = new CalendarEvent();
        event.setStartDate(new Date());
        event.setName("unittest event");
        calendarRepository.createEvent(event);
        List<CalendarEvent> events = calendarRepository.findCalendarEventsForDay(new Date(), null);
        assertEquals(1, events.size());

        events = calendarRepository.findCalendarEventsForDay(new Date(), "unittest event");
        assertEquals(1, events.size());
    }

    @Test
    public void getCalendarEventById() {
        assertNull(calendarRepository.getEvent(23));
    }

    @Test
    public void calendarEventCRUD() {
        CalendarEvent event = new CalendarEvent();
        event.setName("Test");
        event.setDescription("description");
        event.setStartDate(new Date());
        event.setEndDate(new Date());
        event.setType("type");
        event.setSummary("summary");
        event.setLocation("location");
        int id = calendarRepository.createEvent(event);
        CalendarEvent newevent = calendarRepository.getEvent(id);
        assertEquals("Test", newevent.getName());
        assertEquals("description", newevent.getDescription());
        assertEquals("summary", newevent.getSummary());
        assertEquals("type", newevent.getType());
        assertEquals("location", newevent.getLocation());
        assertEquals("Test", newevent.getName());
        assertNotNull(newevent.getStartDate());
        assertNotNull(newevent.getEndDate());

        newevent.setDescription("updated description");
        calendarRepository.updateEvent(newevent);
        CalendarEvent updatedEvent = calendarRepository.getEvent(id);
        assertEquals("updated description", updatedEvent.getDescription());

        int rows = calendarRepository.deleteEvent(id);
        assertTrue(rows == 1);
    }

  

}
