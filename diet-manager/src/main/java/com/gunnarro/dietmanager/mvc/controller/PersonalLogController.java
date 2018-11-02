package com.gunnarro.dietmanager.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.dietmanager.service.exception.ApplicationException;
import com.gunnarro.dietmanager.utility.Utility;
import com.gunnarro.useraccount.domain.user.LocalUser;

/**
 * 
 * http://microbuilder.io/blog/2016/01/10/plotting-json-data-with-chart-js.html
 * https://github.com/chartjs/Chart.js/tree/master/dist
 * 
 * @author admin
 *
 */
@Controller
@Scope("session")
public class PersonalLogController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(PersonalLogController.class);

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat(Utility.DATE_TIME_PATTERN);
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
    }

//    @RequestMapping(value = "/personal/logs", method = RequestMethod.GET)
//    @ResponseBody
//    public ModelAndView getLogEvents() {
//        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
//        List<LogEntry> logs = logEventService.getAllLogEvents(loggedInUser.getId(), 2);
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("number of log entries: " + logs.size());
//        }
//        ModelAndView modelView = new ModelAndView("log/view-event-logs");
//        modelView.getModel().put("logs", logs);
//        modelView.getModel().put("number_of_logs", logs.size());
//        return modelView;
//    }

    @RequestMapping(value = "/personal/log/view/{logId}", method = RequestMethod.GET)
    public ModelAndView logEventView(@PathVariable("logId") int logId) {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException("Not logged in!");
        }
        LogEntry logEvent = logEventService.getLogEvent(loggedInUser.getId(), logId);
        LOG.debug(logEvent.toString());
        ModelAndView modelView = new ModelAndView("log/view-log-event");
        modelView.getModel().put("log", logEvent);
        return modelView;
    }

//    @RequestMapping(value = "/personal/logs/txt", method = RequestMethod.GET)
//    @ResponseBody
//    public ModelAndView viewLogEventsAsPlainText() {
//        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
//        List<LogEntry> logs = logEventService.getAllLogEvents(loggedInUser.getId());
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("number of log entries: " + logs.size());
//        }
//        ModelAndView modelView = new ModelAndView("log/view-event-logs-txt");
//        modelView.getModel().put("logs", logs);
//        return modelView;
//    }

    // ---------------------------------------------
    // New and update log event
    // ---------------------------------------------

    @RequestMapping(value = "/personal/log/new", method = RequestMethod.GET)
    public String initNewLogEventForm(Map<String, Object> model) {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException("Not logged in!");
        }
        LogEntry log = new LogEntry();
        log.setLevel("INFO");
        log.setCreatedDate(new Date());
        log.setLastModifiedTime(System.currentTimeMillis());
        log.setCreatedByUser(loggedInUser.getUsername());
        model.put("log", log);
        return "log/edit-event-log";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/personal/log/new", method = RequestMethod.POST)
    public String processNewLogEventForm(@Valid @ModelAttribute("log") LogEntry log, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(log.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "log/edit-event-log";
        } else {
            // set created by user id
            log.setFkUserId(authenticationFacade.getLoggedInUser().getId());
            this.logEventService.saveLogEvent(log);
            status.setComplete();
            return "redirect:/diet/log/events";
        }
    }

    @RequestMapping(value = "/personal/log/edit/{logEventId}", method = RequestMethod.GET)
    public String initUpdateLogEventForm(@PathVariable("logEventId") int logEventId, Model model) {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        LogEntry log = logEventService.getLogEvent(loggedInUser.getId(), logEventId);
        if (log == null) {
            throw new ApplicationException("Object Not Found, logEventId=" + logEventId);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(log.toString());
        }
        model.addAttribute("log", log);
        return "log/edit-event-log";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/personal/log/edit", method = RequestMethod.POST)
    public String processUpdateLogEventForm(@Valid @ModelAttribute("log") LogEntry log, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(log.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "log/edit-event-log";
        } else {
            logEventService.saveLogEvent(log);
            status.setComplete();
            return "redirect:/diet/log/events";
        }
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/personal/log/edit/{logEventId}", method = RequestMethod.POST)
    public String processUpdateLogEventIdForm(@Valid @ModelAttribute("log") LogEntry log, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(log.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "log/edit-event-log";
        } else {
            logEventService.saveLogEvent(log);
            status.setComplete();
            return "redirect:/diet/log/events";
        }
    }

    /**
     * 
     */
    @RequestMapping(value = "/personal/log/delete/{logEntryId}", method = RequestMethod.GET)
    public String deletelogEvent(@PathVariable("logEntryId") int logEntryId) {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        LogEntry log = logEventService.getLogEvent(loggedInUser.getId(), logEntryId);
        if (log == null) {
            throw new ApplicationException("Object Not Found, logEntryId=" + logEntryId);
        }
        logEventService.deleteLogEvent(loggedInUser.getId(), logEntryId);
        return "redirect:/diet/log/events";
    }

}
