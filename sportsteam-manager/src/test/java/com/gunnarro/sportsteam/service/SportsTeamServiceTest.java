package com.gunnarro.sportsteam.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Type;
import com.gunnarro.sportsteam.domain.party.Address;
import com.gunnarro.sportsteam.domain.party.Contact;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
@Transactional
// @TransactionConfiguration(defaultRollback = true)
// @Ignore
public class SportsTeamServiceTest {

    @Autowired
    @Qualifier("sportsTeamService")
    protected SportsTeamService sportsTeamService;

    @Before
    public void setUp() throws Exception {
        // Because of security we have to set user and pwd before every unit
        // test
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("admin", "uiL2oo3");
        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(ctx);
        ctx.setAuthentication(authRequest);
    }

    @After
    public void terminate() {
        SecurityContextHolder.clearContext();
    }

    @Test(expected = ApplicationException.class)
    public void getTeam() {
        sportsTeamService.getTeam(23);
    }

    @Test
    @Rollback(value = true)
    public void saveClub() {
        // New club
        Club newClub = new Club("UIL", "Bandy");
        Address address = new Address();
        address.setStreetName("persveien");
        address.setStreetNumber("24");
        address.setCity("oslo");
        address.setPostCode("1234");
        address.setCountry("Norway");
        newClub.setAddress(address);
        int clubId = sportsTeamService.saveClub(newClub);
        assertTrue(clubId > 0);
        Club club = sportsTeamService.getClub(clubId);
        assertEquals("UIL Bandy", club.getFullName());
        assertTrue(club.getAddress() != null);
        assertEquals("persveien".toUpperCase(), club.getAddress().getStreetName());

        // Update club
        club.setDepartmentName("Fotball");
        club.getAddress().setStreetName("irisveien");
        clubId = sportsTeamService.saveClub(club);
        assertTrue(clubId > 0);
        assertEquals("UIL Fotball", club.getFullName());
        assertTrue(club.getAddress() != null);
        assertEquals("IRISVEIEN 24, 1234 OSLO NORWAY", club.getAddress().getFullAddress());
    }

    @Test
    public void saveClubAddAddress() {
        // New club
        Club newClub = new Club("SIF", "Bandy");
        int clubId = sportsTeamService.saveClub(newClub);
        assertTrue(clubId > 0);
        Club club = sportsTeamService.getClub(clubId);
        assertEquals("SIF Bandy", club.getFullName());
        assertFalse(club.hasFkAddressId());
        assertFalse(club.hasAddress());

        // Update club with address
        Address address = new Address();
        address.setStreetName("irisveien");
        address.setStreetNumber("24");
        address.setCity("oslo");
        address.setPostCode("1234");
        address.setCountry("Norway");
        club.setAddress(address);
        clubId = sportsTeamService.saveClub(club);
        assertTrue(clubId > 0);
        Club updatedClub = sportsTeamService.getClub(clubId);
        assertEquals("SIF Bandy", updatedClub.getFullName());
        assertTrue(updatedClub.hasAddress());
        assertEquals("IRISVEIEN 24, 1234 OSLO NORWAY", updatedClub.getAddress().getFullAddress());

        // Update address only
        updatedClub.getAddress().setStreetName("kurveien");
        updatedClub.getAddress().setStreetNumber("11");
        updatedClub.getAddress().setStreetNumberPostfix(null);
        clubId = sportsTeamService.saveClub(updatedClub);

        Club updatedClubAddr = sportsTeamService.getClub(clubId);
        assertEquals("SIF Bandy", updatedClubAddr.getFullName());
        assertTrue(updatedClubAddr.hasAddress());
        assertEquals("KURVEIEN 11, 1234 OSLO NORWAY", updatedClubAddr.getAddress().getFullAddress());
    }

