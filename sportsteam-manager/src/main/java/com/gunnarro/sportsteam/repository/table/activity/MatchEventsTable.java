package com.gunnarro.sportsteam.repository.table.activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.sportsteam.domain.activity.MatchEvent;
import com.gunnarro.sportsteam.repository.table.TableHelper;

public class MatchEventsTable {

    public static PreparedStatementCreator createInsertPreparedStatement(final MatchEvent matchEvent) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setInt(2, matchEvent.getFkMatchId());
                ps.setInt(3, matchEvent.getPlayedMinutes());
                ps.setString(4, matchEvent.getTeamName());
                ps.setString(5, matchEvent.getPlayerName());
                ps.setString(6, matchEvent.getMatchEventTypeName());
                ps.setString(7, matchEvent.getValue());
                return ps;
            }
        };
    }
    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }
    // Database table
    public static final String TABLE_NAME = "match_events";
    public static final String COLUMN_FK_MATCH_ID = "fk_match_id";
    // Use player name, since we don not have all teams registered
    public static final String COLUMN_TEAM_NAME = "team_name";
    // Use player name, since we don not have all player names for foriegn teams
    // registered
    public static final String COLUMN_PLAYER_NAME = "player_name";
    public static final String COLUMN_PLAYED_MINUTES = "played_minutes";
    public static final String COLUMN_MATCH_EVENT_TYPE_NAME = "match_event_type_name";
    public static final String COLUMN_KEY = "key";

    public static final String COLUMN_VALUE = "value";

    public static final String COLUMN_DESCRIPTION = "description";

    private static String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_FK_MATCH_ID, COLUMN_PLAYED_MINUTES,
            COLUMN_TEAM_NAME, COLUMN_PLAYER_NAME, COLUMN_MATCH_EVENT_TYPE_NAME, COLUMN_VALUE };

    private MatchEventsTable() {
    }

}
