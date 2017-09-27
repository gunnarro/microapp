package com.gunnarro.useraccount.repository.table.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import com.gunnarro.useraccount.domain.user.Role;
import com.gunnarro.useraccount.repository.table.TableHelper;


public class RolesTable {

    // Database table
    public static final String TABLE_NAME = "roles";
    
    private enum ColumnsEnum {
        username, role;
    }

    public static enum RolesEnum {
        ROLE_USER, ROLE_ADMIN, ROLE_GUEST, ROLE_ANONYMOUS;
    }

    public static String createInsertQuery() {
        return TableHelper.createInsertQuery(TABLE_NAME, TableHelper.getColumnNames(ColumnsEnum.values()));
    }

    public static PreparedStatementCreator createInsertPreparedStatement(final String userName, final String role) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setObject(2, userName);
                ps.setString(3, role);
                return ps;
            }
        };
    }
    
    public static RowMapper<Role> mapToRoleRM() {
        return new RowMapper<Role>() {
            @Override
            public Role mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return new Role(resultSet.getInt("id"), resultSet.getString("name"));
            }
        };
    }
    
    public static RowMapper<String> mapToRoleNameRM() {
        return new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return resultSet.getString(ColumnsEnum.role.name());
            }
        };
    }
}
