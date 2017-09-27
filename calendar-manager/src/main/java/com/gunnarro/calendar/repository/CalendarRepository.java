package com.gunnarro.calendar.repository;

import java.util.Date;
import java.util.List;

import com.gunnarro.calendar.domain.calendar.CalendarConfig;
import com.gunnarro.calendar.domain.calendar.CalendarEvent;
import com.gunnarro.calendar.domain.party.Person;
import com.gunnarro.calendar.domain.party.User;
import com.gunnarro.calendar.domain.view.list.Item;

public interface CalendarRepository {

	public User getUser(String userName);

	public List<Item> getCalendarUrls();

	public String getCalendarUrl(String name);

	public int createCalendarConfiguration(CalendarConfig config);

	public int updateCalendarConfiguration(CalendarConfig config);

	public CalendarConfig getCalendarConfiguration(Integer id);

	public int deleteCalendarConfig(int configId);

	public List<CalendarConfig> getCalendarConfigurations();

	public int createEvent(CalendarEvent event);

	public int updateEvent(CalendarEvent event);

	public int deleteEvent(Integer id);

	public List<CalendarEvent> getCalendarEvents();

	public List<CalendarEvent> findCalendarEventsForDay(Date date,
			String eventName);

	public List<Item> getCalendarEventStartDates();

	public CalendarEvent getEvent(Integer id);

	public List<String> getFollowersPhoneNumbers(Integer eventId);

	public List<Person> getFollowers(Integer eventId);

}
