package com.gunnarro.dietmanager.repository.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.dietmanager.domain.diet.Rule;

/**
 * 
 * @author admin
 * 
 */
public class DietRuleTable {

    public static final String COLUMN_FK_DIET_PLAN_ID = "fk_diet_plan_id";
    public static final String COLUMN_RULE_ACTIVE = "rule_active";
    public static final String COLUMN_RULE_DESCRIPTION = "rule_description";
    public static final String COLUMN_RULE_NAME = "rule_name";
    // Database table
    public static final String TABLE_NAME = "diet_rules";

    private static final String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_CREATED_DATETIME, TableHelper.COLUMN_LAST_MODIFIED_DATETIME,
            COLUMN_FK_DIET_PLAN_ID, COLUMN_RULE_NAME, COLUMN_RULE_DESCRIPTION, COLUMN_RULE_ACTIVE };

    private static final String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_RULE_DESCRIPTION,
            COLUMN_RULE_ACTIVE };

    /**
     * In order to hide public constructor
     */
    private DietRuleTable() {
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final Rule rule) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setTimestamp(2, new Timestamp(TableHelper.getToDay()));
                ps.setInt(3, rule.getFkDietPlanId());
                ps.setString(4, rule.getName());
                ps.setString(5, rule.getDescription());
                ps.setInt(6, rule.isActive() ? 1 : 0);
                return ps;
            }
        };
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }

    public static Object[] createUpdateParam(Rule rule) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), rule.getDescription(), rule.isActive() ? 1 : 0, rule.getId() };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }

}
