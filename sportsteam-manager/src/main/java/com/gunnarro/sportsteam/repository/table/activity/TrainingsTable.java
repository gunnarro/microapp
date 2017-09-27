package com.gunnarro.sportsteam.repository.table.activity;

import java.sql.Timestamp;

import com.gunnarro.sportsteam.domain.activity.Training;
import com.gunnarro.sportsteam.repository.table.TableHelper;

public class TrainingsTable {

    public static Object[] createInsertParam(Training training) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), training.getSeason().getId(), training.getTeam().getId(),
                new Timestamp(training.getStartDate().getTime()), new Timestamp(training.getEndDate().getTime()), training.getVenue() };
    }
    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }
    public static Object[] createUpdateParam(Training training) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), new Timestamp(training.getStartDate().getTime()),
                new Timestamp(training.getEndDate().getTime()), training.getVenue(), training.getId() };
    }
    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }
    // Database table
    public static final String TABLE_NAME = "trainings";
    public static final String COLUMN_FK_SEASON_ID = "fk_season_id";

    public static final String COLUMN_FK_TEAM_ID = "fk_team_id";

    public static final String COLUMN_START_DATE = "start_time";

    public static final String COLUMN_END_TIME = "end_time";

    public static final String COLUMN_PLACE = "place";

    private static String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_FK_SEASON_ID, COLUMN_FK_TEAM_ID,
            COLUMN_START_DATE, COLUMN_END_TIME, COLUMN_PLACE };

    private static String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_START_DATE, COLUMN_END_TIME, COLUMN_PLACE };

    private TrainingsTable() {
    }

}
