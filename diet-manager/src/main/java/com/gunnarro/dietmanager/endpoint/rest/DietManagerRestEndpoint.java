package com.gunnarro.dietmanager.endpoint.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gunnarro.dietmanager.domain.diet.MenuItem;
import com.gunnarro.dietmanager.domain.health.HealthLogEntry;
import com.gunnarro.dietmanager.domain.statistic.Key;
import com.gunnarro.dietmanager.domain.statistic.KeyValuePair;
import com.gunnarro.dietmanager.domain.statistic.MealStatistic;
import com.gunnarro.dietmanager.mvc.controller.AuthenticationFacade;
import com.gunnarro.dietmanager.service.DietManagerService;
import com.gunnarro.dietmanager.service.exception.ApplicationException;
import com.gunnarro.dietmanager.service.impl.DietManagerServiceImpl;
import com.gunnarro.dietmanager.utility.Utility;
import com.gunnarro.useraccount.domain.user.LocalUser;

@RestController
@RequestMapping("/rest")
public class DietManagerRestEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(DietManagerRestEndpoint.class);

    @Autowired
    protected AuthenticationFacade authenticationFacade;
    
    @Autowired
    @Qualifier("dietManagerService")
    private DietManagerService dietManagerService;

    /**
     * default constructor, used by spring
     */
    public DietManagerRestEndpoint() {
    }

    /**
     * For unit testing only
     * 
     * @param sportsTeamService - inject as mock
     */
    public DietManagerRestEndpoint(DietManagerService sportsTeamService, AuthenticationFacade authenticationFacade) {
        this.dietManagerService = sportsTeamService;
        this.authenticationFacade = authenticationFacade;
    }

    @RequestMapping(value = "/chart/data/{type}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public List<ChartData> getChartData(@PathVariable("type") String type) {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException("Not logged in!");
        }
        int days = 30;
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("get chart data for type= " + type);
            }
            List<ChartData> data = new ArrayList<ChartData>();
            if ("bmi".equalsIgnoreCase(type)) {
                data = dietManagerService.getBmiChartData(loggedInUser.getId());
            } else if ("bodymeasure".equalsIgnoreCase(type)) {
                List<HealthLogEntry> bodyMeasurementLogs = dietManagerService.getBodyMeasurementLogs(loggedInUser.getId());
                for (HealthLogEntry log : bodyMeasurementLogs) {
                    data.add(new ChartData(log.getId(), Utility.formatTime(log.getLogDate().getTime(), Utility.DATE_PATTERN), log.getWeight(), log.getHeight(), log.getBmi()));
                }
            } else if ("controlledby".equalsIgnoreCase(type)) {
                List<MealStatistic> mealStatsticList = dietManagerService.getMealStatsticForUsers(loggedInUser.getId(), days);
                Map<Key, List<MealStatistic>> mapMealStatisticByWeekNumber = DietManagerServiceImpl.mapMealStatisticByWeekNumber(mealStatsticList);
                for (Map.Entry<Key, List<MealStatistic>> entry : mapMealStatisticByWeekNumber.entrySet()) {
                    data.add(createChartDataControlledBy(entry));
                }
                Collections.sort(data);
            } else if ("preparedby".equalsIgnoreCase(type)) {
                List<MealStatistic> mealStatsticList = dietManagerService.getMealStatsticForUsers(loggedInUser.getId(), days);
                Map<Key, List<MealStatistic>> mapMealStatisticByWeekNumber = DietManagerServiceImpl.mapMealStatisticByWeekNumber(mealStatsticList);
                for (Map.Entry<Key, List<MealStatistic>> entry : mapMealStatisticByWeekNumber.entrySet()) {
                    data.add(createChartDataPreparedBy(entry));
                }
                Collections.sort(data);
            } else if ("causedconflict".equalsIgnoreCase(type)) {
                List<MealStatistic> mealStatsticList = dietManagerService.getMealStatsticForUsers(loggedInUser.getId(), days);
                Map<Key, List<MealStatistic>> mapMealStatisticByWeekNumber = DietManagerServiceImpl.mapMealStatisticByWeekNumber(mealStatsticList);
                for (Map.Entry<Key, List<MealStatistic>> entry : mapMealStatisticByWeekNumber.entrySet()) {
                    data.add(createChartDataCausedConflict(entry));
                }
                Collections.sort(data);
            } else if ("mealtypes".equalsIgnoreCase(type)) {
                List<KeyValuePair> top10SeletedMeals = dietManagerService.getMealTypesStatistic();
                for (KeyValuePair kv : top10SeletedMeals) {
                    data.add(new ChartData(kv.getKey(), Double.valueOf(kv.getCount())));
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("type: " + type + ", data size: " + data.size());
            }
            return data;
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            throw new RestApplicationException("Application error! type:" + type);
        }
    }

    @RequestMapping(value = "/menu/deregistrer/{userId}/{menuItemId}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer menuSelectionDeRegistrer(@PathVariable("userId") Integer userId, @PathVariable("menuItemId") Integer menuItemId) {
        try {
            // delete only meal type for current date
            boolean isMealAlreadyRegistered = dietManagerService.checkIfSelectedMealAlreadyRegistered(userId, new Date(), menuItemId);
            if (!isMealAlreadyRegistered) {
                LOG.debug("Valgte menuItem id (" + menuItemId + ") er ikke registrert i dag: " + Utility.formatTime(System.currentTimeMillis(), Utility.DATE_PATTERN));
                return dietManagerService.getSelectedFoodCountForUser(userId, menuItemId);
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Delete selection userId=" + userId + ", menuItemId=" + menuItemId);
            }
            dietManagerService.deleteSelectedFoodForUser(userId, menuItemId);
            return dietManagerService.getSelectedFoodCountForUser(userId, menuItemId);
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            throw new RestApplicationException("Application error! userId: " + userId + ", menuItemId:" + menuItemId);
        }
    }

    /**
     * 
     * @param userId
     * @param menuItemId
     * @return
     */
    @RequestMapping(value = "/menu/registrer/{userId}/{menuItemId}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer menuSelectionRegistrer(@PathVariable("userId") Integer userId, @PathVariable("menuItemId") Integer menuItemId) {
        try {
            // Add only one meal type per day.
            boolean isMealAlreadyRegistered = dietManagerService.checkIfSelectedMealAlreadyRegistered(userId, new Date(), menuItemId);
            if (isMealAlreadyRegistered) {
                MenuItem tmpMenuItem = dietManagerService.getDietMenuItem(menuItemId);
                LOG.debug(tmpMenuItem.getName() + " er allerede registrert i dag: " + Utility.formatTime(tmpMenuItem.getCreatedTime(), Utility.DATE_PATTERN));
                return dietManagerService.getSelectedFoodCountForUser(userId, menuItemId);
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Save selection userId=" + userId + ", menuItemId=" + menuItemId);
            }

            MenuItem menuItem = new MenuItem();
            menuItem.setId(menuItemId);
            menuItem.setCreatedDate(new Date());
            menuItem.setControlledByUserId(userId);
            menuItem.setPreparedByUserId(userId);
            menuItem.setCausedConflict(0);
            dietManagerService.saveSelectedFoodForUser(userId, menuItem);
            return dietManagerService.getSelectedFoodCountForUser(userId, menuItemId);
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            throw new RestApplicationException("Application error! userId: " + userId + ", menuItemId:" + menuItemId);
        }
    }