    @Test
    public void saveClubDeleteAddress() {
        // New club
        Club newClub = new Club("GIF", "Bandy");
        Address address = new Address();
        address.setStreetName("irisveien");
        address.setStreetNumber("24");
        address.setCity("oslo");
        address.setPostCode("1234");
        address.setCountry("Norway");
        newClub.setAddress(address);
        int clubId = sportsTeamService.saveClub(newClub);
        assertTrue(clubId > 0);
        Club club = sportsTeamService.getClub(clubId);
        assertEquals("GIF Bandy", club.getFullName());
        assertTrue(club.hasFkAddressId());
        assertTrue(club.hasAddress());
        assertTrue(club.hasAddress());
        assertEquals("IRISVEIEN 24, 1234 OSLO NORWAY", club.getAddress().getFullAddress());

        // Delete address
        club.setAddress(null);
        clubId = sportsTeamService.saveClub(club);
        assertTrue(clubId > 0);
        Club clubdeteledAddr = sportsTeamService.getClub(clubId);
        assertEquals("GIF Bandy", clubdeteledAddr.getFullName());
        assertFalse(clubdeteledAddr.hasFkAddressId());
        assertFalse(clubdeteledAddr.hasAddress());
    }

    @Test
    public void saveContact() {
        // New contact
        List<Type> coachRoleList = new ArrayList<Type>();
        coachRoleList.add(new Type("coach"));
        coachRoleList.add(new Type("teamlead"));
        Contact newContact = new Contact("peder", null, "jensen");
        newContact.setMobile("12345678");
        newContact.setTeamRoleList(coachRoleList);
        newContact.setFkStatusId(2);
        int contactId = sportsTeamService.saveContact(newContact);
        assertTrue(contactId > 0);

        Contact contact = sportsTeamService.getContact(contactId);
        assertEquals("peder jensen".toUpperCase(), contact.getFullName());
        assertEquals("PASSIVE", contact.getStatus().getName());
        assertEquals("12345678", contact.getMobile());
        assertTrue(contact.hasTeamRoles());
        assertTrue(contact.getTeamRoleList().size() == 2);
        assertEquals("COACH", contact.getTeamRoleList().get(0).getName());
        assertEquals("TEAMLEAD", contact.getTeamRoleList().get(1).getName());
        assertNull(contact.getTeamRolesStr());

        // Update contact
        Contact updateContact = sportsTeamService.getContact(contactId);
        updateContact.setFkStatusId(1);
        updateContact.setMobile("87654321");
        updateContact.setTeamRoleList(null);
        contactId = sportsTeamService.saveContact(updateContact);
        assertTrue(contactId > 0);
        contact = sportsTeamService.getContact(contactId);
        assertEquals("ACTIVE", contact.getStatus().getName());
        assertEquals("87654321", contact.getMobile());
        assertFalse(contact.hasTeamRoles());
    }

    @Test
    public void saveTeam() {
        Club club = new Club("UIL", "Bandy");
        int clubId = sportsTeamService.saveClub(club);
        Team newTeam = new Team("MyTeamName");
        newTeam.setFkClubId(clubId);
        newTeam.setFkLeagueId(15);
        // New team
        int teamId = sportsTeamService.saveTeam(newTeam);
        Team team = sportsTeamService.getTeam(teamId);
        assertTrue(team.getId() == teamId);
        assertEquals("MyTeamName", team.getName());
        assertEquals("UIL", team.getClub().getName());
        assertNull(team.getTeamLead());
        assertNull(team.getCoach());
    }

    @Test
    public void updateTeam() {
        Club club = new Club("UIL", "Bandy");
        int clubId = sportsTeamService.saveClub(club);
        Team newTeam = new Team("MyTeamName");
        newTeam.setFkClubId(clubId);
        newTeam.setFkLeagueId(15);
        // New team
        int teamId = sportsTeamService.saveTeam(newTeam);
        // Update team
        Team updateTeam = sportsTeamService.getTeam(teamId);
        updateTeam.setName("ChangedMyTeamName");
        updateTeam.setFkLeagueId(14);
        updateTeam.setFkTeamleadId(null);
        updateTeam.setFkCoachId(null);
        int id = sportsTeamService.saveTeam(updateTeam);
        Team updatedTeam = sportsTeamService.getTeam(id);
        assertTrue(updatedTeam.getId() == id);
        assertEquals("ChangedMyTeamName", updatedTeam.getName());
        assertTrue(updatedTeam.getFkLeagueId() == 14);
        assertNull(updatedTeam.getTeamLead());
        assertNull(updatedTeam.getCoach());
        assertEquals("0", Integer.toString(updatedTeam.getNumberOfContacts()));
        assertEquals("0", Integer.toString(updatedTeam.getNumberOfPlayers()));
    }

