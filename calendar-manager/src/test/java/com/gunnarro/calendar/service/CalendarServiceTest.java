package com.gunnarro.calendar.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.MultiMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.calendar.cache.CacheConfig;
import com.gunnarro.calendar.domain.calendar.CalendarConfig;
import com.gunnarro.calendar.domain.view.list.Item;
import com.gunnarro.calendar.utility.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
//@ContextConfiguration(classes = { CacheConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
// @TransactionConfiguration(defaultRollback = true)
// @Ignore
public class CalendarServiceTest {

    @Autowired
    @Qualifier("calendarService")
    protected CalendarService calendarService;

    @Before
    public void setUp() throws Exception {
        // Because of security we have to set user and pwd before every unit
        // test
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("admin", "uiL2oo3");
        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(ctx);
        ctx.setAuthentication(authRequest);
    }

    @After
    public void terminate() {
        SecurityContextHolder.clearContext();
    }

  
   @Ignore
   @Test
   public void getCalendarEventsFromUrl() {
       MultiMap calendarEvents = calendarService.getCalendarEventFromICalAsMultiMap(Constants.URL_ICAL_J14, "j14key", false);
       System.out.println("read from url: " + calendarEvents.size());
       System.out.println("------------------- test cache ----------------------");
       calendarEvents = calendarService.getCalendarEventFromICalAsMultiMap(Constants.URL_ICAL_J14, "j14key", false);
       System.out.println("read from cache: " + calendarEvents.size());
       calendarEvents = calendarService.getCalendarEventFromICalAsMultiMap(Constants.URL_ICAL_J14, "j14key", true);
       System.out.println("reloaded from url: " + calendarEvents.size());
   }
  

    @Ignore
    @Test
    public void getCalendarEventsFromDB() {
        assertEquals(0, calendarService.getCalendarEvents(null).size());
        assertEquals(0, calendarService.getCalendarEvents(new Date()).size());
    }

    @Ignore
    @Test
    public void findCalendarEvents() {
        assertEquals(0, calendarService.findCalendarEvents(new Date(), null).size());
        assertEquals(0, calendarService.findCalendarEvents(new Date(), "eventname").size());
    }

    @Test
    public void getCalendarUrlsActive() {
        List<Item> calendarUrls = calendarService.getCalendarUrlsActive();
        assertEquals(1, calendarUrls.size());
        // assertEquals("Gutter 12 år avd 13", calendarUrls.get(0).getType());
        // assertEquals("http://www.fotball.no/templates/portal/pages/GenerateICalendar.aspx?teamId=8679",
        // calendarUrls.get(0).getValue());
    }

    @Ignore
    @Test
    public void getCalendarUrlByName() {
        String url = calendarService.getCalendarUrlByName("Gutter 12 år avd 13");
        assertEquals("http://www.fotball.no/templates/portal/pages/GenerateICalendar.aspx?teamId=8679", url);
    }

    @Test
    public void saveCalendarConfig() {
        CalendarConfig config = new CalendarConfig();
        config.setName("name");
        config.setTeamName("teamName");
        config.setUrl("http://ical.ulr.home/team.ical");
        config.setFormat("ical");
        config.setDescription("description");
        config.setEnabled(true);
        int id = calendarService.saveCalendarConfiguration(config);
        assertTrue(id > 0);

        String u = calendarService.getCalendarUrlByName("name");
        assertEquals("http://ical.ulr.home/team.ical", u);

        CalendarConfig newconfig = calendarService.getCalendarConfiguration(id);
        assertEquals("http://ical.ulr.home/team.ical", newconfig.getUrl());

        newconfig.setUrl("http://updated.url.no");
        calendarService.saveCalendarConfiguration(newconfig);

        CalendarConfig updatedConf = calendarService.getCalendarConfiguration(id);
        assertEquals("http://updated.url.no", updatedConf.getUrl());

        int deleteRows = calendarService.deleteCalendarConfig(updatedConf.getId());
        assertTrue(deleteRows == 1);
    }

  
}
