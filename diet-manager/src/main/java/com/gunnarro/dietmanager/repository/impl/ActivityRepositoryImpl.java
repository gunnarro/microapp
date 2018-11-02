package com.gunnarro.dietmanager.repository.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.gunnarro.dietmanager.domain.activity.ActivityLog;
import com.gunnarro.dietmanager.repository.ActivityRepository;
import com.gunnarro.dietmanager.repository.table.activity.ActivityLogTable;
import com.gunnarro.dietmanager.service.exception.ApplicationException;

@Repository
public class ActivityRepositoryImpl extends BaseJdbcRepository implements ActivityRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ActivityRepositoryImpl.class);

    
    @Autowired
    public ActivityRepositoryImpl(JdbcTemplate jdbcTemplate) {
    	super(jdbcTemplate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createActivityLog(ActivityLog activityLog) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(activityLog.toString());
        }
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getJdbcTemplate().update(ActivityLogTable.createInsertPreparedStatement(activityLog), keyHolder);
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            LOG.error(null, e);
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteActivityLog(Integer userId, Integer id) {
        int rows = getJdbcTemplate().update("DELETE FROM activity_log WHERE id = ? AND fk_user_id = ?", new Object[] { id, userId });
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted activity log with id=" + id + ", deleted rows = {}", rows);
        }
        return rows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityLog getActivityLog(Integer userId, Integer activityLogId) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT l.*, u.username");
            query.append(" FROM activity_log l, users u");
            query.append(" WHERE l.id = ?");
            // query.append(" AND l.fk_user_id = ?");
            query.append(" AND l.fk_user_id = u.id");
            ActivityLog log = getJdbcTemplate().queryForObject(query.toString(), new Object[] { activityLogId }, DietManagerRowMapper.mapToActivityLogRM());
            return log;
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + erae.toString());
            }
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActivityLog> getActivityLogs(Integer userId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT l.*, u.username");
        query.append(" FROM activity_log l, users u");
        query.append(" WHERE l.fk_user_id = ?");
        query.append(" AND l.fk_user_id = u.id");
        query.append(" ORDER BY l.last_modified_date_time DESC");
        return getJdbcTemplate().query(query.toString(), new Object[] { userId }, DietManagerRowMapper.mapToActivityLogRM());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateActivityLog(ActivityLog activityLog) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(activityLog.toString());
        }
        return getJdbcTemplate().update(ActivityLogTable.createUpdateQuery(), ActivityLogTable.createUpdateParam(activityLog));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(Integer logEventId, String username) {
        StringBuilder sqlQuery = new StringBuilder();
//        sqlQuery.append("SELECT u.username");
//        sqlQuery.append(" FROM activity_log l, users u");
//        sqlQuery.append(" WHERE l.id = ?");
//        sqlQuery.append(" AND l.fk_user_id = u.id");
        sqlQuery.append("SELECT * FROM users WHERE username = ?");
        try {
            String name = getJdbcTemplate().queryForObject(sqlQuery.toString(), new Object[] { logEventId }, String.class);
            return name.equals(username);
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + erae.toString());
            }
            // ignore this
            return false;
        }
    }
}
