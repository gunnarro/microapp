package com.gunnarro.dietmanager.repository.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.dietmanager.service.exception.ApplicationException;

public class TableHelper {

    private static final Logger LOG = LoggerFactory.getLogger(TableHelper.class);

    // Common database table columns
    public static enum ColumnsDefaultEnum {
        id, createdDateTime, lastModifiedDateTime;
    }

    public static <T extends Enum<T>> String[] getColumnNames(T[] values) {
        List<String> list = new ArrayList<>();
        list.add(ColumnsDefaultEnum.createdDateTime.name());
        list.add(ColumnsDefaultEnum.lastModifiedDateTime.name());
        for (Enum<T> e : values) {
            list.add(e.name());
        }
        return list.toArray(new String[list.size()]);
    }

    // deprecated
    // Common database table columns
    private static final String COLUMN_ID = "id";
    public static final String COLUMN_CREATED_DATETIME = "created_date_time";
    public static final String COLUMN_LAST_MODIFIED_DATETIME = "last_modified_date_time";

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
        return createUpdateQuery(COLUMN_ID, tableName, columnNames);
    }

    private static String createUpdateQuery(String keyName, String tableName, String[] columnNames) {
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
        query.append(" WHERE ").append(keyName).append(" = ?");
        if (LOG.isDebugEnabled()) {
            LOG.debug(query.toString());
        }
        return query.toString();
    }

    public static long getToDay() {
        return System.currentTimeMillis();
    }

}
