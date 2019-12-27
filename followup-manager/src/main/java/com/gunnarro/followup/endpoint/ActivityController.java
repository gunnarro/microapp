package com.gunnarro.followup.endpoint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.followup.domain.activity.ActivityLog;
import com.gunnarro.followup.domain.log.LogEntry;
import com.gunnarro.followup.service.exception.ApplicationException;
import com.gunnarro.followup.utility.Utility;
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
public class ActivityController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ActivityController.class);

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat(Utility.DATE_TIME_PATTERN);
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
    }

    @GetMapping("/activity/logs")
    @ResponseBody
    public ModelAndView getActivityLogs() {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        List<ActivityLog> logs = activityService.getActivityLogs(loggedInUser.getId());
        if (LOG.isDebugEnabled()) {
            LOG.debug("number of log entries: " + logs.size());
        }
        ModelAndView modelView = new ModelAndView("activity/view-activity-logs");
        modelView.getModel().put("logs", logs);
        modelView.getModel().put("numberOfLogs", logs.size());
        modelView.getModel().put("logsFromDate", !logs.isEmpty() ? logs.get(logs.size() - 1).getCreatedDate() : new Date());
        modelView.getModel().put("logsToDate", !logs.isEmpty() ? logs.get(0).getCreatedDate() : new Date());
        return modelView;
    }

    @GetMapping("/activity/log/view/{logId}")
    public ModelAndView activitylogView(@PathVariable("logId") int logId) {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException("Not logged in!");
        }
        ActivityLog activityLog = activityService.getActivityLog(loggedInUser.getId(), logId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("{}", activityLog);
        }
        ModelAndView modelView = new ModelAndView("activity/view-activity-log");
        modelView.getModel().put("log", activityLog);
        return modelView;
    }

    // ---------------------------------------------
    // New and update log event
    // ---------------------------------------------

    @GetMapping("/activity/log/new")
    public String initNewActivityLogForm(Map<String, Object> model) {
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
        return "activity/edit-activity-log";
    }

    /**
     * User POST for new
     * 
     */
    @PostMapping("/activity/log/new")
    public String processNewActivityLogForm(@Valid @ModelAttribute("log") ActivityLog log, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(log.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "activity/edit-activity-log";
        } else {
            // set created by user id
            log.setFkUserId(authenticationFacade.getLoggedInUser().getId());
            this.activityService.saveActivityLog(log);
            status.setComplete();
            return "redirect:/activity/logs";
        }
    }

    @GetMapping("/activity/log/edit/{activityId}")
    public String initUpdateActivityForm(@PathVariable("activityId") int activityId, Model model) {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        ActivityLog log = activityService.getActivityLog(loggedInUser.getId(), activityId);
        if (log == null) {
            throw new ApplicationException("Object Not Found, activityId=" + activityId);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(log.toString());
        }
        model.addAttribute("log", log);
        return "activity/edit-activity-log";
    }

    /**
     * Use PUT for updates
     * 
     */
    @PostMapping("/activity/log/edit")
    public String processUpdateActivityLogForm(@Valid @ModelAttribute("log") ActivityLog log, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(log.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "activity/edit-activity-log";
        } else {
            activityService.saveActivityLog(log);
            status.setComplete();
            return "redirect:/activity/logs";
        }
    }

    /**
     * Use PUT for updates
     * 
     */
    @PostMapping("/activity/log/edit/{activityId}")
    public String processUpdateActivityLogIdForm(@Valid @ModelAttribute("log") ActivityLog log, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(log.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "activity/edit-activity-log";
        } else {
            activityService.saveActivityLog(log);
            status.setComplete();
            return "redirect:/activity/logs";
        }
    }

    /**
     * 
     */
    @RequestMapping(value = "/activity/log/delete/{activityId}", method = RequestMethod.GET)
    public String deleteActivitylog(@PathVariable("activityId") int activityId) {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        ActivityLog log = activityService.getActivityLog(loggedInUser.getId(), activityId);
        if (log == null) {
            throw new ApplicationException("Object Not Found, activityId=" + activityId);
        }
        activityService.deleteActivityLog(loggedInUser.getId(), activityId);
        return "redirect:/activity/logs";
    }
}
