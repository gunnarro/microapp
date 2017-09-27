package com.gunnarro.sportsteam.domain.statistic;

import com.gunnarro.sportsteam.domain.activity.Type.MatchTypesEnum;

public class MatchStatistic {

    private String teamName = null;
    private int leagueId = 0;
    private int seasonId = 0;
    private MatchTypesEnum type = MatchTypesEnum.LEAGUE;
    private int played;
    private int won;
    private int draw;
    private int loss;
    private int goalsScored;
    private int goalsAgainst;
    private int matchTypeId;
    private int scores;
    private int position;

    public MatchStatistic(MatchTypesEnum type) {
        this.type = type;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public MatchTypesEnum getType() {
        return type;
    }

    public void setType(MatchTypesEnum type) {
        this.type = type;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getMatchTypeId() {
        return matchTypeId;
    }

    public void setMatchTypeId(int matchTypeId) {
        this.matchTypeId = matchTypeId;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public int getNumberOfPlayedMatches() {
        return won + draw + loss;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
