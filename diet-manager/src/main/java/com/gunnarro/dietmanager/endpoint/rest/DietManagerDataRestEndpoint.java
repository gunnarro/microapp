package com.gunnarro.dietmanager.endpoint.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gunnarro.dietmanager.domain.health.HealthLogEntry;
import com.gunnarro.dietmanager.domain.statistic.KeyValue;
import com.gunnarro.dietmanager.domain.statistic.KeyValuePair;
import com.gunnarro.dietmanager.domain.statistic.MealStatistic;
import com.gunnarro.dietmanager.mvc.controller.AuthenticationFacade;
import com.gunnarro.dietmanager.service.DietManagerService;
import com.gunnarro.dietmanager.service.exception.ApplicationException;
import com.gunnarro.dietmanager.service.impl.DietManagerServiceImpl;
import com.gunnarro.dietmanager.utility.Utility;
import com.gunnarro.useraccount.domain.user.LocalUser;

@RestController
@RequestMapping("/rest/data")
public class DietManagerDataRestEndpoint {

    private static final String USER_DAD = "pappa";
    private static final String USER_MOM = "mamma";
    private static final String USER_PEPILIE = "pepilie";

    private static final Logger LOG = LoggerFactory.getLogger(DietManagerDataRestEndpoint.class);

    @Autowired
    protected AuthenticationFacade authenticationFacade;

    @Autowired
    private DietManagerService dietManagerService;

    /**
     * default constructor, used by spring
     */
    public DietManagerDataRestEndpoint() {
    }

    /**
     * For unit testing only
     * 
     * @param sportsTeamService - inject as mock
     */
    public DietManagerDataRestEndpoint(DietManagerService sportsTeamService, AuthenticationFacade authenticationFacade) {
        this.dietManagerService = sportsTeamService;
        this.authenticationFacade = authenticationFacade;
    }

