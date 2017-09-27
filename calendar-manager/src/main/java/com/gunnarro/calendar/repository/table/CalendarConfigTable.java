package com.gunnarro.calendar.repository.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.calendar.domain.calendar.CalendarConfig;

public class CalendarConfigTable {

    public static Object[] createInsertParam(CalendarConfig config) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), config.getName(), config.getTeamName(), config.getUrl(), config.getFormat(), config.getDescription() };
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, TABLE_COLUMNS);
    }

    public static Object[] createUpdateParam(CalendarConfig config) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), config.getUrl(), config.getFormat(), config.isEnabled() ? 1 : 0, config.getId() };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, TABLE_COLUMNS_UPDATE);
    }

    // Database table
    public static final String TABLE_NAME = "calendar_urls";

    public static final String COLUMN_NAME = "name";

    public static final String COLUMN_URL = "url";

    public static final String COLUMN_FORMAT = "format";

    public static final String COLUMN_DESCRIPTION = "description";

    public static final String COLUMN_TEAM_NAME = "team_name";

    public static final String COLUMN_ENABLED = "enabled";

    private static String[] TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_NAME, COLUMN_TEAM_NAME, COLUMN_URL, COLUMN_FORMAT, COLUMN_DESCRIPTION,
            COLUMN_ENABLED };

    private static String[] TABLE_COLUMNS_UPDATE = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_URL, COLUMN_FORMAT, COLUMN_ENABLED };

    private CalendarConfigTable() {
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final CalendarConfig config) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setString(2, config.getName());
                ps.setString(3, config.getTeamName());
                ps.setString(4, config.getUrl());
                ps.setString(5, config.getFormat());
                ps.setString(6, config.getDescription());
                ps.setInt(7, config.isEnabled() ? 1 : 0);
                return ps;
            }
        };
    }
}