//    private ChartData createChartData(Map.Entry<String, List<KeyValuePair>> entry) {
//        String weekNumber = null;
//        double pappa = 0;
//        double mamma = 0;
//        double pepilie = 0;
//        for (KeyValuePair kvp : entry.getValue()) {
//            weekNumber = kvp.getKey();
//            if ("pappa".equalsIgnoreCase(kvp.getValue())) {
//                pappa = kvp.getCount();
//            } else if ("mamma".equalsIgnoreCase(kvp.getValue())) {
//                mamma = kvp.getCount();
//            } else if ("pepilie".equalsIgnoreCase(kvp.getValue())) {
//                pepilie = kvp.getCount();
//            }
//        }
//        return new ChartData(weekNumber, pappa, mamma, pepilie);
//    }
    
    private ChartData createChartDataPreparedBy(Map.Entry<Key, List<MealStatistic>> entry) {
        String key =  null;
        String weekNumber = null;
        double pappa = 0;
        double mamma = 0;
        double pepilie = 0;
        for (MealStatistic s : entry.getValue()) {
            key = s.sortBy();
            weekNumber = s.getWeekNumber().toString();
            if ("pappa".equalsIgnoreCase(s.getUserName())) {
                pappa = s.getMealsPreparedByUserCount();
            } else if ("mamma".equalsIgnoreCase(s.getUserName())) {
                mamma = s.getMealsPreparedByUserCount();
            } else if ("pepilie".equalsIgnoreCase(s.getUserName())) {
                pepilie = s.getMealsPreparedByUserCount();
            }
        }
        return new ChartData(key, weekNumber, pappa, mamma, pepilie);
    }
    
    private ChartData createChartDataControlledBy(Map.Entry<Key, List<MealStatistic>> entry) {
        String key =  null;
        String weekNumber = null;
        double pappa = 0;
        double mamma = 0;
        double pepilie = 0;
        for (MealStatistic s : entry.getValue()) {
            key = s.sortBy();
            weekNumber = s.getWeekNumber().toString();
            if ("pappa".equalsIgnoreCase(s.getUserName())) {
                pappa = s.getMealsControlledByUserCount();
            } else if ("mamma".equalsIgnoreCase(s.getUserName())) {
                mamma = s.getMealsControlledByUserCount();
            } else if ("pepilie".equalsIgnoreCase(s.getUserName())) {
                pepilie = s.getMealsControlledByUserCount();
            }
        }
        return new ChartData(key, weekNumber, pappa, mamma, pepilie);
    }
    
    private ChartData createChartDataCausedConflict(Map.Entry<Key, List<MealStatistic>> entry) {
        String key = null;
        String weekNumber = null;
        double pappa = 0;
        double mamma = 0;
        double pepilie = 0;
        for (MealStatistic s : entry.getValue()) {
            key = s.sortBy();
            weekNumber = s.getWeekNumber().toString();
            if ("pappa".equalsIgnoreCase(s.getUserName())) {
                pappa = s.getMealsCausedConflictCount();
            } else if ("mamma".equalsIgnoreCase(s.getUserName())) {
                mamma = s.getMealsCausedConflictCount();
            } else if ("pepilie".equalsIgnoreCase(s.getUserName())) {
                pepilie = s.getMealsCausedConflictCount();
            }
        }
        return new ChartData(key, weekNumber, pappa, mamma, pepilie);
    }
}