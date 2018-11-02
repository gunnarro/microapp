package com.gunnarro.dietmanager.repository.table.diet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.dietmanager.domain.diet.DietMenu;
import com.gunnarro.dietmanager.repository.table.TableHelper;

/**
 * 
 * @author admin
 * 
 */
public class DietMenuTable {

    public static final String COLUMN_MENU_ACTIVE = "menu_active";
    public static final String COLUMN_MENU_DESCRIPTION = "menu_description";
    public static final String COLUMN_MENU_NAME = "menu_name";
    // Database table
    public static final String TABLE_NAME = "diet_menus";

    private static final String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_CREATED_DATETIME, TableHelper.COLUMN_LAST_MODIFIED_DATETIME,
            COLUMN_MENU_NAME, COLUMN_MENU_DESCRIPTION, COLUMN_MENU_ACTIVE };

    private static final String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_MENU_NAME, COLUMN_MENU_DESCRIPTION,
            COLUMN_MENU_ACTIVE };

    /**
     * In order to hide public constructor
     */
    private DietMenuTable() {
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final DietMenu dietMenu) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setTimestamp(2, new Timestamp(TableHelper.getToDay()));
                ps.setObject(3, dietMenu.getName().trim());
                ps.setObject(4, dietMenu.getDescription().trim());
                ps.setObject(5, dietMenu.isActive() ? 1 : 0);
                return ps;
            }
        };
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }

    public static Object[] createUpdateParam(DietMenu dietMenu) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), dietMenu.getName().trim(), dietMenu.getDescription().trim(), dietMenu.isActive() ? 1 : 0,
                dietMenu.getId() };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }

}
