package com.gunnarro.sportsteam.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.gunnarro.sportsteam.domain.party.Contact;

public class TeamTest {

    @Test
    public void testAllTeamFKKeys() {
        Team team = new Team();
        assertFalse(team.hasFkAddressId());
        assertFalse(team.hasFkClubId());
        assertFalse(team.hasFkCoachId());
        assertFalse(team.hasFkContactId());
        assertFalse(team.hasFkLeagueId());
        assertFalse(team.hasFkRefereeId());
        assertFalse(team.hasFkSeasonId());
        assertFalse(team.hasFkStatusId());
        assertFalse(team.hasFkTaskStatusId());
        assertFalse(team.hasFkTeamId());
        assertFalse(team.hasFkTeamleadId());
        assertFalse(team.hasFkTemaRoleId());
    }

    @Test
    public void splitTeamName() {
        String teamName = "ullevål";
        assertEquals("ullevål", teamName.split("\\s+")[0]);

        teamName = "ullevål 2";
        assertEquals("ullevål", teamName.split("\\s+")[0]);
    }

    @Test
    public void testConstructorNew() {
        Team team = new Team("teamName", new Club("name", "department"), 2004, "Male");
        team.setFkClubId(2);
        assertTrue(team.hasFkClubId());
        assertFalse(team.hasTeamLead());
        assertFalse(team.hasCoach());
        assertTrue(team.isNew());
        assertEquals("teamName", team.getName());
        assertEquals("Male", team.getGender());
        assertEquals(2004, team.getTeamYearOfBirth().intValue());
        assertEquals("name department", team.getClub().getFullName());
    }

    @Test
    public void testConstructorId() {
        Team team = new Team(23, "TeamName");
        assertFalse(team.isNew());
        assertEquals(23, team.getId().intValue());
    }

    @Test
    public void hasCoachChange() {
        // test both null
        Team team = new Team(1, "TeamName");
        assertFalse(team.hasCoachChanged(null));
        // test both equal
        Contact currentCoach = new Contact();
        currentCoach.setId(22);
        team.setFkCoachId(22);
        assertFalse(team.hasCoachChanged(currentCoach));
        // test fk changed
        team.setFkCoachId(23);
        assertTrue(team.hasCoachChanged(currentCoach));
        // test fk null
        team.setFkCoachId(null);
        assertTrue(team.hasCoachChanged(currentCoach));
        team.setFkCoachId(22);
        assertFalse(team.hasCoachChanged(currentCoach));
    }
}
