package com.gunnarro.calendar.repository.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.gunnarro.calendar.domain.calendar.CalendarConfig;
import com.gunnarro.calendar.domain.calendar.CalendarEvent;
import com.gunnarro.calendar.domain.party.Person;
import com.gunnarro.calendar.domain.party.User;
import com.gunnarro.calendar.domain.view.list.Item;
import com.gunnarro.calendar.repository.CalendarRepository;
import com.gunnarro.calendar.repository.table.CalendarConfigTable;
import com.gunnarro.calendar.repository.table.CalendarEventTable;
import com.gunnarro.calendar.service.exception.ApplicationException;
import com.gunnarro.calendar.utility.Utility;

/**
 * Database: jbossews User: admincnVhNH8 Password: suSNhqkXILV-
 * 
 * @author admin
 * 
 */
@Repository
// @Transactional
public class CalendarRepositoryImpl extends BaseJdbcRepository implements
		CalendarRepository {

	private static final Logger LOG = LoggerFactory
			.getLogger(CalendarRepositoryImpl.class);

	public CalendarRepositoryImpl() {
		super(null);
	}

	public CalendarRepositoryImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getUser(String userName) {
		try {
			User user = getJdbcTemplate().queryForObject(
					"SELECT * FROM users WHERE username = ?",
					new Object[] { userName },
					CalendarRowMapper.mapToUserRM());
			user.setRoles(getUserRoles(user.getUserName()));
			return user;
		} catch (org.springframework.dao.EmptyResultDataAccessException erae) {
			if (LOG.isWarnEnabled()) {
				LOG.warn(null, erae);
			}
			return null;
		}
	}

	private List<String> getUserRoles(String userName) {
		try {
			return getJdbcTemplate().query(
					"SELECT * FROM roles WHERE username = ?",
					new Object[] { userName },
					CalendarRowMapper.mapToRoleRM());
		} catch (org.springframework.dao.EmptyResultDataAccessException erae) {
			if (LOG.isWarnEnabled()) {
				LOG.warn(null, erae);
			}
			return new ArrayList<String>();
		}
	}

	 /**
     * {@inheritDoc}
     */
    @Override
    public List<Item> getCalendarUrls() {
        return getJdbcTemplate().query("SELECT c.id, c.team_name, c.url AS value, c.name AS type, c.enabled FROM calendar_urls c WHERE c.enabled = 1 ORDER BY c.name ASC",
                new Object[] {}, CalendarRowMapper.mapToItemRM());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCalendarUrl(String name) {
        try {
            return getJdbcTemplate().queryForObject("SELECT c.url FROM calendar_urls c WHERE c.name = ?", new Object[] { name }, String.class);
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            LOG.error(null, erae);
            throw new ApplicationException("No hit for name=" + name);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createCalendarConfiguration(CalendarConfig config) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(CalendarConfigTable.createInsertPreparedStatement(config), keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateCalendarConfiguration(CalendarConfig config) {
        return getJdbcTemplate().update(CalendarConfigTable.createUpdateQuery(), CalendarConfigTable.createUpdateParam(config));
    }

    @Override
    public CalendarConfig getCalendarConfiguration(Integer id) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * from calendar_urls WHERE id = ?", new Object[] { id }, CalendarRowMapper.mapToCalendarConfigRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            LOG.error(null, erae);
            throw new ApplicationException("No config for id=" + id);
        }
    }

    @Override
    public int deleteCalendarConfig(int id) {
        int rows = getJdbcTemplate().update("DELETE FROM calendar_urls WHERE id = ?", new Object[] { id });
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted config id=" + id + ", deleted rows=" + rows);
        }
        return rows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CalendarConfig> getCalendarConfigurations() {
        return getJdbcTemplate().query("SELECT * FROM calendar_urls ORDER BY name", new Object[] {}, CalendarRowMapper.mapToCalendarConfigRM());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createEvent(CalendarEvent event) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getJdbcTemplate().update(CalendarEventTable.createInsertPreparedStatement(event), keyHolder);
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
    public int updateEvent(CalendarEvent event) {
        return getJdbcTemplate().update(CalendarEventTable.createUpdateQuery(), CalendarEventTable.createUpdateParam(event));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteEvent(Integer id) {
        return getJdbcTemplate().update("DELETE FROM calendar_event WHERE id = ?", new Object[] { id });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CalendarEvent> getCalendarEvents() {
        return getJdbcTemplate().query("SELECT * FROM calendar_event ORDER BY start_time ASC", new Object[] {}, CalendarRowMapper.mapToCalendarEventRM());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Item> getCalendarEventStartDates() {
        // return
        // getJdbcTemplate().query("SELECT DISTINCT null AS id, DATE_FORMAT(start_time, '%D %W %M %Y') AS type, null AS team_name, DATE_FORMAT(start_time, '%D %W %M %Y') AS value FROM calendar_event ORDER BY start_time ASC",
        // new Object[] {}, CalendarRowMapper.mapToItemRM());
        return getJdbcTemplate().query("SELECT DISTINCT date(start_time) AS start_date FROM calendar_event ORDER BY start_time DESC", new Object[] {},
                CalendarRowMapper.mapToItemRM());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CalendarEvent> findCalendarEventsForDay(Date forDate, String name) {
        String eventName = "%";
        if (name != null) {
            eventName = name;
        }
        StringBuilder query = new StringBuilder();
        query.append("SELECT *");
        query.append(" FROM calendar_event");
        query.append(" WHERE date(start_time) = ?");
        query.append(" AND name LIKE ?");
        query.append(" ORDER BY start_time ASC");
        return getJdbcTemplate().query(query.toString(), new Object[] { Utility.formatTime(forDate.getTime(), "yyyy-MM-dd"), eventName },
                CalendarRowMapper.mapToCalendarEventRM());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalendarEvent getEvent(Integer id) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM calendar_event WHERE id = ?", new Object[] { id }, CalendarRowMapper.mapToCalendarEventRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return null;
        }
    }

 // FIXME hard coded
    @Override
    public List<String> getFollowersPhoneNumbers(Integer eventId) {
        return Arrays.asList("45465500", "45465502", "45465502");
    }

    // FIXME hard coded
    @Override
    public List<Person> getFollowers(Integer eventId) {
        return Arrays.asList(new Person(1, "gunnar", "ronn", "45465500", "gunnar_ronneberg@yahoo.no"), new Person(1, "alte", "hansen", "45465500", "gunnar_ronneberg@yahoo.no"),
                new Person(1, "per", "nilsen", "45465500", "gunnar_ronneberg@yahoo.no"));
    }


}
