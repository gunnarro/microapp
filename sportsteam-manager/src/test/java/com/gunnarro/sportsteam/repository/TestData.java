package com.gunnarro.sportsteam.repository;

import com.gunnarro.sportsteam.domain.Team;


public class TestData {

    public static Team createTeam(int clubId) {
        Team team = new Team("team name", null, 2004, "Male");
        team.setFkClubId(clubId);
        return team;
    }

    public static Team createTeam(String teamName, int clubId) {
        Team team = new Team(teamName, null, 2004, "Male");
        team.setFkClubId(clubId);
        return team;
    }
}
