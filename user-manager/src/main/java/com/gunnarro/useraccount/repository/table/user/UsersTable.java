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
import com.gunnarro.useraccount.repository.table.TableHelper;

public abstract class UsersTable {

    // Database table
    public static final String TABLE_NAME = "users";

    private enum ColumnsEnum {
        username, password, email, enabled;

        public static String[] updateValues() {
            String[] s = new String[3];
            s[0] = TableHelper.ColumnsDefaultEnum.last_modified_date_time.name();
            s[1] = email.name();
            s[2] = enabled.name();
            return s;
        }
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final LocalUser user) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setTimestamp(2, new Timestamp(TableHelper.getToDay()));
                ps.setObject(3, user.getUsername());
                ps.setString(4, user.getPassword());
                ps.setString(5, user.getEmail());
                ps.setInt(6, user.isActivated() ? 1 : 0);
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
        return TableHelper.createUpdateQuery(TABLE_NAME, ColumnsEnum.updateValues());
    }

    public static Object[] createPasswordUpdateParam(Integer userId, String password) {
        return new Object[] { new Timestamp(TableHelper.getToDay()), password, userId };
    }

    public static String createPasswordUpdateQuery() {
        return TableHelper.createUpdateQuery(TABLE_NAME, new String[] { TableHelper.ColumnsDefaultEnum.last_modified_date_time.name(), ColumnsEnum.password.name() });
    }

    public static RowMapper<LocalUser> mapToUserRM() {
        return new RowMapper<LocalUser>() {
            @Override
            public LocalUser mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                LocalUser user = new LocalUser();
                user.setId(resultSet.getInt(TableHelper.ColumnsDefaultEnum.id.name()));
                user.setCreatedDate(new Date(resultSet.getTimestamp(TableHelper.ColumnsDefaultEnum.created_date_time.name()).getTime()));
                user.setLastModifiedDate(new Date(resultSet.getTimestamp(TableHelper.ColumnsDefaultEnum.last_modified_date_time.name()).getTime()));
                user.setUsername(resultSet.getString(ColumnsEnum.username.name()));
                user.setPassword(resultSet.getString(ColumnsEnum.password.name()));
                user.setEmail(resultSet.getString(ColumnsEnum.email.name()));
                user.setActivated(resultSet.getInt(ColumnsEnum.enabled.name()) == 1 ? true : false);
                return user;
            }
        };
    }
}
