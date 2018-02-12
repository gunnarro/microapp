package com.gunnarro.dietmanager.repository.table.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.dietmanager.domain.log.LogComment;
import com.gunnarro.dietmanager.repository.table.TableHelper;

/**
 * 
 * @author admin
 * 
 */
public class LogCommentTable {

    // Database table
    public static final String TABLE_NAME = "event_log_comment";
    public static final String COLUMN_FK_USER_ID = "fk_user_id";
    public static final String COLUMN_FK_EVENT_LOG_ID = "fk_event_log_id";
    public static final String COLUMN_CONTENT = "content";

    private static final String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_CREATED_DATETIME, TableHelper.COLUMN_LAST_MODIFIED_DATETIME,
            COLUMN_FK_USER_ID, COLUMN_FK_EVENT_LOG_ID, COLUMN_CONTENT };

    private static final String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_CONTENT };

    /**
     * In order to hide public constructor
     */
    private LogCommentTable() {
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final LogComment logComment) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                long createdTime = TableHelper.getToDay();
                // Check if created time is set, if so use it.
                if (logComment.getCreatedTime() > 0) {
                    createdTime = logComment.getCreatedTime();
                }
                ps.setTimestamp(1, new Timestamp(createdTime));
                ps.setTimestamp(2, new Timestamp(TableHelper.getToDay()));
                ps.setInt(3, logComment.getFkUserId());
                ps.setInt(4, logComment.getFkLogId());
                ps.setString(5, logComment.getContent());
                return ps;
            }
        };
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }

    public static Object[] createUpdateParam(LogComment logComment) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), logComment.getContent(), logComment.getId() };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }

}
