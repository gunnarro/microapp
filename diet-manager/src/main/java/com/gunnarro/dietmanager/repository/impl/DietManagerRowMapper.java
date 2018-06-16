package com.gunnarro.dietmanager.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.gunnarro.dietmanager.domain.activity.ActivityLog;
import com.gunnarro.dietmanager.domain.diet.DietMenu;
import com.gunnarro.dietmanager.domain.diet.DietPlan;
import com.gunnarro.dietmanager.domain.diet.FoodProduct;
import com.gunnarro.dietmanager.domain.diet.MenuItem;
import com.gunnarro.dietmanager.domain.diet.Rule;
import com.gunnarro.dietmanager.domain.diet.Type;
import com.gunnarro.dietmanager.domain.health.HealthLogEntry;
import com.gunnarro.dietmanager.domain.health.ReferenceData;
import com.gunnarro.dietmanager.domain.log.LogComment;
import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.dietmanager.domain.statistic.BodyMeasurementStatistic;
import com.gunnarro.dietmanager.domain.statistic.KeyValuePair;
import com.gunnarro.dietmanager.domain.statistic.MealStatistic;
import com.gunnarro.dietmanager.endpoint.rest.ChartData;
import com.gunnarro.dietmanager.utility.Utility;
import com.gunnarro.useraccount.domain.user.LocalUser;
import com.gunnarro.useraccount.domain.user.Privilege;
import com.gunnarro.useraccount.domain.user.Profile;
import com.gunnarro.useraccount.domain.user.Role;

/**
 * This call contain RowMapper which is required for converting ResultSet into
 * java domain class
 * 
 */
public class DietManagerRowMapper {

    private static final Logger LOG = LoggerFactory.getLogger(DietManagerRowMapper.class);

    /**
     * In order to hide public constructor
     */
    private DietManagerRowMapper() {
    }

    public static class MenuItemsExtractor implements ResultSetExtractor<Map<String, List<MenuItem>>> {
        @Override
        public Map<String, List<MenuItem>> extractData(ResultSet resultSet) throws SQLException {
            Map<String, List<MenuItem>> map = new HashMap<>();
            while (resultSet.next()) {
                MenuItem menuItem = mapMenuItemRow(resultSet);
                if (!map.containsKey(menuItem.getName())) {
                    List<MenuItem> list = new ArrayList<>();
                    list.add(menuItem);
                    map.put(menuItem.getName(), list);
                } else {
                    map.get(menuItem.getName()).add(menuItem);
                }
            }
            return map;
        }
    }

    public static class MenuTrendExtractor implements ResultSetExtractor<List<MenuItem>> {
        @Override
        public List<MenuItem> extractData(ResultSet resultSet) throws SQLException {
            List<MenuItem> list = new ArrayList<>();
            while (resultSet.next()) {
                MenuItem menuItem = mapMenuItemRow(resultSet);
                list.add(menuItem);
            }
            return list;
        }
    }

