package com.gunnarro.sportsteam.domain.activity;

import java.io.Serializable;
import java.util.List;

public class Group implements Serializable {

    private static final long serialVersionUID = -7884442350550365894L;

    private Integer id;
    private String tournamentId;
    private String name;
    private List<String> teamNames;
    private List<Match> matches;
    private int matchesPerTeam;

    /**
     * default constructor
     */
    public Group() {

    }

    public Group(int id, String name, List<Match> matches) {
        this.id = id;
        this.name = name;
        this.matches = matches;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTeamNames() {
        return teamNames;
    }

    public void setTeamNames(List<String> teamNames) {
        this.teamNames = teamNames;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public int getMatchesPerTeam() {
        return matchesPerTeam;
    }

    public void setMatchesPerTeam(int matchesPerTeam) {
        this.matchesPerTeam = matchesPerTeam;
    }

    /**
     * 
     */
    public String toString() {
        return name + ":" + matches;
    }
}
