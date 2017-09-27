package com.gunnarro.sportsteam.domain.task;

import java.util.List;

import com.gunnarro.sportsteam.domain.BaseDomain;

public class TaskGroup extends BaseDomain {

    private String description;
    private List<SubTask> subTaskList;

    public TaskGroup() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SubTask> getSubTaskList() {
        return subTaskList;
    }

    public void setSubTaskList(List<SubTask> subTaskList) {
        this.subTaskList = subTaskList;
    }

}