    @Test
    public void updateTeamCoachAndTeamLead() {
        Club club = new Club("UIL", "Bandy");
        int clubId = sportsTeamService.saveClub(club);
        Team newTeam = new Team("MyTeamName");
        newTeam.setFkClubId(clubId);
        newTeam.setFkLeagueId(15);
        int teamId = sportsTeamService.saveTeam(newTeam);
        List<Type> coachRoleList = new ArrayList<Type>();
        coachRoleList.add(new Type("coach"));
        Contact coach = new Contact("pål", null, "jensen");
        coach.setFkTeamId(teamId);
        coach.setTeamRoleList(coachRoleList);
        int coachId = sportsTeamService.saveContact(coach);

        List<Type> teamLeadRoleList = new ArrayList<Type>();
        teamLeadRoleList.add(new Type("teamlead"));
        Contact teamlead = new Contact("per", null, "hansen");
        teamlead.setFkTeamId(teamId);
        teamlead.setTeamRoleList(teamLeadRoleList);
        int teamleadId = sportsTeamService.saveContact(teamlead);

        Team team = sportsTeamService.getTeam(teamId);
        assertNotNull(team);
        assertEquals("MyTeamName", team.getName());
        assertTrue(team.getFkLeagueId() == 15);
        assertTrue(team.getClub().getId() == clubId);
        assertNotNull(team.getTeamLead().getId() == teamleadId);
        assertNotNull(team.getCoach().getId() == coachId);
        assertEquals("2", Integer.toString(team.getNumberOfContacts()));
        assertEquals("0", Integer.toString(team.getNumberOfPlayers()));
    }

    @Test
    public void deleteCoachAndTeamLeadForTeam() {
        Club club = new Club("UIL", "Bandy");
        int clubId = sportsTeamService.saveClub(club);
        Team newTeam = new Team("MyTeamName");
        newTeam.setFkClubId(clubId);
        newTeam.setFkLeagueId(15);
        int teamId = sportsTeamService.saveTeam(newTeam);

        // create contacts
        List<Type> coachRoleList = new ArrayList<Type>();
        coachRoleList.add(new Type("coach"));
        Contact coach = new Contact("pål", null, "jensen");
        coach.setFkTeamId(teamId);
        coach.setTeamRoleList(coachRoleList);
        int coachId = sportsTeamService.saveContact(coach);

        List<Type> teamLeadRoleList = new ArrayList<Type>();
        teamLeadRoleList.add(new Type("teamlead"));
        Contact teamlead = new Contact("per", null, "hansen");
        teamlead.setFkTeamId(teamId);
        teamlead.setTeamRoleList(teamLeadRoleList);
        int teamleadId = sportsTeamService.saveContact(teamlead);

        Team team = sportsTeamService.getTeam(teamId);
        assertNotNull(team);
        assertEquals("MyTeamName", team.getName());
        assertTrue(team.getFkLeagueId() == 15);
        assertTrue(team.getClub().getId() == clubId);
        assertNotNull(team.getTeamLead().getId() == teamleadId);
        assertNotNull(team.getCoach().getId() == coachId);
        assertEquals("2", Integer.toString(team.getNumberOfContacts()));
        assertEquals("0", Integer.toString(team.getNumberOfPlayers()));

        // delete coach and teamlead
        team.setFkTeamleadId(null);
        team.setFkCoachId(null);
        sportsTeamService.saveTeam(team);
        Team updatedTeam = sportsTeamService.getTeam(teamId);
        assertNull(updatedTeam.getFkTeamleadId());
        assertNull(updatedTeam.getFkCoachId());
        assertNull(updatedTeam.getTeamLead());
        assertNull(updatedTeam.getCoach());
        assertEquals("2", Integer.toString(updatedTeam.getNumberOfContacts()));
        assertEquals("0", Integer.toString(updatedTeam.getNumberOfPlayers()));
    }

