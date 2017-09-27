package com.gunnarro.sportsteam.repository.table.activity;

import java.sql.Timestamp;

import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.repository.table.TableHelper;

public class SeasonsTable {

    public static Object[] createInsertParam(Season season) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), season.getPeriod(), new Timestamp(season.getStartTime()),
                new Timestamp(season.getEndTime()) };
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, TABLE_COLUMNS);
    }

    public static Object[] createUpdateParam(Season season) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), season.getPeriod(), new Timestamp(season.getStartTime()),
                new Timestamp(season.getEndTime()) };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, TABLE_COLUMNS);
    }

    // Database table
    public static final String TABLE_NAME = "seasons";

    public static final String COLUMN_PERIOD = "period";

    public static final String COLUMN_START_DATE = "start_date";

    public static final String COLUMN_END_DATE = "end_date";

    private static String[] TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_PERIOD, COLUMN_START_DATE, COLUMN_END_DATE };

    private SeasonsTable() {
    }

}
