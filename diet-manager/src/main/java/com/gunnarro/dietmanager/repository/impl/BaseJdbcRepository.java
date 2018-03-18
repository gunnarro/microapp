package com.gunnarro.dietmanager.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * 
 */
public abstract class BaseJdbcRepository {

	public static final int PAGE_SIZE = 25;

	private JdbcTemplate jdbcTemplate;

	public BaseJdbcRepository() {
	}

	protected int getPageIndex(int pageNumber) {
		// check if fist page, always start at 0
		if (pageNumber <= 1) {
			return 0;
		} else {
			return pageNumber * PAGE_SIZE;
		}
	}

	/**
	 * Creates a new JdbcRepositorySupport for the given JdbcTemplate.
	 * 
	 * @param jdbcTemplate
	 *            the JDBC template to create the JDBC Repository Support for.
	 */
	public BaseJdbcRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Returns the JdbcTemplate injected into the class.
	 * 
	 * @return
	 */
	public final JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	/**
	 * return all user id's this followers are allowed to read
	 * 
	 * @param userIdFollower
	 * @return
	 */
	public List<Integer> getGrantedUserIdsForFollower(Integer userIdFollowerId) {
		RowMapper<Integer> rm = new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				return resultSet.getInt("fk_user_id");
			}
		};
		return getJdbcTemplate().query(
				"SELECT fk_user_id FROM user_follower_lnk WHERE fk_user_follower_id = ? ORDER BY fk_user_id ASC",
				new Object[] { userIdFollowerId }, rm);
	}
}