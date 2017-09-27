package com.gunnarro.sportsteam.repository.table.party;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.util.StringUtils;

import com.gunnarro.sportsteam.domain.party.Contact;
import com.gunnarro.sportsteam.repository.table.TableHelper;

public class ContactsTable {

    public static enum GenderEnum {
        MALE, FEMALE;
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final Contact contact, final Integer addressId) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setObject(2, addressId);
                ps.setObject(3, contact.getFkTeamId());
                ps.setObject(4, contact.getFkStatusId());
                ps.setString(5, contact.getFirstName());
                ps.setString(6, StringUtils.isEmpty(contact.getMiddleName()) ? null : contact.getMiddleName());
                ps.setString(7, contact.getLastName());
                ps.setString(8, contact.getGender());
                ps.setString(9, contact.getMobileNumber());
                ps.setString(10, contact.getEmailAddress());
                return ps;
            }
        };
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }

    public static Object[] createUpdateParam(Contact contact) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), contact.getFkStatusId(), contact.getMobileNumber(), contact.getEmailAddress(),
                contact.getId() };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }

    // Database table
    public static final String TABLE_NAME = "contacts";
    public static final String COLUMN_FK_ADDRESS_ID = "fk_address_id";
    public static final String COLUMN_FK_TEAM_ID = "fk_team_id";
    public static final String COLUMN_FK_STATUS_ID = "fk_status_id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_MIDDLE_NAME = "middle_name";

    public static final String COLUMN_LAST_NAME = "last_name";

    public static final String COLUMN_GENDER = "gender";

    public static final String COLUMN_MOBILE = "mobile";

    public static final String COLUMN_EMAIL = "email";

    private static String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_FK_ADDRESS_ID, COLUMN_FK_TEAM_ID,
            COLUMN_FK_STATUS_ID, COLUMN_FIRST_NAME, COLUMN_MIDDLE_NAME, COLUMN_LAST_NAME, COLUMN_GENDER, COLUMN_MOBILE, COLUMN_EMAIL };

    private static String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_FK_STATUS_ID, COLUMN_MOBILE, COLUMN_EMAIL };

    private ContactsTable() {
    }
}
