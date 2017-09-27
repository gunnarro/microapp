package com.gunnarro.calendar.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.fortuna.ical4j.data.ParserException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MultiMap;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.gunnarro.calendar.domain.calendar.CalendarEvent;
import com.gunnarro.calendar.domain.calendar.CalendarEventPredicateApache;
import com.gunnarro.calendar.utility.Constants;

public class ICalParserTest {

    @Ignore
    @Test
    public void parseEventsToMultiMapManuel() throws IOException, ParserException {
        List<CalendarEvent> filters = new ArrayList<CalendarEvent>();
        filters.add(CalendarEvent.createFilter("match", null));
        filters.add(CalendarEvent.createFilter("training", null));
        filters.add(CalendarEvent.createFilter("cup", null));
        filters.add(CalendarEvent.createFilter("Jenter 14 år 9er 2. div.", "sagene"));
        filters.add(CalendarEvent.createFilter("tournament", null));
        filters.add(CalendarEvent.createFilter("social", null));
        filters.add(CalendarEvent.createFilter("volunteer", null));
        
        ICalParser parser = new ICalParser();
        MultiMap<Date, List<CalendarEvent>> multiMap = parser.parseEventsToMultiMap(Constants.URL_ICAL_J14);
        Assert.assertEquals(30, multiMap.size());
        List<CalendarEvent> eventList = new ArrayList<CalendarEvent>();

        for (Date key : multiMap.keySet()) {
        	eventList.addAll((List<CalendarEvent>)multiMap.get(key));
        }
        Assert.assertEquals(90, eventList.size());
        CollectionUtils.filter(eventList, CalendarEventPredicateApache.createPredicate(filters));
        System.out.println("---------------------- Filtered list -----------------------------------");
        for (CalendarEvent event : eventList) {
            System.out.println("Event: " + event.getStartDate() + " " + event.getSummary());
        }
//        Assert.assertEquals(14, eventList.size());
    }
    
    
//    @Ignore
//    @Test
//    public void readHttps() {
//        InputStream inputStream = URLReader.getHttpsInput("http://www.fotball.no/templates/portal/pages/GenerateICalendar.aspx?teamId=146838");
//    }
}