    @Test
    public void changeCoachAndTeamLeadForTeam() {
        Club club = new Club("UIL", "Bandy");
        int clubId = sportsTeamService.saveClub(club);
        Team newTeam = new Team("MyTeamName");
        newTeam.setFkClubId(clubId);
        newTeam.setFkLeagueId(15);
        int teamId = sportsTeamService.saveTeam(newTeam);

        // create contacts
        List<Type> coachRoleList = new ArrayList<Type>();
        coachRoleList.add(new Type("coach"));
        Contact coach = new Contact("pål", null, "jensen");
        coach.setFkTeamId(teamId);
        coach.setTeamRoleList(coachRoleList);
        int coachId = sportsTeamService.saveContact(coach);

        List<Type> teamLeadRoleList = new ArrayList<Type>();
        teamLeadRoleList.add(new Type("teamlead"));
        Contact teamlead = new Contact("per", null, "hansen");
        teamlead.setFkTeamId(teamId);
        teamlead.setTeamRoleList(teamLeadRoleList);
        int teamleadId = sportsTeamService.saveContact(teamlead);

        Contact newCoach = new Contact("peder", null, "jensen");
        newCoach.setFkTeamId(teamId);
        int newCoachId = sportsTeamService.saveContact(newCoach);

        Contact newTeamlead = new Contact("anton", null, "hansen");
        newTeamlead.setFkTeamId(teamId);
        int newTeamleadId = sportsTeamService.saveContact(newTeamlead);

        Team team = sportsTeamService.getTeam(teamId);
        assertNotNull(team);
        assertEquals("MyTeamName", team.getName());
        assertTrue(team.getFkLeagueId() == 15);
        assertTrue(team.getClub().getId() == clubId);
        assertNotNull(team.getTeamLead().getId() == teamleadId);
        assertNotNull(team.getCoach().getId() == coachId);
        assertEquals("PER", team.getTeamLead().getFirstName());
        assertEquals("PÅL", team.getCoach().getFirstName());
        assertEquals("4", Integer.toString(team.getNumberOfContacts()));
        assertEquals("0", Integer.toString(team.getNumberOfPlayers()));

        // change coach and teamlead
        team.setFkTeamleadId(newTeamleadId);
        team.setFkCoachId(newCoachId);
        teamId = sportsTeamService.saveTeam(team);
        Team updatedTeam = sportsTeamService.getTeam(teamId);
        assertTrue(newTeamleadId == updatedTeam.getFkTeamleadId());
        assertTrue(newCoachId == updatedTeam.getFkCoachId());
        assertTrue(newTeamleadId == updatedTeam.getTeamLead().getId());
        assertTrue(newCoachId == updatedTeam.getCoach().getId());
        assertEquals("ANTON", updatedTeam.getTeamLead().getFirstName());
        assertEquals("PEDER", updatedTeam.getCoach().getFirstName());
        assertEquals("4", Integer.toString(updatedTeam.getNumberOfContacts()));
        assertEquals("0", Integer.toString(updatedTeam.getNumberOfPlayers()));
    }

      

    // @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @Ignore
    @Test(expected = AuthenticationException.class)
    public void deleteMethodsSecureCheck() {
        Method[] methods = SportsTeamService.class.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("delete")) {
                System.out.println("method = " + method.getName());
                try {
                    method.invoke(sportsTeamService, new Integer(1));
                } catch (IllegalAccessException iae) {
                    System.out.println(iae.getMessage());
                } catch (IllegalArgumentException ia) {
                    System.out.println(ia.getMessage());
                } catch (InvocationTargetException ite) {
                    System.out.println(ite.getMessage());
                }
            }
        }
    }
}
