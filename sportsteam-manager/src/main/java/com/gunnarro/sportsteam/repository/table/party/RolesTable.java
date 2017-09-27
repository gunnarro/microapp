package com.gunnarro.sportsteam.repository.table.party;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.sportsteam.repository.table.TableHelper;

public class RolesTable {

    public static PreparedStatementCreator createInsertPreparedStatement(final String userName, final String role) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setObject(2, userName);
                ps.setString(3, role);
                return ps;
            }
        };
    }
    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }
    // Database table
    public static final String TABLE_NAME = "roles";

    public static final String COLUMN_USERNAME = "username";

    public static final String COLUMN_ROLE = "role";
    
    private static String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_USERNAME, COLUMN_ROLE };

    private RolesTable() {
    }

}
