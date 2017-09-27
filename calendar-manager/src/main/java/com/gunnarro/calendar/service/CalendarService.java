package com.gunnarro.calendar.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.MultiMap;
import org.springframework.security.access.annotation.Secured;

import com.gunnarro.calendar.domain.calendar.Agenda;
import com.gunnarro.calendar.domain.calendar.CalendarConfig;
import com.gunnarro.calendar.domain.calendar.CalendarEvent;
import com.gunnarro.calendar.domain.party.Person;
import com.gunnarro.calendar.domain.view.list.Item;
import com.gunnarro.calendar.domain.party.User;

/**
 * Method level security should be applied for all save*() and delete*() methods
 * here.
 * 
 * @author admin
 *
 */
public interface CalendarService {

	public User getUser(String username);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int saveCalendarConfiguration(CalendarConfig config);

	public CalendarEvent getCalendarEvent(int eventId);

	public List<String> getCalendarConfigurationTeamNames();

	public MultiMap<Date, List<CalendarEvent>> getCalendarEventFromICalAsMultiMap(String url, String type, boolean reload);

	public String getCalendarUrlByName(String type);

	public int saveCalendarEvent(CalendarEvent event);

	public int deleteCalendarEvent(int eventId);

	public List<Item> getCalendarEventStartDates();

	public int saveAgenda(Agenda agenda);

	public List<CalendarEvent> getCalendarEvents(Date forDate);

	public List<CalendarEvent> findCalendarEvents(Date toDate, String name);

	public MultiMap<Date, List<CalendarEvent>> getCalendarEventMultiMap();

	public CalendarConfig getCalendarConfiguration(Integer id);

	public int deleteCalendarConfig(int configId);

	public List<CalendarConfig> getCalendarConfigurations();

	public List<String> getFollowersPhoneNumbers(Integer eventId);

	public List<Person> getFollowers(Integer eventId);

	public Agenda getAgenda(Integer calendarEventId);

	public List<Item> getCalendarUrlsActive();

}
