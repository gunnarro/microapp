package com.gunnarro.dietmanager.repository.impl;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.gunnarro.dietmanager.domain.log.LogComment;
import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.dietmanager.repository.LogEventRepository;
import com.gunnarro.dietmanager.repository.table.log.EventLogTable;
import com.gunnarro.dietmanager.repository.table.log.LogCommentTable;
import com.gunnarro.dietmanager.service.exception.ApplicationException;

@Repository
public class LogEventRepositoryImpl extends BaseJdbcRepository implements LogEventRepository {

	private static final Logger LOG = LoggerFactory.getLogger(LogEventRepositoryImpl.class);

	@Autowired
	public LogEventRepositoryImpl(@Qualifier("logEventDataSource") DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * Needed by spring framework
	 */
	public LogEventRepositoryImpl() {
		super(null);
	}

	@Override
	public int createLogEvent(LogEntry logEntry) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(logEntry.toString());
		}
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			getJdbcTemplate().update(EventLogTable.createInsertPreparedStatement(logEntry), keyHolder);
			return keyHolder.getKey().intValue();
		} catch (Exception e) {
			LOG.error(null, e);
			throw new ApplicationException(e.getMessage());
		}
	}

	@Override
	public int createLogComment(LogComment logComment) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(logComment.toString());
		}
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			getJdbcTemplate().update(LogCommentTable.createInsertPreparedStatement(logComment), keyHolder);
			return keyHolder.getKey().intValue();
		} catch (Exception e) {
			LOG.error(null, e);
			throw new ApplicationException(e.getMessage());
		}
	}

	@Override
	public int deleteLogComment(Integer userId, Integer id) {
		int rows = getJdbcTemplate().update("DELETE FROM event_log_comment WHERE id = ? AND fk_user_id = ?",
				new Object[] { id, userId });
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleted log comment with id=" + id + ", deleted rows=" + rows);
		}
		return rows;
	}

	@Override
	public int deleteLogEvent(Integer userId, Integer id) {
		int rows = getJdbcTemplate().update("DELETE FROM event_log WHERE id = ? AND fk_user_id = ?", new Object[] { id, userId });
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleted log event with id=" + id + ", deleted rows=" + rows);
		}
		return rows;
	}

	@Override
	public List<LogEntry> getAllLogEvents(Integer userId) {
		StringBuffer inQuery = new StringBuffer();
		inQuery.append("IN(?");
		List<Integer> grantedUserIdsForFollower = getGrantedUserIdsForFollower(userId);
		for (int i = 0; i < grantedUserIdsForFollower.size(); i++) {
			inQuery.append(",?");
		}
		inQuery.append(")");
		grantedUserIdsForFollower.add(userId);

		StringBuilder query = new StringBuilder();
		query.append("SELECT l.*");
		query.append(", u.username");
		query.append(
				", (SELECT count(c.id) FROM event_log_comment c WHERE c.fk_event_log_id = l.id) AS number_of_comments");
		query.append(" FROM event_log l, users u");
		query.append(" WHERE l.fk_user_id ").append(inQuery.toString());
		query.append(" AND l.fk_user_id = u.id");
		query.append(" ORDER BY l.created_date_time DESC");
		return getJdbcTemplate().query(query.toString(), grantedUserIdsForFollower.toArray(),
				DietManagerRowMapper.mapToLogEntryRM());
	}

	@Override
	public LogEntry getLogEvent(Integer userId, Integer logEntryId) {
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT l.*, u.username");
			query.append(" FROM event_log l, users u");
			query.append(" WHERE l.id = ?");
			// query.append(" AND l.fk_user_id = ?");
			query.append(" AND l.fk_user_id = u.id");
			LogEntry log = getJdbcTemplate().queryForObject(query.toString(), new Object[] { logEntryId },
					DietManagerRowMapper.mapToLogEntryRM());
			List<LogComment> logComments = getLogComments(log.getId());
			log.setLogComments(logComments);
			return log;
		} catch (org.springframework.dao.EmptyResultDataAccessException erae) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Error: " + erae.toString());
			}
			return null;
		}
	}

	@Override
	public List<LogComment> getLogComments(Integer logEntryId) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT l.*, u.username");
		query.append(" FROM event_log_comment l, users u");
		query.append(" WHERE l.fk_event_log_id = ?");
		query.append(" AND l.fk_user_id = u.id");
		query.append(" ORDER BY l.created_date_time ASC");
		return getJdbcTemplate().query(query.toString(), new Object[] { logEntryId },
				DietManagerRowMapper.mapToLogCommentRM());
	}

	@Override
	public List<LogEntry> getLogEventsFilteredByType(Integer userId, String type) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT l.*, u.username");
		query.append(" FROM event_log l, users u");
		query.append(" WHERE l.fk_user_id = ?");
		query.append(" AND l.fk_user_id = u.id");
		query.append(" AND l.level = ?");
		query.append(" ORDER BY l.created_date_time DESC");
		return getJdbcTemplate().query(query.toString(), new Object[] { userId, type.replace("*", "%") },
				DietManagerRowMapper.mapToLogEntryRM());
	}

	@Override
	public List<LogEntry> getLogEventsFilteredByTitle(Integer userId, String title) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT l.*, u.username");
		query.append(" FROM event_log l, users u");
		query.append(" WHERE l.fk_user_id = ?");
		query.append(" AND l.fk_user_id = u.id");
		query.append(" AND l.title = ?");
		query.append(" ORDER BY l.created_date_time DESC");
		return getJdbcTemplate().query(query.toString(), new Object[] { userId, title.replace("*", "%") },
				DietManagerRowMapper.mapToLogEntryRM());
	}

	@Override
	public List<LogEntry> searchLogEventsContent(Integer userId, String text) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT l.*, u.username");
		query.append(" FROM event_log l, users u");
		query.append(" WHERE l.fk_user_id = ?");
		query.append(" AND l.fk_user_id = u.id");
		query.append(" AND l.content = ?");
		query.append(" ORDER BY l.created_date_time DESC");
		return getJdbcTemplate().query(query.toString(), new Object[] { userId, text.replace("*", "%") },
				DietManagerRowMapper.mapToLogEntryRM());
	}

	@Override
	public List<LogEntry> getLogEvents(Integer userId) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT l.*");
		query.append(", u.username");
		query.append(
				", (SELECT count(c.id) FROM event_log_comment c WHERE c.fk_event_log_id = l.id) AS number_of_comments");
		query.append(" FROM event_log l, users u");
		query.append(" WHERE l.fk_user_id = ?");
		query.append(" AND l.fk_user_id = u.id");
		query.append(" ORDER BY l.created_date_time DESC");
		return getJdbcTemplate().query(query.toString(), new Object[] { userId },
				DietManagerRowMapper.mapToLogEntryRM());
	}

	@Override
	public LogEntry getMyLastStatusReport(Integer userId) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT *");
		sqlQuery.append(" FROM event_log");
		sqlQuery.append(" WHERE YEARWEEK(created_date_time) = YEARWEEK(NOW())-1");
		sqlQuery.append(" AND level = ?");
		sqlQuery.append(" ORDER BY created_date_time DESC");
		sqlQuery.append(" LIMIT 1");
		try {
			return getJdbcTemplate().queryForObject(sqlQuery.toString(), new Object[] { "REPORT" },
					DietManagerRowMapper.mapToLogEntryRM());
		} catch (org.springframework.dao.EmptyResultDataAccessException erae) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Error: " + erae.toString());
			}
			// ignore this
			return null;
		}
	}

	@Override
	public LogEntry getRecentLogEvent(Integer userId, String type, Integer forLastDays) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT *");
		sqlQuery.append(" FROM event_log");
		sqlQuery.append(" WHERE created_date_time > CURRENT_DATE - INTERVAL ? DAY");
		sqlQuery.append(" AND level = ?");
		sqlQuery.append(" ORDER BY created_date_time DESC");
		sqlQuery.append(" LIMIT 1");
		try {
			return getJdbcTemplate().queryForObject(sqlQuery.toString(), new Object[] { forLastDays, type },
					DietManagerRowMapper.mapToLogEntryRM());
		} catch (org.springframework.dao.EmptyResultDataAccessException erae) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Error: " + erae.toString());
			}
			// ignore this
			return null;
		}
	}

	@Override
	public int updateLogEvent(LogEntry logEntry) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(logEntry.toString());
		}
		return getJdbcTemplate().update(EventLogTable.createUpdateQuery(), EventLogTable.createUpdateParam(logEntry));

	}

	@Override
	public boolean hasPermission(Integer logEventId, String username) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT u.username");
		sqlQuery.append(" FROM event_log l, users u");
		sqlQuery.append(" WHERE l.id = ?");
		sqlQuery.append(" AND l.fk_user_id = u.id");
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
