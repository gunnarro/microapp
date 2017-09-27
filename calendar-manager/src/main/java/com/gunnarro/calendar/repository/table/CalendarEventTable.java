package com.gunnarro.calendar.repository.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.calendar.domain.calendar.CalendarEvent;

public class CalendarEventTable {

    // Database table
    public static final String TABLE_NAME = "calendar_event";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_END_TIME = "end_time";
    public static final String COLUMN_TYPE = "event_type";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_SUMMARY = "summary";

    private static String[] TABLE_COLUMNS = new String[] { TableHelper.COLUMN_CREATED_DATETIME, TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_START_TIME, COLUMN_END_TIME,
            COLUMN_NAME, COLUMN_TYPE, COLUMN_LOCATION, COLUMN_SUMMARY, COLUMN_DESCRIPTION };

    private static String[] TABLE_COLUMNS_UPDATE = TABLE_COLUMNS;

    public static Object[] createInsertParam(CalendarEvent event) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), new Timestamp(TableHelper.getToDay()), new Timestamp(event.getStartDate().getTime()),
                new Timestamp(event.getEndDate().getTime()), event.getName(), event.getType(), event.getLocation(), event.getSummary(), event.getDescription() };
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, TABLE_COLUMNS);
    }

    public static Object[] createUpdateParam(CalendarEvent event) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), new Timestamp(TableHelper.getToDay()), new Timestamp(event.getStartDateTime().getTime()),
                new Timestamp(event.getEndDateTime().getTime()), event.getName(), event.getType(), event.getLocation(), event.getSummary(), event.getDescription(), event.getId() };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, TABLE_COLUMNS_UPDATE);
    }

    private CalendarEventTable() {
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final CalendarEvent event) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setTimestamp(2, new Timestamp(TableHelper.getToDay()));
                ps.setTimestamp(3, new Timestamp(event.getStartDateTime().getTime()));
                ps.setTimestamp(4, new Timestamp(event.getEndDateTime().getTime()));
                ps.setString(5, event.getName());
                ps.setString(6, event.getType());
                ps.setString(7, event.getLocation());
                ps.setString(8, event.getSummary());
                ps.setString(9, event.getDescription());
                return ps;
            }
        };
    }
}
