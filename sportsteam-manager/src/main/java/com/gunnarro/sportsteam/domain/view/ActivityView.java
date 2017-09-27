package com.gunnarro.sportsteam.domain.view;

import java.util.Date;

import com.gunnarro.sportsteam.domain.activity.Activity.ActivityTypesEnum;
import com.gunnarro.sportsteam.utility.Utility;

public class ActivityView implements Comparable<ActivityView> {

    private Integer id;
    private String type;
    private Date startDate;
    private String place;
    private String description;
    private String status;
    private int numberOfSignedPlayers;

    public ActivityView() {
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStartWeekDayName() {
        return Utility.formatTime(startDate.getTime(), "EEE");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfSignedPlayers() {
        return numberOfSignedPlayers;
    }

    public void setNumberOfSignedPlayers(int numberOfSignedPlayers) {
        this.numberOfSignedPlayers = numberOfSignedPlayers;
    }

    public boolean isMatch() {
        return this.type.equalsIgnoreCase(ActivityTypesEnum.Match.name());
    }

    public boolean isTraining() {
        return this.type.equalsIgnoreCase(ActivityTypesEnum.Training.name());
    }

    public boolean isCup() {
        return this.type.equalsIgnoreCase(ActivityTypesEnum.Cup.name());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(ActivityView a) {
        return Long.valueOf(startDate.getTime()).compareTo(a.startDate.getTime());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(", startDate=").append(startDate.toString());
        sb.append(", type=").append(type);
        sb.append(", place=").append(place);
        sb.append(", description=").append(description);
        return sb.toString();
    }
}
