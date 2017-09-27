package com.gunnarro.sportsteam.domain.activity;

public class SeasonStatistic {

    private int seasonId;
    private int numberOfMatches;
    private int numberOfCups;
    private int numberOfTournaments;
    private int numberOfClubs;
    private int numberOfTeams;
    private int numberOfPlayers;
    private int numberOfLeagues;

    public SeasonStatistic() {
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public int getNumberOfMatches() {
        return numberOfMatches;
    }

    public void setNumberOfMatches(int numberOfMatches) {
        this.numberOfMatches = numberOfMatches;
    }

    public int getNumberOfCups() {
        return numberOfCups;
    }

    public void setNumberOfCups(int numberOfCups) {
        this.numberOfCups = numberOfCups;
    }

    public int getNumberOfTournaments() {
        return numberOfTournaments;
    }

    public void setNumberOfTournaments(int numberOfTournaments) {
        this.numberOfTournaments = numberOfTournaments;
    }

    public int getNumberOfClubs() {
        return numberOfClubs;
    }

    public void setNumberOfClubs(int numberOfClubs) {
        this.numberOfClubs = numberOfClubs;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfLeagues() {
        return numberOfLeagues;
    }

    public void setNumberOfLeagues(int numberOfLeagues) {
        this.numberOfLeagues = numberOfLeagues;
    }

}