package com.gunnarro.sportsteam.domain.task;

public class SubTask extends Task {

    private Integer fkParentTaskId;
    private Task parentTask;
    private Integer assignedTeamId;

    public SubTask() {
    }

    public Integer getFkParentTaskId() {
        return fkParentTaskId;
    }

    public Task getParentTask() {
        return parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    public void setFkParentTaskId(Integer fkParentTaskId) {
        this.fkParentTaskId = fkParentTaskId;
    }

    public boolean hasFkParentId() {
        return hasId(fkParentTaskId);
    }

    public Integer getAssignedTeamId() {
        return assignedTeamId;
    }

    public void setAssignedTeamId(Integer assignedTeamId) {
        this.assignedTeamId = assignedTeamId;
    }

}
