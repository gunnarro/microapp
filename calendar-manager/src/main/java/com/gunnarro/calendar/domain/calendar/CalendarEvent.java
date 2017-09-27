package com.gunnarro.calendar.domain.calendar;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.gunnarro.calendar.domain.BaseDomain;
import com.gunnarro.calendar.utility.Utility;

public class CalendarEvent extends BaseDomain implements Comparable<CalendarEvent>, Serializable {

    private static final long serialVersionUID = -5501199883864533942L;
    private String type;
    @NotNull
    private Date startDate = new Date();
    @NotNull
    private Date endDate = new Date();
    // helpers for user input to only hold the time part of the start date in
    // HH:mm format
    @DateTimeFormat(pattern = "HH:mm")
    private String startTime;
    // helpers for user input to only hold the time part of the start date in
    // HH:mm format
    @DateTimeFormat(pattern = "HH:mm")
    private String endTime;
    private String location;
    private String summary;
    private String description;
    private String link;
    private Integer reiterations = 1;
    private String status;

    /**
     * Default constructor
     */
    public CalendarEvent() {
    }

    public CalendarEvent(Integer id) {
        super.setId(id);
    }

    public String getShortType() {
        String shortType = type.split("\\(")[0];
        return shortType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getStartDateTime() {
        if (Utility.validateTime24H(startTime)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            String[] tt = startTime.split(":");
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tt[0]));
            cal.set(Calendar.MINUTE, Integer.parseInt(tt[1]));
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }
        return startDate;
    }

    public Date getEndDateTime() {
        if (Utility.validateTime24H(endTime)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            String[] tt = endTime.split(":");
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tt[0]));
            cal.set(Calendar.MINUTE, Integer.parseInt(tt[1]));
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }
        return endDate;
    }

    public Date getStartDateOnly() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        // Reset time part of date
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getReiterations() {
        return reiterations;
    }

    public void setReiterations(Integer reiterations) {
        this.reiterations = reiterations;
    }

    public boolean isTypeEqual(String type) {
    	return this.type.equalsIgnoreCase(type);
    }
    
    public boolean summaryContains(String value) {
    	return this.summary.toLowerCase().contains(value.toLowerCase());
    }
    
    public static CalendarEvent copyEvent(CalendarEvent event, Date date) {
        CalendarEvent newEvent = new CalendarEvent();
        newEvent.setStartDate(date);
        newEvent.setEndDate(date);
        newEvent.setStartTime(event.getStartTime());
        newEvent.setEndTime(event.getEndTime());
        newEvent.setName(event.getName());
        newEvent.setSummary(event.getSummary());
        newEvent.setLocation(event.getLocation());
        newEvent.setType(event.getType());
        newEvent.setDescription(event.getDescription());
        newEvent.setLink(event.getLink());
        return newEvent;
    }

    public static CalendarEvent createFilter(String type, String name) {
        CalendarEvent filter = new CalendarEvent();
        filter.setType(type);
        filter.setName(name);
        return filter;
    }

    @Override
	public String toString() {
		return "CalendarEvent [type=" + type + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", location=" + location
				+ ", summary=" + summary + ", status=" + status
				+ ", link=" + link + "]";
	}

	/**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(CalendarEvent event) {
        return this.getStartDate().compareTo(event.getStartDate());
    }

}
