package com.gunnarro.sportsteam.repository.table.activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.sportsteam.domain.activity.Match;
import com.gunnarro.sportsteam.repository.table.TableHelper;

public class MatchesTable {

    public static PreparedStatementCreator createInsertPreparedStatement(final Match match) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setObject(2, match.hasFkLeagueId() ? match.getFkLeagueId() : null);
                ps.setObject(3, match.hasFkTeamId() ? match.getFkTeamId() : null);
                ps.setInt(4, match.getFkSeasonId());
                ps.setInt(5, match.getMatchType().getId());
                ps.setInt(6, match.getMatchStatus().getId());
                ps.setObject(7, match.getFkRefereeId());
                ps.setTimestamp(8, new Timestamp(match.getStartDate().getTime()));
                ps.setString(9, match.getHomeTeam().getName());
                ps.setString(10, match.getAwayTeam().getName());
                ps.setObject(11, match.getNumberOfGoalsHome());
                ps.setObject(12, match.getNumberOfGoalsAway());
                ps.setString(13, match.getVenue());
                return ps;
            }
        };
    }
    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }
    public static Object[] createUpdateParam(Match match) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), new Timestamp(match.getStartDate().getTime()), match.getMatchStatus().getId(),
                match.getNumberOfGoalsHome(), match.getNumberOfGoalsAway(), match.getVenue(), match.getFkRefereeId(), match.getId() };
    }
    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }
    public static String updateGoalsQuery() {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE matches");
        query.append(" SET");
        query.append(" goals_home_team = ?,");
        query.append(" goals_away_team = ?");
        query.append(" WHERE id = ?");
        return query.toString();
    }
    public static String updateMatchStatusQuery() {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE matches");
        query.append(" SET");
        query.append(" fk_match_status = ?");
        query.append(" WHERE id = ?");
        return query.toString();
    }
    // Database table
    public static final String TABLE_NAME = "matches";
    public static final String COLUMN_FK_LEAGUE_ID = "fk_league_id";
    public static final String COLUMN_FK_TEAM_ID = "fk_team_id";
    public static final String COLUMN_FK_SEASON_ID = "fk_season_id";
    public static final String COLUMN_FK_MATCH_TYPE_ID = "fk_match_type_id";
    public static final String COLUMN_FK_MATCH_STATUS_ID = "fk_match_status_id";
    public static final String COLUMN_FK_REFEREE_ID = "fk_referee_id";

    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_HOME_TEAM_NAME = "home_team_name";

    public static final String COLUMN_AWAY_TEAM_NAME = "away_team_name";

    public static final String COLUMN_NUMBER_OF_GOALS_HOME_TEAM = "goals_home_team";

    public static final String COLUMN_NUMBER_OF_GOALS_AWAY_TEAM = "goals_away_team";

    public static final String COLUMN_VENUE = "venue";

    public static final String CREATE_INDEX_QUERY = "CREATE INDEX team_index ON teams(id);";
    
    public static final String DROP_INDEX_QUERY = "DROP INDEX team_index;";

    private static String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_FK_LEAGUE_ID, COLUMN_FK_TEAM_ID,
            COLUMN_FK_SEASON_ID, COLUMN_FK_MATCH_TYPE_ID, COLUMN_FK_MATCH_STATUS_ID, COLUMN_FK_REFEREE_ID, COLUMN_START_DATE, COLUMN_HOME_TEAM_NAME,
            COLUMN_AWAY_TEAM_NAME, COLUMN_NUMBER_OF_GOALS_HOME_TEAM, COLUMN_NUMBER_OF_GOALS_AWAY_TEAM, COLUMN_VENUE };

    private static String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_START_DATE, COLUMN_FK_MATCH_STATUS_ID,
            COLUMN_NUMBER_OF_GOALS_HOME_TEAM, COLUMN_NUMBER_OF_GOALS_AWAY_TEAM, COLUMN_VENUE, COLUMN_FK_REFEREE_ID };

    private MatchesTable() {
    }

}
