package com.gunnarro.followup.repository.table.activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.followup.domain.activity.ActivityLog;
import com.gunnarro.followup.repository.table.TableHelper;

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
    public static final String COLUMN_ACTIVITY_ID = "fk_activity_id";
    public static final String COLUMN_FROM_HOUR = "from_hour";
    public static final String COLUMN_TO_HOUR = "to_hour";
    public static final String COLUMN_INTENSITY = "rating_intensivity";
    public static final String COLUMN_EMOTIONS = "rating_emotions";
    public static final String COLUMN_DESCRIPTION = "description";
    
    private static final String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_CREATED_DATETIME,
            TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_FK_USER_ID, COLUMN_ACTIVITY_ID, COLUMN_FROM_HOUR, COLUMN_TO_HOUR, COLUMN_INTENSITY, COLUMN_EMOTIONS, COLUMN_DESCRIPTION };

    private static final String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_ACTIVITY_ID, COLUMN_FROM_HOUR, COLUMN_TO_HOUR, COLUMN_INTENSITY, COLUMN_EMOTIONS, COLUMN_DESCRIPTION  };

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
                ps.setInt(4, activityLog.getActivity().getId());
                ps.setTime(5, java.sql.Time.valueOf(activityLog.getFromTime()));
                ps.setTime(6, java.sql.Time.valueOf(activityLog.getToTime()));
                ps.setInt(7, activityLog.getIntensitivity());
                ps.setInt(8, activityLog.getEmotions());
                ps.setString(9, activityLog.getDescription());
                return ps;
            }
        };
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }

    public static Object[] createUpdateParam(ActivityLog activityLog) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), activityLog.getDescription(), java.sql.Time.valueOf(activityLog.getFromTime()), java.sql.Time.valueOf(activityLog.getToTime()),
                activityLog.getId() };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }

}
