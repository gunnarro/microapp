package com.gunnarro.calendar.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gunnarro.calendar.domain.calendar.Agenda;
import com.gunnarro.calendar.domain.calendar.CalendarConfig;
import com.gunnarro.calendar.domain.calendar.CalendarEvent;
import com.gunnarro.calendar.domain.party.Person;
import com.gunnarro.calendar.domain.party.User;
import com.gunnarro.calendar.domain.view.list.Item;
import com.gunnarro.calendar.parser.ICalParser;
import com.gunnarro.calendar.repository.CalendarRepository;
import com.gunnarro.calendar.service.CalendarService;
import com.gunnarro.calendar.service.exception.ApplicationException;

@Service
//@CacheConfig(cacheNames={"calendarEventListCache"})
public class CalendarServiceImpl implements CalendarService {

    private static final Logger LOG = LoggerFactory.getLogger(CalendarServiceImpl.class);

    @Autowired
    @Qualifier("calendarRepository")
    private CalendarRepository calendarRepository;

//    @Autowired
//    @Qualifier("cacheHandler")
//    private CacheHandler cacheHandler;

    @Override
    public User getUser(String userName) {
        return calendarRepository.getUser(userName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int saveCalendarConfiguration(CalendarConfig config) {
        if (config.isNew()) {
            return calendarRepository.createCalendarConfiguration(config);
        } else {
            return calendarRepository.updateCalendarConfiguration(config);
        }
    }

    /**
     * @Cacheable will skip running the method, 
     * whereas @CachePut will actually run the method and then put its results in the cache.
     * {@inheritDoc}
     */
    @Override
//    @CachePut(value="calendarEventListCache", key="#key")
    @Cacheable(value="calendarEventListCache", key="#key", condition="#isReload==false")
    public MultiMap getCalendarEventFromICalAsMultiMap(String url, String key, boolean isReload) {
        try {
            ICalParser parser = new ICalParser();
            MultiMap multiMap = parser.parseEventsToMultiMap(url);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Got data from remote url: " + url);
                LOG.debug("Events: " + multiMap.size());
            }
            return multiMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

//    @Override
//    public MultiMap getCalendarEventFromICalAsMultiMap(String url, String key, boolean isReload) {
//        // First of all check the cache, and if cached, use it.
//        if (!isReload) {
//            MultiMap eventsFromCache = cacheHandler.getCalendarEventMultiMapFromCache("multimap_" + key);
//            if (eventsFromCache != null) {
//                MultiMap multiMap = new MultiValueMap();
//                Set<Map.Entry<Date, List<CalendarEvent>>> calenderEventEntrySet = eventsFromCache.entrySet();
//                for (Map.Entry<Date, List<CalendarEvent>> entry : calenderEventEntrySet) {
//                    for (CalendarEvent event : entry.getValue()) {
//                        multiMap.put(entry.getKey(), event);
//                    }
//                }
//                if (LOG.isDebugEnabled()) {
//                    LOG.debug("Got data from cache: " + key + " for url: " + url);
//                    LOG.debug("Events: " + multiMap.size());
//                }
//                return multiMap;
//            }
//        }
//        // Reload events from external url
//        try {
//            ICalParser parser = new ICalParser();
//            MultiMap multiMap = parser.parseEventsToMultiMap(url);
//            cacheHandler.putCalendarEventListInToCacheMultiMap("multimap_" + key, multiMap);
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("Got data from remote url: " + url);
//                LOG.debug("Events: " + multiMap.size());
//            }
//            return multiMap;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ApplicationException(e.getMessage());
//        }
//    }

    @Override
    public String getCalendarUrlByName(String name) {
        return calendarRepository.getCalendarUrl(name);
    }

    @Override
    public CalendarConfig getCalendarConfiguration(Integer id) {
        return calendarRepository.getCalendarConfiguration(id);
    }

    @Override
    public int deleteCalendarConfig(int configId) {
        return calendarRepository.deleteCalendarConfig(configId);
    }

    @Override
    public List<CalendarConfig> getCalendarConfigurations() {
        return calendarRepository.getCalendarConfigurations();
    }

    @Override
    public List<String> getCalendarConfigurationTeamNames() {
        List<String> teamNames = new ArrayList<String>();
        for (CalendarConfig config : calendarRepository.getCalendarConfigurations()) {
            if (config.isEnabled()) {
                teamNames.add(config.getTeamName());
            }
        }
        Collections.sort(teamNames);
        return teamNames;
    }

    @Override
    public int saveCalendarEvent(CalendarEvent event) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(event.toString());
        }
        if (event.isNew()) {
            return calendarRepository.createEvent(event);
        } else {
            return calendarRepository.updateEvent(event);
        }
    }

    @Override
    public int deleteCalendarEvent(int id) {
        int deletedRows = calendarRepository.deleteEvent(id);
        if (LOG.isDebugEnabled()) {
            LOG.debug("delete eventId=" + id + ", deleted rows: " + deletedRows);
        }
        return deletedRows;
    }

    @Override
    public CalendarEvent getCalendarEvent(int id) {
        return calendarRepository.getEvent(id);
    }

    @Override
    public List<CalendarEvent> getCalendarEvents(Date forDate) {
        if (forDate != null) {
            return calendarRepository.findCalendarEventsForDay(forDate, null);
        }
        return calendarRepository.getCalendarEvents();
    }

    @Override
    public List<CalendarEvent> findCalendarEvents(Date forDate, String eventName) {
        return calendarRepository.findCalendarEventsForDay(forDate, eventName);
    }

    @Override
    public MultiMap getCalendarEventMultiMap() {
        MultiMap multiMap = new MultiValueMap();
        for (CalendarEvent event : getCalendarEvents(null)) {
            multiMap.put(event.getStartDateOnly(), event);
            if (LOG.isDebugEnabled()) {
                LOG.debug("ADD Event to map: " + event.toString());
            }
        }
        return multiMap;
    }

    @Override
    public List<String> getFollowersPhoneNumbers(Integer eventId) {
        return calendarRepository.getFollowersPhoneNumbers(eventId);
    }

    @Override
    public List<Person> getFollowers(Integer eventId) {
        return calendarRepository.getFollowers(eventId);
    }

    @Override
    public List<Item> getCalendarEventStartDates() {
        return calendarRepository.getCalendarEventStartDates();
    }

    /**
     * TODO implement me hard coded
     * 
     * @param calendarEventId
     * @return
     */
    @Override
    public Agenda getAgenda(Integer calendarEventId) {
        // DietPlan dietPlan = DietPlan.createDietPlanDefault();
        return new Agenda(null, null);
    }

    /**
     * TODO implement me
     */
    @Override
    public int saveAgenda(Agenda agenda) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(agenda.toString());
        }
        return 0;
    }

    @Override
    public List<Item> getCalendarUrlsActive() {
        return calendarRepository.getCalendarUrls();
    }

}
