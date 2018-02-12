package com.gunnarro.dietmanager.repository.table.health;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.dietmanager.domain.health.HealthLogEntry;
import com.gunnarro.dietmanager.repository.table.TableHelper;

/**
 * 
 * @author admin
 * 
 */
public class BodyMeasurementsLogTable {

    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_FK_USER_ID = "fk_user_id";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_HEIGHT_METRIC = "height_metric";
    public static final String COLUMN_LOG_DATE = "log_date";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_WEIGHT_METRIC = "weight_metric";
    // Database table
    public static final String TABLE_NAME = "body_measurements_log";

    private static final String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_LOG_DATE, COLUMN_FK_USER_ID,
            COLUMN_WEIGHT, COLUMN_HEIGHT, COLUMN_COMMENT, COLUMN_WEIGHT_METRIC, COLUMN_HEIGHT_METRIC };

    private static final String[] UPDATE_TABLE_COLUMNS = INSERT_TABLE_COLUMNS;

    /**
     * In order to hide public constructor
     */
    private BodyMeasurementsLogTable() {
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final HealthLogEntry healthLogEntry) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setTimestamp(2, new Timestamp(healthLogEntry.getLogDate().getTime()));
                ps.setInt(3, healthLogEntry.getUserId());
                ps.setDouble(4, healthLogEntry.getWeight());
                ps.setDouble(5, healthLogEntry.getHeight());
                ps.setString(6, healthLogEntry.getComment());
                ps.setString(7, healthLogEntry.getWeightMetric());
                ps.setString(8, healthLogEntry.getHeightMetric());
                return ps;
            }
        };
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }

    public static Object[] createUpdateParam(HealthLogEntry healthLogEntry) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), healthLogEntry.getWeight(), healthLogEntry.getHeight(), healthLogEntry.getComment(),
                healthLogEntry.getId() };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }

}
