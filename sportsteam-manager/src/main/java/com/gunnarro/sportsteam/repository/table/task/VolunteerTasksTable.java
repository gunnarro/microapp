package com.gunnarro.sportsteam.repository.table.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.sportsteam.domain.task.VolunteerTask;
import com.gunnarro.sportsteam.repository.table.TableHelper;

public class VolunteerTasksTable {

    public static PreparedStatementCreator createInsertPreparedStatement(final VolunteerTask task) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setTimestamp(2, new Timestamp(task.getStartDate().getTime()));
                ps.setTimestamp(3, task.getEndDate() != null ? new Timestamp(task.getEndDate().getTime()) : null);
                ps.setTimestamp(4, task.getDeadlineDate() != null ? new Timestamp(task.getDeadlineDate().getTime()) : null);
                ps.setInt(5, task.getFkTaskStatusId());
                ps.setInt(6, task.getFkClubId() != null ? task.getFkClubId() : null);
                ps.setInt(7, task.getFkSeasonId() != null ? task.getFkSeasonId() : null);
                ps.setObject(8, task.getFkAssignedToPersonId() != null ? task.getFkAssignedToPersonId() : null);
                ps.setString(9, task.getTaskName());
                ps.setString(10, task.getDescription() != null ? task.getDescription() : null);
                ps.setString(11, task.getType() != null ? task.getType() : null);
                return ps;
            }
        };
    }
    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }
    public static Object[] createUpdateParam(VolunteerTask task) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), new Timestamp(task.getStartDate().getTime()), new Timestamp(task.getEndDate().getTime()),
                new Timestamp(task.getDeadlineDate().getTime()), task.getFkTaskStatusId(),
                task.getFkAssignedToPersonId() != null ? task.getFkAssignedToPersonId() : null, task.getTaskName(), task.getDescription(), task.getId() };
    }
    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }
    // Database table
    public static final String TABLE_NAME = "volunteer_tasks";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_END_DATE = "end_date";
    public static final String COLUMN_DEADLINE_DATE = "deadline_date";
    public static final String COLUMN_FK_TASK_STATUS_ID = "fk_task_status_id";
    public static final String COLUMN_FK_CLUB_ID = "fk_club_id";
    public static final String COLUMN_FK_SEASON_ID = "fk_season_id";
    public static final String COLUMN_FK_ASSIGNED_TO_PERSON_ID = "fk_assigned_to_person_id";

    public static final String COLUMN_NAME = "name";

    public static final String COLUMN_STATUS = "status";

    public static final String COLUMN_DESCRIPTION = "description";

    public static final String COLUMN_TYPE = "type";

    private static String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_START_DATE, COLUMN_END_DATE,
            COLUMN_DEADLINE_DATE, COLUMN_FK_TASK_STATUS_ID, COLUMN_FK_CLUB_ID, COLUMN_FK_SEASON_ID, COLUMN_FK_ASSIGNED_TO_PERSON_ID, COLUMN_NAME,
            COLUMN_DESCRIPTION, COLUMN_TYPE };

    private static String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_START_DATE, COLUMN_END_DATE,
            COLUMN_DEADLINE_DATE, COLUMN_FK_TASK_STATUS_ID, COLUMN_FK_ASSIGNED_TO_PERSON_ID, COLUMN_NAME, COLUMN_DESCRIPTION };

    private VolunteerTasksTable() {
    }

}
