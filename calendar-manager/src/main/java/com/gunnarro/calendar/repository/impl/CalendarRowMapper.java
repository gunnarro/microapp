package com.gunnarro.calendar.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.gunnarro.calendar.domain.activity.Type;
import com.gunnarro.calendar.domain.calendar.CalendarConfig;
import com.gunnarro.calendar.domain.calendar.CalendarEvent;
import com.gunnarro.calendar.domain.party.User;
import com.gunnarro.calendar.domain.statistic.KeyValuePair;
import com.gunnarro.calendar.domain.view.list.Item;
import com.gunnarro.calendar.utility.Utility;

/**
 * This call contain RowMapper which is required for converting ResultSet into
 * java domain class
 * 
 */
public class CalendarRowMapper {

	private CalendarRowMapper() {
	}

	public static RowMapper<Type> mapToTypeRM() {
		return new RowMapper<Type>() {
			@Override
			public Type mapRow(ResultSet resultSet, int rowNum)
					throws SQLException {
				Type type = new Type();
				type.setId(resultSet.getInt("id"));
				type.setName(resultSet.getString("name"));
				try {
					type.setDescription(resultSet.getString("description"));
				} catch (SQLException sqle) {
					// ignore, the column diden't exist
				}
				return type;
			}
		};
	}

	public static RowMapper<KeyValuePair> mapToKeyValueRM() {
		return new RowMapper<KeyValuePair>() {
			@Override
			public KeyValuePair mapRow(ResultSet resultSet, int rowNum)
					throws SQLException {
				return new KeyValuePair(resultSet.getString("key"),
						resultSet.getString("value"));
			}
		};
	}

	public static RowMapper<KeyValuePair> mapToKeyValueRM(
			final String keyColName, final String valueColName) {
		return new RowMapper<KeyValuePair>() {
			@Override
			public KeyValuePair mapRow(ResultSet resultSet, int rowNum)
					throws SQLException {
				return new KeyValuePair(resultSet.getString(keyColName),
						resultSet.getString(valueColName));
			}
		};
	}

	public static RowMapper<String> singelValueRM(final String colomnName) {
		return new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet resultSet, int rowNum)
					throws SQLException {
				return resultSet.getString(colomnName);
			}
		};
	}

	public static RowMapper<User> mapToUserRM() {
		return new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet resultSet, int rowNum)
					throws SQLException {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setUserName(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setEmail(resultSet.getString("email"));
				user.setActivated(resultSet.getInt("enabled") == 1 ? true
						: false);
				return user;
			}
		};
	}

	public static RowMapper<String> mapToRoleRM() {
		return new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet resultSet, int rowNum)
					throws SQLException {
				return resultSet.getString("role");
			}
		};
	}

	 public static RowMapper<Item> mapToItemRM() {
	        return new RowMapper<Item>() {
	            @Override
	            public Item mapRow(ResultSet resultSet, int rowNum) throws SQLException {
	                Item item = new Item();
	                try {
	                    item.setId(resultSet.getInt("id"));
	                } catch (SQLException sqle) {
	                }
	                try {
	                    item.setValue(resultSet.getString("value"));
	                } catch (SQLException sqle) {
	                }
	                try {
	                    item.setType(resultSet.getString("type"));
	                } catch (SQLException sqle) {
	                }
	                try {
	                    item.setName(resultSet.getString("team_name"));
	                } catch (SQLException sqle) {
	                }
	                try {
	                    boolean isEnabled = (resultSet.getInt("enabled") != 0);
	                    item.setEnabled(isEnabled);
	                } catch (SQLException sqle) {
	                }
	                try {
	                    item.setStartDate(new Date(resultSet.getTimestamp("start_date").getTime()));
	                } catch (SQLException sqle) {
	                }

	                try {
	                    if (resultSet.getInt("signed") > 0) {
	                        item.setSelected(true);
	                    } else {
	                        item.setSelected(false);
	                    }
	                } catch (SQLException sqle) {
	                    // Ignore it, the signed did not appear in the result set
	                    // which will happen for some query mappings
	                }
	                return item;
	            }
	        };
	    }
	 
	 public static RowMapper<CalendarConfig> mapToCalendarConfigRM() {
	        return new RowMapper<CalendarConfig>() {
	            @Override
	            public CalendarConfig mapRow(ResultSet resultSet, int rowNum) throws SQLException {
	                CalendarConfig config = new CalendarConfig();
	                config.setId(resultSet.getInt("id"));
	                config.setName(resultSet.getString("name"));
	                config.setTeamName(resultSet.getString("team_name"));
	                config.setUrl(resultSet.getString("url"));
	                config.setFormat(resultSet.getString("format"));
	                config.setDescription(resultSet.getString("description"));
	                config.setEnabled((resultSet.getInt("enabled") != 0));
	                return config;
	            }
	        };
	    }

	 public static RowMapper<CalendarEvent> mapToCalendarEventRM() {
	        return new RowMapper<CalendarEvent>() {
	            @Override
	            public CalendarEvent mapRow(ResultSet resultSet, int rowNum) throws SQLException {
	                CalendarEvent event = new CalendarEvent();
	                event.setId(resultSet.getInt("id"));
	                event.setStartDate(new Date(resultSet.getTimestamp("start_time").getTime()));
	                event.setEndDate(new Date(resultSet.getTimestamp("end_time").getTime()));
	                event.setStartTime(Utility.formatTime(event.getStartDate().getTime(), "HH:mm"));
	                event.setEndTime(Utility.formatTime(event.getEndDate().getTime(), "HH:mm"));
	                event.setName(resultSet.getString("name"));
	                event.setType(resultSet.getString("event_type"));
	                event.setLocation(resultSet.getString("location"));
	                event.setDescription(resultSet.getString("description"));
	                event.setSummary(resultSet.getString("summary"));
	                return event;
	            }
	        };
	    }
}