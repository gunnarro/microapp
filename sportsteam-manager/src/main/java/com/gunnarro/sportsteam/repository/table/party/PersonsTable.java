package com.gunnarro.sportsteam.repository.table.party;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.sportsteam.domain.party.Person;
import com.gunnarro.sportsteam.repository.table.TableHelper;

public class PersonsTable {

    public static PreparedStatementCreator createInsertPreparedStatement(final Person person, final Integer addressId) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setObject(2, addressId);
                ps.setString(3, person.getFirstName());
                ps.setString(4, person.getMiddleName());
                ps.setString(5, person.getLastName());
                ps.setString(6, person.getGender());
                ps.setString(7, person.getMobileNumber());
                ps.setString(8, person.getEmailAddress());
                ps.setTimestamp(9, person.getDateOfBirth() != null ? new Timestamp(person.getDateOfBirth().getTime()) : null);
                return ps;
            }
        };
    }
    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, INSERT_TABLE_COLUMNS);
    }
    public static Object[] createUpdateParam(Person person) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), person.getMobileNumber(), person.getEmailAddress(), person.getId() };
    }
    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, UPDATE_TABLE_COLUMNS);
    }
    // Database table
    public static final String TABLE_NAME = "persons";
    public static final String COLUMN_FK_ADDRESS_ID = "fk_address_id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_MIDDLE_NAME = "middle_name";
    public static final String COLUMN_LAST_NAME = "last_name";

    public static final String COLUMN_GENDER = "gender";
    
    public static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";

    public static final String COLUMN_MOBILE = "mobile";

    public static final String COLUMN_EMAIL = "email";

    private static String[] INSERT_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_FK_ADDRESS_ID, COLUMN_FIRST_NAME,
            COLUMN_MIDDLE_NAME, COLUMN_LAST_NAME, COLUMN_GENDER, COLUMN_MOBILE, COLUMN_EMAIL, COLUMN_DATE_OF_BIRTH };

    private static String[] UPDATE_TABLE_COLUMNS = new String[] { TableHelper.COLUMN_LAST_MODIFIED_DATETIME, COLUMN_MOBILE, COLUMN_EMAIL };

    private PersonsTable() {
    }
}
