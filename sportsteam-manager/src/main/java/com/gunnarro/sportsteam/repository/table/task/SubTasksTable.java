package com.gunnarro.sportsteam.repository.table.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.sportsteam.domain.task.SubTask;
import com.gunnarro.sportsteam.repository.table.TableHelper;

public class SubTasksTable {

    public static PreparedStatementCreator createInsertPreparedStatement(final SubTask task) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setTimestamp(2, new Timestamp(task.getStartDate().getTime()));
                ps.setTimestamp(3, new Timestamp(task.getEndDate().getTime()));
                ps.setTimestamp(4, new Timestamp(task.getDeadlineDate().getTime()));
                ps.setInt(5, task.getFkTaskStatusId());
                ps.setInt(6, task.getFkParentTaskId());
                ps.setObject(7, task.getFkAssignedToPersonId() != null ? task.getFkAssignedToPersonId() : null);
                ps.setString(8, task.getTaskName());
                ps.setString(9, task.getDescription());
                ps.setString(10, task.getType());
                return ps;
            }
        };
    }
    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }
    public static Object[] createUpdateParam(SubTask subTask) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), 
                new Timestamp(subTask.getStartDate().getTime()),
                new Timestamp(subTask.getEndDate().getTime()), 
                new Timestamp(subTask.getDeadlineDate().getTime()), 
                subTask.getFkTaskStatusId(),
                subTask.getFkAssignedToPersonId(),
                subTask.getTaskName(),
                subTask.getDescription(), 
                subTask.getId() };
    }
    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }
    // Database table
    public static final String TABLE_NAME = "sub_tasks";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_END_DATE = "end_date";
    public static final String COLUMN_DEADLINE_DATE = "deadline_date";
    public static final String COLUMN_FK_TASK_STATUS_ID = "fk_task_status_id";
    public static final String COLUMN_FK_PARENT_TASK_ID = "fk_parent_task_id";
    public static final String COLUMN_FK_ASSIGNED_TO_PERSON_ID = "fk_assigned_to_person_id";

    public static final String COLUMN_NAME = "name";

    public static final String COLUMN_STATUS = "status";

    public static final String COLUMN_DESCRIPTION = "description";

    public static final String COLUMN_TYPE = "type";

    private static String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_START_DATE, COLUMN_END_DATE,
            COLUMN_DEADLINE_DATE, COLUMN_FK_TASK_STATUS_ID, COLUMN_FK_PARENT_TASK_ID, COLUMN_FK_ASSIGNED_TO_PERSON_ID, COLUMN_NAME, COLUMN_DESCRIPTION,
            COLUMN_TYPE };

    private static String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_START_DATE, COLUMN_END_DATE,
            COLUMN_DEADLINE_DATE, COLUMN_FK_TASK_STATUS_ID, COLUMN_FK_ASSIGNED_TO_PERSON_ID, COLUMN_NAME, COLUMN_DESCRIPTION };

    private SubTasksTable() {
    }
}
