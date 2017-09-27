package com.gunnarro.sportsteam.domain.activity;

public class MatchEvent {

    // Goal
    // Own Goal
    // Penalty
    // Penalty Missed
    // Assist
    // Penalty Shootout Goal
    // Penalty Shootout Miss
    // Yellow Card
    // Red Card
    // Substitution IN
    // Substitution OUT
    // Injury

    public enum MatchEventTypesEnum {
        GOAL_HOME, GOAL_AWAY;
    }

    private int id;
    private int fkMatchId;
    private long eventTime;
    private Integer playedMinutes;
    private String teamName;
    private String playerName;
    private String matchEventTypeName;
    private String value;

    public MatchEvent() {

    }

    public MatchEvent(int matchId, int playedMinutes, String teamName, String playerName, String eventTypeName, String value) {
        this.fkMatchId = matchId;
        this.playedMinutes = playedMinutes;
        this.teamName = teamName;
        this.playerName = playerName;
        this.matchEventTypeName = eventTypeName;
        this.value = value;
    }

    public MatchEvent(int id, int matchId, int playedMinutes, String teamName, String playerName, String eventTypeName, String value) {
        this(matchId, playedMinutes, teamName, playerName, eventTypeName, value);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFkMatchId() {
        return fkMatchId;
    }

    public void setFkMatchId(int fkMatchId) {
        this.fkMatchId = fkMatchId;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public Integer getPlayedMinutes() {
        return playedMinutes;
    }

    public void setPlayedMinutes(Integer playedMinutes) {
        this.playedMinutes = playedMinutes;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getMatchEventTypeName() {
        return matchEventTypeName;
    }

    public void setMatchEventTypeName(String matchEventTypeName) {
        this.matchEventTypeName = matchEventTypeName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInfo() {
        return playedMinutes + ":" + playerName + " " + value;
    }

    @Override
    public String toString() {
        return "MatchEvent [id=" + id + ", matchId=" + fkMatchId + ", teamName=" + teamName + ", eventTime=" + eventTime + ", playedMinutes=" + playedMinutes
                + ", playerName=" + playerName + ", eventTypeName=" + matchEventTypeName + ", value=" + value + "]";
    }

}
