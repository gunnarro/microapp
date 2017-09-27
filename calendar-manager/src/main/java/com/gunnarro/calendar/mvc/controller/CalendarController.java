package com.gunnarro.calendar.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.Predicate;
//import org.apache.commons.collections.Predicate;
import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.calendar.domain.calendar.Agenda;
import com.gunnarro.calendar.domain.calendar.CalendarEvent;
import com.gunnarro.calendar.domain.calendar.CalendarEventPredicateApache;
import com.gunnarro.calendar.domain.party.Person;
import com.gunnarro.calendar.domain.view.CommonFormInputData;
import com.gunnarro.calendar.domain.view.SearchActivity;
import com.gunnarro.calendar.domain.view.list.Item;
import com.gunnarro.calendar.service.exception.ApplicationException;
import com.gunnarro.calendar.utility.DateFilter;
import com.gunnarro.calendar.utility.Utility;

//import java.util.function.Predicate;

@Controller
@Scope("session")
public class CalendarController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(CalendarController.class);
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
    	SimpleDateFormat sdf = new SimpleDateFormat(Utility.DATE_PATTERN);
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
        // binder.setRequiredFields(new String[] {"id", "name", "fromDate", "toDate"});
    }

    @RequestMapping(value = "/cache/statistic", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView cacheStatistic() {
        ModelAndView modelView = new ModelAndView("config/cache-statistic-view");
        modelView.getModel().put("ehcachestat", null);
        return modelView;
    }

    @RequestMapping(value = "/calendar/event/{eventId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewEvent(@PathVariable("eventId") int eventId, Map<String, Object> model) {
        CalendarEvent calendarEvent = calendarService.getCalendarEvent(eventId);
        if (calendarEvent == null) {
            throw new ApplicationException("No event found for Id:" + calendarEvent);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(calendarEvent.toString());
        }
        List<Person> followers = calendarService.getFollowers(eventId);
        List<Person> participants = calendarService.getFollowers(eventId);

        ModelAndView modelView = new ModelAndView("calendar/view-event");
        modelView.getModel().put("event", calendarEvent);
        modelView.getModel().put("followers", mapToItems(followers));
        modelView.getModel().put("numberOfFollowers", followers.size());
        modelView.getModel().put("numberOfRegistreredParticipants", participants.size());
        return modelView;
    }

    @RequestMapping(value = "/calendar/event/agenda/{eventId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewEventAgenda(@PathVariable("eventId") int eventId, Map<String, Object> model) {
        Agenda agenda = calendarService.getAgenda(eventId);
        if (agenda == null) {
            throw new ApplicationException("No agenda found for Id:" + eventId);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(agenda.toString());
        }

        ModelAndView modelView = new ModelAndView("calendar/view-agenda");
        modelView.getModel().put("agenda", agenda);
        return modelView;
    }

    private List<Item> mapToItems(List<Person> list) {
        List<Item> items = new ArrayList<Item>();
        if (list != null) {
            for (Person p : list) {
                Item item = new Item();
                item.setId(p.getId());
                item.setValue(p.getFullName());
                item.setType("");
                items.add(item);
            }
        }
        return items;
    }

    @RequestMapping(value = "/calendar/search/events", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView searchCalendarEvents(@RequestParam("reload") boolean reload, @RequestParam("period") String period, @RequestParam("amount") int amount,
            @RequestParam("type") String type, @RequestParam("name") String name) {
        return listCalendarEvents(reload, period, amount, type, name);
    }

    @RequestMapping(value = "/calendar/listevents/{reload}/{period}/{amount}/{type}/{name}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView listCalendarEvents(@PathVariable("reload") boolean reload, @PathVariable("period") String period, @PathVariable("amount") int amount,
            @PathVariable("type") String type, @PathVariable("name") String name) {

        StringBuffer infoMsg = new StringBuffer();
        List<String> teamNames = calendarService.getCalendarConfigurationTeamNames();
        MultiMap multiMap = new MultiValueMap();
        List<CalendarEvent> filters = new ArrayList<CalendarEvent>();

        if (type.equalsIgnoreCase("all")) {
            for (Item item : calendarService.getCalendarUrlsActive()) {
                String url = calendarService.getCalendarUrlByName(item.getType());
                try {
                    MultiMap tmpMultiMap = calendarService.getCalendarEventFromICalAsMultiMap(url, item.getType(), reload);
                    multiMap.putAll(tmpMultiMap);
                    filters.add(CalendarEvent.createFilter(item.getType(), item.getName()));
                } catch (ApplicationException ae) {
                    // only log this, typically thrown when remote ical file is not available
                    LOG.error(null, ae);
                    infoMsg.append("Failed getting: " + item.getType()).append("\n");
                }
            }
        } else {
            // Selected single type and/or name
            filters.add(CalendarEvent.createFilter(type, name));
            try {
                String url = calendarService.getCalendarUrlByName(type);
                multiMap = calendarService.getCalendarEventFromICalAsMultiMap(url, type, reload);
            } catch (ApplicationException ae) {
                // only log this, typically thrown when remote ical file not
                // available
                LOG.error(null, ae);
                infoMsg.append("Failed getting: " + type).append("\n");
            }
        }

        filters.add(CalendarEvent.createFilter("match", null));
        filters.add(CalendarEvent.createFilter("training", null));
        filters.add(CalendarEvent.createFilter("cup", null));
        filters.add(CalendarEvent.createFilter("tournament", null));
        filters.add(CalendarEvent.createFilter("social", null));
        filters.add(CalendarEvent.createFilter("volunteer", null));
        filters.add(CalendarEvent.createFilter("meeting", null));
        // Get local events, note this are never put into the cache, therefore
        // it can always be added at this point in code.
        MultiMap calendarEventMultiMap = calendarService.getCalendarEventMultiMap();
        if (calendarEventMultiMap.size() > 0) {
            multiMap.putAll(calendarEventMultiMap);
        }

        // get all the set of keys
        Set<Map.Entry<Date, List<CalendarEvent>>> calenderEventEntrySet = multiMap.entrySet();
        // Sort and filter the map
        List<Entry<Date, List<CalendarEvent>>> filteredMatchEntryList = filterEntrySet(period, amount, filters, calenderEventEntrySet);

        String selectedPeriodInfo = Utility.getPeriodInfo(period, amount);

        List<Item> calendarUrls = calendarService.getCalendarUrlsActive();
        SearchActivity searchActivity = new SearchActivity();
        searchActivity.setAmount(amount);
        searchActivity.setReload(reload);
        searchActivity.setType(type);
        searchActivity.setPeriod(period);
        searchActivity.setName(name);
        ModelAndView modelView = new ModelAndView("calendar/list-events-view");
        modelView.getModel().put("infoMsg", infoMsg.toString());
        modelView.getModel().put("searchActivity", searchActivity);
        modelView.getModel().put("lastReloaded", new Date());
        modelView.getModel().put("lastReloadedFromProvider", new Date());
        modelView.getModel().put("calendarUrls", calendarUrls);
        modelView.getModel().put("names", teamNames);
        modelView.getModel().put("entryEventList", filteredMatchEntryList);
        modelView.getModel().put("numberOfMatches", filteredMatchEntryList.size());
        modelView.getModel().put("numberOfMatchesTotal", calenderEventEntrySet.size());
        modelView.getModel().put("numberOfMatchDays", filteredMatchEntryList.size());
        modelView.getModel().put("selectedPeriodTxt", selectedPeriodInfo);
        return modelView;
    }

    /**
     * Java 1.8 Only for testing
     */
    // public List<CalendarEvent> filterEntrySetNew(String period, int amount,
    // Predicate<CalendarEvent> eventPredicate, List<CalendarEvent> entryList) {
    // List<CalendarEvent> events =
    // entryList.stream().filter(eventPredicate).collect(Collectors.toList());
    // return events;
    // }

    /**
     * Should create a copy of the original list which only contains the
     * filtered matches.
     * 
     * @param period
     * @param amount
     * @param leagueFilter
     * @param teamNameFilter
     * @param entrySet
     * @return
     */
    protected List<Map.Entry<Date, List<CalendarEvent>>> filterEntrySet(String period, int amount, List<CalendarEvent> filters, Set<Map.Entry<Date, List<CalendarEvent>>> entrySet) {
        List<Map.Entry<Date, List<CalendarEvent>>> list = new ArrayList<Map.Entry<Date, List<CalendarEvent>>>();
        if (LOG.isDebugEnabled()) {
            LOG.debug("------------------------------");
            LOG.debug("Start - Filters: " + (filters != null ? filters.toString() : "None"));
            LOG.debug("Start - Before filtering, number of events: " + entrySet.size());
        }
        int totalNumberOfEvents = 0;
        int numberOfEvents = 0;
        Predicate<CalendarEvent> predicate = CalendarEventPredicateApache.createPredicate(filters);
        for (Map.Entry<Date, List<CalendarEvent>> entry : entrySet) {
            // first check that start date is in the selected period
            if (DateFilter.filterByDate(period, entry.getKey(), amount)) {
                // Requires Java 1.8: new way to filter
                // List<CalendarEvent> filteredEventList =
                // CalendarEventPredicates.filterCalendarEvents(entry.getValue(),CalendarEventPredicates.isCombinedTypeAndNamePairs(filters));
                // Filter with apache common collections
                List<CalendarEvent> filteredEventList = new ArrayList<CalendarEvent>(entry.getValue());
                CollectionUtils.filter(filteredEventList, predicate);
                // Only include days that has events
                if (filteredEventList.size() > 0) {
                    Collections.sort(filteredEventList);
                    list.add(new AbstractMap.SimpleEntry<Date, List<CalendarEvent>>(entry.getKey(), filteredEventList));
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Filtered out by date, event: " + entry.getKey() + ", period: " + period + ", today: " + Utility.formatTime(System.currentTimeMillis(), null)
                            + ", pluss days: " + amount);
                }
            }
        }// end for loop
        if (LOG.isDebugEnabled()) {
            LOG.debug("After filtered, number of entries: " + list.size());
//            LOG.debug("After filtered, events: " + numberOfEvents + ", total number of events:" + totalNumberOfEvents);
            LOG.debug("------------------------------");
        }
        // Finally, sort all events by date
        Collections.sort(list, new Comparator<Map.Entry<Date, List<CalendarEvent>>>() {
            @Override
            public int compare(Map.Entry<Date, List<CalendarEvent>> e1, Map.Entry<Date, List<CalendarEvent>> e2) {
                return e1.getKey().compareTo(e2.getKey());
            }
        });
        return list;
    }

    // ---------------------------------------------
    // New and update event
    // ---------------------------------------------

    /**
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/calendar/event/new", method = RequestMethod.GET)
    public String initNewEventForm(Map<String, Object> model) {
        CalendarEvent event = new CalendarEvent();
        event.setName("event-name");
        event.setStartDate(Utility.roundToClosestHour(event.getStartDate()));
        event.setEndDate(Utility.addMinutes(event.getStartDate(), 90));
        event.setStartTime(Utility.formatTime(event.getStartDate().getTime(), "HH:mm"));
        event.setEndTime(Utility.formatTime(event.getEndDate().getTime(), "HH:mm"));
        event.setType("meeting");
        model.put("calendarevent", event);
        return "calendar/edit-event";
    }

    /**
     * User POST for new
     * 
     * @param event
     * @param result
     * @param status
     * @return
     */
    @RequestMapping(value = "/calendar/event/new", method = RequestMethod.POST)
    public String processNewEventForm(@Valid @ModelAttribute("calendarevent") CalendarEvent event, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "calendar/edit-event";
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Status:" + status.toString());
                LOG.debug("Event: " + event);
            }

            if (DateUtils.isSameDay(event.getStartDate(), event.getEndDate())) {
                this.calendarService.saveCalendarEvent(event);
            } else {
                // Different start and end dates, therefore create one equal
                // event per day, max 7 days
                for (int i = 0; i < 8; i++) {
                    Date nextDay = DateUtils.addDays(event.getStartDate(), i);
                    if (nextDay.before(event.getEndDate()) || DateUtils.isSameDay(nextDay, event.getEndDate())) {
                        this.calendarService.saveCalendarEvent(CalendarEvent.copyEvent(event, nextDay));
                    }
                }
            }

            // Create more events if requested
            if (LOG.isDebugEnabled()) {
                LOG.debug("Create number of events: " + event.getReiterations());
            }
            if (event.getReiterations() > 1) {
                generateWeeklyEventsAndSave(event);
            }
            status.setComplete();
            return "redirect:/calendar/listevents/false/day/0/all/all";
        }
    }

    /**
     * 
     * @param trainingId
     * @param model
     * @return
     */
    @RequestMapping(value = "/calendar/event/edit/{eventId}", method = RequestMethod.GET)
    public String initUpdateEventForm(@PathVariable("eventId") int eventId, Model model) {
        CalendarEvent event = calendarService.getCalendarEvent(eventId);
        if (event == null) {
            throw new ApplicationException("Object Not Found, eventId=" + eventId);
        }
        model.addAttribute("calendarevent", event);
        return "calendar/edit-event";
    }

    /**
     * Use PUT for updates
     * 
     * @param event
     * @param result
     * @param status
     * @return
     */
    @RequestMapping(value = "/calendar/event/edit/{eventId}", method = RequestMethod.POST)
    public String processUpdateEventForm(@Valid @ModelAttribute("calendarevent") CalendarEvent event, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(event.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "calendar/edit-event";
        } else {
            calendarService.saveCalendarEvent(event);
            status.setComplete();
            return "redirect:/calendar/listevents/false/day/0/all/all";
        }
    }

    /**
     * 
     * @param eventId
     * @return
     */
    @RequestMapping(value = "/calendar/event/delete/{eventId}", method = RequestMethod.GET)
    public String deleteEvent(@PathVariable("eventId") int eventId) {
        CalendarEvent event = calendarService.getCalendarEvent(eventId);
        if (event == null) {
            throw new ApplicationException("Object Not Found, eventId=" + eventId);
        }
        calendarService.deleteCalendarEvent(eventId);
        return "redirect:/calendar/listevents/false/day/0/all/all";
    }

    /**
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/calendar/event/copy", method = RequestMethod.GET)
    public String initCopyEventForm(Map<String, Object> model) {
        List<Item> items = calendarService.getCalendarEventStartDates();
        CommonFormInputData inputData = new CommonFormInputData();
        inputData.setFromDate(Calendar.getInstance().getTime());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        inputData.setToDate(cal.getTime());
        inputData.setItems(items);
        model.put("inputData", inputData);
        return "calendar/copy-events";
    }
    
    /**
     * 
     * @param input
     * @param result
     * @param status
     * @return
     */
    @RequestMapping(value = "/calendar/event/copy", method = RequestMethod.POST)
    public String processCopyEventForm(@Valid @ModelAttribute("inputData") CommonFormInputData input, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "calendar/copy-events";
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Status:" + status.toString());
                LOG.debug(input.toString());
            }
            if (DateUtils.isSameDay(input.getFromDate(), input.getToDate())) {
            	result.rejectValue("toDate", "msg.error.fromdate-todate-equal","To Date can not be equal to From Date!");
                return "calendar/copy-events";
            } else {
            	if (isEventAlreadyCopied(input.getToDate(), input.getName())) {
            		result.rejectValue("toDate", "msg.error.event-copied", input.getName() + " event is already copied to " + input.getToDate());
                    return "calendar/copy-event";
            	}
                copyEventsForDay(input.getFromDate(), input.getToDate());
            }

            status.setComplete();
            return "redirect:/calendar/listevents/false/week/0/all/all";
        }
    }

    /**
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/calendar/event/copy/{eventId}", method = RequestMethod.GET)
    public String initCopyEventByIdForm(@PathVariable("eventId") int eventId, Map<String, Object> model) {
        CalendarEvent calendarEvent = calendarService.getCalendarEvent(eventId);
        CommonFormInputData inputData = new CommonFormInputData();
        inputData.setId(calendarEvent.getId());
        inputData.setName(calendarEvent.getName());
        inputData.setFromDate(calendarEvent.getStartDate());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        inputData.setToDate(cal.getTime());
        model.put("inputData", inputData);
        return "calendar/copy-event";
    }

    @RequestMapping(value = "/calendar/event/copy/{eventId}", method = RequestMethod.POST)
    public String processCopyEventByIdForm(@Valid @ModelAttribute("inputData") CommonFormInputData input, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "calendar/copy-event";
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Status:" + status.toString());
                LOG.debug(input.toString());
            }

            if (DateUtils.isSameDay(input.getFromDate(), input.getToDate())) {
            	result.rejectValue("toDate", "msg.error.fromdate-todate-equal","To Date can not be equal to From Date!");
                return "calendar/copy-event";
            } else {
            	if (isEventAlreadyCopied(input.getToDate(), input.getName())) {
            		result.rejectValue("toDate", "msg.error.event-copied", input.getName() + " event is already copied to " + input.getToDate());
            		return "calendar/copy-event";
            	}
                CalendarEvent calendarEvent = calendarService.getCalendarEvent(input.getId());
                calendarService.saveCalendarEvent(CalendarEvent.copyEvent(calendarEvent, input.getToDate()));
                if (LOG.isDebugEnabled()) {
                	LOG.debug("Copied event: " + calendarEvent.getName() + " " + calendarEvent.getName() + " to date " + input.getToDate());
                }
            }
            status.setComplete();
            return "redirect:/calendar/listevents/false/week/0/all/all";
        }
    }
    
    private boolean isEventAlreadyCopied(Date forDate, String eventName) {
    	List<CalendarEvent> calendarEvents = calendarService.getCalendarEvents(forDate);
    	for (CalendarEvent event  : calendarEvents) {
    		if (event.getName().equalsIgnoreCase(eventName)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * 
     * @param agendaId
     * @param model
     * @return
     */
    @RequestMapping(value = "/calendar/event/agenda/edit/{agendaId}", method = RequestMethod.GET)
    public String initUpdateEventAgandaForm(@PathVariable("agendaId") int agendaId, Model model) {
        Agenda agenda = calendarService.getAgenda(agendaId);
        if (agenda == null) {
            throw new ApplicationException("Object Not Found, agendaId=" + agendaId);
        }
        model.addAttribute("agenda", agenda);
        return "calendar/edit-agenda";
    }

    /**
     * Use PUT for updates
     * 
     * @param event
     * @param result
     * @param status
     * @return
     */
    @RequestMapping(value = "/calendar/event/agenda/edit/{agendaId}", method = RequestMethod.PUT)
    public String processUpdateEventAgendaForm(@Valid @ModelAttribute("agenda") Agenda agenda, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(agenda.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "calendar/edit-agenda";
        } else {
            calendarService.saveAgenda(agenda);
            status.setComplete();
            return "redirect:/calendar/event/agenda/" + agenda.getId();
        }
    }

    /**
     * 
     * @param event
     */
    protected void generateWeeklyEventsAndSave(CalendarEvent event) {
        DateTime dt = new DateTime(event.getStartDate());
        for (int i = 1; i <= event.getReiterations(); i++) {
            dt = dt.plusDays(7);
            calendarService.saveCalendarEvent(CalendarEvent.copyEvent(event, dt.toDate()));
        }
    }

    /**
     * Copy all events from one day to another
     * 
     * @param forDate date to copy events from
     * @param toDate to copy events to
     */
    protected void copyEventsForDay(Date forDate, Date toDate) {
        List<CalendarEvent> calendarEvents = calendarService.getCalendarEvents(forDate);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start copying number of events " + calendarEvents.size() + " for date " + forDate + " to date " + toDate);
        }
        for (CalendarEvent event : calendarEvents) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("check before copy event: " + event.getStartDate() + " " + event.getName());
            }
            // Avoid duplicates, therefore check that the event dose not already
            // exits
            List<CalendarEvent> findCalendarEvents = calendarService.findCalendarEvents(toDate, event.getName());
            if (findCalendarEvents.size() == 0) {
                calendarService.saveCalendarEvent(CalendarEvent.copyEvent(event, toDate));
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Event exist: " + findCalendarEvents);
                }
            }
        }
    }

}
