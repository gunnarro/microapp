package com.gunnarro.sportsteam.domain.statistic;

public class ActivityStatistic {

    private int numberOfLeagueMatches;
    private int numberOfCupMatches;
    private int numberOfTrainingMatches;

    public ActivityStatistic(int numberOfLeagueMatches, int numberOfCupMatches, int numberOfTrainingMatches) {
        this.numberOfLeagueMatches = numberOfLeagueMatches;
        this.numberOfCupMatches = numberOfCupMatches;
        this.numberOfTrainingMatches = numberOfTrainingMatches;
    }

    public int getNumberOfLeagueMatches() {
        return numberOfLeagueMatches;
    }

    public int getNumberOfCupMatches() {
        return numberOfCupMatches;
    }

    public int getNumberOfTrainingMatches() {
        return numberOfTrainingMatches;
    }

}
