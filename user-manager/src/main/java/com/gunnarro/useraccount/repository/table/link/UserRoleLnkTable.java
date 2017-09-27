package com.gunnarro.useraccount.repository.table.link;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.gunnarro.useraccount.repository.table.TableHelper;

public class UserRoleLnkTable {

	// Database table
	public static final String TABLE_NAME = "user_role_lnk";

	private enum ColumnsEnum {
		fk_user_id, fk_role_id;
	}

	/**
	 * In order to hide public constructor
	 */
	private UserRoleLnkTable() {
	}

	public static PreparedStatementCreator createInsertPreparedStatement(
			final Integer userId, final Integer roleId) {
		return new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						createInsertQuery(), new String[] { "id" });
				ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
				ps.setTimestamp(2, new Timestamp(TableHelper.getToDay()));
				ps.setInt(3, userId);
				ps.setInt(4, roleId);
				return ps;
			}
		};
	}

	public static String createInsertQuery() {
		return TableHelper.createInsertQuery(TABLE_NAME,
				TableHelper.getColumnNames(ColumnsEnum.values()));
	}

}
