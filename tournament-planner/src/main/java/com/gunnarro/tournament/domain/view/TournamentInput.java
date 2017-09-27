package com.gunnarro.tournament.domain.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gunnarro.tournament.domain.view.list.Item;

public class TournamentInput {

    private enum TournamentTypesEnum {
        SINGLE, SINGLE_AND_GROUP_PLAY;
    }

    private String name;
    private String type;
    private String teams;
    private List<String> teamNames;
    private Integer numberOfGroups = 2;
    /**
     * The playing surface for the game
     */
    private Integer numberOfFields = 2;
    /**
     * The length of a match in minutes.
     */
    private Integer playTime = 20;
    private Integer pauseTimeBetweenMatches = 5;
    private Integer bracketSize;
    private boolean generateTeamNamesForGroup = false;

    public TournamentInput() {
    }

    public TournamentInput(String name, String type, List<String> teamNames) {
        this.name = name;
        this.type = type;
        this.teamNames = teamNames;
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

    public String getTeams() {
        return teams;
    }

    public List<String> getTeamsAsList() {
        if (teams != null) {
            return Arrays.asList(teams.split("\n"));
        }
        return null;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }

    public List<String> getTeamNames() {
        return teamNames;
    }

    public void setTeamNames(List<String> teamNames) {
        this.teamNames = teamNames;
    }

    public Integer getBracketSize() {
        return bracketSize;
    }

    public void setBracketSize(Integer bracketSize) {
        this.bracketSize = bracketSize;
    }

    public Integer getNumberOfGroups() {
        return numberOfGroups;
    }

    public void setNumberOfGroups(Integer numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }

    public Integer getNumberOfFields() {
        return numberOfFields;
    }

    public void setNumberOfFields(Integer numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    public Integer getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Integer playTime) {
        this.playTime = playTime;
    }

    public Integer getPauseTimeBetweenMatches() {
        return pauseTimeBetweenMatches;
    }

    public void setPauseTimeBetweenMatches(Integer pauseTimeBetweenMatches) {
        this.pauseTimeBetweenMatches = pauseTimeBetweenMatches;
    }

    public static List<Item> getTournamentTypeOptions() {
        List<Item> typeItems = new ArrayList<Item>();
        typeItems.add(new Item("Single", "Single"));
        typeItems.add(new Item("Double", "Double"));
        return typeItems;
    }

    @Override
    public String toString() {
        return "TournamentInput [name=" + name + ", type=" + type + ", teamNames=" + teamNames + ", numberOfGroups=" + numberOfGroups + "]";
    }

}
