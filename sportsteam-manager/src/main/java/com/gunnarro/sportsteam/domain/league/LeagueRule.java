package com.gunnarro.sportsteam.domain.league;

import java.awt.Dimension;
import java.io.Serializable;

import com.gunnarro.sportsteam.domain.BaseDomain;

public class LeagueRule extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 34343L;

    private String description;
    private String gender;

    private Integer pointsWon;
    private Integer pointsDraw;
    private Integer pointsLoss;
    private Integer playerAgeMax;
    private Integer playerAgeMin;
    private Integer matchPeriodTimeMinutes;
    private Integer matchExtraPeriodTimeMinutes;
    private Integer numberOfPlayers;
    private Integer numberOfSubstitutes;

    private boolean yellowCard;
    private boolean reedCard;
    private boolean offside;
    private boolean penalty;
    private boolean playerExclusion;
    private boolean extraPlayer;

    private Dimension field;
    private Dimension goal;

    public LeagueRule() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public boolean isMale() {
        return "M".equalsIgnoreCase(gender) || "MALE".equalsIgnoreCase(gender);
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getPlayerAgeMax() {
        return playerAgeMax;
    }

    public void setPlayerAgeMax(Integer playerAgeMax) {
        this.playerAgeMax = playerAgeMax;
    }

    public Integer getPlayerAgeMin() {
        return playerAgeMin;
    }

    public void setPlayerAgeMin(Integer playerAgeMin) {
        this.playerAgeMin = playerAgeMin;
    }

    public Integer getMatchPeriodTimeMinutes() {
        return matchPeriodTimeMinutes;
    }

    public void setMatchPeriodTimeMinutes(Integer matchPeriodTimeMinutes) {
        this.matchPeriodTimeMinutes = matchPeriodTimeMinutes;
    }

    public Integer getMatchExtraPeriodTimeMinutes() {
        return matchExtraPeriodTimeMinutes;
    }

    public void setMatchExtraPeriodTimeMinutes(Integer matchExtraPeriodTimeMinutes) {
        this.matchExtraPeriodTimeMinutes = matchExtraPeriodTimeMinutes;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public boolean isYellowCard() {
        return yellowCard;
    }

    public void setYellowCard(boolean yellowCard) {
        this.yellowCard = yellowCard;
    }

    public boolean isReedCard() {
        return reedCard;
    }

    public void setReedCard(boolean reedCard) {
        this.reedCard = reedCard;
    }

    public boolean isOffside() {
        return offside;
    }

    public void setOffside(boolean offside) {
        this.offside = offside;
    }

    public boolean isPenalty() {
        return penalty;
    }

    public void setPenalty(boolean penalty) {
        this.penalty = penalty;
    }

    public boolean isPlayerExclusion() {
        return playerExclusion;
    }

    public void setPlayerExclusion(boolean playerExclusion) {
        this.playerExclusion = playerExclusion;
    }

    public Dimension getField() {
        return field;
    }

    public void setField(Dimension field) {
        this.field = field;
    }

    public Dimension getGoal() {
        return goal;
    }

    public void setGoal(Dimension goal) {
        this.goal = goal;
    }

    public Integer getNumberOfSubstitutes() {
        return numberOfSubstitutes;
    }

    public void setNumberOfSubstitutes(Integer numberOfSubstitutes) {
        this.numberOfSubstitutes = numberOfSubstitutes;
    }

    public boolean isExtraPlayer() {
        return extraPlayer;
    }

    public void setExtraPlayer(boolean extraPlayer) {
        this.extraPlayer = extraPlayer;
    }

    public Integer getPointsWon() {
        return pointsWon;
    }

    public void setPointsWon(Integer pointsWon) {
        this.pointsWon = pointsWon;
    }

    public Integer getPointsDraw() {
        return pointsDraw;
    }

    public void setPointsDraw(Integer pointsDraw) {
        this.pointsDraw = pointsDraw;
    }

    public Integer getPointsLoss() {
        return pointsLoss;
    }

    public void setPointsLoss(Integer pointsLoss) {
        this.pointsLoss = pointsLoss;
    }

}
