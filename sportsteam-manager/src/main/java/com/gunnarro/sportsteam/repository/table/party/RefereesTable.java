package com.gunnarro.sportsteam.repository.table.party;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.sportsteam.domain.party.Referee;
import com.gunnarro.sportsteam.repository.table.TableHelper;

public class RefereesTable {

    public static PreparedStatementCreator createInsertPreparedStatement(final Referee referee, final Integer addressId) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setObject(2, referee.getFkClubId() != null ? referee.getFkClubId() : null);
                ps.setObject(3, addressId);
                ps.setString(4, referee.getFirstName());
                ps.setString(5, referee.getMiddleName());
                ps.setString(6, referee.getLastName());
                ps.setString(7, referee.getGender());
                ps.setString(8, referee.getMobileNumber());
                ps.setString(9, referee.getEmailAddress());
                return ps;
            }
        };
    }
    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }
    public static Object[] createUpdateParam(Referee referee) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), referee.getMobileNumber(), referee.getEmailAddress(), referee.getId() };
    }
    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }
    // Database table
    public static final String TABLE_NAME = "referees";
    public static final String COLUMN_FK_CLUB_ID = "fk_club_id";
    public static final String COLUMN_FK_ADDRESS_ID = "fk_address_id";
    public static final String COLUMN_FK_SPORT_TYPE_ID = "fk_sport_type_id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_MIDDLE_NAME = "middle_name";

    public static final String COLUMN_LAST_NAME = "last_name";
    
    public static final String COLUMN_GENDER = "gender";

    public static final String COLUMN_MOBILE = "mobile";

    public static final String COLUMN_EMAIL = "email";

    private static String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_FK_CLUB_ID, COLUMN_FK_ADDRESS_ID,
            COLUMN_FIRST_NAME, COLUMN_MIDDLE_NAME, COLUMN_LAST_NAME, COLUMN_GENDER, COLUMN_MOBILE, COLUMN_EMAIL };

    private static String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_MOBILE, COLUMN_EMAIL };

    private RefereesTable() {
    }
}
