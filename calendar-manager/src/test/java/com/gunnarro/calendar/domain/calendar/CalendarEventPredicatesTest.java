package com.gunnarro.calendar.domain.calendar;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class CalendarEventPredicatesTest {

//    @Ignore
//    @Test
//    public void filterCalendarEventListPredicate() {
//        CalendarEvent matchJ13_1 = new CalendarEvent();
//        matchJ13_1.setType("Jenter 13 år 2.div avd 03");
//        matchJ13_1.setSummary("Skeid - Sagene");
//        matchJ13_1.setId(13);
//
//        CalendarEvent matchJ13_2 = new CalendarEvent();
//        matchJ13_2.setType("Jenter 13 år 2.div avd 03");
//        matchJ13_2.setSummary("Lyn - Asker");
//        matchJ13_2.setId(14);
//
//        CalendarEvent matchG12_1 = new CalendarEvent();
//        matchG12_1.setType("Gutter 12 år avd 13");
//        matchG12_1.setSummary("Lyn - Haslum");
//        matchG12_1.setId(15);
//
//        CalendarEvent matchG12_2 = new CalendarEvent();
//        matchG12_2.setType("Gutter 12 år avd 13");
//        matchG12_2.setSummary("Sagene - Hurdal");
//        matchG12_2.setId(16);
//
//        List<CalendarEvent> list = new ArrayList<CalendarEvent>();
//        list.add(matchJ13_1);
//        list.add(matchJ13_2);
//        list.add(matchG12_1);
//        list.add(matchG12_2);
//
//        List<CalendarEvent> filters = new ArrayList<CalendarEvent>();
//        filters.add(CalendarEvent.createFilter("Jenter 13 år 2.div avd 03", "Sagene"));
//        filters.add(CalendarEvent.createFilter("Gutter 12 år avd 13", "Lyn"));
//
//        List<CalendarEvent> filteredList = CalendarEventPredicates.filterCalendarEvents(list, CalendarEventPredicates.isCombinedTypeAndNamePairs(filters));
//        Assert.assertEquals(2, filteredList.size());
//        Assert.assertEquals("Skeid - Sagene", filteredList.get(0).getSummary());
//        Assert.assertEquals("Lyn - Haslum", filteredList.get(1).getSummary());
//    }
}
