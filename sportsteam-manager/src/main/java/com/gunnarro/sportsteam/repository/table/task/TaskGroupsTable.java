package com.gunnarro.sportsteam.repository.table.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.sportsteam.domain.task.TaskGroup;
import com.gunnarro.sportsteam.repository.table.TableHelper;

public class TaskGroupsTable {

    public static PreparedStatementCreator createInsertPreparedStatement(final TaskGroup taskGroup) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setString(2, taskGroup.getName());
                ps.setString(3, taskGroup.getDescription());
                return ps;
            }
        };
    }
    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }
    public static Object[] createUpdateParam(final TaskGroup taskGroup) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), taskGroup.getName(), taskGroup.getDescription(), taskGroup.getId() };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }

    // Database table
    public static final String TABLE_NAME = "task_groups";

    public static final String COLUMN_NAME = "name";

    public static final String COLUMN_DESCRIPTION = "description";

    private static String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_NAME, COLUMN_DESCRIPTION };

    private static String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_NAME, COLUMN_DESCRIPTION };

    private TaskGroupsTable() {
    }
}
