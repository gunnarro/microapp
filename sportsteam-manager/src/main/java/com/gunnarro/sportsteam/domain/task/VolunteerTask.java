package com.gunnarro.sportsteam.domain.task;

import java.util.List;

import com.gunnarro.sportsteam.domain.Club;

public class VolunteerTask extends Task {

    private Club club;
    private List<SubTask> subTaskList;

    public VolunteerTask() {
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public List<SubTask> getSubTaskList() {
        return subTaskList;
    }

    public void setSubTaskList(List<SubTask> subTaskList) {
        this.subTaskList = subTaskList;
    }

}
