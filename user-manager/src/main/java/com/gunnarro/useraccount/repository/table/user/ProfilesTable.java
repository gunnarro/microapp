package com.gunnarro.useraccount.repository.table.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import com.gunnarro.useraccount.domain.user.LocalUser;
import com.gunnarro.useraccount.domain.user.Profile;
import com.gunnarro.useraccount.repository.table.TableHelper;

public abstract class ProfilesTable {

    // Database table
    public static final String TABLE_NAME = "users";

    private enum ColumnsEnum {
        username, password, email, enabled;

        public static ColumnsEnum[] updateValues() {
            ColumnsEnum[] c = new ColumnsEnum[2];
            c[0] = email;
            c[1] = enabled;
            return c;
        }
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final Profile profile) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setTimestamp(2, new Timestamp(TableHelper.getToDay()));
                // ps.setObject(2, profile.getUserId());
                // ps.setString(3, profile.getFirstName());
                // ps.setString(4, profile.getMiddleName());
                // ps.setString(4, profile.getLastName());
                // ps.setString(4, profile.getEmailAddress());
                // ps.setDate(4, profile.getDateOfBirth());
                // ps.setString(4, profile.getGender());
                // ps.setInt(5, profile.isActivated() ? 1 : 0);
                return ps;
            }
        };
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, TableHelper.getColumnNames(ColumnsEnum.values()));
    }

    public static Object[] createUpdateParam(LocalUser user) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), user.getEmail(), user.isActivated() ? 1 : 0, user.getId() };
    }

    public static String createUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, TableHelper.getColumnNames(ColumnsEnum.updateValues()));
    }

    public static RowMapper<Profile> mapToProfileRM() {
        return new RowMapper<Profile>() {
            @Override
            public Profile mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Profile profile = new Profile();
                // profile.setCreatedDate(resultSet.getTimestamp("created_date_time"));
                // profile.setLastModifiedDate(resultSet.getTimestamp("last_modified_date_time"));
                profile.setId(resultSet.getInt("id"));
                profile.setUserId(resultSet.getInt("fk_user_id"));
                profile.setFirstName(resultSet.getString("firstname"));
                profile.setMiddleName(resultSet.getString("middlename"));
                profile.setLastName(resultSet.getString("lastname"));
                profile.setEmailAddress(resultSet.getString("email"));
                profile.setGender(resultSet.getString("gender").charAt(0));
                profile.setDateOfBirth(new Date(resultSet.getDate("date_of_birth").getTime()));
                // profile.setActivated(resultSet.getInt("enabled") == 1 ? true
                // : false);
                return profile;
            }
        };
    }
}
