package com.gunnarro.sportsteam.domain.task;

//import javax.validation.constraints.Pattern;

public class ScheduledTask {

    private Integer id;
    private Integer teamId;
    private String name;
    private String type;
    private String description;
    private String cronExpression;
    private boolean isEnabled = false;

    public ScheduledTask() {

    }

    public ScheduledTask(Integer teamId, String name, String type, boolean isEnabled) {
        this.teamId = teamId;
        this.name = name;
        this.type = type;
        this.isEnabled = isEnabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

}
