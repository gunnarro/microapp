package com.gunnarro.dietmanager.repository.table.link;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.dietmanager.domain.diet.MenuItem;
import com.gunnarro.dietmanager.repository.table.TableHelper;

public class UserDietMenuItemLnkTable {

    // Database table
    public static final String TABLE_NAME = "user_diet_menu_item_lnk";
    public static final String COLUMN_CAUSED_CONFLICT = "caused_conflict";
    public static final String COLUMN_FK_CONTROLLED_BY_USER_ID = "fk_controlled_by_user_id";
    public static final String COLUMN_FK_DIET_MENU_ITEM_ID = "fk_diet_menu_item_id";
    public static final String COLUMN_FK_PREPARED_BY_USER_ID = "fk_prepared_by_user_id";
    public static final String COLUMN_FK_USER_ID = "fk_user_id";
    public static final String COLUMN_FK_LOG_ID = "fk_log_id";

    private static final String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_CREATED_DATETIME, COLUMN_FK_USER_ID, COLUMN_FK_DIET_MENU_ITEM_ID,
            COLUMN_FK_CONTROLLED_BY_USER_ID, COLUMN_FK_PREPARED_BY_USER_ID, COLUMN_CAUSED_CONFLICT, COLUMN_FK_LOG_ID };

    private static final String[] TABLE_FK_COLUMNS = { COLUMN_FK_USER_ID, COLUMN_FK_DIET_MENU_ITEM_ID, COLUMN_FK_CONTROLLED_BY_USER_ID,
            COLUMN_FK_PREPARED_BY_USER_ID, COLUMN_CAUSED_CONFLICT, COLUMN_FK_LOG_ID };

    /**
     * In order to hide public constructor
     */
    private UserDietMenuItemLnkTable() {
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final Integer userId, final MenuItem menuItem) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(menuItem.getCreatedDate().getTime()));
                ps.setInt(2, userId);
                ps.setInt(3, menuItem.getId());
                ps.setInt(4, menuItem.getControlledByUserId());
                ps.setInt(5, menuItem.getPreparedByUserId());
                ps.setInt(6, menuItem.getCausedConflict());
                if (menuItem.getFkLogId() != null) {
                    ps.setInt(7, menuItem.getFkLogId());
                } else {
                    ps.setNull(7, java.sql.Types.INTEGER);
                }
                return ps;
            }
        };
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }

    public static String[] getFKColumnNames() {
        return TABLE_FK_COLUMNS.clone();
    }

}
