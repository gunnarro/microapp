package com.gunnarro.sportsteam.domain.activity;

import java.util.Date;

import com.gunnarro.sportsteam.utility.Utility;

public class Season {

    private Integer id;
    private Long startTime;
    private Long endTime;

    public Season() {
    }

    public Season(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Season(int id, long startTime, long endTime) {
        this(startTime, endTime);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPeriod() {
        if (startTime != null && endTime != null) {
            return Utility.formatTime(startTime, "yyyy") + "-" + Utility.formatTime(endTime, "yyyy");
        }
        return null;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Date getStartTimeDate() {
        return new Date(startTime);
    }

    public Date getEndTimeDate() {
        return new Date(endTime);
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Season [id=" + id + ", period=" + getPeriod() + ", startTime=" + startTime + ", endTime=" + endTime + "]";
    }

}
