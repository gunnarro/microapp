package com.gunnarro.dietmanager.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gunnarro.dietmanager.domain.diet.DietMenu;
import com.gunnarro.dietmanager.domain.diet.DietPlan;
import com.gunnarro.dietmanager.domain.diet.FoodProduct;
import com.gunnarro.dietmanager.domain.diet.MenuItem;
import com.gunnarro.dietmanager.domain.diet.Rule;
import com.gunnarro.dietmanager.domain.diet.Type;
import com.gunnarro.dietmanager.domain.health.HealthLogEntry;
import com.gunnarro.dietmanager.domain.health.ReferenceData;
import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.dietmanager.domain.statistic.BodyMeasurementStatistic;
import com.gunnarro.dietmanager.domain.statistic.Key;
import com.gunnarro.dietmanager.domain.statistic.KeyValuePair;
import com.gunnarro.dietmanager.domain.statistic.MealStatistic;
import com.gunnarro.dietmanager.domain.view.KeyValuePairList;
import com.gunnarro.dietmanager.endpoint.rest.ChartData;
import com.gunnarro.dietmanager.repository.DietManagerRepository;
import com.gunnarro.dietmanager.repository.LogEventRepository;
import com.gunnarro.dietmanager.service.DietManagerService;
import com.gunnarro.dietmanager.service.exception.ApplicationException;
import com.gunnarro.dietmanager.utility.Utility;

@Service
public class DietManagerServiceImpl implements DietManagerService {

    private static final Logger LOG = LoggerFactory.getLogger(DietManagerServiceImpl.class);

    @Autowired
    @Qualifier("dietManagerRepository")
    private DietManagerRepository dietManagerRepository;

    @Autowired
    @Qualifier("logEventRepository")
    private LogEventRepository logEventRepository;

    @Override
    public boolean checkIfSelectedMealAlreadyRegistered(Integer userId, Date createdDate, Integer dietMenuItemId) {
        MenuItem dietMenuItem = dietManagerRepository.getDietMenuItem(dietMenuItemId);
        if (dietMenuItem == null) {
            throw new ApplicationException("Unkown diet menu item id: " + dietMenuItemId);
        }
        String mealName = dietMenuItem.getName();
        boolean isSelected = dietManagerRepository.checkUserDietMenuItemLnk(userId, createdDate, mealName) > 0;
        if (LOG.isDebugEnabled()) {
            LOG.debug("isAlreadyRegistrered: " + isSelected + ", userId: " + userId + ", date: " + createdDate + ", mealName: " + mealName);
        }
        return isSelected;
    }

    @Override
    public int deleteBodyMeasurementLog(Integer id) {
        return dietManagerRepository.deletePersonalHealthData(id);
    }

    @Override
    public int deleteDietMenu(int dietMenuId) {
        return dietManagerRepository.deleteDietMenu(dietMenuId);
    }

    @Override
    public int deleteDietMenuItem(int menuItemId) {
        return dietManagerRepository.deleteDietMenuItem(menuItemId);
    }

    @Override
    public int deleteSelectedFoodForUser(Integer id) {
        int rows = dietManagerRepository.deleteUserDietMenuItemLnk(id);
        if (LOG.isDebugEnabled()) {
            LOG.debug("delete selelection, id = " + id);
        }
        return rows;
    }

    @Override
    public int deleteSelectedFoodForUser(Integer userId, Integer dietMenuItemId) {
        int rows = dietManagerRepository.deleteUserDietMenuItemLnk(userId, dietMenuItemId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("delete selected menu item, userId:" + userId + ", menuItemId: " + dietMenuItemId + ", deleted rows: " + rows);
        }
        return rows;
    }

