package com.gunnarro.sportsteam.repository.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.sportsteam.domain.Team;

/**
 * In the database model are teams deleted if the club is deleted
 * 
 * @author admin
 * 
 */
public class TeamsTable {

    public static PreparedStatementCreator createInsertPreparedStatement(final Team team) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setInt(2, team.getFkClubId());
                ps.setObject(3, team.getFkLeagueId());
                ps.setObject(4, team.getName());
                ps.setObject(5, team.getTeamYearOfBirth());
                ps.setString(6, team.getGender());
                return ps;
            }
        };
    }
    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }
    public static Object[] createUpdateParam(Team team) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), team.getFkLeagueId(), team.getName(), team.getTeamYearOfBirth(), team.getGender(),
                team.getId() };
    }
    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }
    // Database table
    public static final String TABLE_NAME = "teams";
    public static final String COLUMN_FK_CLUB_ID = "fk_club_id";

    public static final String COLUMN_FK_LEAGUE_ID = "fk_league_id";

    public static final String COLUMN_TEAM_NAME = "team_name";

    public static final String COLUMN_TEAM_YEAR_OF_BIRTH = "team_year_of_birth";

    public static final String COLUMN_TEAM_GENDER = "team_gender";

    private static String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_FK_CLUB_ID, COLUMN_FK_LEAGUE_ID,
            COLUMN_TEAM_NAME, COLUMN_TEAM_YEAR_OF_BIRTH, COLUMN_TEAM_GENDER };

    private static String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_FK_LEAGUE_ID, COLUMN_TEAM_NAME,
            COLUMN_TEAM_YEAR_OF_BIRTH, COLUMN_TEAM_GENDER };

    private TeamsTable() {
    }
}
