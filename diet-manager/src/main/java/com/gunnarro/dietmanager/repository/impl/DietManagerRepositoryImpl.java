package com.gunnarro.dietmanager.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
import com.gunnarro.dietmanager.repository.DietManagerRepository;
import com.gunnarro.dietmanager.repository.impl.DietManagerRowMapper.MenuItemsExtractor;
import com.gunnarro.dietmanager.repository.impl.DietManagerRowMapper.MenuTrendExtractor;
import com.gunnarro.dietmanager.repository.table.DietRuleTable;
import com.gunnarro.dietmanager.repository.table.TableHelper;
import com.gunnarro.dietmanager.repository.table.diet.DietMenuItemTable;
import com.gunnarro.dietmanager.repository.table.diet.DietMenuTable;
import com.gunnarro.dietmanager.repository.table.diet.ProductTable;
import com.gunnarro.dietmanager.repository.table.health.BodyMeasurementsLogTable;
import com.gunnarro.dietmanager.repository.table.link.UserDietMenuItemLnkTable;
import com.gunnarro.dietmanager.repository.table.link.UserFollowerLnkTable;
import com.gunnarro.dietmanager.service.exception.ApplicationException;
import com.gunnarro.dietmanager.utility.Utility;

/**
 * Database: jbossews User: admincnVhNH8 Password: suSNhqkXILV-
 * 
 * create calendar data https://gist.github.com/bryhal/4129042
 * 
 * @author admin
 * 
 */
@Repository
// @Transactional
public class DietManagerRepositoryImpl extends BaseJdbcRepository implements DietManagerRepository {

    private static final Logger LOG = LoggerFactory.getLogger(DietManagerRepositoryImpl.class);

    private static final String FIELD_COUNT = "count";
    private static final String FIELD_MEALS_CONTROLLED_BY_USER_COUNT = "meals_controlled_by_user_count";
    private static final String FIELD_MEALS_PREPARED_BY_USER_COUNT = "meals_prepared_by_user_count";
    private static final String FIELD_MENU_ITEM_DESCRIPTION = "menu_item_description";
    private static final String FIELD_MENU_ITEM_NAME = "menu_item_name";
    private static final String FIELD_USERNAME = "username";

    private static final String FIELD_WEEKNUMBER = "weeknumber";

    /**
     * Needed by spring framework
     */
    public DietManagerRepositoryImpl() {
        super(null);
    }

    @Autowired
    public DietManagerRepositoryImpl(@Qualifier("dietManagerDataSource") DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<String> getSelecedMealNamesForDate(Date forDate) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT i.menu_item_name AS meal_name");
        query.append(" FROM user_diet_menu_item_lnk l, diet_menu_items i");
        query.append(" WHERE date_format(l.created_date_time, '%d.%m.%Y') = ?");
        query.append(" AND l.fk_diet_menu_item_id = i.id");
        String forDateStr = Utility.formatTime(forDate.getTime(), "dd.MM.yyyy");
        return getJdbcTemplate().query(query.toString(), new Object[] { forDateStr }, DietManagerRowMapper.mapToStringValueRM("meal_name"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int checkUserDietMenuItemLnk(Integer userId, Date createdDate, String mealName) {
        try {
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("SELECT l.id");
            sqlQuery.append(" FROM user_diet_menu_item_lnk l, diet_menu_items i");
            sqlQuery.append(" WHERE date_format(l.created_date_time, '%d.%m.%Y') = ?");
            sqlQuery.append(" AND l.fk_diet_menu_item_id = i.id");
            sqlQuery.append(" AND i.menu_item_name = ?");
            sqlQuery.append(" LIMIT 1");
            String createdDateStr = Utility.formatTime(createdDate.getTime(), "dd.MM.yyyy");
            LOG.debug("created date: " + createdDateStr);
            return getJdbcTemplate().queryForObject(sqlQuery.toString(), new Object[] { createdDateStr, mealName }, Integer.class);
        } catch (EmptyResultDataAccessException erae) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + erae.toString());
            }
            return 0;
        }
    }

