package com.gunnarro.followup.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.gunnarro.followup.domain.activity.Activity;
import com.gunnarro.followup.domain.activity.ActivityLog;

/**
 * This call contain RowMapper which is required for converting ResultSet into
 * java domain class
 * 
 */
public class ActivityRowMapper {

	/**
	 * In order to hide public constructor
	 */
	private ActivityRowMapper() {
	}

	public static RowMapper<Integer> mapCountRM() {
		return new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				return resultSet.getInt("count");
			}
		};
	}

	public static RowMapper<ActivityLog> mapToActivityLogRM() {
		return new RowMapper<ActivityLog>() {
			@Override
			public ActivityLog mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				ActivityLog log = new ActivityLog();
				Activity activity = new Activity();
				activity.setId(resultSet.getInt("fk_activity_id"));
				activity.setName(resultSet.getString("activity_name"));
				log.setActivity(activity);
				log.setId(resultSet.getInt("id"));
				log.setFkUserId(resultSet.getInt("fk_user_id"));
				log.setCreatedDate(new Date(resultSet.getTimestamp("created_date_time").getTime()));
				log.setLastModifiedTime(resultSet.getTimestamp("last_modified_date_time").getTime());
				log.setIntensitivity(resultSet.getInt("rating_intensivity"));
				log.setEmotions(resultSet.getInt("rating_emotions"));
				log.setFromTime(resultSet.getTime("from_hour").toLocalTime());
				log.setToTime(resultSet.getTime("to_hour").toLocalTime());
				return log;
			}
		};
	}

	public static RowMapper<Activity> mapToActivityRM() {
		return new RowMapper<Activity>() {
			@Override
			public Activity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				Activity activity = new Activity();
				activity.setId(resultSet.getInt("id"));
				activity.setId(resultSet.getInt("name"));
				activity.setId(resultSet.getInt("category"));
				activity.setId(resultSet.getInt("description"));
				return activity;
			}
		};
	}

}