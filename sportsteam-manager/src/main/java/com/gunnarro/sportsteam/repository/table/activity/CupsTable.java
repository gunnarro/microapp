package com.gunnarro.sportsteam.repository.table.activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.sportsteam.domain.activity.Cup;
import com.gunnarro.sportsteam.repository.table.TableHelper;

public class CupsTable {

    public static Object[] createInsertParam(Cup cup) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), cup.getSeason().getId(), new Timestamp(cup.getStartDate().getTime()), cup.getName(),
                cup.getClubName(), cup.getVenue(), new Timestamp(cup.getDeadlineDate().getTime()) };
    }
    public static PreparedStatementCreator createInsertPreparedStatement(final Cup cup) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setObject(2, cup.getFkSeasonId());
                ps.setTimestamp(3, new Timestamp(cup.getStartDate().getTime()));
                ps.setTimestamp(4, new Timestamp(cup.getEndDate().getTime()));
                ps.setTimestamp(5, new Timestamp(cup.getDeadlineDate().getTime()));
                ps.setString(6, cup.getCupName());
                ps.setString(7, cup.getClubName());
                ps.setString(8, cup.getVenue());
                ps.setString(9, cup.getEmail());
                ps.setString(10, cup.getHomePage());
                return ps;
            }
        };
    }
    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }
    public static Object[] createUpdateParam(Cup cup) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), cup.getFkSeasonId(), new Timestamp(cup.getStartDate().getTime()), cup.getVenue(),
                new Timestamp(cup.getDeadlineDate().getTime()), cup.getId() };
    }
    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }
    // Database table
    public static final String TABLE_NAME = "cups";
    public static final String COLUMN_FK_SEASON_ID = "fk_season_id";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_END_DATE = "end_date";
    public static final String COLUMN_DEADLINE_DATE = "deadline_date";

    public static final String COLUMN_CUP_NAME = "cup_name";
    
    public static final String COLUMN_CLUB_NAME = "club_name";

    public static final String COLUMN_VENUE = "venue";

    public static final String COLUMN_EMAIL = "email";

    public static final String COLUMN_HOMEPAGE = "home_page";

    private static String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_FK_SEASON_ID, COLUMN_START_DATE,
            COLUMN_END_DATE, COLUMN_DEADLINE_DATE, COLUMN_CUP_NAME, COLUMN_CLUB_NAME, COLUMN_VENUE, COLUMN_EMAIL, COLUMN_HOMEPAGE };

    private static String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_FK_SEASON_ID, COLUMN_START_DATE,
            COLUMN_VENUE, COLUMN_DEADLINE_DATE };

    private CupsTable() {
    }
}
