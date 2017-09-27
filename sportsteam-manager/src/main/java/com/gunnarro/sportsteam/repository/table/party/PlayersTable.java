package com.gunnarro.sportsteam.repository.table.party;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.util.StringUtils;

import com.gunnarro.sportsteam.domain.party.Player;
import com.gunnarro.sportsteam.repository.table.TableHelper;

public class PlayersTable {

    public static PreparedStatementCreator createInsertPreparedStatement(final Player player, final Integer addressId) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setObject(2, player.getFkTeamId());
                ps.setObject(3, addressId);
                ps.setObject(4, player.getFkStatusId());
                ps.setObject(5, player.getFkPlayerPositionId());
                ps.setString(6, player.getFirstName());
                ps.setString(7, StringUtils.isEmpty(player.getMiddleName()) ? null : player.getMiddleName());
                ps.setString(8, player.getLastName());
                ps.setString(9, player.getGender());
                ps.setTimestamp(10, player.getDateOfBirth() != null ? new Timestamp(player.getDateOfBirth().getTime()) : null);
                ps.setString(11, player.getEmailAddress());
                ps.setString(12, player.getMobileNumber());
                ps.setInt(13, player.getJerseyNumber());
                ps.setString(14, player.getSchoolName());
                return ps;
            }
        };
    }
    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }
    public static Object[] createUpdateParam(Player player) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), player.getFkStatusId(), player.getFkPlayerPositionId(), player.getEmailAddress(),
                player.getMobileNumber(), player.getJerseyNumber(), player.getSchoolName(), player.getId() };
    }
    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }
    // Database table
    public static final String TABLE_NAME = "players";
    public static final String COLUMN_FK_TEAM_ID = "fk_team_id";
    public static final String COLUMN_FK_ADDRESS_ID = "fk_address_id";
    public static final String COLUMN_FK_STATUS_ID = "fk_status_id";
    public static final String COLUMN_FK_PLAYER_POSITION_ID = "fk_position_id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_MIDDLE_NAME = "middle_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_MOBILE = "mobile";

    public static final String COLUMN_COUNTRY_OF_BIRTH = "country_of_birth";
    
    public static final String COLUMN_NATIONAL_TEAM = "national_team";

    public static final String COLUMN_SCHOOL_NAME = "school_name";

    public static final String COLUMN_JERSEY_NUMBER = " jersey_number";

    private static String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_FK_TEAM_ID, COLUMN_FK_ADDRESS_ID,
            COLUMN_FK_STATUS_ID, COLUMN_FK_PLAYER_POSITION_ID, COLUMN_FIRST_NAME, COLUMN_MIDDLE_NAME, COLUMN_LAST_NAME, COLUMN_GENDER, COLUMN_DATE_OF_BIRTH,
            COLUMN_EMAIL, COLUMN_MOBILE, COLUMN_JERSEY_NUMBER, COLUMN_SCHOOL_NAME };

    private static String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_FK_STATUS_ID, COLUMN_FK_PLAYER_POSITION_ID,
            COLUMN_EMAIL, COLUMN_MOBILE, COLUMN_JERSEY_NUMBER, COLUMN_SCHOOL_NAME };

    private PlayersTable() {
    }
}
