package com.gunnarro.calendar.parser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringUtils;
import com.gunnarro.calendar.domain.activity.Match;
import com.gunnarro.calendar.domain.calendar.CalendarEvent;
import com.gunnarro.calendar.utility.Utility;

public class ICalParser {

    private static final Logger LOG = LoggerFactory.getLogger(ICalParser.class);

    private enum ICalPropertyNamesEnum {
        VERSION, PRODID, BEGIN, VEVENT, DESCRIPTION, DTEND, DTSTAMP, DTSTART, LOCATION, SEQUENCE, SUMMARY, UID, END, VTIMEZONE, TZID, URL
    }

    @SuppressWarnings("unchecked")
    public MultiMap parseEventsToMultiMap(String url) throws IOException, ParserException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Read data from: " + url);
        }
        URL u = new URL(url);
        Reader reader = new InputStreamReader(u.openStream(), "UTF-8");
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(reader);

        MultiMap eventMultiMap = new MultiValueMap();
        for (Iterator<?> i = calendar.getComponents().iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            if (component.getName().equals("VEVENT")) {
                CalendarEvent event = mapToCalendarEvent(component);
                eventMultiMap.put(event.getStartDateOnly(), event);
            }
        }
        // Finally, sort all events by time for all dates in the multimap
        Set<Map.Entry<Date, List<CalendarEvent>>> entrySet = eventMultiMap.entrySet();
        for (Map.Entry<Date, List<CalendarEvent>> entry : entrySet) {
            Collections.sort(entry.getValue());
        }
        
        if (LOG.isDebugEnabled()) {
            LOG.debug("Loaded " + eventMultiMap.size() + " events from: " + url);
        }
        return eventMultiMap;
    }

   
    private CalendarEvent mapToCalendarEvent(Component component) {
        CalendarEvent event = new CalendarEvent();
        String startDateTimeStr = component.getProperty(ICalPropertyNamesEnum.DTSTART.name()).getValue();
        String location = component.getProperty(ICalPropertyNamesEnum.LOCATION.name()).getValue();
        String summary = component.getProperty(ICalPropertyNamesEnum.SUMMARY.name()).getValue();
        String description = component.getProperty(ICalPropertyNamesEnum.DESCRIPTION.name()).getValue();
        String url = component.getProperty(ICalPropertyNamesEnum.URL.name()).getValue();
        
        // FIXME hard coding for test purpose only, matchId is not included in
        // the iCal data
        event.setId(component.hashCode());
        event.setLink(url);
        event.setStartDate(Utility.timeToDate(startDateTimeStr.replace("T", ""), "yyyyMMddHHmmss"));
        event.setSummary(summary);
        event.setLocation(location);
        if (!StringUtils.isBlank(description)) {
            String league = description.split("\\r?\\n")[0];
            league = league.split("\\(")[0].trim();
            event.setType(league);
            event.setDescription(description);
        }
        return event;
    }
}
