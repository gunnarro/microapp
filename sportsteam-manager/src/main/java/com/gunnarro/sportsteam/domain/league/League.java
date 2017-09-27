package com.gunnarro.sportsteam.domain.league;

import java.io.Serializable;

import com.gunnarro.sportsteam.domain.BaseDomain;
import com.gunnarro.sportsteam.domain.activity.Status;

public class League extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 243232L;

    private String name;
    private LeagueCategory leagueCategory;
    private Status status;

    /**
     * Default constructor
     */
    public League() {
    }

    public League(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getShortName() {
        String shortName = getName();
        shortName = shortName.split("\\(")[0];
        shortName = shortName.replace("Jenter", "J");
        shortName = shortName.replace("Gutter", "G");
        return shortName.trim();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public LeagueCategory getLeagueCategory() {
        return leagueCategory;
    }

    public void setLeagueCategory(LeagueCategory leagueCategory) {
        this.leagueCategory = leagueCategory;
    }

}
