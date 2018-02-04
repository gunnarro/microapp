package com.gunnarro.dietmanager.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
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
import com.gunnarro.dietmanager.domain.statistic.KeyValuePair;
import com.gunnarro.dietmanager.domain.statistic.MealStatistic;
import com.gunnarro.dietmanager.domain.view.KeyValuePairList;
import com.gunnarro.dietmanager.endpoint.rest.ChartData;

/**
 * Method level security should be applied for all save*() and delete*() methods
 * here.
 * 
 * @author admin
 *
 */
@Service
public interface DietManagerService {

    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public boolean checkIfSelectedMealAlreadyRegistered(Integer userId, Date createdDate, Integer dietMenuItemId);

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public int deleteBodyMeasurementLog(Integer id);

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public int deleteDietMenu(int dietMenuId);

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public int deleteDietMenuItem(int menuItemId);

    /**
     * 
     * @param userId
     * @param menuItem
     * @return
     */
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public int saveSelectedFoodForUser(Integer userId, MenuItem menuItem);

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public int deleteSelectedFoodForUser(Integer id);

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public int deleteSelectedFoodForUser(Integer userId, Integer dietMenuItemId);

    public List<ChartData> getBmiChartData(Integer userId);

    public HealthLogEntry getBodyMeasurementLog(int logEntryId);

    public List<HealthLogEntry> getBodyMeasurementLogs(Integer userId);

    public BodyMeasurementStatistic getBodyMeasurementStatistic(Integer userId, Integer forDays);

    public List<FoodProduct> getDietFoodChangeList();

    public DietMenu getDietMenu(int userId);

    public MenuItem getDietMenuItem(int menuItemId);

    public List<Type> getDietMenuItemTypes();

    public DietPlan getDietPlan(Integer dietPlanId);

    public List<DietPlan> getDietPlans(int userId);

    public List<Rule> getDietRules(int dietPlanId);

    public List<KeyValuePairList> getFoodRecipes();

    public List<KeyValuePair> getMealTypesStatistic();

    public List<DietMenu> getMenus(Integer id);

    public Map<Date, List<String>> getMissingMealsForUser(Integer userId, Integer forLastDays);

    /**
     * 
     * @return
     */
    public Map<String, List<KeyValuePair>> getMyChoicesStatisticByWeek();

    /**
     * 
     * @return
     */
    public List<KeyValuePair> getMyChoicesStatisticSummary();

    public List<KeyValuePair> getMyStatus(Integer userId, int forLastDays);

    public LogEntry getMyStatusReport(Integer id);

    public LogEntry getRecentLogEvent(Integer userId, String type, Integer forLastDays);

    public int getSelectedFoodCountForUser(Integer userId, Integer dietMenuItemId);

    public List<MenuItem> getSelectedFoodForUser(Integer userId, Integer forDays);

    public List<MenuItem> getSelectedMenuItemsForUser(int userId, int forLastDays);

    public List<KeyValuePair> getTop10SeletedMeals();

    // ---------------------------- body services ----------------------------
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public int saveBodyMeasurementLog(HealthLogEntry log);

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public int saveDietMenu(DietMenu dietMenu);

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public void saveDietMenuItem(MenuItem menuItem);

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public int saveDietPlan(DietPlan dietPlan);

    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public HealthLogEntry getBodyMeasurementLog(Integer userId, Date logDate);

    public ReferenceData getGrowthReferenceDataForDateOfBirth(Integer userId);

    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public List<MealStatistic> getMealStatsticForUsers(Integer userId, Integer days);

    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public List<String> getSelectedMealNamesForDate(Date date);

}