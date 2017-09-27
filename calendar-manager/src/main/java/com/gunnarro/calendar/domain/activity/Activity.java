package com.gunnarro.calendar.domain.activity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.gunnarro.calendar.domain.BaseDomain;
import com.gunnarro.calendar.utility.Utility;

public abstract class Activity extends BaseDomain implements Serializable {

    private static final long serialVersionUID = -3968811135575991342L;

    public static enum ActivityStatusEnum {
        COMPLETED, CANCELLED, BEGIN, NOT_STARTED;
    }

    private String information;
    private Integer numberOfRegistreredTeams = 0;
    private Integer numberOfSignedPlayers = 0;
    private Integer reiterations = 1;

    @DateTimeFormat(pattern = "MM.dd.yyyy HH:mm")
    @NotNull
    private Date startDate = Calendar.getInstance().getTime();

    @DateTimeFormat(pattern = "MM.dd.yyyy HH:mm")
    @NotNull
    private Date endDate = Calendar.getInstance().getTime();

    // helpers for user input to only hold the time part of the start date in
    // HH:mm format
    @DateTimeFormat(pattern = "HH:mm")
    private String startTime;

    // helpers for user input to only hold the time part of the start date in
    // HH:mm format
    @DateTimeFormat(pattern = "HH:mm")
    private String endTime;
    
    @Override
    public abstract String getName();

    public abstract String getType();
    
    /**
     * default constructor
     */
    public Activity() {
    }


    public static enum ActivityTypesEnum {
        Training, Match, Cup, Tournament, Event;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }


    public Integer getNumberOfSignedPlayers() {
        return numberOfSignedPlayers;
    }

    public void setNumberOfSignedPlayers(Integer numberOfSignedPlayers) {
        this.numberOfSignedPlayers = numberOfSignedPlayers;
    }

    public Integer getNumberOfRegistreredTeams() {
        return numberOfRegistreredTeams;
    }

    public void setNumberOfRegistreredTeams(Integer numberOfRegistreredTeams) {
        this.numberOfRegistreredTeams = numberOfRegistreredTeams;
    }


    public String getStartWeekDayName() {
        return Utility.formatTime(startDate.getTime(), "EEE");
    }

    public Date getStartDateOnly() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        // Reset time part of date
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public Date getStartDate() {
        appendTimeToStartDate();
        return startDate;
    }

    public String getStartDateEEEEDDMMMYYYY() {
        return Utility.formatTime(getStartDate().getTime(), Utility.DATE_EEE_PATTERN);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        appendTimeToEndDate();
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        // if (StringUtils.isEmpty(startTime)) {
        // return Utility.formatTime(startDate.getTime(), "HH:mm");
        // }
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        if (StringUtils.isEmpty(endTime)) {
            return Utility.formatTime(endDate.getTime(), "HH:mm");
        }
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isMatch() {
        return this.getType().equalsIgnoreCase(ActivityTypesEnum.Match.name());
    }

    public boolean isTraining() {
        return this.getType().equalsIgnoreCase(ActivityTypesEnum.Training.name());
    }

    public boolean isCup() {
        return this.getType().equalsIgnoreCase(ActivityTypesEnum.Cup.name());
    }

    public boolean isEvent() {
        return this.getType().equalsIgnoreCase(ActivityTypesEnum.Event.name());
    }


    public void appendTimeToStartDate() {
        if (!StringUtils.isEmpty(startTime)) {
            String startDateStr = Utility.formatTime(startDate.getTime(), "dd.MM.yyyy");
            startDate = Utility.timeToDate(startDateStr + " " + startTime, "dd.MM.yyyy HH:mm");
        }
    }

    public void appendTimeToEndDate() {
        if (!StringUtils.isEmpty(endTime)) {
            String endDateStr = Utility.formatTime(endDate.getTime(), "dd.MM.yyyy");
            endDate = Utility.timeToDate(endDateStr + " " + endTime, "dd.MM.yyyy HH:mm");
        }
    }

    public Integer getReiterations() {
        return reiterations;
    }

    public void setReiterations(Integer reiterations) {
        this.reiterations = reiterations;
    }

}
