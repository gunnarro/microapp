package com.gunnarro.sportsteam.repository.table.party;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.sportsteam.domain.party.User;
import com.gunnarro.sportsteam.repository.table.TableHelper;

public class UsersTable {

    public static PreparedStatementCreator createInsertPreparedStatement(final User user) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setObject(2, user.getUserName());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getEmail());
                ps.setInt(5, user.isActivated() ? 1 : 0);
                return ps;
            }
        };
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }

    public static Object[] createUpdateParam(User user) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), user.getPassword(), user.getEmail(), user.isActivated() ? 1 : 0, user.getId() };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }

    // Database table
    public static final String TABLE_NAME = "users";

    public static final String COLUMN_USERNAME = "username";

    public static final String COLUMN_PASSWORD = "password";

    public static final String COLUMN_EMAIL = "email";

    public static final String COLUMN_ENABLED = "enabled";

    private static String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_EMAIL, COLUMN_ENABLED };

    private static String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_PASSWORD, COLUMN_EMAIL, COLUMN_ENABLED };

    private UsersTable() {
    }
}
