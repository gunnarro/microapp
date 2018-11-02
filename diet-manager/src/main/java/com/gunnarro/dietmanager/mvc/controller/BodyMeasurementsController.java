package com.gunnarro.dietmanager.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
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

import com.gunnarro.dietmanager.domain.health.HealthLogEntry;
import com.gunnarro.dietmanager.domain.health.ReferenceData;
import com.gunnarro.dietmanager.domain.health.Trend;
import com.gunnarro.dietmanager.domain.statistic.BodyMeasurementStatistic;
import com.gunnarro.dietmanager.service.exception.ApplicationException;
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
public class BodyMeasurementsController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(BodyMeasurementsController.class);

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
    }

    /**
     * 
     */
    @RequestMapping(value = "/diet/body/measurement/delete/{id}", method = RequestMethod.GET)
    public String deleteLogEntry(@PathVariable("id") int id) {
        HealthLogEntry log = dietManagerService.getBodyMeasurementLog(id);
        if (log == null) {
            throw new ApplicationException("Object Not Found, logEntryId=" + id);
        }
        dietManagerService.deleteBodyMeasurementLog(id);
        return "redirect:/diet/body/measurement/log";
    }

    @RequestMapping(value = "/diet/body/measurement/graph", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getChart() {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException("Not logged in!");
        }
        ModelAndView modelView = new ModelAndView("log/view-graph");
        return modelView;
    }

    @RequestMapping(value = "/diet/body/measurement/details", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getWeightDetails() {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException("Not logged in!");
        }

        List<HealthLogEntry> logs = dietManagerService.getBodyMeasurementLogs(loggedInUser.getId());

        BodyMeasurementStatistic statistic = new BodyMeasurementStatistic();
        // Set trends
        Trend trend = new Trend();
        double avgWeightIncrease = 0;
        double avgWeightUp = 0;
        double avgWeightDown = 0;
        double avgWeightUpStep = 0;
        double avgWeightDownStep = 0;
        double avgHeightIncrease = 0;
        DateTime toDate = new DateTime();
        DateTime lastDays = toDate.minusDays(365 * 5);// last 5 year
        if (logs.size() > 0) {
            trend.setNumberOfMeasurements(logs.size());
            trend.setFromDate(logs.get(0).getLogDate());
            trend.setToDate(logs.get(logs.size() - 1).getLogDate());
            int step = 4;
            // The most recent log is at index 0
            for (int i = 0; i < logs.size() - 1; i++) {
                if (logs.get(i).getLogDate().before(lastDays.toDate())) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Reached filter limit of days! Stopped at date: " + lastDays.toString());
                    }
                    break;
                }

                statistic.increaseNumberOfMeasurements();

                if (i % step == 0 && (i + step) < logs.size()) {
                    statistic.increaseNumberOfMeasurementsStep();
                    double diffStep = logs.get(i).getWeight() - logs.get(i + step).getWeight();
                    if (diffStep > 0) {
                        trend.increaseUpStep();
                        avgWeightUpStep = +diffStep;
                    } else if (diffStep < 0) {
                        trend.increaseDownStep();
                        avgWeightDownStep = +diffStep;
                    }
                }
                double diffHeight = logs.get(i).getHeight() - logs.get(i + 1).getHeight();
                double diffWeight = logs.get(i).getWeight() - logs.get(i + 1).getWeight();
                if (diffWeight > 0) {
                    trend.increaseUp();
                    avgWeightUp = +diffWeight;
                } else if (diffWeight < 0) {
                    trend.increaseDown();
                    avgWeightDown = +diffWeight;
                } else {
                    trend.increaseNeutral();
                }
                // collect statistics
                statistic.setMaxWeight(Math.max(statistic.getMaxWeight(), logs.get(i).getWeight()));
                statistic.setMinWeight(Math.min(statistic.getMinWeight(), logs.get(i).getWeight()));
                statistic.setMaxHeight(Math.max(statistic.getMaxHeight(), logs.get(i).getHeight()));
                statistic.setMinHeight(Math.min(statistic.getMinHeight(), logs.get(i).getHeight()));
                avgWeightIncrease = +diffWeight;
                avgHeightIncrease = +diffHeight;

                statistic.setMaxWeightIncrease(Math.max(statistic.getMaxWeightIncrease(), diffWeight));
                statistic.setMaxWeightDecrease(Math.min(statistic.getMaxWeightDecrease(), diffWeight));
            } // end loop

            statistic.setStartDate(logs.get(logs.size() - 1).getLogDate());
            statistic.setEndDate(logs.get(0).getLogDate());
            statistic.setWeight(logs.get(0).getWeight());
            statistic.setHeight(logs.get(0).getHeight());
            statistic.setNumberOfDown(trend.getNumberOfDown());
            statistic.setNumberOfUp(trend.getNumberOfUp());
            statistic.setNumberOfDownStep(trend.getNumberOfDownStep());
            statistic.setNumberOfUpStep(trend.getNumberOfUpStep());
            statistic.setNumberOfNeutral(trend.getNumberOfNeutral());
            statistic.setAverageWeight(avgWeightIncrease / statistic.getNumberOfMeasurements());
            statistic.setAverageHeight(avgHeightIncrease / statistic.getNumberOfMeasurements());
            statistic.setAverageWeightUp(avgWeightUp / trend.getNumberOfUp());
            statistic.setAverageWeightDown(avgWeightDown / trend.getNumberOfDown());
            statistic.setAverageWeightUpStep(avgWeightUpStep / trend.getNumberOfUpStep());
            statistic.setAverageWeightDownStep(avgWeightDownStep / trend.getNumberOfDownStep());
        }

        ReferenceData referenceData = dietManagerService.getGrowthReferenceDataForDateOfBirth(loggedInUser.getId());

        ModelAndView modelView = new ModelAndView("log/view-weight-details");
        modelView.getModel().put("referenceData", referenceData);
        modelView.getModel().put("myStatistic", statistic);
        return modelView;
    }

    @RequestMapping(value = "/diet/body/measurement/log", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getLog() {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException("Not logged in!");
        }
        List<HealthLogEntry> logs = dietManagerService.getBodyMeasurementLogs(loggedInUser.getId());
        if (LOG.isDebugEnabled()) {
            LOG.debug("number of log entries: " + logs.size());
        }
        ModelAndView modelView = new ModelAndView("log/view-weight-log");
        modelView.getModel().put("logs", logs);
        return modelView;
    }

    // ---------------------------------------------
    // New and delete weight log
    // ---------------------------------------------

    @RequestMapping(value = "/diet/body/measurement/new", method = RequestMethod.GET)
    public String initNewLogEntryForm(Map<String, Object> model) {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException("Not logged in!");
        }
        HealthLogEntry log = new HealthLogEntry();
        log.setUserId(loggedInUser.getId());
        log.setLogDate(new Date());
        model.put("log", log);
        return "log/edit-weight-log";
    }

    /**
     * User POST for new
     * 
     * Note! The BindingResult argument needs to be positioned right after our
     * form backing object
     * 
     */
    @RequestMapping(value = "/diet/body/measurement/new", method = RequestMethod.POST)
    public String processNewLogEntryForm(@Valid @ModelAttribute("log") HealthLogEntry log, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(log.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "log/edit-weight-log";
        } else {
            // dietManagerService.getBodyMeasurementLog(log.getUserId(),
            // log.getLogDate());
            this.dietManagerService.saveBodyMeasurementLog(log);
            status.setComplete();
            return "redirect:/diet/body/measurement/log";
        }
    }

}
