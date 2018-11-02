package com.gunnarro.dietmanager.repository.table.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.dietmanager.repository.table.TableHelper;

/**
 * 
 * @author admin
 * 
 */
public class EventLogTable {

    // Database table
    public static final String TABLE_NAME = "event_log";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_FK_USER_ID = "fk_user_id";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_TITLE = "title";

    private static final String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_CREATED_DATETIME, TableHelper.COLUMN_LAST_MODIFIED_DATETIME,
            COLUMN_FK_USER_ID, COLUMN_LEVEL, COLUMN_TITLE, COLUMN_CONTENT };

    private static final String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_LEVEL, COLUMN_TITLE, COLUMN_CONTENT };

    /**
     * In order to hide public constructor
     */
    private EventLogTable() {
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final LogEntry logEntry) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                long createdTime = TableHelper.getToDay();
                // Check if created time is set, if so use it.
                if (logEntry.getCreatedTime() > 0) {
                    createdTime = logEntry.getCreatedTime();
                }
                ps.setTimestamp(1, new Timestamp(createdTime));
                // last modified is always set equal to current time
                ps.setTimestamp(2, new Timestamp(TableHelper.getToDay()));
                ps.setInt(3, logEntry.getFkUserId());
                ps.setString(4, logEntry.getLevel());
                ps.setString(5, logEntry.getTitle());
                ps.setString(6, logEntry.getContent());
                return ps;
            }
        };
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }

    public static Object[] createUpdateParam(LogEntry logEntry) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), logEntry.getLevel(), logEntry.getTitle(), logEntry.getContent(), logEntry.getId() };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }

    public static String createUpdateLastModifiedQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME });
    }

    public static Object[] createUpdateLastModifieParam(Integer logEntryId) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), logEntryId };
    }
}
