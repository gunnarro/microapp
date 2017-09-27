package com.gunnarro.calendar.domain.calendar;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalendarEventPredicateApacheTest {

    CalendarEvent matchJ13_sagene = new CalendarEvent();
    CalendarEvent matchJ13_2 = new CalendarEvent();
    CalendarEvent matchG12_lyn = new CalendarEvent();
    CalendarEvent matchG12_2 = new CalendarEvent();

    List<CalendarEvent> typeFilters = new ArrayList<CalendarEvent>();
    
    @Before
    public void before() {
        matchJ13_sagene.setType("Jenter 13 år 2.div avd 03");
        matchJ13_sagene.setSummary("Skeid - Sagene");
        matchJ13_sagene.setId(13);

        matchJ13_2.setType("Jenter 13 år 2.div avd 03");
        matchJ13_2.setSummary("Lyn - Asker");
        matchJ13_2.setId(14);

        matchG12_lyn.setType("Gutter 12 år avd 13");
        matchG12_lyn.setSummary("Lyn G - Haslum");
        matchG12_lyn.setId(15);

        matchG12_2.setType("Gutter 12 år avd 13");
        matchG12_2.setSummary("Sagene G - Hurdal");
        matchG12_2.setId(16);
        
        // Default filters
        typeFilters.add(CalendarEvent.createFilter("match", null));
        typeFilters.add(CalendarEvent.createFilter("training", null));
        typeFilters.add(CalendarEvent.createFilter("cup", null));
        typeFilters.add(CalendarEvent.createFilter("tournament", null));
        typeFilters.add(CalendarEvent.createFilter("social", null));
        typeFilters.add(CalendarEvent.createFilter("volunteer", null));
    }

    @Test
    public void filterByAllTypeAndAllName() {
        List<CalendarEvent> filters = new ArrayList<CalendarEvent>();
        filters.addAll(typeFilters);
        filters.add(CalendarEvent.createFilter("All", "All"));
        Predicate<CalendarEvent> predicate = CalendarEventPredicateApache.createPredicate(filters);
        Assert.assertTrue(predicate.evaluate(matchJ13_sagene));
        Assert.assertTrue(predicate.evaluate(matchJ13_2));
        Assert.assertTrue(predicate.evaluate(matchG12_lyn));
        Assert.assertTrue(predicate.evaluate(matchG12_2));
    }

    @Test
    public void filterByTypeAndName() {
        List<CalendarEvent> filters = new ArrayList<CalendarEvent>();
        filters.addAll(typeFilters);
        filters.add(CalendarEvent.createFilter("Gutter 12 år avd 13", "Lyn"));
        filters.add(CalendarEvent.createFilter("Jenter 13 år 2.div avd 03", "Sagene"));
        Predicate<CalendarEvent> predicate = CalendarEventPredicateApache.createPredicate(filters);
        Assert.assertTrue(predicate.evaluate(matchJ13_sagene));
        Assert.assertFalse(predicate.evaluate(matchJ13_2));
        Assert.assertTrue(predicate.evaluate(matchG12_lyn));
        Assert.assertFalse(predicate.evaluate(matchG12_2));
    }

    @Test
    public void filterByTypeAndName1() {
        List<CalendarEvent> filters = new ArrayList<CalendarEvent>();
        filters.addAll(typeFilters);
        filters.add(CalendarEvent.createFilter("Jenter 13 år 2.div avd 03", "Sagene"));
        Predicate<CalendarEvent> predicate = CalendarEventPredicateApache.createPredicate(filters);
        Assert.assertTrue(predicate.evaluate(matchJ13_sagene));
        Assert.assertFalse(predicate.evaluate(matchJ13_2));
        Assert.assertFalse(predicate.evaluate(matchG12_lyn));
        Assert.assertFalse(predicate.evaluate(matchG12_2));
    }

    @Test
    public void filterByTypeAndName2() {
        List<CalendarEvent> filters = new ArrayList<CalendarEvent>();
        filters.addAll(typeFilters);
        filters.add(CalendarEvent.createFilter("Gutter 12 år avd 13", "Lyn"));
        Predicate<CalendarEvent> predicate = CalendarEventPredicateApache.createPredicate(filters);
        Assert.assertFalse(predicate.evaluate(matchJ13_sagene));
        Assert.assertFalse(predicate.evaluate(matchJ13_2));
        Assert.assertTrue(predicate.evaluate(matchG12_lyn));
        Assert.assertFalse(predicate.evaluate(matchG12_2));
    }
    
    @Test
    public void filterByTypeAndAllName() {
        List<CalendarEvent> filters = new ArrayList<CalendarEvent>();
        filters.addAll(typeFilters);
        filters.add(CalendarEvent.createFilter("Gutter 12 år avd 13", "all"));
        Predicate<CalendarEvent> predicate = CalendarEventPredicateApache.createPredicate(filters);
        Assert.assertFalse(predicate.evaluate(matchJ13_sagene));
        Assert.assertFalse(predicate.evaluate(matchJ13_2));
        Assert.assertTrue(predicate.evaluate(matchG12_lyn));
        Assert.assertTrue(predicate.evaluate(matchG12_2));
    }
    
    @Test
    public void filterCalendarEventList() {
    	List<CalendarEvent> filters = new ArrayList<CalendarEvent>();
        filters.addAll(typeFilters);
        filters.add(CalendarEvent.createFilter("Gutter 12 år avd 13", "all"));
        List<CalendarEvent> eventList = new ArrayList<CalendarEvent>();
        eventList.add(matchJ13_sagene);
        eventList.add(matchJ13_2);
        eventList.add(matchG12_lyn);
        eventList.add(matchG12_2);
        Assert.assertTrue(eventList.size() == 4);
        CollectionUtils.filter(eventList, CalendarEventPredicateApache.createPredicate(filters));
        Assert.assertTrue(eventList.size() == 2);
    }
}
