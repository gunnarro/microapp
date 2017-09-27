package com.gunnarro.sportsteam.domain.party;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Status;
import com.gunnarro.sportsteam.domain.activity.Type;
import com.gunnarro.sportsteam.domain.party.Contact.GenderEnum;
import com.gunnarro.sportsteam.domain.party.Contact.StatusEnum;

public class ContactTest {

    @Test
    public void testConstructorNew() {
        Address address = new Address("streetname", "25", "c", "1212", "city", "country");
        Contact newContact = new Contact(new Team("team name"), null, "firstName", "middleName", "lastName", GenderEnum.MALE.name(), "11111111", "p1@email.no",
                address);
        assertTrue(newContact.isActive());
        assertTrue(newContact.isNew());
        assertTrue(newContact.isMale());
        assertFalse(newContact.hasTeamRoles());
        assertEquals(StatusEnum.ACTIVE.name(), newContact.getStatus().getName());
        assertFalse(newContact.hasFkStatusId());
        assertEquals("Firstname Middlename Lastname".toUpperCase(), newContact.getFullName());
        assertEquals("FML".toUpperCase(), newContact.getNameAbbreviation());
        assertEquals("Firstname M L".toUpperCase(), newContact.getShortName());
        assertEquals("MALE", newContact.getGender());
        assertEquals("Country".toUpperCase(), newContact.getAddress().getCountry());
        assertEquals("Streetname 25C".toUpperCase(), newContact.getAddress().getFullStreetName());
        assertEquals("1212", newContact.getAddress().getPostCode());
        // assertEquals("Streetname 25C\npostalcode City\nCountry".toUpperCase(),
        // newContact.getAddress().getFullAddress());
        assertTrue(newContact.getAddress().isAddressValid());
        assertFalse(newContact.hasEmailAddress());
        assertFalse(newContact.hasMobileNumber());

        Contact c = new Contact(new Team("teamName"), "firstName", "middleName", "lastName", "F", null);
        assertTrue(c.isNew());
        assertNull(c.getId());
    }

    @Test
    public void testConstructorId() {
        Contact contact = new Contact(new Integer(12), new Team("team name"), "firstName", "middleName", "lastName", "M");
        assertFalse(contact.isNew());
        assertEquals(12, contact.getId().intValue());
    }

    @Test
    public void hasTeamRoles() {
        Contact contact = new Contact();
        List<Type> teamRules = new ArrayList<Type>();
        teamRules.add(new Type("teamlead"));
        contact.setTeamRoleList(teamRules);
        assertTrue(contact.hasTeamRoles());
        assertTrue(contact.getTeamRoleList().size() == 1);
    }

    @Test
    public void hasNoneTeamRoles() {
        Contact contact = new Contact();
        assertFalse(contact.hasTeamRoles());
        assertTrue(contact.getTeamRoleList().size() == 0);
    }

    @Test
    public void isTeamLead() {
        Contact contact = new Contact();
        List<Type> roles = new ArrayList<Type>();
        roles.add(new Type("TEAMLEAD"));
        contact.setTeamRoleList(roles);
        assertTrue(contact.hasTeamRoles());
        assertTrue(contact.getTeamRoleList().size() == 1);
        assertEquals("TEAMLEAD", contact.getTeamRoleList().get(0).getName());
        assertTrue(contact.isTeamLead());
        assertFalse(contact.isCoach());
    }

    @Test
    public void isCoach() {
        Contact contact = new Contact();
        List<Type> roles = new ArrayList<Type>();
        roles.add(new Type("COACH"));
        contact.setTeamRoleList(roles);
        assertTrue(contact.hasTeamRoles());
        assertTrue(contact.getTeamRoleList().size() == 1);
        assertEquals("COACH", contact.getTeamRoleList().get(0).getName());
        assertTrue(contact.isCoach());
        assertFalse(contact.isTeamLead());
    }

    @Test
    public void notActive() {
        Contact contact = new Contact();
        contact.setStatus(new Status(3, "QUIT"));
        assertFalse(contact.isActive());
    }

    @Test
    public void mapTeamRoleNames() {
        assertTrue(Contact.mapNewTeamRolesNameToTypeList(null).size() == 0);
        Contact contact = new Contact();
        String[] teamRoleNames = new String[] { "PARENT", "COACH", "TEAMLEAD" };
        contact.setTeamRoleList(Contact.mapNewTeamRolesNameToTypeList(teamRoleNames));
        assertTrue(contact.hasTeamRoles());
        assertEquals("[PARENT, COACH, TEAMLEAD]", contact.getTeamRoleList().toString());
    }
}
