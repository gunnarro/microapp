package com.gunnarro.dietmanager.repository.table.activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.dietmanager.domain.activity.ActivityLog;
import com.gunnarro.dietmanager.repository.table.TableHelper;

/**
 * 
 * @author admin
 * 
 */
public class ActivityLogTable {

    // Database table
    public static final String TABLE_NAME = "activity_log";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_FK_USER_ID = "fk_user_id";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_TITLE = "title";

    private static final String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.ColumnsDefaultEnum.createdDateTime.name(),
            TableHelper.ColumnsDefaultEnum.lastModifiedDateTime.name(), COLUMN_FK_USER_ID, COLUMN_LEVEL, COLUMN_TITLE, COLUMN_CONTENT };

    private static final String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.ColumnsDefaultEnum.lastModifiedDateTime.name(), COLUMN_LEVEL, COLUMN_TITLE,
            COLUMN_CONTENT };

    /**
     * In order to hide public constructor
     */
    private ActivityLogTable() {
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final ActivityLog activityLog) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                long createdTime = TableHelper.getToDay();
                // Check if created time is set, if so use it.
                if (activityLog.getCreatedTime() > 0) {
                    createdTime = activityLog.getCreatedTime();
                }
                ps.setTimestamp(1, new Timestamp(createdTime));
                // last modified is always set equal to current time
                ps.setTimestamp(2, new Timestamp(TableHelper.getToDay()));
                ps.setInt(3, activityLog.getFkUserId());
                // ps.setString(4, activityLog.getLevel());
                // ps.setString(5, activityLog.getTitle());
                // ps.setString(6, activityLog.getContent());
                return ps;
            }
        };
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }

    //FIXME
    public static Object[] createUpdateParam(ActivityLog activityLog) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), activityLog.getName(), activityLog.getDescription(), activityLog.getError(),
                activityLog.getId() };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }

}
