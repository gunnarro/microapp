package com.gunnarro.sportsteam.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Cup;
import com.gunnarro.sportsteam.domain.activity.Match;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.activity.Training;
import com.gunnarro.sportsteam.domain.party.Address;
import com.gunnarro.sportsteam.domain.party.Contact;
import com.gunnarro.sportsteam.repository.TestData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
@TransactionConfiguration(defaultRollback = true)
// @FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class SportsTeamServicePerformanceTest {

    @Autowired
    @Qualifier("sportsTeamService")
    protected SportsTeamService sportsTeamService;

    private final static int TEST_RUNS = 100;
    private static Integer clubId;
    private static Integer teamId;
    private static Season season;

    @BeforeClass
    public static void setUpTestData() throws Exception {
        // Club club = new Club();
        // club.setName("myClub");
        // club.setDepartmentName("Bandy");
        // Address address = new Address();
        // address.setStreetName("test-streeet");
        // address.setStreetNumber("34");
        // address.setStreetNumberPostfix("A");
        // address.setCity("Oslo");
        // address.setPostCode("0880");
        // address.setCountry("Norway");
        // club.setAddress(address);
        // clubId = sportsTeamService.saveClub(club);
        // Team team = new Team("newTeam", null, 2004, "Male");
        // team.setFkClubId(clubId);
        // teamId = sportsTeamService.saveTeam(team);
        // season = sportsTeamService.getCurrentSeason();
    }

    // @AfterClass
    // public static void verifyTest() {
    // assertEquals(TEST_RUNS, sportsTeamService.getMatches(teamId,
    // season.getPeriod()).size());
    // // assertEquals(TEST_RUNS, sportsTeamService.getCups(teamId,
    // // season.getPeriod()).size());
    // assertEquals(TEST_RUNS, sportsTeamService.getTrainings(teamId,
    // season.getPeriod()).size());
    //
    // // finally clean up
    // int deletedClubRows = sportsTeamService.deleteClub(clubId);
    // assertEquals(1, deletedClubRows);
    // assertNull(sportsTeamService.getTeam(teamId));
    //
    // // int deletedCupRows = sportsTeamService.deleteAllCups();
    // // assertEquals(TEST_RUNS, deletedCupRows);
    // assertEquals(0, sportsTeamService.getMatches(teamId,
    // season.getPeriod()).size());
    // // assertEquals(0, sportsTeamService.getCups(teamId,
    // // season.getPeriod()).size());
    // assertEquals(0, sportsTeamService.getTrainings(teamId,
    // season.getPeriod()).size());
    // }

    // @Test
    // @Ignore
    // public void performanceTest() {
    //
    // }

    @Ignore
    @Test
    public void performanceTestCreateMatch() {
        sportsTeamService.getSeason("current");
        for (int index = 0; index < TEST_RUNS; index++) {
            Match newMatch = new Match(null, new Date(System.currentTimeMillis() + index * 24 * 3600 * 1000), null, new Team("homeTeam_" + index), new Team(
                    "awayTeam_" + index), "home-venue", null);
            newMatch.setFkTeamId(teamId);
            newMatch.setFkSeasonId(season.getId());
            sportsTeamService.saveMatch(newMatch);
        }
        assertEquals(TEST_RUNS, sportsTeamService.getMatches(teamId, season.getPeriod()).size());
    }

    @Test
    public void saveContact() {
        sportsTeamService.deleteAllData(1);
        int clubId = sportsTeamService.saveClub(new Club(null, "club1", "football", "CK", "bandyStadium", null, "http://club.homepage.org"));
        int teamId = sportsTeamService.saveTeam(TestData.createTeam("team1", clubId));
        Team team = sportsTeamService.getTeam(teamId);
        Address address = new Address("streetname", "25", "c", "7777", "city", "country");
        Contact newContact = new Contact(team, null, "firstName", "middleName", "lastName", "M", "11111111", "p1@email.no", address);
        newContact.setTeamRoleList(Contact.mapNewTeamRolesNameToTypeList(new String[] { "COACH" }));
        int contactId1 = sportsTeamService.saveContact(newContact);

        Contact contact = sportsTeamService.getContact(contactId1);
        assertEquals("[COACH]", contact.getTeamRoleList().toString());
        assertEquals("streetname".toUpperCase(), contact.getAddress().getStreetName());
        contact.getAddress().setStreetName("updated streetname");
        contact.setTeamRoleList(Contact.mapNewTeamRolesNameToTypeList(new String[] { "PARENT", "TEAMLEAD" }));
        sportsTeamService.saveContact(contact);
        contact = sportsTeamService.getContact(contactId1);
        assertEquals("[PARENT, TEAMLEAD]", contact.getTeamRoleList().toString());
        assertEquals("updated streetname".toUpperCase(), contact.getAddress().getStreetName());
    }

    @Test
    @Ignore
    public void createCup() {
        for (int index = 0; index < TEST_RUNS; index++) {
            Cup newCup = new Cup(null, new Date(), "Cup Name " + index, "arranging club name", "cup-venue", new Date());
            newCup.setFkSeasonId(season.getId());
            sportsTeamService.saveCup(newCup);
        }
        assertEquals(TEST_RUNS, sportsTeamService.getCups(teamId, season.getPeriod()).size());
    }

    @Ignore
    @Test
    public void createTraining() {
        for (int index = 0; index < TEST_RUNS; index++) {
            Training newTraining = new Training(null, new Date(System.currentTimeMillis() + index * 3600), new Date(System.currentTimeMillis() + index * 3600
                    + 360000), null, "place");
            newTraining.setFkTeamId(teamId);
            newTraining.setFkSeasonId(season.getId());
            sportsTeamService.saveTraining(newTraining);
        }
        assertEquals(TEST_RUNS, sportsTeamService.getTrainings(teamId, season.getPeriod()).size());
    }

    private void createVolunteerTasks(int teamId, int seasonId) {

    }

    private void createPlayers() {

    }

    private void createContacts() {

    }

    private void createReferees() {

    }
}
