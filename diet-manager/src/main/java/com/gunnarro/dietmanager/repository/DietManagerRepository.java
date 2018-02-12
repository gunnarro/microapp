package com.gunnarro.dietmanager.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gunnarro.dietmanager.domain.diet.DietMenu;
import com.gunnarro.dietmanager.domain.diet.DietPlan;
import com.gunnarro.dietmanager.domain.diet.FoodProduct;
import com.gunnarro.dietmanager.domain.diet.MenuItem;
import com.gunnarro.dietmanager.domain.diet.Product;
import com.gunnarro.dietmanager.domain.diet.Rule;
import com.gunnarro.dietmanager.domain.diet.Type;
import com.gunnarro.dietmanager.domain.health.HealthLogEntry;
import com.gunnarro.dietmanager.domain.health.ReferenceData;
import com.gunnarro.dietmanager.domain.statistic.BodyMeasurementStatistic;
import com.gunnarro.dietmanager.domain.statistic.KeyValuePair;
import com.gunnarro.dietmanager.domain.statistic.MealStatistic;
import com.gunnarro.dietmanager.domain.view.KeyValuePairList;
import com.gunnarro.dietmanager.endpoint.rest.ChartData;

public interface DietManagerRepository {

    public int checkUserDietMenuItemLnk(Integer userId, Date createdDate, String mealName);

    // ------------------------ diet menu --------------------------------
    public int createDietMenu(DietMenu dietMenu);

    public int createDietMenuItem(MenuItem menuItem);

    public int createDietRule(Rule rule);

    public int createPersonalHealthData(HealthLogEntry healthLogEntry);

    // ------------------ products ----------------------------
    public int createProduct(Product product);

    public int createUserDietMenuItemLnk(Integer userId, MenuItem menuItem);

    public int deleteDietMenu(int dietMenuId);

    public int deleteDietMenuItem(Integer menuItemId);

    public int deleteDietRule(Integer id);

    public int deletePersonalHealthData(Integer id);

    public int deleteProduct(Integer productId);

    public int deleteUserDietMenuItemLnk(Integer id);

    public int deleteUserDietMenuItemLnk(Integer userId, Integer dietMenuItemId);

    public List<Rule> getAllDietRules();

    public List<ChartData> getBmiReferenceData();

    public HealthLogEntry getBodyMeasurementLog(Integer logEntryId);

    // ---------- personal health log services -------------------------

    public List<HealthLogEntry> getBodyMeasurementLogs(Integer userId);

    public List<KeyValuePair> getConflictStatistic(Integer days);

    public DietMenu getDietMenu(int userId);

    public MenuItem getDietMenuItem(Integer menuItemId);

    public List<Type> getDietMenuItemTypes();

    // ---------- event log services -------------------------

    public DietPlan getDietPlan(Integer dietPlanId);

    public List<DietPlan> getDietPlans(Integer userId);

    public List<FoodProduct> getDietProductChangeList();

    // ------------------------ diet rules --------------------------------
    public Rule getDietRule(Integer Id);

    public List<Rule> getDietRules(Integer dietPlanId);

    public List<KeyValuePairList> getFoodRecipes();

    public List<KeyValuePair> getMealsManagedByUserStatistic(Integer days);

    public List<KeyValuePair> getMealsPreparedByUserStatistic(Integer days);

    public List<KeyValuePair> getMealTypesStatistic(Integer days);

    public List<String> getMenuItemSelectionTrend(Integer userId, Integer menuItemId, Integer days);

    public List<DietMenu> getMenus(Integer userId);

    public List<MenuItem> getMenuSelctionTrendForUser(Integer userId, Integer days);

    // ----------------------- my status --------------------------
    public Map<Date, List<String>> getMissingMealsForUser(Integer userId, Integer forLastDays);

    public int getNumberOfActivitiesLastDays(Integer days);

    public int getNumberOfConflictsLastDays(Integer days);

    public int getNumberOfMealsLastDays(Integer days);

    public Product getProduct(Integer productId);

    public List<Product> getProducts();

    // ---------- statistic services -------------------------
    public List<KeyValuePair> getSelectedMealsStatistic(Integer days);

    public List<MenuItem> getSelectedMenuItemsForUser(Integer userId, Integer forDays);

    public List<KeyValuePair> getSummaryStatistic(Integer days);

    public Integer getUserSelectedMenuItemCount(Integer userId, Integer dietMenuItemId);

    public int updateDietMenu(DietMenu dietMenu);

    public int updateDietMenuItem(MenuItem menuItem);

    public int updateDietRule(Rule rule);

    public int updatePersonalHealthData(HealthLogEntry healthLogEntry);

    public int updateProduct(Product product);

    public BodyMeasurementStatistic getBodyMeasurementStatistic(Integer userId, Integer forDays);

    public ReferenceData getGrowthReferenceDataForDateOfBirth(Integer userId);

    public HealthLogEntry getBodyMeasurementLog(Integer userId, Date logDate);

    public int createFollowerForUser(Integer userId, Integer userFollowerId);

    public int deleteFollowerForUser(Integer userId, Integer userFollowerId);

    public List<Integer> getGrantedUserIdsForFollower(Integer userIdFollowerId);

    public List<MealStatistic> getMealStatistic(Integer userId, Integer days);

    public List<String> getSelecedMealNamesForDate(Date forDate);
}
