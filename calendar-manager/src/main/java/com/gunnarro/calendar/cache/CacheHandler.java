package com.gunnarro.calendar.cache;

//import java.util.Date;
//import java.util.List;
//
//import net.sf.ehcache.Cache;
//import net.sf.ehcache.CacheManager;
//import net.sf.ehcache.Element;
//
//import org.apache.commons.collections4.MultiMap;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//import com.gunnarro.calendar.domain.calendar.CalendarEvent;

public class CacheHandler {

//    private static final Logger LOG = LoggerFactory.getLogger(CacheHandler.class);
//
//    private CacheManager cacheManager;
//    private boolean cacheEnabled = true;
//
//    public void setCacheEnabled(boolean cacheEnabled) {
//        this.cacheEnabled = cacheEnabled;
//    }
//
//    public void setCacheManager(CacheManager cacheManager) {
//        this.cacheManager = cacheManager;
//    }

    /**
     * Method to search in cache after a given calendar event id.
     * 
     * @param key - cache key
     * @param id - calendar event id
     * @return
     */
//    public CalendarEvent findCalendarEventById(String key, Integer id) {
//        if (key != null) {
//            return find(key, id);
//        } else {
//            for (Object obj : getCalendarEventListCache().getKeys()) {
//                CalendarEvent e = find(obj.toString(), id);
//                if (e != null) {
//                    return e;
//                }
//            }
//        }
//        return null;
//    }
//
//    private CalendarEvent find(String key, Integer id) {
//        MultiMap<Date, List<CalendarEvent>> calendarEventMultiMap = getCalendarEventMultiMapFromCache(key);
//        if (calendarEventMultiMap != null) {
//            for (Object obj : calendarEventMultiMap.values()) {
//                CalendarEvent event = (CalendarEvent) obj;
//                if (event.getId() != null && event.getId().intValue() == id) {
//                    return event;
//                }
//
//            }
//        }
//        return null;
//    }

//    @SuppressWarnings("unchecked")
//    public List<CalendarEvent> getCalendarEventsFromCache(String key) {
//        if (cacheEnabled) {
//            Element element = getCalendarEventListCache().get(key);
//            if (element != null) {
//                return (List<CalendarEvent>) element.getObjectValue();
//            } else {
//                if (LOG.isDebugEnabled()) {
//                    LOG.debug("No hit for key=" + key);
//                }
//            }
//        }
//        return null;
//    }

//    @SuppressWarnings("unchecked")
//    public MultiMap<Date, List<CalendarEvent>> getCalendarEventMultiMapFromCache(String key) {
//        if (cacheEnabled) {
//            Element element = getCalendarEventListCache().get(key);
//            if (element != null) {
//                return (MultiMap<Date, List<CalendarEvent>>) element.getObjectValue();
//            } else {
//                if (LOG.isDebugEnabled()) {
//                    LOG.debug("No hit for key=" + key);
//                }
//            }
//        }
//        return null;
//    }

//    public void putCalendarEventListInToCache(String key, List<CalendarEvent> eventList) {
//        if (cacheEnabled) {
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("Updated cache, key=" + key + ", number of events: " + eventList.size());
//            }
//            getCalendarEventListCache().put(new Element(key, eventList));
//        }
//    }

//    public void putCalendarEventListInToCacheMultiMap(String key, MultiMap<Date, List<CalendarEvent>> eventMultiMap) {
//        if (cacheEnabled) {
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("Updated cache, key=" + key + ", number of events: " + eventMultiMap.size());
//            }
//            getCalendarEventListCache().put(new Element(key, eventMultiMap));
//        }
//    }
//
//    public void invalidateCacheElement(String key) {
//        if (getCalendarEventListCache().get(key) != null) {
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("removed from cache, key=" + key);
//            }
//            if (!getCalendarEventListCache().remove(key)) {
//                if (LOG.isDebugEnabled()) {
//                    LOG.debug("Failed to invalidate cahce element with key: " + key);
//                }
//            }
//        }
//    }
//
//    private Cache getCalendarEventListCache() {
//        return cacheManager.getCache("calendarEventListCache");
//    }
//
//    public void cacheEnabled(boolean cacheEnabled) {
//        this.cacheEnabled = cacheEnabled;
//    }

}
