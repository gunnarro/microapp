package com.gunnarro.sportsteam.domain.league;

import java.io.Serializable;
import java.util.List;

import com.gunnarro.sportsteam.domain.BaseDomain;
import com.gunnarro.sportsteam.domain.activity.Status;

public class LeagueCategory extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 3456L;

    private String name;
    private String sportType;
    private Status status;
    private LeagueRule leagueRule;
    private List<League> leagues;

    public LeagueCategory() {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public LeagueRule getLeagueRule() {
        return leagueRule;
    }

    public void setLeagueRule(LeagueRule leagueRule) {
        this.leagueRule = leagueRule;
    }

    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }

}
