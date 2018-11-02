package com.gunnarro.dietmanager.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.dietmanager.domain.togetherness.TogethernessLog;
import com.gunnarro.dietmanager.service.TogethernessService;
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
public class TogethernessController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(TogethernessController.class);

    @Autowired
    private TogethernessService togethernessService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat(Utility.DATE_TIME_PATTERN);
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
    }

    @RequestMapping(value = "/togetherness/log", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView gettogethernesssLog() {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        List<TogethernessLog> logs = togethernessService.getLogs(loggedInUser.getId());
        if (LOG.isDebugEnabled()) {
            LOG.debug("number of log entries: " + logs.size());
        }
        ModelAndView modelView = new ModelAndView("log/view-togetherness-logs");
        modelView.getModel().put("logs", logs);
        return modelView;
    }

    @RequestMapping(value = "/togetherness/log/view/{logId}", method = RequestMethod.GET)
    public ModelAndView logView(@PathVariable("logId") int logId) {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException("Not logged in!");
        }
        TogethernessLog log = togethernessService.getLog(loggedInUser.getId(), logId);
        ModelAndView modelView = new ModelAndView("log/view-log");
        modelView.getModel().put("log", log);
        return modelView;
    }

    // ---------------------------------------------
    // New and update log event
    // ---------------------------------------------

    @RequestMapping(value = "/togetherness/log/new", method = RequestMethod.GET)
    public String initNewLogForm(Map<String, Object> model) {
        TogethernessLog log = new TogethernessLog();
        log.setFromDate(new DateTime().toDate());
        log.setToDate(new DateTime().plusDays(1).toDate());
        model.put("log", log);
        return "log/edit-log";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/togetherness/log/new", method = RequestMethod.POST)
    public String processNewLogForm(@Valid @ModelAttribute("log") TogethernessLog log, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(log.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "log/edit-log";
        } else {
            // set created by user id
            log.setFkUserId(authenticationFacade.getLoggedInUser().getId());
            this.togethernessService.saveLog(log);
            status.setComplete();
            return "redirect:/diet/log/events";
        }
    }

    @RequestMapping(value = "/togetherness/log/edit/{logId}", method = RequestMethod.GET)
    public String initUpdateLogForm(@PathVariable("logId") int logId, Model model) {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        TogethernessLog log = togethernessService.getLog(loggedInUser.getId(), logId);
        if (log == null) {
            throw new ApplicationException("Object Not Found, logId=" + logId);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(log.toString());
        }
        model.addAttribute("log", log);
        return "log/edit-togetherness-log";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/togetherness/log/edit/{logId}", method = RequestMethod.PUT)
    public String processUpdateLogForm(@Valid @ModelAttribute("log") TogethernessLog log, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(log.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "togetherness/edit-log";
        } else {
            togethernessService.saveLog(log);
            status.setComplete();
            return "redirect:/togetherness/log";
        }
    }

    /**
     * 
     */
    @RequestMapping(value = "/togetherness/log/delete/{logId}", method = RequestMethod.GET)
    public String deletelog(@PathVariable("logId") int logId) {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        TogethernessLog log = togethernessService.getLog(loggedInUser.getId(), logId);
        if (log == null) {
            throw new ApplicationException("Object Not Found, logId=" + logId);
        }
        togethernessService.deleteLog(loggedInUser.getId(), logId);
        return "redirect:/togetherness/log";
    }
}
