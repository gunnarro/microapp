package com.gunnarro.dietmanager.repository.table.link;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.dietmanager.repository.table.TableHelper;

public class UserRoleLnkTable {

    // Database table
    public static final String TABLE_NAME = "user_role_lnk";

    public static final String COLUMN_FK_USER_ID = "fk_user_id";
    public static final String COLUMN_FK_ROLE_ID = "fk_role_id";

    private static final String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_CREATED_DATETIME, TableHelper.COLUMN_LAST_MODIFIED_DATETIME,
            COLUMN_FK_USER_ID, COLUMN_FK_ROLE_ID };

    private static final String[] TABLE_FK_COLUMNS = { COLUMN_FK_USER_ID, COLUMN_FK_ROLE_ID };

    /**
     * In order to hide public constructor
     */
    private UserRoleLnkTable() {
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final Integer userId, final Integer roleId) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setTimestamp(2, new Timestamp(TableHelper.getToDay()));
                ps.setInt(3, userId);
                ps.setInt(4, roleId);
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
