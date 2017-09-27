package com.gunnarro.calendar.mvc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.MultiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.calendar.domain.calendar.CalendarConfig;
import com.gunnarro.calendar.domain.calendar.CalendarEvent;
import com.gunnarro.calendar.parser.ICalParser;
import com.gunnarro.calendar.service.exception.ApplicationException;

@Controller
@Scope("session")
public class ConfigController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigController.class);

    @ResponseBody
    @RequestMapping(value = "/config/listcalendarconfig", method = RequestMethod.GET)
    public ModelAndView listCalendarConfigurations() {
        ModelAndView modelView = new ModelAndView("config/list-calendar-config");
        modelView.addObject("calendarConfigList", calendarService.getCalendarConfigurations());
        return modelView;
    }

    // ---------------------------------------------
    // View, New and update calendar configuration
    // ---------------------------------------------

    @RequestMapping(value = "/config/calendar/{configId}", method = RequestMethod.GET)
    public ModelAndView viewCalendarConfig(@PathVariable("configId") int configId) {
        CalendarConfig calendarConfiguration = calendarService.getCalendarConfiguration(configId);
        if (calendarConfiguration == null) {
            throw new ApplicationException("Object Not Found, configId=" + configId);
        }
        ModelAndView modelView = new ModelAndView("config/view-calendar-config");
        modelView.addObject("config", calendarConfiguration);
        return modelView;
    }

    @RequestMapping(value = "/config/calendar/new", method = RequestMethod.GET)
    public String initNewCalendarConfigForm(Map<String, Object> model) {
        CalendarConfig config = new CalendarConfig();
        model.put("config", config);
        return "config/edit-calendar-config";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/config/calendar/new", method = RequestMethod.POST)
    public String processNewCalendarConfigForm(CalendarConfig config, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(config.toString());
        }
        if (result.hasErrors()) {
            return "config/edit-calendar-config";
        } else {
            this.calendarService.saveCalendarConfiguration(config);
            status.setComplete();
            return "redirect:/config/listcalendarconfig";
        }
    }

    @RequestMapping(value = "/config/calendar/edit/{configId}", method = RequestMethod.GET)
    public String initUpdateCalendarConfigForm(@PathVariable("configId") int configId, Model model) {
        CalendarConfig config = calendarService.getCalendarConfiguration(configId);
        if (config == null) {
            throw new ApplicationException("Object Not Found, configId=" + configId);
        }
        model.addAttribute("config", config);
        return "config/edit-calendar-config";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/config/calendar/edit/{configId}", method = RequestMethod.PUT)
    public String processUpdateCalendarConfigForm(CalendarConfig config, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "config/edit-calendar-config";
        } else {
        	calendarService.saveCalendarConfiguration(config);
            status.setComplete();
            return "redirect:/config/listcalendarconfig";
        }
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/config/calendar/delete/{configId}", method = RequestMethod.GET)
    public String deleteUserAccount(@PathVariable("configId") int configId) {
        CalendarConfig config = calendarService.getCalendarConfiguration(configId);
        if (config == null) {
            throw new ApplicationException("Object Not Found, configId=" + configId);
        }
        calendarService.deleteCalendarConfig(configId);
        return "redirect:/config/listcalendarconfig";
    }

    @RequestMapping(value = "/config/calendar/verify/{configId}", method = RequestMethod.GET)
    public ModelAndView viewVerifyCalendarConfigUrl(@PathVariable("configId") int configId) {
        CalendarConfig calendarConfiguration = calendarService.getCalendarConfiguration(configId);
        if (calendarConfiguration == null) {
            throw new ApplicationException("Object Not Found, configId=" + configId);
        }
        ModelAndView modelView = new ModelAndView("calendar/list-events-test");
        modelView.addObject("errorMsg", null);
        ICalParser parser = new ICalParser();
        List<CalendarEvent> eventList = new ArrayList<CalendarEvent>();
        try {
            MultiMap multiMap = parser.parseEventsToMultiMap(calendarConfiguration.getUrl());
            Set<Map.Entry<Date, List<CalendarEvent>>> calenderEventEntrySet = multiMap.entrySet();
            for (Map.Entry<Date, List<CalendarEvent>> entry : calenderEventEntrySet) {
                eventList.addAll(entry.getValue());
            }
        } catch (Exception e) {
            LOG.error("", e);
            modelView.addObject("errorMsg", e.getMessage());
        }
        modelView.addObject("eventList", eventList);
        modelView.addObject("numberOfEvents", eventList.size());
        modelView.addObject("calendarInfo", calendarConfiguration.getDescription() + " " + calendarConfiguration.getUrl());
        return modelView;
    }
}