    @RequestMapping(value = "/chart/data/mealtypes", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public List<ChartData> getChartDataMealtypes() {
        List<ChartData> data = new ArrayList<>();
        try {
            LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
            if (loggedInUser == null) {
                throw new ApplicationException("Not logged in!");
            }
            List<KeyValuePair> top10SeletedMeals = dietManagerService.getMealTypesStatistic();
            for (KeyValuePair kv : top10SeletedMeals) {
                data.add(new ChartData(kv.getKey(), Double.valueOf(kv.getCount())));
            }
            return data;
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            throw new RestApplicationException("Application error getting bmi data!");
        }
    }

    @RequestMapping(value = "/chart/data/causedconflict", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public List<ChartData> getChartDataCausedconflict() {
        int days = 30;
        List<ChartData> data = new ArrayList<>();
        try {
            LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
            if (loggedInUser == null) {
                throw new ApplicationException("Not logged in!");
            }
            List<MealStatistic> mealStatsticList = dietManagerService.getMealStatsticForUsers(loggedInUser.getId(), days);
            Map<KeyValue, List<MealStatistic>> mapMealStatisticByWeekNumber = DietManagerServiceImpl.mapMealStatisticByWeekNumber(mealStatsticList);
            for (Map.Entry<KeyValue, List<MealStatistic>> entry : mapMealStatisticByWeekNumber.entrySet()) {
                data.add(createChartDataCausedConflict(entry));
            }
            Collections.sort(data);
            return data;
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            throw new RestApplicationException("Application error getting bmi data!");
        }
    }

    @RequestMapping(value = "/chart/data/preparedby", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public List<ChartData> getChartDataPreparedby() {
        int days = 30;
        List<ChartData> data = new ArrayList<>();
        try {
            LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
            if (loggedInUser == null) {
                throw new ApplicationException("Not logged in!");
            }
            List<MealStatistic> mealStatsticList = dietManagerService.getMealStatsticForUsers(loggedInUser.getId(), days);
            Map<KeyValue, List<MealStatistic>> mapMealStatisticByWeekNumber = DietManagerServiceImpl.mapMealStatisticByWeekNumber(mealStatsticList);
            for (Map.Entry<KeyValue, List<MealStatistic>> entry : mapMealStatisticByWeekNumber.entrySet()) {
                data.add(createChartDataPreparedBy(entry));
            }
            Collections.sort(data);
            return data;
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            throw new RestApplicationException("Application error getting bmi data!");
        }
    }

    @RequestMapping(value = "/chart/data/controlledby", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public List<ChartData> getChartDataControlledby() {
        int days = 30;
        List<ChartData> data = new ArrayList<>();
        try {
            LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
            if (loggedInUser == null) {
                throw new ApplicationException("Not logged in!");
            }
            List<MealStatistic> mealStatsticList = dietManagerService.getMealStatsticForUsers(loggedInUser.getId(), days);
            Map<KeyValue, List<MealStatistic>> mapMealStatisticByWeekNumber = DietManagerServiceImpl.mapMealStatisticByWeekNumber(mealStatsticList);
            for (Map.Entry<KeyValue, List<MealStatistic>> entry : mapMealStatisticByWeekNumber.entrySet()) {
                data.add(createChartDataControlledBy(entry));
            }
            Collections.sort(data);
            return data;
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            throw new RestApplicationException("Application error getting bmi data!");
        }
    }

    @RequestMapping(value = "/chart/data/bodymeasure", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public List<ChartData> getChartDataBodymeasure() {
        List<ChartData> data = new ArrayList<>();
        try {
            LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
            if (loggedInUser == null) {
                throw new ApplicationException("Not logged in!");
            }
            List<HealthLogEntry> bodyMeasurementLogs = dietManagerService.getBodyMeasurementLogs(loggedInUser.getId());
            for (HealthLogEntry log : bodyMeasurementLogs) {
                data.add(new ChartData(log.getId(), Utility.formatTime(log.getLogDate().getTime(), Utility.DATE_PATTERN), log.getWeight(), log.getHeight(),
                        log.getBmi()));
            }
            return data;
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            throw new RestApplicationException("Application error getting bmi data!");
        }
    }

    @RequestMapping(value = "/chart/data/bmi", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public List<ChartData> getChartDataBmi() {
        try {
            LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
            if (loggedInUser == null) {
                throw new ApplicationException("Not logged in!");
            }
            return dietManagerService.getBmiChartData(loggedInUser.getId());
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            throw new RestApplicationException("Application error getting bmi data!");
        }
    }

    private ChartData createChartDataPreparedBy(Map.Entry<KeyValue, List<MealStatistic>> entry) {
        String key = null;
        String weekNumber = null;
        double pappa = 0;
        double mamma = 0;
        double pepilie = 0;
        for (MealStatistic s : entry.getValue()) {
            key = s.sortBy();
            weekNumber = s.getWeekNumber().toString();
            if (USER_DAD.equalsIgnoreCase(s.getUserName())) {
                pappa = s.getMealsPreparedByUserCount();
            } else if (USER_MOM.equalsIgnoreCase(s.getUserName())) {
                mamma = s.getMealsPreparedByUserCount();
            } else if (USER_PEPILIE.equalsIgnoreCase(s.getUserName())) {
                pepilie = s.getMealsPreparedByUserCount();
            }
        }
        return new ChartData(key, weekNumber, pappa, mamma, pepilie);
    }

    private ChartData createChartDataControlledBy(Map.Entry<KeyValue, List<MealStatistic>> entry) {
        String key = null;
        String weekNumber = null;
        double pappa = 0;
        double mamma = 0;
        double pepilie = 0;
        for (MealStatistic s : entry.getValue()) {
            key = s.sortBy();
            weekNumber = s.getWeekNumber().toString();
            if (USER_DAD.equalsIgnoreCase(s.getUserName())) {
                pappa = s.getMealsControlledByUserCount();
            } else if (USER_MOM.equalsIgnoreCase(s.getUserName())) {
                mamma = s.getMealsControlledByUserCount();
            } else if (USER_PEPILIE.equalsIgnoreCase(s.getUserName())) {
                pepilie = s.getMealsControlledByUserCount();
            }
        }
        return new ChartData(key, weekNumber, pappa, mamma, pepilie);
    }

    private ChartData createChartDataCausedConflict(Map.Entry<KeyValue, List<MealStatistic>> entry) {
        String key = null;
        String weekNumber = null;
        double pappa = 0;
        double mamma = 0;
        double pepilie = 0;
        for (MealStatistic s : entry.getValue()) {
            key = s.sortBy();
            weekNumber = s.getWeekNumber().toString();
            if (USER_DAD.equalsIgnoreCase(s.getUserName())) {
                pappa = s.getMealsCausedConflictCount();
            } else if (USER_MOM.equalsIgnoreCase(s.getUserName())) {
                mamma = s.getMealsCausedConflictCount();
            } else if (USER_PEPILIE.equalsIgnoreCase(s.getUserName())) {
                pepilie = s.getMealsCausedConflictCount();
            }
        }
        return new ChartData(key, weekNumber, pappa, mamma, pepilie);
    }
}