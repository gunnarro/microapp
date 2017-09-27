package com.gunnarro.sportsteam.domain.task;

import java.util.Calendar;
import java.util.Date;

import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.gunnarro.sportsteam.domain.BaseDomain;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.activity.Status;
import com.gunnarro.sportsteam.domain.party.Contact;
import com.gunnarro.sportsteam.utility.Utility;

public class Task extends BaseDomain {

    public static enum TaskStatusEnum {
        OPEN, IN_PROGRESS, FINISHED, CLOSED, CANCELLED;
    }

    /**
     * This is the id of the contact person who is assigned to the task.
     */
    private Integer fkAssignedToPersonId;

    @Size(min = 2, max = 30)
    @NotNull
    // @Pattern(regexp = Utility.VALIDATOR_PATTERN_STRING)
    private String taskName;

    @Size(min = 2, max = 30)
    @NotNull
    // @Pattern(regexp = Utility.VALIDATOR_PATTERN_STRING)
    private String description;

    private Status status = null;

    private Season season;

    @DateTimeFormat(pattern = "MM.dd.yyyy")
    @NotNull
    private Date startDate = Calendar.getInstance().getTime();

    @DateTimeFormat(pattern = "MM.dd.yyyy")
    @NotNull
    private Date endDate = Calendar.getInstance().getTime();

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @NotNull
    private Date deadlineDate = Calendar.getInstance().getTime();

    private Contact assignee;

    public Task() {
        super(null);
    }

    public Contact getAssignee() {
        return assignee;
    }

    public void setAssignee(Contact assignee) {
        this.assignee = assignee;
    }

    @Override
    public String getName() {
        return taskName;
    }

    public String getType() {
        return "volunteer_task";
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Date getStartDate() {
        return startDate;
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

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public Integer getFkAssignedToPersonId() {
        return fkAssignedToPersonId;
    }

    public void setFkAssignedToPersonId(Integer fkAssignedToPersonId) {
        this.fkAssignedToPersonId = fkAssignedToPersonId;
    }

    public boolean isAssigned() {
        if (fkAssignedToPersonId != null && fkAssignedToPersonId > 0) {
            return true;
        }
        return false;
    }

    public boolean isToBePerformedToday() {
        return Utility.isSameDate(new Date(), startDate);
    }

    public boolean isFinished() {
        if (status != null) {
            return status.getName().equalsIgnoreCase(TaskStatusEnum.FINISHED.name());
        }
        return false;
    }

    public boolean isNotStarted() {
        if (status != null) {
            return status.getName().equalsIgnoreCase(TaskStatusEnum.OPEN.name());
        }
        return false;
    }

}
