package com.gunnarro.calendar.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.calendar.domain.calendar.CalendarEvent;
import com.gunnarro.calendar.domain.view.SearchActivity;
import com.gunnarro.calendar.domain.view.list.Item;
import com.gunnarro.calendar.service.CalendarService;
import com.gunnarro.calendar.utility.Constants;

public class CalendarControllerTest extends SpringTestSetup {

	    @Mock
	    private CalendarService calendarServiceMock;

	    private CalendarController controller;
	    
	    @Before
	    public void init() {
	        MockitoAnnotations.initMocks(this);
	        controller = new CalendarController();
	        controller.setCalendarService(calendarServiceMock);
	    }

	    @Test
	    public void generateWeeklyEvents() {
	        CalendarEvent event = new CalendarEvent();
	        controller.generateWeeklyEventsAndSave(event);
	    }

	    @Test
	    public void copyEventsFromOneDayToAnother() {
	        controller.copyEventsForDay(new Date(), new Date());;
	    }
	    
	    @Test
	    public void filterCalendarEvents() {

	        final CalendarEvent matchJ13_1 = new CalendarEvent();
	        matchJ13_1.setType("Jenter 13 �r 2.div avd 03");
	        matchJ13_1.setSummary("Sagene J - Skeid J");
	        matchJ13_1.setId(13);

	        final CalendarEvent matchJ13_2 = new CalendarEvent();
	        matchJ13_2.setType("Jenter 13 �r 2.div avd 03");
	        matchJ13_2.setSummary("Lyn J - Haslum J");
	        matchJ13_2.setId(14);

	        final CalendarEvent matchG12_1 = new CalendarEvent();
	        matchG12_1.setType("Gutter 12 �r avd 13");
	        matchG12_1.setSummary("Lyn G - Ready G");
	        matchG12_1.setId(11);

	        final CalendarEvent matchG12_2 = new CalendarEvent();
	        matchG12_2.setType("Gutter 12 �r avd 13");
	        matchG12_2.setSummary("Sagene G - Asker G");
	        matchG12_2.setId(12);

	        MultiMap matchMultiMap = new MultiValueMap();
	        matchMultiMap.put(matchG12_1.getStartDateOnly(), matchG12_1);
	        matchMultiMap.put(matchG12_1.getStartDateOnly(), matchG12_2);
	        matchMultiMap.put(matchJ13_1.getStartDateOnly(), matchJ13_1);
	        matchMultiMap.put(matchJ13_1.getStartDateOnly(), matchJ13_2);
	        Set<Map.Entry<Date, List<CalendarEvent>>> matchEntrySet = matchMultiMap.entrySet();

	        List<Entry<Date, List<CalendarEvent>>> filterMatchEntrySet = controller.filterEntrySet("day", 0, null, matchEntrySet);
	        Assert.assertEquals(4, filterMatchEntrySet.get(0).getValue().size());

	        filterMatchEntrySet = controller.filterEntrySet("day", 0, null, matchEntrySet);
	        Assert.assertEquals(4, filterMatchEntrySet.get(0).getValue().size());

	        List<CalendarEvent> filters = new ArrayList<CalendarEvent>();
	        filters.add(CalendarEvent.createFilter("all", "Lyn"));
	        filterMatchEntrySet = controller.filterEntrySet("day", 0, filters, matchEntrySet);
	        Assert.assertEquals(2, filterMatchEntrySet.get(0).getValue().size());
	        Assert.assertEquals("Lyn G - Ready G", filterMatchEntrySet.get(0).getValue().get(0).getSummary());
	        Assert.assertEquals("Lyn J - Haslum J", filterMatchEntrySet.get(0).getValue().get(1).getSummary());

	        filters = new ArrayList<CalendarEvent>();
	        filters.add(CalendarEvent.createFilter("Gutter 12 �r avd 13", "Lyn"));
	        filterMatchEntrySet = controller.filterEntrySet("day", 0, filters, matchEntrySet);
	        Assert.assertEquals(1, filterMatchEntrySet.get(0).getValue().size());
	        Assert.assertEquals("Lyn G - Ready G", filterMatchEntrySet.get(0).getValue().get(0).getSummary());
	        
	        filters = new ArrayList<CalendarEvent>();
	        filters.add(CalendarEvent.createFilter("Gutter 12 �r avd 13", "all"));
	        List<Entry<Date, List<CalendarEvent>>> filterMatchEntrySet2 = controller.filterEntrySet("day", 0, filters, matchEntrySet);
	        Assert.assertEquals(1, filterMatchEntrySet2.size());
//	        Assert.assertEquals("", filterMatchEntrySet2.get(0).getKey());
	        Assert.assertEquals(2, filterMatchEntrySet2.get(0).getValue().size());
	        Assert.assertEquals("Lyn G - Ready G", filterMatchEntrySet2.get(0).getValue().get(0).getSummary());
	        Assert.assertEquals("Sagene G - Asker G", filterMatchEntrySet2.get(0).getValue().get(1).getSummary());
	    }

