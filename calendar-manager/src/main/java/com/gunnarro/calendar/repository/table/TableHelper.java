package com.gunnarro.calendar.repository.table;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.calendar.service.exception.ApplicationException;

public class TableHelper {

    private static final Logger LOG = LoggerFactory.getLogger(TableHelper.class);

    // Common database table columns
    public static final String COLUMN_ID = "id";

    public static final String COLUMN_CREATED_DATETIME = "created_date_time";

    public static final String COLUMN_LAST_MODIFIED_DATETIME = "last_modified_date_time";

    public static enum PlayerLinkTableTypeEnum {
        MATCH, CUP, TRAINING, CONTACT;
    }

    private TableHelper() {
    }

    public static void checkInputs(Object[] colNames, Object[] values) {
        checkArray(colNames);
        checkArray(values);
        if (colNames.length != values.length) {
            throw new ApplicationException("Wrong input! columnNames <> values length!");
        }
    }

    private static void checkArray(Object[] arr) {
        if (arr == null) {
            throw new ApplicationException("Array is null!");
        }
        if (arr.length == 0) {
            throw new ApplicationException("Array is empty!");
        }
        for (Object o : arr) {
            if (o == null) {
                throw new ApplicationException("Wrong input! Array contains null values!");
            }
        }
    }

    public static String[] createColumns(String[] columns) {
        ArrayList<String> columnNames = new ArrayList<String>();
        columnNames.add(COLUMN_ID);
        columnNames.add(COLUMN_CREATED_DATETIME);
        columnNames.add(COLUMN_LAST_MODIFIED_DATETIME);
        for (String name : columns) {
            columnNames.add(name);
        }
        return columnNames.toArray(new String[columnNames.size()]);
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final String query, final Object[] values) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(query, new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                for (int i = 0; i < values.length; i++) {
                    ps.setObject(i + 1, values[i]);
                }
                return ps;
            }
        };
    }

    public static String createInsertQuery(String tableName, String[] columnNames) {
        StringBuilder query = new StringBuilder();
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        query.append("INSERT INTO ").append(tableName);
        int n = 0;
        for (String columnName : columnNames) {
            n++;
            columns.append(columnName);
            values.append("?");
            if (n < columnNames.length) {
                columns.append(",");
                values.append(",");
            }
        }
        query.append("(").append(columns.toString()).append(") VALUES (").append(values.toString()).append(")");
        return query.toString();
    }

    public static String createUpdateQuery(String tableName, String[] columnNames) {
        StringBuilder query = new StringBuilder();
        StringBuilder columnValuePairs = new StringBuilder();
        query.append("UPDATE ").append(tableName);
        int n = 0;
        for (String columnName : columnNames) {
            n++;
            columnValuePairs.append(columnName).append(" = ?");
            if (n < columnNames.length) {
                columnValuePairs.append(",");
            }
        }
        query.append(" SET ").append(columnValuePairs.toString());
        query.append(" WHERE id = ?");
        if (LOG.isDebugEnabled()) {
            LOG.debug(query.toString());
        }
        return query.toString();
    }

    public static String defaultContentValues() {
        StringBuilder query = new StringBuilder();
        query.append(COLUMN_LAST_MODIFIED_DATETIME);
        return query.toString();
    }

    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public static long getToDay() {
        return System.currentTimeMillis();
    }

}
