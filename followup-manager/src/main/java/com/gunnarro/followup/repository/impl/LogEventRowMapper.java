package com.gunnarro.followup.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.gunnarro.followup.domain.log.LogComment;
import com.gunnarro.followup.domain.log.LogEntry;
import com.gunnarro.followup.utility.Utility;

/**
 * This call contain RowMapper which is required for converting ResultSet into
 * java domain class
 * 
 */
public class LogEventRowMapper {

	private static final Logger LOG = LoggerFactory.getLogger(LogEventRowMapper.class);

	/**
	 * In order to hide public constructor
	 */
	private LogEventRowMapper() {
	}

	public static RowMapper<Integer> mapCountRM() {
		return new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				return resultSet.getInt("count");
			}
		};
	}

	public static RowMapper<LogEntry> mapToLogEntryRM() {
		return new RowMapper<LogEntry>() {
			@Override
			public LogEntry mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				LogEntry log = new LogEntry();
				log.setId(resultSet.getInt("id"));
				log.setFkUserId(resultSet.getInt("fk_user_id"));
				log.setCreatedDate(new Date(resultSet.getTimestamp("created_date_time").getTime()));
				log.setLastModifiedTime(resultSet.getTimestamp("last_modified_date_time").getTime());
				log.setContent(resultSet.getString("content"));
				log.setContentHtml(Utility.convertMarkdownToHtml(resultSet.getString("content")));
				log.setLevel(resultSet.getString("level"));
				log.setTitle(resultSet.getString("title"));
				try {
					log.setNumberOfComments(resultSet.getInt("number_of_comments"));
				} catch (SQLException sqle) {
					// ignore, the column diden't exist
					if (LOG.isDebugEnabled()) {
						LOG.debug("Error: " + sqle.getMessage());
					}
				}
				try {
					log.setCreatedByUser(resultSet.getString("username"));
				} catch (SQLException sqle) {
					// ignore, the column diden't exist
					if (LOG.isDebugEnabled()) {
						LOG.debug("Error: " + sqle.getMessage());
					}
				}
				return log;
			}
		};
	}

	public static RowMapper<LogComment> mapToLogCommentRM() {
		return new RowMapper<LogComment>() {
			@Override
			public LogComment mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				LogComment comment = new LogComment();
				comment.setId(resultSet.getInt("id"));
				comment.setFkUserId(resultSet.getInt("fk_user_id"));
				comment.setFkLogId(resultSet.getInt("fk_event_log_id"));
				comment.setCreatedDate(new Date(resultSet.getTimestamp("created_date_time").getTime()));
				comment.setLastModifiedTime(resultSet.getTimestamp("last_modified_date_time").getTime());
				comment.setContent(resultSet.getString("content"));
				comment.setContentHtml(Utility.convertMarkdownToHtml(resultSet.getString("content")));
				try {
					comment.setCreatedByUser(resultSet.getString("username"));
				} catch (SQLException sqle) {
					// ignore, the column diden't exist
					if (LOG.isDebugEnabled()) {
						LOG.debug("Error: " + sqle.getMessage());
					}
				}
				return comment;
			}
		};
	}

}