	    @Ignore
	    @Test
	    public void sortDateString() {
	        List<String> list = new ArrayList<String>();
	        list.add("Friday, 09. Oct 2015");
	        list.add("Friday, 12. Jun 2015");
	        list.add("Friday, 24. Apr 2015");
	        list.add("Friday, 04. Sep 2015");
	        list.add("Friday, 11. Sep 2015");
	        list.add("Friday, 18. Sep 2015");
	        Collections.sort(list);
	        for (String date : list) {
	            System.out.println(date);
	        }
	    }

	    @Test
	    public void listAllCalendarEventsByTypeMultiMap() throws Exception {
	        MultiMap j13MultiMap = new MultiValueMap();
	        CalendarEvent matchJ13 = new CalendarEvent();
	        matchJ13.setType("Jenter 13 �r 2.div avd 03");
	        matchJ13.setSummary("Sagene - Skeid");
	        matchJ13.setId(13);
	        j13MultiMap.put(matchJ13.getStartDateOnly(), matchJ13);

	        MultiMap g12MultiMap = new MultiValueMap();
	        CalendarEvent matchG12 = new CalendarEvent();
	        matchG12.setType("Gutter 12 �r avd 13");
	        matchG12.setSummary("Lyn - Ready");
	        matchG12.setId(12);

	        g12MultiMap.put(DateUtils.addDays(matchJ13.getStartDateOnly(), 1), matchG12);

	        List<Item> urls = new ArrayList<Item>();
	        Item itemJ13 = new Item("Jenter 13 �r 2.div avd 03", Constants.URL_ICAL_J13);
	        Item itemG12 = new Item("Gutter 12 �r avd 13", Constants.URL_ICAL_G12);
	        urls.add(itemJ13);
	        urls.add(itemG12);

	        MultiMap calEventMultiMap = new MultiValueMap();
	        CalendarEvent trainingEvent = new CalendarEvent();
	        trainingEvent.setType("training");
	        trainingEvent.setSummary("Lyn g03 trening");
	        trainingEvent.setId(111);
	        calEventMultiMap.put(DateUtils.addDays(trainingEvent.getStartDateOnly(), 2), trainingEvent);

	        when(calendarServiceMock.getCalendarEventFromICalAsMultiMap(itemJ13.getValue(), itemJ13.getType(), false)).thenReturn(j13MultiMap);
	        when(calendarServiceMock.getCalendarEventFromICalAsMultiMap(itemG12.getValue(), itemG12.getType(), false)).thenReturn(g12MultiMap);
	        when(calendarServiceMock.getCalendarEventMultiMap()).thenReturn(calEventMultiMap);

	        when(calendarServiceMock.getCalendarUrlsActive()).thenReturn(urls);
	        when(calendarServiceMock.getCalendarUrlByName(itemG12.getType())).thenReturn(Constants.URL_ICAL_G12);
	        when(calendarServiceMock.getCalendarUrlByName(itemJ13.getType())).thenReturn(Constants.URL_ICAL_J13);

	        ModelAndView modelAndView = controller.listCalendarEvents(false, "year", 0, "all", "all");
	        Assert.assertEquals("calendar/list-events-view", modelAndView.getViewName());
	        Assert.assertNotNull(modelAndView.getModel());
	        SearchActivity searchActivity = (SearchActivity) modelAndView.getModel().get("searchActivity");

	        Assert.assertEquals("all", searchActivity.getName());
	        Assert.assertEquals("all", searchActivity.getType());
	        Assert.assertEquals("year", searchActivity.getPeriod());
	        Assert.assertEquals(0, searchActivity.getAmount());
	        Assert.assertFalse(searchActivity.isReload());
	        Assert.assertNotNull(modelAndView.getModel().get("lastReloaded"));
	        List<Item> items = (List<Item>) modelAndView.getModel().get("calendarUrls");
	        Assert.assertNotNull(items);
	        Assert.assertEquals(2, items.size());
	        List<Entry<String, List<CalendarEvent>>> events = (List<Entry<String, List<CalendarEvent>>>) modelAndView.getModel().get("entryEventList");
	        Assert.assertNotNull(events);
	        Assert.assertEquals(3, events.size());
	        Assert.assertEquals(3, modelAndView.getModel().get("numberOfMatches"));
	        Assert.assertEquals(3, modelAndView.getModel().get("numberOfMatchesTotal"));
	        Assert.assertNotNull(modelAndView.getModel().get("selectedPeriodTxt"));
	        // Fixme mix up the id's
	        // Assert.assertEquals(13, matches.get(0).getId().intValue());
	        // Assert.assertEquals(12, matches.get(1).getId().intValue());
	    }

//	    @Test
//	    public void filterCalendarEventListPredicator() {
//	        CalendarEvent matchJ13_1 = new CalendarEvent();
//	        matchJ13_1.setType("Jenter 13 �r 2.div avd 03");
//	        matchJ13_1.setSummary("Sagene - Skeid");
//	        matchJ13_1.setId(13);
	//
//	        CalendarEvent matchJ13_2 = new CalendarEvent();
//	        matchJ13_2.setType("Jenter 13 �r 2.div avd 03");
//	        matchJ13_2.setSummary("Lyn - Asker");
//	        matchJ13_2.setId(14);
	//
//	        CalendarEvent matchG12_1 = new CalendarEvent();
//	        matchG12_1.setType("Gutter 12 �r avd 13");
//	        matchG12_1.setSummary("Lyn - Haslum");
//	        matchG12_1.setId(15);
	//
//	        CalendarEvent matchG12_2 = new CalendarEvent();
//	        matchG12_2.setType("Gutter 12 �r avd 13");
//	        matchG12_2.setSummary("Sagene - Haslum");
//	        matchG12_2.setId(16);
	//
//	        List<CalendarEvent> list = new ArrayList<CalendarEvent>();
//	        list.add(matchJ13_1);
//	        list.add(matchJ13_2);
//	        list.add(matchG12_1);
	//
//	        Predicate<CalendarEvent> eventTypePredicate1 = p -> p.getType().equalsIgnoreCase("Jenter 13 �r 2.div avd 03");
//	        Predicate<CalendarEvent> eventSummaryPredicate1 = p -> p.getSummary().toLowerCase().contains("Sagene".toLowerCase());
	//
//	        Predicate<CalendarEvent> eventTypePredicate2 = p -> p.getType().equalsIgnoreCase("Gutter 12 �r avd 13");
//	        Predicate<CalendarEvent> eventSummaryPredicate2 = p -> p.getSummary().toLowerCase().contains("Lyn".toLowerCase());
	//
//	        Predicate<CalendarEvent> allPredicate1 = eventTypePredicate1.and(eventSummaryPredicate1);
//	        Predicate<CalendarEvent> allPredicate2 = eventTypePredicate2.and(eventSummaryPredicate2);
//	        //
//	        // Predicate<CalendarEvent> allPredicate = allPredicate1;
//	        // allPredicate = allPredicate.or(allPredicate2);
//	        Predicate<CalendarEvent> allPredicate = allPredicate1.or(allPredicate2);
	//
//	        List<CalendarEvent> filteredList = controller.filterEntrySetNew("week", 1, allPredicate, list);
//	        Assert.assertEquals(2, filteredList.size());
//	        Assert.assertEquals("Sagene - Skeid", filteredList.get(0).getSummary());
//	        Assert.assertEquals("Lyn - Haslum", filteredList.get(1).getSummary());
//	    }
	}
  

