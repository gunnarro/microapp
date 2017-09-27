package com.gunnarro.sportsteam.repository.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.sportsteam.domain.Club;

/**
 * CREATE TABLE clubs(_id INTEGER PRIMARY KEY AUTOINCREMENT, created_date_time
 * DATETIME DEFAULT 'CURRENT_TIMESTAMP', last_modified_date_time DATETIME
 * DEFAULT 'CURRENT_TIMESTAMP', fk_address_id INTEGER UNIQUE ON CONFLICT FAIL
 * REFERENCES addresses(_id) ON DELETE SET NULL ON UPDATE CASCADE, club_name
 * TEXT NOT NULL, club_department_name TEXT NOT NULL, club_name_abbreviation
 * TEXT, club_stadium_name TEXT, club_url_home_page TEXT,
 * UNIQUE(club_name,club_department_name));
 */
public class ClubsTable {

    public static Object[] createInsertParam(Club club, Integer addressId) {
        return new Object[] { TableHelper.getToDay(), club.getName(), club.getDepartmentName(), addressId, club.getStadiumName(),
                club.getClubNameAbbreviation(), club.getHomePageUrl() };
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final Club club, final Integer addressId) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setString(2, club.getName());
                ps.setString(3, club.getDepartmentName());
                ps.setObject(4, addressId);
                ps.setString(5, club.getStadiumName());
                ps.setString(6, club.getClubNameAbbreviation());
                ps.setString(7, club.getHomePageUrl());
                return ps;
            }
        };
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }

    public static Object[] createUpdateParam(Club club, Integer addressId) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), club.getName(), club.getDepartmentName(), addressId, club.getStadiumName(),
                club.getClubNameAbbreviation(), club.getHomePageUrl(), club.getId() };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }

    // Database table
    public static final String TABLE_NAME = "clubs";
    public static final String COLUMN_FK_ADDRESS_ID = "fk_address_id";

    public static final String COLUMN_CLUB_NAME = "club_name";

    public static final String COLUMN_CLUB_DEPARTMENT_NAME = "club_department_name";

    public static final String COLUMN_CLUB_STADIUM_NAME = "club_stadium_name";

    public static final String COLUMN_CLUB_NAME_ABBREVIATION = "club_name_abbreviation";

    public static final String COLUMN_CLUB_URL_HOME_PAGE = "club_url_home_page";

    public static final String CREATE_INDEX = "CREATE INDEX " + TABLE_NAME + "index ON " + TABLE_NAME + "(" + TableHelper.COLUMN_ID + ");";

    private static final String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_CLUB_NAME,
            COLUMN_CLUB_DEPARTMENT_NAME, COLUMN_FK_ADDRESS_ID, COLUMN_CLUB_STADIUM_NAME, COLUMN_CLUB_NAME_ABBREVIATION, COLUMN_CLUB_URL_HOME_PAGE };

    private static final String[] UPDATE_TABLE_COLUMNS = INSERT_TABLE_COLUMNS;

    private ClubsTable() {
    }

}