package com.gunnarro.dietmanager.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * 
 */
public abstract class BaseJdbcRepository {

    private JdbcTemplate jdbcTemplate;

    public BaseJdbcRepository() {
    }

    /**
     * Creates a new JdbcRepositorySupport for the given JdbcTemplate.
     * 
     * @param jdbcTemplate the JDBC template to create the JDBC Repository
     *            Support for.
     */
    public BaseJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
        return getJdbcTemplate().query("SELECT fk_user_id FROM user_follower_lnk WHERE fk_user_follower_id = ? ORDER BY fk_user_id ASC", new Object[] { userIdFollowerId }, rm);
    }
}