    @Override
    public int createDietMenu(DietMenu dietMenu) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(dietMenu.toString());
        }
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getJdbcTemplate().update(DietMenuTable.createInsertPreparedStatement(dietMenu), keyHolder);
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            LOG.error(null, e);
            throw new ApplicationException(e.getMessage());
        }

    }

    @Override
    public int createDietMenuItem(MenuItem menuItem) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(menuItem.toString());
        }
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getJdbcTemplate().update(DietMenuItemTable.createInsertPreparedStatement(menuItem), keyHolder);
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            LOG.error(null, e);
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public int createDietRule(Rule rule) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(rule.toString());
        }
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getJdbcTemplate().update(DietRuleTable.createInsertPreparedStatement(rule), keyHolder);
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            LOG.error(null, e);
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public int createPersonalHealthData(HealthLogEntry healthLogEntry) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(healthLogEntry.toString());
        }
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getJdbcTemplate().update(BodyMeasurementsLogTable.createInsertPreparedStatement(healthLogEntry), keyHolder);
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            LOG.error(null, e);
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public int createProduct(Product product) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(product.toString());
        }
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getJdbcTemplate().update(ProductTable.createInsertPreparedStatement(product), keyHolder);
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            LOG.error(null, e);
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createUserDietMenuItemLnk(Integer userId, MenuItem menuItem) {
        if (menuItem.isNew()) {
            return createLink(UserDietMenuItemLnkTable.TABLE_NAME, UserDietMenuItemLnkTable.getFKColumnNames(), new Object[] { userId, menuItem.getId(),
                    menuItem.getControlledByUserId(), menuItem.getPreparedByUserId(), menuItem.getCausedConflict(), menuItem.getFkLogId() });
        } else {
            try {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Update link: {}", menuItem);
                }
                KeyHolder keyHolder = new GeneratedKeyHolder();
                getJdbcTemplate().update(UserDietMenuItemLnkTable.createInsertPreparedStatement(userId, menuItem), keyHolder);
                return keyHolder.getKey().intValue();
            } catch (Exception e) {
                LOG.error(null, e);
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    @Override
    public int deleteDietMenu(int dietMenuId) {
        int rows = getJdbcTemplate().update("DELETE FROM diet_menus WHERE id = ?", new Object[] { dietMenuId });
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted diet menu id = {}, deleted rows = {}", dietMenuId, rows);
        }
        return rows;
    }

    @Override
    public int deleteDietMenuItem(Integer menuItemId) {
        int rows = getJdbcTemplate().update("DELETE FROM diet_menu_items WHERE id = ?", new Object[] { menuItemId });
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted diet menu item id=" + menuItemId + ", deleted rows=" + rows);
        }
        return rows;
    }

    @Override
    public int deleteDietRule(Integer id) {
        int rows = getJdbcTemplate().update("DELETE FROM diet_rules WHERE id = ?", new Object[] { id });
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted diet rule with id=" + id + ", deleted rows=" + rows);
        }
        return rows;
    }

    @Override
    public int deletePersonalHealthData(Integer id) {
        int rows = getJdbcTemplate().update("DELETE FROM body_measurements_log WHERE id = ?", new Object[] { id });
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted log entry with id=" + id + ", deleted rows=" + rows);
        }
        return rows;
    }

    @Override
    public int deleteProduct(Integer productId) {
        int rows = getJdbcTemplate().update("DELETE FROM products WHERE id = ?", new Object[] { productId });
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted product id=" + productId + ", deleted rows=" + rows);
        }
        return rows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteUserDietMenuItemLnk(Integer id) {
        String query = "DELETE FROM " + UserDietMenuItemLnkTable.TABLE_NAME + " WHERE id = ?";
        return getJdbcTemplate().update(query, new Object[] { id });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteUserDietMenuItemLnk(Integer userId, Integer dietMenuItemId) {
        return deleteLink(UserDietMenuItemLnkTable.TABLE_NAME, UserDietMenuItemLnkTable.getFKColumnNames(), new Object[] { userId, dietMenuItemId });
    }

    @Override
    public List<Rule> getAllDietRules() {
        return getJdbcTemplate().query("SELECT * FROM diet_rules r ORDER BY r.rule_name DESC", new Object[] {}, DietManagerRowMapper.mapToRuleRM());
    }

    @Override
    public List<ChartData> getBmiReferenceData() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT b.id, CAST(b.month AS CHAR) AS label, b.bmi_p25 AS value1, b.bmi_p50 AS value2, b.bmi_p75 AS value3 ");
        query.append(" FROM growth_reference_data b");
        query.append(" WHERE b.fk_health_reference_data_id = 1");
        query.append(" ORDER BY b.month ASC");
        return getJdbcTemplate().query(query.toString(), new Object[] {}, DietManagerRowMapper.mapToChartDataRM());
    }

    @Override
    public ReferenceData getGrowthReferenceDataForDateOfBirth(Integer userId) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT d.*");
            query.append(" FROM growth_reference_data d, profiles p");
            query.append(" WHERE d.month = TIMESTAMPDIFF(MONTH, p.date_of_birth, now())");
            return getJdbcTemplate().queryForObject(query.toString(), new Object[] {}, DietManagerRowMapper.mapToReferenceDataRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + erae.toString());
            }
            return null;
        }
    }

    // ----------------------- Diet menu items
    // -------------------------------------------

    // -------------------------------------------------------------------------------------
    @Override
    public HealthLogEntry getBodyMeasurementLog(Integer logEntryId) {
        try {
            return getJdbcTemplate().queryForObject(
                    "SELECT *, TIMESTAMPDIFF(MONTH, '2002-01-22', l.log_date) AS months_old FROM body_measurements_log l WHERE l.id = ?",
                    new Object[] { logEntryId }, DietManagerRowMapper.mapToHealthLogEntryRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + erae.toString());
            }
            return null;
        }
    }

    public void getAverageBodyProgresjon() {

        // select l.id, l.created_date_time, l.weight,
        // (l.weight - lprev.weight) as weight_diff,
        // (l.height - lprev.height) as height_diff,
        // avg(l.weight - lprev.weight),
        // avg(l.height - lprev.height)
        // from body_measurements_log l left join
        // body_measurements_log lprev
        // on lprev.id = l.id - 1
        // order by l.id asc

    }

    @Override
    public BodyMeasurementStatistic getBodyMeasurementStatistic(Integer userId, Integer forDays) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT l.height_metric, l.weight_metric,");
            // query.append(" (l.weight - lprev.weight) AS weight_diff,");
            // query.append(" (l.height - lprev.height) AS height_diff,");
            query.append(" avg(l.weight - lprev.weight) AS average_weight,");
            query.append(" avg(l.height - lprev.height) AS average_height,");
            query.append(" sum(if(l.weight > lprev.weight, 1, 0)) AS numberOfUp,");
            query.append(" sum(if(l.weight < lprev.weight, 1, 0)) AS numberOfDown,");
            query.append(" sum(if(l.weight = lprev.weight, 1, 0)) AS numberOfNeutral,");
            query.append(" max(l.weight) AS max_weight,");
            query.append(" max(l.height) AS max_height,");
            query.append(" min(l.weight) AS min_weight,");
            query.append(" min(l.height) AS min_height,");
            query.append(" min(l.log_date) AS start_date,");
            query.append(" max(l.log_date) AS end_date");
            query.append(" FROM body_measurements_log l LEFT JOIN");
            query.append(" body_measurements_log lprev");
            query.append(" ON lprev.id = l.id - 1");
            query.append(" WHERE l.fk_user_id = ?");
            query.append(" AND l.log_date >= CURRENT_DATE - INTERVAL ? DAY");
            query.append(" ORDER BY l.log_date, lprev.log_date DESC");
            // query.append(" ORDER BY l.id ASC");
            return getJdbcTemplate().queryForObject(query.toString(), new Object[] { userId, forDays }, DietManagerRowMapper.mapToBodyMeasurementStatisticRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + erae.toString());
            }
            return null;
        }
    }

    @Override
    public HealthLogEntry getBodyMeasurementLog(Integer userId, Date logDate) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM body_measurements_log l WHERE l.fk_user_id = ? AND l.log_date = ?",
                    new Object[] { userId, logDate }, DietManagerRowMapper.mapToHealthLogEntryRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + erae.toString());
            }
            return null;
        }
    }

    @Override
    public List<HealthLogEntry> getBodyMeasurementLogs(Integer userId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT *, TIMESTAMPDIFF(MONTH, p.date_of_birth, l.log_date) AS months_old");
        query.append(" FROM body_measurements_log l, profiles p");
        query.append(" WHERE l.fk_user_id IN (4,5,6)");
        // query.append(" AND l.fk_user_id = p.fk_user_id");
        query.append(" AND p.fk_user_id = 4");// Hack to get the correct profile
        query.append(" ORDER BY l.log_date DESC");
        return getJdbcTemplate().query(query.toString(), new Object[] {}, DietManagerRowMapper.mapToHealthLogEntryRM());
    }

    @Override
    public List<KeyValuePair> getConflictStatistic(Integer days) {
        return getJdbcTemplate().query(DietManagerSql.createConfictsQuery(), new Object[] { days, days, days },
                DietManagerRowMapper.mapToKeyValueRM(FIELD_MENU_ITEM_NAME, FIELD_USERNAME, FIELD_COUNT));
    }

    @Override
    public DietMenu getDietMenu(int menuId) {
        DietMenu dietMenu = null;
        try {
            StringBuilder dieltPlanQuery = new StringBuilder();
            dieltPlanQuery.append("SELECT DISTINCT * FROM diet_menus WHERE id = ?");
            dietMenu = getJdbcTemplate().queryForObject(dieltPlanQuery.toString(), new Object[] { menuId }, DietManagerRowMapper.mapToDietMenuRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + erae.toString());
            }
            return dietMenu;
        }
        // Count how many times a menu item is selected query
        StringBuilder countQuery = new StringBuilder();
        countQuery.append("SELECT count(*) FROM  user_diet_menu_item_lnk l");
        countQuery.append(" WHERE l.fk_user_id IN (1,2,3,4,5,6)");
        countQuery.append(" AND l.fk_diet_menu_item_id = i.id");
        // Get menu items for all meals
        StringBuilder menuItemsQuery = new StringBuilder();
        menuItemsQuery.append("SELECT i.*");
        menuItemsQuery.append(",(").append(countQuery.toString()).append(") AS selection_count");
        menuItemsQuery.append(" FROM diet_menu_items i");
        menuItemsQuery.append(" WHERE i.fk_diet_menu_id = ?");
        menuItemsQuery.append(" ORDER BY i.menu_item_name, i.menu_item_description ASC");
        if (LOG.isDebugEnabled()) {
            LOG.debug(menuItemsQuery.toString());
        }
        Map<String, List<MenuItem>> map = getJdbcTemplate().query(menuItemsQuery.toString(), new Object[] { menuId }, new MenuItemsExtractor());
        dietMenu.setMenuCategoriesMap(map);
        return dietMenu;
    }

    // ----------------------- Diet menu items
    // -------------------------------------------

    @Override
    public MenuItem getDietMenuItem(Integer menuItemId) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM diet_menu_items WHERE id = ?", new Object[] { menuItemId },
                    DietManagerRowMapper.mapToDietMenuItemRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + erae.toString());
            }
            return null;
        }
    }

    @Override
    public List<Type> getDietMenuItemTypes() {
        return getJdbcTemplate().query("SELECT DISTINCT menu_item_name AS name, 0 AS id FROM diet_menu_items", new Object[] {},
                DietManagerRowMapper.mapToTypeRM());
    }

    @Override
    public DietPlan getDietPlan(Integer dietPlanId) {
        DietPlan dietPlan = null;
        try {
            StringBuilder dieltPlanQuery = new StringBuilder();
            int tmpDietPlanId = dietPlanId;
            if (tmpDietPlanId == -1) {
                tmpDietPlanId = 1;
                dieltPlanQuery.append("SELECT DISTINCT * FROM diet_plans WHERE diet_plan_active = ? ORDER BY created_date_time ASC");
            } else {
                dieltPlanQuery.append("SELECT * FROM diet_plans WHERE id = ?");
            }
            dietPlan = getJdbcTemplate().queryForObject(dieltPlanQuery.toString(), new Object[] { tmpDietPlanId }, DietManagerRowMapper.mapToDietPlanRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + erae.toString());
            }
            return dietPlan;
        }

        StringBuilder mealsQuery = new StringBuilder();
        mealsQuery.append("SELECT m.meal_name, m.meal_period, i.meal_item_description");
        mealsQuery.append(" FROM diet_plan_meal_items i, diet_plan_meals m");
        mealsQuery.append(" WHERE i.fk_diet_plan_id = ?");
        mealsQuery.append(" AND i.fk_diet_plan_meal_id = m.id");
        mealsQuery.append(" AND m.id != 7");
        mealsQuery.append(" ORDER BY m.meal_period ASC");

        if (LOG.isDebugEnabled()) {
            LOG.debug("meals query: " + mealsQuery.toString());
        }
        List<KeyValuePair> mealItems = getJdbcTemplate().query(mealsQuery.toString(), new Object[] { dietPlan.getId() },
                DietManagerRowMapper.mapToDietPlanMealRM());
        dietPlan.setPlanItems(mapToKeyValuePairList(mealItems));
        dietPlan.setDietPlanRules(getDietPlanMealRules(dietPlan.getId()));
        return dietPlan;
    }

    @Override
    public List<DietPlan> getDietPlans(Integer userId) {
        return getJdbcTemplate().query("SELECT * FROM diet_plans", new Object[] {}, DietManagerRowMapper.mapToDietPlanRM());
    }

    @Override
    public List<FoodProduct> getDietProductChangeList() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT p.id, p.product_name, p.product_type, p.product_description,i.id, i.product_equivalent_description");
        query.append(" FROM diet_products p, diet_product_equivalent_items i");
        query.append(" WHERE p.id = i.fk_diet_product_id");
        query.append(" ORDER BY p.id ASC");

        List<FoodProduct> products = getJdbcTemplate().query("SELECT * from diet_products ORDER BY id", new Object[] {},
                DietManagerRowMapper.mapToFoodProductRM());
        for (FoodProduct p : products) {
            List<KeyValuePair> productEquivalents = getJdbcTemplate().query("SELECT * from diet_product_equivalent_items WHERE fk_diet_product_id = ?",
                    new Object[] { p.getId() }, DietManagerRowMapper.mapToKeyValueRM("product_equivalent_name", "product_equivalent_description"));
            p.setProductEquivalents(productEquivalents);
        }
        return products;
    }

    @Override
    public Rule getDietRule(Integer id) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM diet_rules r WHERE r.id = ?", new Object[] { id }, DietManagerRowMapper.mapToRuleRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + erae.toString());
            }
            return null;
        }
    }

    @Override
    public List<Rule> getDietRules(Integer dietPlanId) {
        return getJdbcTemplate().query("SELECT * FROM diet_rules r WHERE r.fk_diet_plan_id = ? ORDER BY r.rule_name DESC", new Object[] { dietPlanId },
                DietManagerRowMapper.mapToRuleRM());
    }

    @Override
    public List<KeyValuePairList> getFoodRecipes() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT i.recipe_item_name, i.recipe_item_description");
        query.append(" FROM food_recipes r, food_recipe_items i");
        query.append(" WHERE r.id = i.fk_food_recipe_id");
        query.append(" ORDER BY i.recipe_item_name, i.recipe_item_description ASC");
        List<KeyValuePair> recipes = getJdbcTemplate().query(query.toString(), new Object[] {},
                DietManagerRowMapper.mapToKeyValueRM("recipe_item_name", "recipe_item_description"));
        // have to map to unique key pair
        return mapToKeyValuePairList(recipes);
    }

    @Override
    public List<MealStatistic> getMealStatistic(Integer userId, Integer days) {
        return getJdbcTemplate().query(DietManagerSql.createMealStatisticQuery(), new Object[] { userId, userId, userId, userId },
                DietManagerRowMapper.mapToMealStatisticRM(userId));
    }

    @Override
    public List<KeyValuePair> getMealsManagedByUserStatistic(Integer days) {
        return getJdbcTemplate().query(DietManagerSql.createMealsManagedByUserQuery(), new Object[] {},
                DietManagerRowMapper.mapToKeyValueRM(FIELD_WEEKNUMBER, FIELD_USERNAME, FIELD_MEALS_CONTROLLED_BY_USER_COUNT));
    }

    @Override
    public List<KeyValuePair> getMealsPreparedByUserStatistic(Integer days) {
        return getJdbcTemplate().query(DietManagerSql.createMealsPreparedByUserQuery(), new Object[] {},
                DietManagerRowMapper.mapToKeyValueRM(FIELD_WEEKNUMBER, FIELD_USERNAME, FIELD_MEALS_PREPARED_BY_USER_COUNT));
    }

    @Override
    public List<KeyValuePair> getMealTypesStatistic(Integer days) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT count(i.menu_item_name) AS count, i.menu_item_name, null AS menu_item_description");
        query.append(" FROM user_diet_menu_item_lnk l, diet_menu_items i");
        query.append(" WHERE l.fk_diet_menu_item_id = i.id");
        query.append(" GROUP BY i.menu_item_name");
        query.append(" ORDER BY count DESC");
        return getJdbcTemplate().query(query.toString(), new Object[] {},
                DietManagerRowMapper.mapToKeyValueRM(FIELD_MENU_ITEM_NAME, FIELD_MENU_ITEM_DESCRIPTION, FIELD_COUNT));
    }

    @Override
    public List<String> getMenuItemSelectionTrend(Integer userId, Integer menuItemId, Integer days) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT date_format(l.created_date_time, '%d.%m.%Y') AS created_date_time");
        query.append(" FROM user_diet_menu_item_lnk l");
        query.append(" WHERE l.fk_user_id IN(1,2,3,4,5,6)");
        query.append(" AND l.fk_diet_menu_item_id = ?");
        query.append(" ORDER BY l.created_date_time DESC");
        query.append(" LIMIT ?");
        return getJdbcTemplate().query(query.toString(), new Object[] { menuItemId, days }, DietManagerRowMapper.singelValueRM("created_date_time"));
    }

    // -------------------------------- products
    // -------------------------------------------

    @Override
    public List<DietMenu> getMenus(Integer userId) {
        return getJdbcTemplate().query("SELECT * FROM diet_menus", new Object[] {}, DietManagerRowMapper.mapToDietMenuRM());
    }

    @Override
    public List<MenuItem> getMenuSelctionTrendForUser(Integer userId, Integer days) {
        StringBuilder query = new StringBuilder();
        query.append(
                "SELECT l.created_date_time, l.last_modified_date_time, l.fk_diet_menu_item_id, i.id, i.menu_item_name, i.menu_item_category, i.menu_item_description, i.menu_item_energy_kcal, i.menu_item_enabled, i.fk_diet_menu_id");
        query.append(" FROM  user_diet_menu_item_lnk l, diet_menu_items i");
        query.append(" WHERE l.fk_user_id IN(1,2,3,4,5,6)");
        query.append(" AND l.fk_diet_menu_item_id = i.id");
        // query.append(" ORDER BY l.created_date_time DESC");
        query.append(" LIMIT ?");
        return getJdbcTemplate().query(query.toString(), new Object[] { days }, new MenuTrendExtractor());
    }

    @Override
    public Map<Date, List<String>> getMissingMealsForUser(Integer id, Integer forLastDays) {
        Map<Date, List<String>> map = new TreeMap<>();
        List<String> defaultMeals = getJdbcTemplate().query("SELECT meal_name FROM diet_plan_meals WHERE meal_default = 1", new Object[] {},
                DietManagerRowMapper.mapToStringValueRM("meal_name"));
        List<KeyValuePair> list = getJdbcTemplate().query(DietManagerSql.createDroppedMealsOverviewQuery(), new Object[] { forLastDays },
                DietManagerRowMapper.mapToKeyValueRM("created_date", "meals"));
        if (LOG.isDebugEnabled()) {
            LOG.debug(list.toString());
        }
        // Loop through all days with missing meals
        for (KeyValuePair k : list) {
            List<MenuItem> thisDayMeals = getJdbcTemplate().query(DietManagerSql.createGetMealsForDate(), new Object[] { k.getKey() },
                    DietManagerRowMapper.mapToMenuItemRM());
            if (LOG.isDebugEnabled()) {
                LOG.debug("missing meals for: " + thisDayMeals);
            }
            List<String> thisDayMissingMeals = new ArrayList<>(defaultMeals);
            // Remove all completed meals
            for (MenuItem m : thisDayMeals) {
                thisDayMissingMeals.remove(m.getName());
            }
            if (thisDayMissingMeals.isEmpty()) {
                // The list contains now only not completed meals.
                map.put(Utility.timeToDate(k.getKey(), Utility.DATE_PATTERN), thisDayMissingMeals);
            }
        }
        return map;
    }

    // ----------------------------------------------------------------
    // Log events
    // ----------------------------------------------------------------

    @Override
    public int getNumberOfActivitiesLastDays(Integer days) {
        return getJdbcTemplate().queryForObject(DietManagerSql.createLogLevelCountQuery(), new Object[] { days, "ACTIVITY" },
                DietManagerRowMapper.mapCountRM());
    }

    @Override
    public int getNumberOfConflictsLastDays(Integer days) {
        return getJdbcTemplate().queryForObject(DietManagerSql.createLogLevelCountQuery(), new Object[] { days, "CONFLICT" },
                DietManagerRowMapper.mapCountRM());

    }

    @Override
    public int getNumberOfMealsLastDays(Integer days) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT count(*) AS count");
        query.append(" FROM user_diet_menu_item_lnk");
        query.append(" WHERE created_date_time > CURRENT_DATE - INTERVAL ? DAY");
        return getJdbcTemplate().queryForObject(query.toString(), new Object[] { days }, DietManagerRowMapper.mapCountRM());
    }

    @Override
    public Product getProduct(Integer productId) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM products WHERE id = ?", new Object[] { productId }, ProductTable.mapToProductRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + erae.toString());
            }
            return null;
        }
    }

    @Override
    public List<Product> getProducts() {
        return getJdbcTemplate().query("SELECT * FROM products p ORDER BY p.name, p.category DESC", new Object[] {}, ProductTable.mapToProductRM());
    }

    @Override
    public List<KeyValuePair> getSelectedMealsStatistic(Integer days) {
        return getJdbcTemplate().query(DietManagerSql.createSelectedMealsStatisticQuery(), new Object[] {},
                DietManagerRowMapper.mapToKeyValueRM(FIELD_MENU_ITEM_NAME, FIELD_MENU_ITEM_DESCRIPTION, FIELD_COUNT));
    }

    // ----------------------------------------------------------------
    // Diet rule
    // ----------------------------------------------------------------

    /**
     * Top 10 meal selections query: SELECT COUNT(l.fk_diet_menu_item_id) AS
     * item_count, (SELECT concat(i.menu_item_name,' ',i.menu_item_description)
     * FROM diet_menu_items i WHERE i.id = l.fk_diet_menu_item_id) FROM
     * user_diet_menu_item_lnk l GROUP BY l.fk_diet_menu_item_id ORDER BY
     * item_count DESC LIMIT 10
     * 
     * Meals controlled by count: SELECT COUNT(l.fk_controlled_by_user_id) AS
     * controlled_meal_count, u.username FROM user_diet_menu_item_lnk l, users u
     * WHERE l.fk_controlled_by_user_id = u.id GROUP BY
     * l.fk_controlled_by_user_id ORDER BY controlled_meal_count DESC
     * 
     * Missing meals: SELECT COUNT( date( l.created_date_time ) ) AS
     * meal_count_pr_day, date( l.created_date_time ) FROM
     * user_diet_menu_item_lnk l GROUP BY date( l.created_date_time ) HAVING
     * meal_count_pr_day =5 ORDER BY l.created_date_time DESC LIMIT 0 , 30
     */
    @Override
    public List<MenuItem> getSelectedMenuItemsForUser(Integer userId, Integer forDays) {
        StringBuilder countQuery = new StringBuilder();
        countQuery.append("SELECT count(*) FROM  user_diet_menu_item_lnk l");
        countQuery.append(" WHERE l.fk_user_id IN(1,2,3,4,5,6)");
        countQuery.append(" AND l.fk_diet_menu_item_id = i.id");
        countQuery.append(" LIMIT ?");
        StringBuilder mealsQuery = new StringBuilder();
        mealsQuery.append(
                "SELECT i.id, i.fk_diet_menu_id, i.menu_item_name, i.menu_item_category, i.menu_item_description, i.menu_item_energy_kcal, i.menu_item_enabled, l.id AS primary_key_id, l.created_date_time, l.last_modified_date_time, l.fk_controlled_by_user_id, l.caused_conflict, l.fk_log_id, m.meal_order");
        mealsQuery.append(",(").append(countQuery.toString()).append(") AS selection_count");
        mealsQuery.append(",(SELECT m.id FROM diet_plan_meals m WHERE i.menu_item_name = m.meal_name) AS fk_menu_item_name_id");
        mealsQuery.append(" FROM user_diet_menu_item_lnk l, diet_menu_items i, diet_plan_meals m");
        mealsQuery.append(" WHERE l.fk_user_id IN (1,2,3,4,5,6)");
        mealsQuery.append(" AND l.fk_diet_menu_item_id = i.id");
        mealsQuery.append(" AND i.menu_item_name = m.meal_name");
        mealsQuery.append(" ORDER BY l.created_date_time DESC");
        mealsQuery.append(" LIMIT ?");

        if (LOG.isDebugEnabled()) {
            LOG.debug("sql query: " + mealsQuery.toString());
        }
        return getJdbcTemplate().query(mealsQuery.toString(), new Object[] { forDays, forDays }, DietManagerRowMapper.mapToMenuItemRM());
    }

    // Statistic
    @Override
    public List<KeyValuePair> getSummaryStatistic(Integer days) {
        return getJdbcTemplate().query(DietManagerSql.createSummaryStatisticQuery(), new Object[] {},
                DietManagerRowMapper.mapToKeyValueRM(FIELD_USERNAME, FIELD_USERNAME, FIELD_MEALS_CONTROLLED_BY_USER_COUNT));
    }

    @Override
    public Integer getUserSelectedMenuItemCount(Integer userId, Integer dietMenuItemId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT count(l.fk_diet_menu_item_id) AS count");
        query.append(" FROM user_diet_menu_item_lnk l");
        query.append(" WHERE l.fk_user_id IN(1,2,3,4,5,6)");
        query.append(" AND l.fk_diet_menu_item_id = ?");
        return getJdbcTemplate().queryForObject(query.toString(), new Object[] { dietMenuItemId }, DietManagerRowMapper.mapCountRM());
    }

    @Override
    public int updateDietMenu(DietMenu dietMenu) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(dietMenu.toString());
        }
        return getJdbcTemplate().update(DietMenuTable.createUpdateQuery(), DietMenuTable.createUpdateParam(dietMenu));
    }

    @Override
    public int updateDietMenuItem(MenuItem menuItem) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(menuItem.toString());
        }
        return getJdbcTemplate().update(DietMenuItemTable.createUpdateQuery(), DietMenuItemTable.createUpdateParam(menuItem));
    }

    @Override
    public int updateDietRule(Rule rule) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(rule.toString());
        }
        return getJdbcTemplate().update(DietRuleTable.createUpdateQuery(), DietRuleTable.createUpdateParam(rule));

    }

    @Override
    public int updatePersonalHealthData(HealthLogEntry healthLogEntry) {
        // FIXME Auto-generated method stub
        return 0;
    }

    @Override
    public int updateProduct(Product product) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(product.toString());
        }
        return getJdbcTemplate().update(ProductTable.createUpdateQuery(), ProductTable.createUpdateParam(product));
    }

    @Override
    public int createFollowerForUser(Integer userId, Integer userFollowerId) {
        return createLink(UserFollowerLnkTable.TABLE_NAME, UserFollowerLnkTable.getFKColumnNames(), new Object[] { userId, userFollowerId });
    }

    @Override
    public int deleteFollowerForUser(Integer userId, Integer userFollowerId) {
        return deleteLink(UserFollowerLnkTable.TABLE_NAME, UserFollowerLnkTable.getFKColumnNames(), new Object[] { userId, userFollowerId });
    }

    private int createLink(String tableName, String[] columnNames, Object[] values) {
        // Check for null's
        if (StringUtils.isEmpty(tableName)) {
            throw new ApplicationException("Table name not set!");
        }
        TableHelper.checkInputs(columnNames, values);

        try {
            String query = TableHelper.createInsertQuery(tableName, columnNames);
            if (LOG.isDebugEnabled()) {
                StringBuilder sb = new StringBuilder();
                for (Object o : values) {
                    sb.append(o).append(",");
                }
                LOG.debug("query = {} , {}", query, sb.toString());
            }
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getJdbcTemplate().update(TableHelper.createInsertPreparedStatement(query, values), keyHolder);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Created new link in table: {} whith id: {}, for {}, {}", tableName, keyHolder.getKey().intValue(), values[0], values[1]);
            }
            runQuery("SELECT * FROM " + tableName, Type.class);
            return keyHolder.getKey().intValue();
        } catch (DuplicateKeyException e) {
            // Ignore it, the entry already exist
            LOG.warn(null, e);
            LOG.warn("Duplicate entry! table: {}, {} = {}, {} = {}", tableName, columnNames[0], values[0], columnNames[1], values[1]);
            return 0;
        } catch (Exception sqle) {
            LOG.error("Error creating link! table: {}, {} = {}, {} = {}", tableName, columnNames[0], values[0], columnNames[1], values[1]);
            throw new ApplicationException("Error creating link! table=" + tableName + " " + columnNames[0] + " = " + values[0] + ", " + columnNames[1] + " = "
                    + values[1] + "\n" + sqle);
        }
    }

    /**
     * Delete only the last inserted row
     * 
     * @param tableName
     * @param columnNames
     * @param values
     * @return
     */
    private int deleteLink(String tableName, String[] columnNames, Object[] values) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(tableName);
        query.append(" WHERE ").append(columnNames[0]).append(" = ?");
        query.append(" AND ").append(columnNames[1]).append(" = ?");
        query.append(" ORDER BY created_date_time DESC");
        query.append(" LIMIT 1");
        if (LOG.isDebugEnabled()) {
            LOG.debug("query=" + query.toString() + ", id1 = " + values[0] + ", id2 = " + values[1]);
        }
        return getJdbcTemplate().update(query.toString(), values);
    }

    private List<KeyValuePair> getDietPlanMealRules(Integer dietPlanId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT i.meal_item_name, i.meal_item_description");
        query.append(" FROM diet_plan_meal_items i");
        query.append(" WHERE i.fk_diet_plan_id = ?");
        query.append(" AND i.meal_item_name LIKE 'Regler%'");
        query.append(" ORDER BY i.meal_item_description ASC");
        return getJdbcTemplate().query(query.toString(), new Object[] { dietPlanId },
                DietManagerRowMapper.mapToKeyValueRM("meal_item_name", "meal_item_description"));
    }

    private List<KeyValuePairList> mapToKeyValuePairList(List<KeyValuePair> keyValuePairs) {
        Map<String, KeyValuePairList> map = new HashMap<>();
        for (KeyValuePair p : keyValuePairs) {
            if (!map.containsKey(p.getKey())) {
                List<String> list = new ArrayList<>();
                list.add(p.getValue());
                map.put(p.getKey(), new KeyValuePairList(p.getName(), p.getKey(), list));
            } else {
                List<String> list = map.get(p.getKey()).getValue();
                list.add(p.getValue());
            }
        }
        List<KeyValuePairList> list = new ArrayList<>(map.values());
        Collections.sort(list, new KeyValuePairComparator());
        return list;
    }

    private class KeyValuePairComparator implements Comparator<KeyValuePairList> {
        @Override
        public int compare(final KeyValuePairList kv1, final KeyValuePairList kv2) {
            return kv1.getKeyToCompare().compareTo(kv2.getKeyToCompare());
        }
    }

    private void runQuery(String sqlQuery, Class<?> clazz) {
        // List<Object> list = getJdbcTemplate().query(sqlQuery, new
        // BeanPropertyRowMapper(clazz));
        if (LOG.isDebugEnabled()) {
            // LOG.debug("----------------------------");
            LOG.debug(clazz.getName() + " Query: " + sqlQuery);
            // LOG.debug("----------------------------");
            // for (Object obj : list) {
            // LOG.debug("result: " + obj);
            // }
            // LOG.debug("----------------------------");
        }
    }
}