    public static RowMapper<Integer> mapCountRM() {
        return new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return resultSet.getInt("count");
            }
        };
    }

    public static RowMapper<ChartData> mapToChartDataRM() {
        return new RowMapper<ChartData>() {
            @Override
            public ChartData mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                ChartData data = new ChartData();
                data.setId(resultSet.getInt("id"));
                data.setLabel(resultSet.getString("label"));
                data.setValue1(resultSet.getDouble("value1"));
                data.setValue2(resultSet.getDouble("value2"));
                data.setValue3(resultSet.getDouble("value3"));
                return data;
            }
        };
    }

    public static RowMapper<MenuItem> mapToDietMenuItemRM() {
        return new RowMapper<MenuItem>() {
            @Override
            public MenuItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return mapMenuItemRow(resultSet);
            }
        };
    }

    public static RowMapper<KeyValuePair> mapToDietMenuItemsRM() {
        return new RowMapper<KeyValuePair>() {
            @Override
            public KeyValuePair mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                String name = resultSet.getString("menu_item_name");
                return new KeyValuePair(name, resultSet.getString("menu_item_description"));
            }
        };
    }

    public static RowMapper<DietMenu> mapToDietMenuRM() {
        return new RowMapper<DietMenu>() {
            @Override
            public DietMenu mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                DietMenu dieltMenu = new DietMenu();
                dieltMenu.setId(resultSet.getInt("id"));
                dieltMenu.setName(resultSet.getString("menu_name"));
                dieltMenu.setDescription(resultSet.getString("menu_description"));
                boolean isActiv = resultSet.getInt("menu_active") != 0;
                dieltMenu.setActive(isActiv);
                return dieltMenu;
            }
        };
    }

    public static RowMapper<KeyValuePair> mapToDietPlanMealRM() {
        return new RowMapper<KeyValuePair>() {
            @Override
            public KeyValuePair mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                String key = resultSet.getString("meal_name") + " " + resultSet.getString("meal_period");
                return new KeyValuePair(resultSet.getString("meal_name"), key, resultSet.getString("meal_item_description"));
            }
        };
    }

    public static RowMapper<DietPlan> mapToDietPlanRM() {
        return new RowMapper<DietPlan>() {
            @Override
            public DietPlan mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                DietPlan dieltPlan = new DietPlan();
                dieltPlan.setId(resultSet.getInt("id"));
                dieltPlan.setName(resultSet.getString("diet_plan_name"));
                dieltPlan.setDescription(resultSet.getString("diet_plan_description"));
                boolean isActiv = resultSet.getInt("diet_plan_active") != 0;
                dieltPlan.setActive(isActiv);
                return dieltPlan;
            }
        };
    }

    public static RowMapper<FoodProduct> mapToFoodProductRM() {
        return new RowMapper<FoodProduct>() {
            @Override
            public FoodProduct mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                FoodProduct product = new FoodProduct();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("product_name"));
                product.setType(resultSet.getString("product_type"));
                product.setDescription(resultSet.getString("product_description"));
                return product;
            }
        };
    }

    public static RowMapper<HealthLogEntry> mapToHealthLogEntryRM() {
        return new RowMapper<HealthLogEntry>() {
            @Override
            public HealthLogEntry mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                HealthLogEntry log = new HealthLogEntry();
                log.setId(resultSet.getInt("id"));
                log.setUserId(resultSet.getInt("fk_user_id"));
                log.setLogDate(new Date(resultSet.getTimestamp("log_date").getTime()));
                log.setWeight(resultSet.getDouble("weight"));
                log.setHeight(resultSet.getDouble("height"));
                log.setReferenceHeight(resultSet.getDouble("weight"));
                log.setReferenceWeight(resultSet.getDouble("height"));
                log.setComment(resultSet.getString("comment"));
                log.setWeightMetric(resultSet.getString("weight_metric"));
                log.setHeightMetric(resultSet.getString("height_metric"));
                try {
                    log.setMonthsOld(resultSet.getInt("months_old"));
                } catch (SQLException sqle) {
                    // ignore
                }
                return log;
            }
        };
    }

    public static RowMapper<BodyMeasurementStatistic> mapToBodyMeasurementStatisticRM() {
        return new RowMapper<BodyMeasurementStatistic>() {
            @Override
            public BodyMeasurementStatistic mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                BodyMeasurementStatistic log = new BodyMeasurementStatistic();
                log.setStartDate(new Date(resultSet.getTimestamp("start_date").getTime()));
                log.setEndDate(new Date(resultSet.getTimestamp("end_date").getTime()));
                log.setAverageWeight(resultSet.getDouble("average_weight"));
                log.setAverageHeight(resultSet.getDouble("average_height"));
                log.setWeightMetric(resultSet.getString("weight_metric"));
                log.setHeightMetric(resultSet.getString("height_metric"));
                log.setMinWeight(resultSet.getDouble("min_weight"));
                log.setMaxWeight(resultSet.getDouble("max_weight"));
                log.setMinHeight(resultSet.getDouble("min_height"));
                log.setMaxHeight(resultSet.getDouble("max_height"));
                return log;
            }
        };
    }

    public static RowMapper<ReferenceData> mapToReferenceDataRM() {
        return new RowMapper<ReferenceData>() {
            @Override
            public ReferenceData mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                ReferenceData data = new ReferenceData();
                data.setId(resultSet.getInt("id"));
                data.setMonth(resultSet.getInt("month"));
                // data.setGender(resultSet.getString("gender").charAt(0));
                data.setBmiP25(resultSet.getDouble("bmi_p25"));
                data.setBmiP50(resultSet.getDouble("bmi_p50"));
                data.setBmiP75(resultSet.getDouble("bmi_p75"));
                data.setWeightP25(resultSet.getDouble("weight_p25"));
                data.setWeightP50(resultSet.getDouble("weight_p50"));
                data.setWeightP75(resultSet.getDouble("weight_p75"));
                data.setHeightP25(resultSet.getDouble("height_p25"));
                data.setHeightP25(resultSet.getDouble("height_p50"));
                data.setHeightP25(resultSet.getDouble("height_p75"));
                return data;
            }
        };
    }

    public static RowMapper<KeyValuePair> mapToKeyValueRM() {
        return new RowMapper<KeyValuePair>() {
            @Override
            public KeyValuePair mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return new KeyValuePair(resultSet.getString("key"), resultSet.getString("value"));
            }
        };
    }

    public static RowMapper<KeyValuePair> mapToKeyValueRM(final String keyColName, final String valueColName) {
        return new RowMapper<KeyValuePair>() {
            @Override
            public KeyValuePair mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return new KeyValuePair(resultSet.getString(keyColName), resultSet.getString(valueColName));
            }
        };
    }

    public static RowMapper<KeyValuePair> mapToKeyValueRM(final String keyColName, final String valueColName, final String countColName) {
        return new RowMapper<KeyValuePair>() {
            @Override
            public KeyValuePair mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                KeyValuePair keyValuePair = new KeyValuePair(resultSet.getString(keyColName), resultSet.getString(valueColName),
                        resultSet.getInt(countColName));
                try {
                    keyValuePair.setId(resultSet.getInt("id"));
                    keyValuePair.setTotalCount(resultSet.getInt("total_count"));
                    keyValuePair.setFromDate(new Date(resultSet.getTimestamp("from_date").getTime()));
                    keyValuePair.setToDate(new Date(resultSet.getTimestamp("to_date").getTime()));
                } catch (SQLException sqle) {
                    // ignore, field not found
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Error: " + sqle.getMessage());
                    }
                }
                return keyValuePair;
            }
        };
    }

    public static RowMapper<MealStatistic> mapToMealStatisticRM(final Integer userId) {
        return new RowMapper<MealStatistic>() {
            @Override
            public MealStatistic mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                MealStatistic statistic = new MealStatistic();
                statistic.setUserId(userId);
                statistic.setUserName(resultSet.getString("username"));
                statistic.setYear(resultSet.getInt("year"));
                statistic.setWeekNumber(resultSet.getInt("weeknumber"));
                statistic.setMealsControlledByUserCount(resultSet.getInt("meals_controlled_by_user_count"));
                statistic.setMealsPreparedByUserCount(resultSet.getInt("meals_prepared_by_user_count"));
                statistic.setMealsCausedConflictCount(resultSet.getInt("meals_caused_conflict_count"));
                statistic.setFromDate(new Date(resultSet.getTimestamp("created_date").getTime()));
                // statistic.setToDate(new
                // Date(resultSet.getTimestamp("to_date").getTime()));
                statistic.setCreatedDate(new Date(resultSet.getTimestamp("created_date").getTime()));
                return statistic;
            }
        };
    }

    public static RowMapper<ActivityLog> mapToActivityLogRM() {
        return new RowMapper<ActivityLog>() {
            @Override
            public ActivityLog mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                ActivityLog log = new ActivityLog();
                log.setId(resultSet.getInt("id"));
                log.setFkUserId(resultSet.getInt("fk_user_id"));
                log.setCreatedDate(new Date(resultSet.getTimestamp("created_date_time").getTime()));
                log.setLastModifiedTime(resultSet.getTimestamp("last_modified_date_time").getTime());
                log.setDescription(resultSet.getString("description"));
                return log;
            }
        };
    }

    public static RowMapper<LogEntry> mapToLogEntryRM() {
        return new RowMapper<LogEntry>() {
            @Override
            public LogEntry mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                LogEntry log = new LogEntry();
                log.setId(resultSet.getInt("id"));
                log.setFkUserId(resultSet.getInt("fk_user_id"));
                log.setCreatedDate(new Date(resultSet.getTimestamp("created_date_time").getTime()));
                log.setLastModifiedTime(resultSet.getTimestamp("last_modified_date_time").getTime());
                log.setContent(resultSet.getString("content"));
                log.setContentHtml(Utility.convertMarkdownToHtml(resultSet.getString("content")));
                log.setLevel(resultSet.getString("level"));
                log.setTitle(resultSet.getString("title"));
                try {
                    log.setNumberOfComments(resultSet.getInt("number_of_comments"));
                } catch (SQLException sqle) {
                    // ignore, the column diden't exist
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Error: " + sqle.getMessage());
                    }
                }
                try {
                    log.setCreatedByUser(resultSet.getString("username"));
                } catch (SQLException sqle) {
                    // ignore, the column diden't exist
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Error: " + sqle.getMessage());
                    }
                }
                return log;
            }
        };
    }

    public static RowMapper<LogComment> mapToLogCommentRM() {
        return new RowMapper<LogComment>() {
            @Override
            public LogComment mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                LogComment comment = new LogComment();
                comment.setId(resultSet.getInt("id"));
                comment.setFkUserId(resultSet.getInt("fk_user_id"));
                comment.setFkLogId(resultSet.getInt("fk_event_log_id"));
                comment.setCreatedDate(new Date(resultSet.getTimestamp("created_date_time").getTime()));
                comment.setLastModifiedTime(resultSet.getTimestamp("last_modified_date_time").getTime());
                comment.setContent(resultSet.getString("content"));
                comment.setContentHtml(Utility.convertMarkdownToHtml(resultSet.getString("content")));
                try {
                    comment.setCreatedByUser(resultSet.getString("username"));
                } catch (SQLException sqle) {
                    // ignore, the column diden't exist
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Error: " + sqle.getMessage());
                    }
                }
                return comment;
            }
        };
    }

    public static RowMapper<MenuItem> mapToMenuItemRM() {
        return new RowMapper<MenuItem>() {
            @Override
            public MenuItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return mapMenuItemRow(resultSet);
            }
        };
    }

    public static RowMapper<KeyValuePair> mapToProductItemsRM() {
        return new RowMapper<KeyValuePair>() {
            @Override
            public KeyValuePair mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return new KeyValuePair(resultSet.getString("name"), resultSet.getString("name"));
            }
        };
    }

    public static RowMapper<Role> mapToRoleRM() {
        return new RowMapper<Role>() {
            @Override
            public Role mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return new Role(resultSet.getInt("id"), resultSet.getString("name"));
            }
        };
    }

    public static RowMapper<Privilege> mapToPrivilegeRM() {
        return new RowMapper<Privilege>() {
            @Override
            public Privilege mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return new Privilege(resultSet.getInt("id"), resultSet.getString("name"));
            }
        };
    }

    public static RowMapper<Rule> mapToRuleRM() {
        return new RowMapper<Rule>() {
            @Override
            public Rule mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Rule rule = new Rule();
                rule.setId(resultSet.getInt("id"));
                rule.setFkDietPlanId(resultSet.getInt("fk_diet_plan_id"));
                rule.setCreatedTime(resultSet.getTimestamp("created_date_time").getTime());
                rule.setLastModifiedTime(resultSet.getTimestamp("last_modified_date_time").getTime());
                rule.setName(resultSet.getString("rule_name"));
                rule.setDescription(resultSet.getString("rule_description"));
                rule.setActive(resultSet.getInt("rule_active") == 0 ? false : true);
                return rule;
            }
        };
    }

    public static RowMapper<String> mapToStringValueRM(final String colName) {
        return new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return resultSet.getString(colName);
            }
        };
    }

    public static RowMapper<Type> mapToTypeRM() {
        return new RowMapper<Type>() {
            @Override
            public Type mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Type type = new Type(resultSet.getString("name"));
                type.setId(resultSet.getInt("id"));
                try {
                    type.setDescription(resultSet.getString("description"));
                } catch (SQLException sqle) {
                    // ignore, the column diden't exist
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Error: " + sqle.getMessage());
                    }
                }
                return type;
            }
        };
    }

    public static RowMapper<LocalUser> mapToUserRM() {
        return new RowMapper<LocalUser>() {
            @Override
            public LocalUser mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                LocalUser user = new LocalUser();
                user.setCreatedDate(resultSet.getTimestamp("created_date_time"));
                user.setLastModifiedDate(resultSet.getTimestamp("last_modified_date_time"));
                user.setId(resultSet.getInt("id"));
                user.setUserId(resultSet.getString("username"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setActivated(resultSet.getInt("enabled") == 1 ? true : false);
                return user;
            }
        };
    }

    public static RowMapper<Profile> mapToProfileRM() {
        return new RowMapper<Profile>() {
            @Override
            public Profile mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Profile profile = new Profile();
                // profile.setCreatedDate(resultSet.getTimestamp("created_date_time"));
                // profile.setLastModifiedDate(resultSet.getTimestamp("last_modified_date_time"));
                profile.setId(resultSet.getInt("id"));
                profile.setUserId(resultSet.getInt("fk_user_id"));
                profile.setFirstName(resultSet.getString("firstname"));
                profile.setMiddleName(resultSet.getString("middlename"));
                profile.setLastName(resultSet.getString("lastname"));
                profile.setEmailAddress(resultSet.getString("email"));
                profile.setGender(resultSet.getString("gender").charAt(0));
                profile.setDateOfBirth(new Date(resultSet.getDate("date_of_birth").getTime()));
                // profile.setActivated(resultSet.getInt("enabled") == 1 ? true
                // : false);
                return profile;
            }
        };
    }

    public static void putMap(MenuItem menuItem, Map<Integer, MenuItem> map) {
        if (!map.containsKey(menuItem.getId())) {
            List<MenuItem> list = new ArrayList<MenuItem>();
            list.add(menuItem);
            map.put(menuItem.getId(), menuItem);
        } else {
            map.get(menuItem.getId()).addSelectionTrends(Utility.formatTime(menuItem.getCreatedTime(), "dd.MM.yyyy"));
        }
    }

    public static RowMapper<String> singelValueRM(final String colomnName) {
        return new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return resultSet.getString(colomnName);
            }
        };
    }

    private static MenuItem mapMenuItemRow(ResultSet resultSet) throws SQLException {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(resultSet.getInt("id"));
        menuItem.setFkDietMenuId(resultSet.getInt("fk_diet_menu_id"));
        menuItem.setCreatedTime(resultSet.getTimestamp("created_date_time").getTime());
        menuItem.setLastModifiedTime(resultSet.getTimestamp("last_modified_date_time").getTime());
        menuItem.setName(resultSet.getString("menu_item_name"));
        menuItem.setCategory(resultSet.getString("menu_item_category"));
        menuItem.setDescription(resultSet.getString("menu_item_description"));
        menuItem.setEnergy(resultSet.getInt("menu_item_energy_kcal"));
        menuItem.setEnabled(resultSet.getInt("menu_item_enabled") == 1 ? true : false);
        try {
            menuItem.setPrimaryKeyId(resultSet.getInt("primary_key_id"));
        } catch (SQLException sqle) {
            // ignore, field not found
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + sqle.getMessage());
            }
        }
        try {
            menuItem.setImageLink(resultSet.getString("menu_item_img_link"));
        } catch (SQLException sqle) {
            // ignore, field not found
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + sqle.getMessage());
            }
        }
        try {
            menuItem.setSortByValue(resultSet.getString("fk_menu_item_name_id") + "_" + resultSet.getString("menu_item_name"));
        } catch (SQLException sqle) {
            // ignore, field not found
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + sqle.getMessage());
            }
        }
        try {
            menuItem.setSelectedCount(resultSet.getInt("selection_count"));
        } catch (SQLException sqle) {
            // ignore, field not found
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + sqle.getMessage());
            }
        }
        try {
            menuItem.setControlledByUserId(resultSet.getInt("fk_controlled_by_user_id"));
        } catch (SQLException sqle) {
            // ignore, field not found
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + sqle.getMessage());
            }
        }
        try {
            menuItem.setPreparedByUserId(resultSet.getInt("fk_prepared_by_user_id"));
        } catch (SQLException sqle) {
            // ignore, field not found
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + sqle.getMessage());
            }
        }
        try {
            menuItem.setCausedConflict(resultSet.getInt("caused_conflict"));
        } catch (SQLException sqle) {
            // ignore, field not found
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + sqle.getMessage());
            }
        }
        try {
            menuItem.setFkLogId(resultSet.getInt("fk_log_id"));
        } catch (SQLException sqle) {
            // ignore, field not found
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + sqle.getMessage());
            }
        }
        try {
            menuItem.setSortByValue(Integer.toString(resultSet.getInt("meal_order")));
        } catch (SQLException sqle) {
            // ignore, field not found
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + sqle.getMessage());
            }
        }
        return menuItem;
    }
}