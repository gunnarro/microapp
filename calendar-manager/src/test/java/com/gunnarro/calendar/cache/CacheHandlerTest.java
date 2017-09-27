package com.gunnarro.calendar.cache;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
public class CacheHandlerTest {

    @Autowired
    @Qualifier("cacheHandler")
    private CacheHandler cacheHandler;

    @Before
    public void before() {
        // cacheHandler = new CacheHandler();
        // cacheHandler.cacheEnabled(false);
    }

//    @Test
//    public void findById() {
//        MultiMap<Date, List<CalendarEvent>> map = new MultiValueMap<Date, List<CalendarEvent>>();
//        Date today = new Date();
//        map.put(today, new CalendarEvent(12));
//        map.put(today, new CalendarEvent(13));
//        map.put(today, new CalendarEvent(14));
//        cacheHandler.putCalendarEventListInToCacheMultiMap("multimap_test", map);
//        CalendarEvent event = cacheHandler.findCalendarEventById("multimap_test", 12);
//        assertEquals(12, event.getId().intValue());
//
//        event = cacheHandler.findCalendarEventById(null, 12);
//        assertEquals(12, event.getId().intValue());
//
//        event = cacheHandler.findCalendarEventById(null, 122);
//        assertNull(event);
//    }

//    @Test
//    public void getCalendarEventsForG12() {
//        // assertNull(cacheHandler.getCalendarEventsFromCache("Gutter 12 år avd 13"));
//        List<CalendarEvent> list = new ArrayList<CalendarEvent>();
//        list.add(new CalendarEvent());
//        list.add(new CalendarEvent());
//        list.add(new CalendarEvent());
//        cacheHandler.putCalendarEventListInToCache("Gutter 12 år avd 13", list);
//        List<CalendarEvent> matchesFromCache = cacheHandler.getCalendarEventsFromCache("Gutter 12 år avd 13");
//        assertTrue(matchesFromCache.size() == 3);
//        List<CalendarEvent> newlist = new ArrayList<CalendarEvent>();
//        newlist.add(new CalendarEvent());
//        newlist.add(new CalendarEvent());
//        newlist.add(new CalendarEvent());
//        newlist.add(new CalendarEvent());
//        cacheHandler.putCalendarEventListInToCache("Gutter 12 år avd 13", newlist);
//        matchesFromCache = cacheHandler.getCalendarEventsFromCache("Gutter 12 år avd 13");
//        assertTrue(matchesFromCache.size() == 4);
//    }

//    @Test
//    public void putMultiMapIntoCache() {
//        MultiMap<Date, List<CalendarEvent>> eventMultiMap = new MultiValueMap<Date, List<CalendarEvent>>();
//        CalendarEvent event = CalendarEvent.createFilter("type", "name");
//        eventMultiMap.put(event.getStartDateOnly(), event);
//        cacheHandler.putCalendarEventListInToCacheMultiMap("multimap_test", eventMultiMap);
//        MultiMap<?, ?> eventsFromCache = cacheHandler.getCalendarEventMultiMapFromCache("multimap_test");
//        assertTrue(eventsFromCache.size() == 1);
//    }

}