    @Override
    public List<ChartData> getBmiChartData(Integer userId) {
        try {
            List<ChartData> bmiReferenceData = dietManagerRepository.getBmiReferenceData();
            int startMonth = Integer.parseInt(bmiReferenceData.get(0).getLabel());
            if (LOG.isDebugEnabled()) {
                LOG.debug("bmi reference data size: " + bmiReferenceData.size());
                LOG.debug("Start index, month number: " + startMonth);
            }
            List<HealthLogEntry> userBodyMeasurementLogs = getBodyMeasurementLogs(userId);
            if (LOG.isDebugEnabled()) {
                LOG.debug("bmi user data size: " + userBodyMeasurementLogs.size());
            }
            // The user log is sorted descending, so loop from end to start
            for (int i = userBodyMeasurementLogs.size() - 1; i > -1; i--) {
                HealthLogEntry userLog = userBodyMeasurementLogs.get(i);
                int monthToIndex = userLog.getMonthsOld() - startMonth;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("index: " + monthToIndex + ", bmi: " + userLog.getBmi() + "(" + userLog.getWeight() + "/" + userLog.getHeight() + ")");
                }
                // Note! user log is sorted by date, we overwrite months with
                // more than one measurements and we use the last measurement
                bmiReferenceData.get(monthToIndex).setValue4(userLog.getBmi());
            }
            return bmiReferenceData;
        } catch (Exception e) {
            LOG.error("", e);
            throw new ApplicationException("Error getting chart data! " + e.getMessage());
        }
    }

    @Override
    public HealthLogEntry getBodyMeasurementLog(Integer userId, Date logDate) {
        return dietManagerRepository.getBodyMeasurementLog(userId, logDate);
    }

    @Override
    public HealthLogEntry getBodyMeasurementLog(int logEntryId) {
        return dietManagerRepository.getBodyMeasurementLog(logEntryId);
    }

    @Override
    public List<HealthLogEntry> getBodyMeasurementLogs(Integer userId) {
        List<HealthLogEntry> bodyMeasurementsLog = dietManagerRepository.getBodyMeasurementLogs(userId);
        return bodyMeasurementsLog;
    }

    @Override
    public BodyMeasurementStatistic getBodyMeasurementStatistic(Integer userId, Integer forDays) {
        return dietManagerRepository.getBodyMeasurementStatistic(userId, forDays);
    }

    @Override
    public List<FoodProduct> getDietFoodChangeList() {
        return dietManagerRepository.getDietProductChangeList();
    }

    /**
     */
    @Override
    public DietMenu getDietMenu(int menuId) {
        return dietManagerRepository.getDietMenu(menuId);
    }

    @Override
    public MenuItem getDietMenuItem(int menuItemId) {
        return dietManagerRepository.getDietMenuItem(menuItemId);
    }

    @Override
    public List<Type> getDietMenuItemTypes() {
        return dietManagerRepository.getDietMenuItemTypes();
    }

    /**
     * @param calendarEventId
     * @return
     */
    @Override
    public DietPlan getDietPlan(Integer dietPlanId) {
        return dietManagerRepository.getDietPlan(dietPlanId);
    }

    /**
     */
    @Override
    public List<DietPlan> getDietPlans(int userId) {
        return dietManagerRepository.getDietPlans(userId);
    }

    // ----------------------- diet menu services ----------------------------

    /**
     */
    @Override
    public List<Rule> getDietRules(int dietPlanId) {
        return dietManagerRepository.getDietRules(dietPlanId);
    }

    @Override
    public List<KeyValuePairList> getFoodRecipes() {
        return dietManagerRepository.getFoodRecipes();
    }

    // ----------------------- diet menu item services
    // ----------------------------

    @Override
    public List<KeyValuePair> getMealTypesStatistic() {
        return dietManagerRepository.getMealTypesStatistic(30);
    }

    @Override
    public List<DietMenu> getMenus(Integer userId) {
        return dietManagerRepository.getMenus(userId);
    }

    @Override
    public Map<Date, List<String>> getMissingMealsForUser(Integer userId, Integer forLastDays) {
        Map<Date, List<String>> missingMealsForUser = dietManagerRepository.getMissingMealsForUser(userId, forLastDays);
        if (LOG.isDebugEnabled()) {
            LOG.debug(missingMealsForUser.toString());
        }
        return missingMealsForUser;
    }

    @Override
    public Map<String, List<KeyValuePair>> getMyChoicesStatisticByWeek() {
        List<KeyValuePair> mealsManagedByUserStatistic = dietManagerRepository.getMealsManagedByUserStatistic(30);
        // List<KeyValuePair> conflictStatistic =
        // dietManagerRepository.getConflictStatistic(30);
        SortedMap<String, List<KeyValuePair>> map = new TreeMap<String, List<KeyValuePair>>(Collections.reverseOrder());
        for (KeyValuePair k : mealsManagedByUserStatistic) {
            k.setPeriode(Utility.getWeekInfo(Integer.parseInt(k.getKey())));
            if (!map.containsKey(k.getPeriode())) {
                List<KeyValuePair> list = new ArrayList<KeyValuePair>();
                list.add(k);
                map.put(k.getPeriode(), list);
            } else {
                map.get(k.getPeriode()).add(k);
            }
        }
        return map;
    }

    @Override
    public List<KeyValuePair> getMyChoicesStatisticSummary() {
        return dietManagerRepository.getSummaryStatistic(30);
    }

    @Override
    public List<KeyValuePair> getMyStatus(Integer userId, int forLastDays) {
        List<KeyValuePair> list = new ArrayList<>();
        List<HealthLogEntry> bodyMeasurementsLog = dietManagerRepository.getBodyMeasurementLogs(userId);
        double weightDiff = bodyMeasurementsLog.get(0).getWeight() - bodyMeasurementsLog.get(1).getWeight();
        int weightIndex = 0;
        if (weightDiff < 0) {
            weightIndex = -2;
        } else if (weightDiff > 0) {
            weightIndex = 2;
        }

        int numberOfConflictsLastDays = dietManagerRepository.getNumberOfConflictsLastDays(forLastDays);
        int conflictIndex = numberOfConflictsLastDays;
        if (numberOfConflictsLastDays == 0) {
            conflictIndex = 2;
        } else if (numberOfConflictsLastDays == 1) {
            conflictIndex = 1;
        } else if (numberOfConflictsLastDays == 2) {
            conflictIndex = 0;
        } else if (numberOfConflictsLastDays == 3) {
            conflictIndex = -1;
        } else if (numberOfConflictsLastDays > 3) {
            conflictIndex = -2;
        }

        // max number of activities pr. week 7*1=7
        int numberOfActivitiesLastDays = dietManagerRepository.getNumberOfActivitiesLastDays(forLastDays);
        int activityIndex = numberOfConflictsLastDays;
        if (numberOfActivitiesLastDays == 0) {
            activityIndex = 0;
        } else if (numberOfActivitiesLastDays == 1) {
            activityIndex = 1;
        } else if (numberOfActivitiesLastDays == 2) {
            activityIndex = 2;
        } else if (numberOfActivitiesLastDays == 3) {
            activityIndex = -1;
        } else if (numberOfActivitiesLastDays >= 4) {
            activityIndex = -2;
        }

        // max number of meals pr. week 7*4=28
        int numberOfMealsLastDays = dietManagerRepository.getNumberOfMealsLastDays(forLastDays);
        int mealIndex = 0;
        if (numberOfMealsLastDays < 25) {
            mealIndex = -2;
        } else if (numberOfMealsLastDays == 25) {
            mealIndex = -1;
        } else if (numberOfMealsLastDays == 26) {
            mealIndex = 0;
        } else if (numberOfMealsLastDays == 27) {
            mealIndex = 1;
        } else if (numberOfMealsLastDays >= 28) {
            mealIndex = 2;
        }

        list.add(new KeyValuePair("Fulgt dietplan", null, mealIndex));
        list.add(new KeyValuePair("Vektøkning", null, weightIndex));
        list.add(new KeyValuePair("Konfliktnivå", null, conflictIndex));
        list.add(new KeyValuePair("Aktivitetsnivå", null, activityIndex));
        return list;
    }

    @Override
    public LogEntry getMyStatusReport(Integer userId) {
        return logEventRepository.getMyLastStatusReport(userId);
    }

    // ----------------------- event log services ----------------------------

    @Override
    public LogEntry getRecentLogEvent(Integer userId, String type, Integer forLastDays) {
        return logEventRepository.getRecentLogEvent(userId, type, forLastDays);
    }

    @Override
    public int getSelectedFoodCountForUser(Integer userId, Integer dietMenuItemId) {
        int count = dietManagerRepository.getUserSelectedMenuItemCount(userId, dietMenuItemId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("userId:" + userId + ", menuItemId: " + dietMenuItemId + ", count: " + count);
        }
        return count;
    }

    @Override
    public List<MenuItem> getSelectedFoodForUser(Integer userId, Integer forDays) {
        return dietManagerRepository.getSelectedMenuItemsForUser(userId, forDays);
    }

    @Override
    public List<MenuItem> getSelectedMenuItemsForUser(int userId, int forLastDays) {
        int forDays = 7;
        List<MenuItem> menuItems = dietManagerRepository.getSelectedMenuItemsForUser(userId, forLastDays);
        for (MenuItem m : menuItems) {
            m.setSelectionTrends(MenuItem.generateDefaltSelectionTrends(forDays, "dd.MM.yyyy"));
            List<String> menuItemSelectionTrendList = dietManagerRepository.getMenuItemSelectionTrend(userId, m.getId(), forDays);
            m.createSelectionTrends(menuItemSelectionTrendList);
        }
        return menuItems;
    }

    @Override
    public List<KeyValuePair> getTop10SeletedMeals() {
        return dietManagerRepository.getSelectedMealsStatistic(30);
    }

    // ----------------------- body services ----------------------------
    @Override
    public int saveBodyMeasurementLog(HealthLogEntry log) {
        if (log.isNew()) {
            return dietManagerRepository.createPersonalHealthData(log);
        } else {
            return dietManagerRepository.updatePersonalHealthData(log);
        }
    }

    @Override
    public int saveDietMenu(DietMenu dietMenu) {
        if (dietMenu.isNew()) {
            return dietManagerRepository.createDietMenu(dietMenu);
        } else {
            return dietManagerRepository.updateDietMenu(dietMenu);
        }
    }

    @Override
    public void saveDietMenuItem(MenuItem menuItem) {
        if (menuItem.isNew()) {
            dietManagerRepository.createDietMenuItem(menuItem);
        } else {
            dietManagerRepository.updateDietMenuItem(menuItem);
        }
    }

    /**
     * TODO not implemented
     */
    @Override
    public int saveDietPlan(DietPlan dietPlan) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(dietPlan.toString());
        }
        return 0;
    }

    @Override
    public int saveSelectedFoodForUser(Integer userId, MenuItem menuItem) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(menuItem.toString());
        }
        if (userId == null || menuItem == null) {
            throw new ApplicationException("userId and menuItem must be set!");
        }
        return dietManagerRepository.createUserDietMenuItemLnk(userId, menuItem);
    }

    @Override
    public List<MealStatistic> getMealStatsticForUsers(Integer userId, Integer days) {
        List<MealStatistic> mealStatisticList = new ArrayList<MealStatistic>();
        List<Integer> grantedUserIdsForFollower = dietManagerRepository.getGrantedUserIdsForFollower(userId);
        grantedUserIdsForFollower.add(userId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("for user id's: " + grantedUserIdsForFollower);
        }
        for (int id : grantedUserIdsForFollower) {
            mealStatisticList.addAll(dietManagerRepository.getMealStatistic(id, days));
        }
        Collections.sort(mealStatisticList);
        return mealStatisticList;
    }

    public static Map<Key, List<MealStatistic>> mapMealStatisticByWeekNumber(List<MealStatistic> mealStatisticList) {
        SortedMap<Key, List<MealStatistic>> map = new TreeMap<Key, List<MealStatistic>>(Collections.reverseOrder());
        for (MealStatistic s : mealStatisticList) {
            s.setPeriod(Utility.getWeekInfo(s.getCreatedDate()));
            Key key = new Key(s.sortBy(), s.getPeriod());
            if (!map.containsKey(key)) {
                List<MealStatistic> list = new ArrayList<MealStatistic>();
                list.add(s);
                map.put(key, list);
            } else {
                map.get(key).add(s);
            }
        }
        return map;
    }

    public static List<MealStatistic> sumMealStatisticByUserName(List<MealStatistic> mealStatisticList) {
        // int totalMealsCausedConflictCount = 0;
        // int totalMealsControlledByUserCount = 0;
        // int totalMealsPreparedByUserCount = 0;
        SortedMap<String, MealStatistic> map = new TreeMap<String, MealStatistic>(Collections.reverseOrder());
        for (MealStatistic s : mealStatisticList) {
            if (!map.containsKey(s.getUserName())) {
                map.put(s.getUserName(), s);
            } else {
                MealStatistic tmp = map.get(s.getUserName());
                tmp.setMealsCausedConflictCount(tmp.getMealsCausedConflictCount() + s.getMealsCausedConflictCount());
                tmp.setMealsControlledByUserCount(tmp.getMealsControlledByUserCount() + s.getMealsControlledByUserCount());
                tmp.setMealsPreparedByUserCount(tmp.getMealsPreparedByUserCount() + s.getMealsPreparedByUserCount());
                tmp.setToDate(s.getCreatedDate());
            }
            // totalMealsCausedConflictCount = totalMealsCausedConflictCount +
            // s.getMealsCausedConflictCount();
            // totalMealsControlledByUserCount = totalMealsCausedConflictCount +
            // s.getMealsControlledByUserCount();
            // totalMealsPreparedByUserCount = totalMealsCausedConflictCount +
            // s.getMealsPreparedByUserCount();
        }
        // MealStatistic total = new MealStatistic();
        // total.setUserName("Total");
        // total.setPeriod("");
        // total.setMealsCausedConflictCount(totalMealsCausedConflictCount);
        // total.setMealsControlledByUserCount(totalMealsControlledByUserCount);
        // total.setMealsPreparedByUserCount(totalMealsPreparedByUserCount);
        // map.put("Total", total);
        return new ArrayList<MealStatistic>(map.values());
    }

    @Override
    public ReferenceData getGrowthReferenceDataForDateOfBirth(Integer userId) {
        return dietManagerRepository.getGrowthReferenceDataForDateOfBirth(userId);
    }

    public void setDietManagerRepository(DietManagerRepository dietManagerRepository) {
        this.dietManagerRepository = dietManagerRepository;
    }

    @Override
    public List<String> getSelectedMealNamesForDate(Date forDate) {
        return dietManagerRepository.getSelecedMealNamesForDate(forDate);
    }
}
