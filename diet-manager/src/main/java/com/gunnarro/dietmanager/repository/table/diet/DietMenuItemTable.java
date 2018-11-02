package com.gunnarro.dietmanager.repository.table.diet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.dietmanager.domain.diet.MenuItem;
import com.gunnarro.dietmanager.repository.table.TableHelper;

/**
 * 
 * @author admin
 * 
 */
public class DietMenuItemTable {

    public static final String COLUMN_FK_DIET_MENU_ID = "fk_diet_menu_id";
    public static final String COLUMN_MENU_ITEM_CATEGORY = "menu_item_category";
    public static final String COLUMN_MENU_ITEM_DESCRIPTION = "menu_item_description";
    public static final String COLUMN_MENU_ITEM_ENERGY_KCAL = "menu_item_energy_kcal";
    public static final String COLUMN_MENU_ITEM_IMG_LINK = "menu_item_img_link";
    public static final String COLUMN_MENU_ITEM_NAME = "menu_item_name";
    public static final String COLUMN_MENU_ITEM_ENABLED = "menu_item_enabled";
    // Database table
    public static final String TABLE_NAME = "diet_menu_items";

    private static final String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.ColumnsDefaultEnum.createdDateTime.name(),
            TableHelper.ColumnsDefaultEnum.lastModifiedDateTime.name(), COLUMN_FK_DIET_MENU_ID, COLUMN_MENU_ITEM_NAME, COLUMN_MENU_ITEM_CATEGORY,
            COLUMN_MENU_ITEM_DESCRIPTION, COLUMN_MENU_ITEM_ENERGY_KCAL, COLUMN_MENU_ITEM_IMG_LINK, COLUMN_MENU_ITEM_ENABLED };

    private static final String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.ColumnsDefaultEnum.lastModifiedDateTime.name(), COLUMN_MENU_ITEM_CATEGORY,
            COLUMN_MENU_ITEM_DESCRIPTION, COLUMN_MENU_ITEM_ENERGY_KCAL, COLUMN_MENU_ITEM_IMG_LINK, COLUMN_MENU_ITEM_ENABLED };

    /**
     * In order to hide public constructor
     */
    private DietMenuItemTable() {
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final MenuItem meuItem) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setTimestamp(2, new Timestamp(TableHelper.getToDay()));
                ps.setInt(3, meuItem.getFkDietMenuId());
                ps.setObject(4, meuItem.getName().trim());
                ps.setObject(5, meuItem.getCategory().trim());
                ps.setObject(6, meuItem.getDescription().trim());
                ps.setObject(7, meuItem.getEnergy());
                ps.setObject(8, meuItem.getImageLink());
                ps.setInt(9, meuItem.isEnabled() ? 1 : 0);
                return ps;
            }
        };
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }

    public static Object[] createUpdateParam(MenuItem menuItem) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), menuItem.getCategory().trim(), menuItem.getDescription().trim(), menuItem.getEnergy(),
                menuItem.getImageLink(), menuItem.isEnabled() ? 1 : 0, menuItem.getId() };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }

}
