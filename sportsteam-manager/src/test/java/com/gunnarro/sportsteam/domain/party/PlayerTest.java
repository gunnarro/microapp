package com.gunnarro.sportsteam.domain.party;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Status;
import com.gunnarro.sportsteam.domain.activity.Type;
import com.gunnarro.sportsteam.domain.party.Contact.StatusEnum;
import com.gunnarro.sportsteam.domain.party.Role.RoleTypesEnum;

public class PlayerTest {

    @Test
    public void testConstructorNew() {
        Address address = new Address("streetname", "25", "c", "1111", "city", "country");
        List<Contact> parents = new ArrayList<Contact>();

        List<Type> p1roles = new ArrayList<Type>();
        Type coachType = new Type();
        coachType.setName(RoleTypesEnum.COACH.name());
        p1roles.add(coachType);

        List<Type> p2roles = new ArrayList<Type>();
        Type teamleadType = new Type();
        teamleadType.setName(RoleTypesEnum.TEAMLEAD.name());
        p2roles.add(teamleadType);

        parents.add(new Contact(new Team("team name"), p1roles, "p1firstname", "p1middleName", "p1lastName", "M", "11111111", "p1@email.no", address));
        parents.add(new Contact(new Team("team name"), p2roles, "p2firstname", "p2middleName", "p2lastName", "M", "22222222", "p2@email.no", address));
        Date dateOfBirth = new Date();
        Player newPlayer = new Player(new Team("team name"), "firstname", null, "lastname", "M", address);
        newPlayer.setDateOfBirth(dateOfBirth);
        newPlayer.setParents(parents);
        assertTrue(newPlayer.isNew());

        assertEquals("Firstname Lastname".toUpperCase(), newPlayer.getFullName());
        assertEquals("M", newPlayer.getGender());
        assertEquals(StatusEnum.ACTIVE.name(), newPlayer.getStatus().getName());
        assertFalse(newPlayer.hasFkStatusId());
        assertTrue(newPlayer.isActive());
        assertTrue(newPlayer.hasParents());
        assertEquals("COACH", newPlayer.getParents().get(0).getTeamRoleList().get(0).getName());
        assertEquals("P1firstname P1middlename P1lastname".toUpperCase(), newPlayer.getParents().get(0).getFullName());
        assertEquals("p1@email.no", newPlayer.getParents().get(0).getEmailAddress());
        assertEquals("11111111", newPlayer.getParents().get(0).getMobileNumber());
        assertEquals("City".toUpperCase(), newPlayer.getAddress().getCity());
        assertEquals("Country".toUpperCase(), newPlayer.getAddress().getCountry());
        assertEquals("Streetname 25C".toUpperCase(), newPlayer.getAddress().getFullStreetName());
        assertEquals("1111", newPlayer.getAddress().getPostCode());
        assertTrue(newPlayer.getAddress().isAddressValid());
    }

    @Test
    public void notActive() {
        Player player = new Player();
        player.setStatus(new Status(3, "QUIT"));
        assertFalse(player.isActive());
    }

    @Test
    public void mapPlayerStatus() {
        assertEquals(1, StatusEnum.valueOf("active".toUpperCase()).getId());
        assertEquals(2, StatusEnum.valueOf("passive".toUpperCase()).getId());
        assertEquals(3, StatusEnum.valueOf("injured".toUpperCase()).getId());
        assertEquals(4, StatusEnum.valueOf("quit".toUpperCase()).getId());
    }
}
