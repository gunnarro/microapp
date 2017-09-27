package com.gunnarro.sportsteam.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.ImageDetail;
import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Cup;
import com.gunnarro.sportsteam.domain.activity.Match;
import com.gunnarro.sportsteam.domain.activity.MatchEvent;
import com.gunnarro.sportsteam.domain.activity.MatchEvent.MatchEventTypesEnum;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.activity.Status;
import com.gunnarro.sportsteam.domain.activity.Training;
import com.gunnarro.sportsteam.domain.activity.Type;
import com.gunnarro.sportsteam.domain.activity.Type.MatchTypesEnum;
import com.gunnarro.sportsteam.domain.league.League;
import com.gunnarro.sportsteam.domain.league.LeagueCategory;
import com.gunnarro.sportsteam.domain.league.LeagueRule;
import com.gunnarro.sportsteam.domain.party.Address;
import com.gunnarro.sportsteam.domain.party.Contact;
import com.gunnarro.sportsteam.domain.party.Contact.StatusEnum;
import com.gunnarro.sportsteam.domain.party.Person;
import com.gunnarro.sportsteam.domain.party.Player;
import com.gunnarro.sportsteam.domain.party.Referee;
import com.gunnarro.sportsteam.domain.party.Role;
import com.gunnarro.sportsteam.domain.party.User;
import com.gunnarro.sportsteam.domain.statistic.KeyValuePair;
import com.gunnarro.sportsteam.domain.statistic.Statistic;
import com.gunnarro.sportsteam.domain.task.ScheduledTask;
import com.gunnarro.sportsteam.domain.task.SubTask;
import com.gunnarro.sportsteam.domain.task.TaskGroup;
import com.gunnarro.sportsteam.domain.task.VolunteerTask;
import com.gunnarro.sportsteam.domain.view.list.Item;
import com.gunnarro.sportsteam.repository.table.TableHelper.PlayerLinkTableTypeEnum;
import com.gunnarro.sportsteam.service.exception.ApplicationException;
import com.gunnarro.sportsteam.utility.Utility;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
// @Ignore
public class SportsTeamRepository2Test {

    @Autowired
    @Qualifier("sportsTeamRepository")
    private SportsTeamRepository sportsTeamRepository;
    private int updateSubTaskRows;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    // @Ignore
    // @Test
    // public void rawSQLqueryTest() {
    // // sportsTeamRepository.deleteClub(7);
    // // sportsTeamRepository.deletePlayer(1);
    // for (String name : sportsTeamRepository.getClubNames()) {
    // System.out.println("CLUB: " + name);
    // }
    //
    // for (String name : sportsTeamRepository.getTeamNames("%")) {
    // System.out.println("TEAM: " + name);
    // }
    //
    // for (Player player : sportsTeamRepository.getPlayerList(1)) {
    // System.out.println("PLAYER: " + player.getId() + " " +
    // player.getFullName());
    // }
    //
    // for (Contact contact : sportsTeamRepository.getContactList(1)) {
    // System.out.println("PLAYER: " + contact.getId() + " " +
    // contact.getFullName());
    // }
    // }

    // @Test
    // public void verifySettings() {
    // assertThat(DataLoader.TEAM_XML_URL + "/uil.xml",
    // equalTo(sportsTeamRepository.getSetting(SettingsTable.DATA_FILE_URL_KEY)));
    // // assertThat("0.1",
    // //
    // equalTo(sportsTeamRepository.getSetting(SettingsTable.DATA_FILE_VERSION_KEY)));
    // // assertThat("0",
    // //
    // equalTo(sportsTeamRepository.getSetting(SettingsTable.DATA_FILE_LAST_UPDATED_KEY)));
    // assertThat("na",
    // equalTo(sportsTeamRepository.getSetting(SettingsTable.MAIL_ACCOUNT_KEY)));
    // assertThat("na",
    // equalTo(sportsTeamRepository.getSetting(SettingsTable.MAIL_ACCOUNT_PWD_KEY)));
    // }

    // H2 db sql syntax problem
    @Test
    @Ignore
    public void getClubStatistic() {
        List<KeyValuePair> clubStatistic = sportsTeamRepository.getClubStatistic();
        assertEquals(10, clubStatistic.size());
    }

    @Test
    public void deleteAllData() {
        assertTrue(sportsTeamRepository.deleteAllData() > -1);
        assertTrue(sportsTeamRepository.deleteAllCups() > -1);
        assertTrue(sportsTeamRepository.deleteAllMatches() > -1);
        assertTrue(sportsTeamRepository.deleteAllReferees() > -1);
        assertTrue(sportsTeamRepository.deleteAllTrainings() > -1);
        assertTrue(sportsTeamRepository.deleteAllPlayers() > -1);
        assertTrue(sportsTeamRepository.deleteAllContacts() > -1);
        assertTrue(sportsTeamRepository.deleteAllVolunteerTasks() > -1);
    }

    /**
     * methods not implemented yet
     */
    // @Ignore
    @Test
    public void checkInitializedData() {
        assertEquals(3, sportsTeamRepository.getContactStatusTypes().size());
        assertEquals(4, sportsTeamRepository.getMatchTypes().size());
        assertEquals(5, sportsTeamRepository.getMatchStatusTypes().size());
        assertEquals(2, sportsTeamRepository.getSportTypes().size());
        assertEquals(32, sportsTeamRepository.getLeagueTypes(1).size());
        assertEquals(32, sportsTeamRepository.getLeagues().size());
        assertEquals(4, sportsTeamRepository.getPlayerStatusTypes().size());
        assertEquals(4, sportsTeamRepository.getPlayerPositionTypes().size());
        assertEquals(5, sportsTeamRepository.getTaskStatuses().size());
        assertEquals(7, sportsTeamRepository.getTeamRoleTypes().size());
        assertEquals(4, sportsTeamRepository.getSeasonPeriodes().size());
        assertEquals(4, sportsTeamRepository.getUserRoles().size());
    }

    @Test
    public void verifyGettersNoMatch() {
        assertNull(sportsTeamRepository.getAddress(0));
        assertNull(sportsTeamRepository.getContact(0));
        assertNull(sportsTeamRepository.getClub(0));
        assertNull(sportsTeamRepository.getCup(0));
        assertNotNull(sportsTeamRepository.getCurrentSeason());
        assertNull(sportsTeamRepository.getMatch(0));
        assertNull(sportsTeamRepository.getPlayer(0));
        assertNull(sportsTeamRepository.getReferee(0));
        assertNull(sportsTeamRepository.getTeam(0));
        assertNull(sportsTeamRepository.getTraining(0));
        // assertNull(sportsTeamRepository.getSetting("invalid"));
    }

    @Test
    public void verifyGettersMatch() {
        assertNotNull(sportsTeamRepository.getCurrentSeason());
        assertNotNull(sportsTeamRepository.getLeague("Gutt krets Oslo"));
        assertNotNull(sportsTeamRepository.getLeague("BANDY", "Gutt krets Oslo"));
    }

    @Test
    public void verifyGettersNoMatchException() {
        try {
            sportsTeamRepository.getSeason(0);
            assertTrue(false);
        } catch (ApplicationException ae) {
            assertEquals("No Season found for id=0", ae.getMessage());
        }

        try {
            sportsTeamRepository.getLeague("Gutt krets Oslo xx");
            assertTrue(false);
        } catch (ApplicationException ae) {
            assertEquals("No leagues found for name=Gutt krets Oslo xx", ae.getMessage());
        }

    }

    @Test
    public void updateClub() {
        Address address = new Address();
        address.setStreetName("Stavangergt");
        address.setStreetNumber("22");
        address.setStreetNumberPostfix("a");
        address.setPostCode("9090");
        address.setCity("oslo");
        address.setCountry("norge");

        int clubId = sportsTeamRepository.createClub(new Club(null, "newSportsClub", "newBandy", "CK", "bandyStadium", address, "http://club.homepage.org"));
        Club club = sportsTeamRepository.getClub(clubId);
        assertEquals("Stavangergt".toUpperCase(), club.getAddress().getStreetName());
        assertEquals("22".toUpperCase(), club.getAddress().getStreetNumber());
        assertEquals("a".toUpperCase(), club.getAddress().getStreetNumberPostfix());
        assertEquals("Stavangergt 22a".toUpperCase(), club.getAddress().getFullStreetName());
        // assertEquals("9090", club.getAddress().getPostCode());
        assertEquals("oslo".toUpperCase(), club.getAddress().getCity());
        assertEquals("norge".toUpperCase(), club.getAddress().getCountry());

        assertEquals("newSportsClub", club.getName());
        club.setName("changedSportsClub");
        int updatedRows = sportsTeamRepository.updateClub(club);
        assertEquals(1, updatedRows);
        Club updatedClub = sportsTeamRepository.getClub(clubId);
        assertEquals("changedSportsClub", updatedClub.getName());

        int deletedClubRows = sportsTeamRepository.deleteClub(clubId);
        assertTrue(deletedClubRows == 1);
    }

    @Test
    public void updateAddress() {
        int addressId = sportsTeamRepository.createAddress(new Address("Stavangergt", "22", "d", "9090", "oslo", "norge"));
        Address address = sportsTeamRepository.getAddress(addressId);
        assertNotNull(address);
        assertEquals("Stavangergt 22d".toUpperCase(), address.getFullStreetName());
        address.setStreetName("oslogata");
        address.setStreetNumber("34");
        address.setStreetNumberPostfix("a");
        sportsTeamRepository.updateAddress(address);
        Address updatedAddress = sportsTeamRepository.getAddress(addressId);
        assertEquals("Oslogata 34A".toUpperCase(), updatedAddress.getFullStreetName());
        int deletedAddressRows = sportsTeamRepository.deleteAddress(addressId);
        assertTrue(deletedAddressRows == 1);
    }

    // @Test
    // public void updateMatchStatusInvalidId() {
    // try {
    // int updateMatchStatusRows = sportsTeamRepository.updateMatchStatus(1, 1);
    // assertTrue(updateMatchStatusRows == 0);
    // } catch (Exception e) {
    // assertEquals("sss", e.getMessage());
    // }
    // }

    @Test
    public void verifyGettersNoMatchReturnEmptyList() {
        // assertEquals(0, sportsTeamRepository.getClubAsItems().length);
        // assertEquals(0, sportsTeamRepository.getClubNames().length);
        // FIXME
        // assertEquals(0, sportsTeamRepository.getClubs().size());
        // assertEquals(0, sportsTeamRepository.getContactList(1,
        // RoleTypesEnum.DEFAULT.name()).size());
        // assertEquals(0, sportsTeamRepository.getContactList(1).size());
        // assertEquals(0,
        // sportsTeamRepository.getContactsAsItemList(1).size());
        // assertEquals(0, sportsTeamRepository.getCupList(1, 2014).size());
        // assertEquals(0, sportsTeamRepository.getMatchEventList(0).size());
        assertEquals(0, sportsTeamRepository.getVolunteerTasks(1, 1).size());
        assertEquals(0, sportsTeamRepository.getCupList(1, 1).size());
        assertEquals(0, sportsTeamRepository.getMatchList(1, 1).size());
        // assertEquals(0, sportsTeamRepository.getTrainings(1, 1).size()); //
        // H2 db sql syntax problem
        assertEquals(0, sportsTeamRepository.getContacts(1, new Status(1, "ACTIVE")).size());
        assertEquals(0, sportsTeamRepository.getContactsForClub(1).size());
        // assertEquals(0,
        // sportsTeamRepository.getContactsForPlayerItem(1).size()); // H2 db
        // sql syntax problem
        // assertEquals(0,
        // sportsTeamRepository.getPlayersForContactItem(1).size());// H2 db sql
        // syntax problem
        assertEquals(0, sportsTeamRepository.getContactsForPlayer(1).size());
        assertEquals(0, sportsTeamRepository.getVolunteerTasks(1, 1).size());
        assertEquals(0, sportsTeamRepository.getMatchList(1, 2014).size());
        assertEquals(0, sportsTeamRepository.getMatchPlayerList(1, 1).size());
        assertEquals(0, sportsTeamRepository.getCupPlayerList(1, 1).size());
        assertEquals(0, sportsTeamRepository.getTrainingPlayerList(1, 1).size());
        assertEquals(0, sportsTeamRepository.getNumberOfSignedPlayers(PlayerLinkTableTypeEnum.MATCH.name(), 1).intValue());
        assertEquals(0, sportsTeamRepository.getNumberOfSignedPlayers(PlayerLinkTableTypeEnum.CUP.name(), 1).intValue());
        assertEquals(0, sportsTeamRepository.getNumberOfSignedPlayers(PlayerLinkTableTypeEnum.TRAINING.name(), 1).intValue());
        assertEquals(0, sportsTeamRepository.getPlayers(1, new Status(1, "ACTIVE")).size());
        // assertEquals(0, sportsTeamRepository.getPlayersAsItemList(1).size());
        // assertEquals(0, sportsTeamRepository.getRefereeNames().length);
        // assertEquals(0, sportsTeamRepository.getTeamAsItems(1).length);
        assertEquals(0, sportsTeamRepository.getTeams(1).size());
        assertEquals(0, sportsTeamRepository.getReferees(1).size());
        assertEquals(0, sportsTeamRepository.getMatchList(1, 1).size());
        assertEquals(0, sportsTeamRepository.getMatchListForLeague(1, 1, "team name").size());
        // assertEquals(0,
        // sportsTeamRepository.getTeamList("unkown_club_name").size());
        // assertEquals(0,
        // sportsTeamRepository.getTeamNames("unkown_club_name").length);
        // assertEquals(0,
        // sportsTeamRepository.getTeamNames("unkown_club_name").length);
        // assertEquals(0, sportsTeamRepository.getTrainingList(1,
        // 2014).size());
    }

    @Test
    public void newClubTeamPlayerAndReferee() {
        int addressId = sportsTeamRepository.createAddress(new Address("Stavangergt", "22", null, "9090", "oslo", "norge"));
        Address clubAddress = sportsTeamRepository.getAddress(addressId);
        assertNotNull(clubAddress);
        int clubId = sportsTeamRepository.createClub(new Club(null, "newSportsClub", "newBandy", "CK", "bandyStadium", clubAddress, "http://club.homepage.org"));
        Club club = sportsTeamRepository.getClub(clubId);
        assertNotNull(club);
        assertEquals("newSportsClub", club.getName());
        assertEquals("newBandy", club.getDepartmentName());
        assertEquals("CK", club.getClubNameAbbreviation());
        assertEquals("bandyStadium", club.getStadiumName());
        assertEquals("http://club.homepage.org", club.getHomePageUrl());
        assertEquals("STAVANGERGT 22", club.getAddress().getFullStreetName());
        // assertEquals("9090", club.getAddress().getPostalCode());
        assertEquals("OSLO", club.getAddress().getCity());
        assertEquals("NORGE", club.getAddress().getCountry());

        // Create team
        int teamId = sportsTeamRepository.createTeam(TestData.createTeam("newTeam", clubId));
        Team team = sportsTeamRepository.getTeam(teamId);
        assertNotNull(team);
        assertEquals(teamId, team.getId().intValue());
        assertEquals(2004, team.getTeamYearOfBirth().intValue());
        assertEquals("newTeam", team.getName());
        assertEquals("Male", team.getGender());
        assertEquals(0, team.getNumberOfContacts());
        assertEquals(0, team.getNumberOfPlayers());

        // Create player
        Date dateOfBirth = new Date();
        List<Contact> parents = new ArrayList<Contact>();
        Address playerAddress = new Address("streetname", "24", "d", "9098", "city", "country");
        Player newPlayer = new Player(team, "newPlayerFirstname", null, "newPlayerLastName", "M", playerAddress);
        newPlayer.setDateOfBirth(dateOfBirth);
        newPlayer.setParents(parents);
        List<Type> playerStatusTypes = sportsTeamRepository.getPlayerStatusTypes();
        newPlayer.setFkStatusId(playerStatusTypes.get(0).getId());
        newPlayer.setFkTeamId(teamId);
        int playerId = sportsTeamRepository.createPlayer(newPlayer);

        Player player = sportsTeamRepository.getPlayer(playerId);
        assertNotNull(player);
        assertEquals("NEWPLAYERFIRSTNAME NEWPLAYERLASTNAME", player.getFullName());
        assertEquals("M", player.getGender());
        assertFalse(player.hasParents());
        assertEquals(StatusEnum.ACTIVE.name(), player.getStatus().getName());
        assertEquals("CITY", player.getAddress().getCity());
        assertEquals("COUNTRY", player.getAddress().getCountry());
        assertEquals("STREETNAME 24D", player.getAddress().getFullStreetName());
        // assertEquals("9098", player.getAddress().getPostCode());
        // assertTrue(player.getAddress().isAddressValid());

        // Create referee
        Referee newReferee = new Referee("refFirstname", "refMiddlename", "refLastname");
        newReferee.setClub(club);
        newReferee.setGender("M");
        newReferee.setMobileNumber("12345678");
        newReferee.setEmailAddress("r@mail.com");
        int refereeId = sportsTeamRepository.createReferee(newReferee);
        Referee referee = sportsTeamRepository.getReferee(refereeId);
        assertNotNull(referee);
        assertNotNull(referee.getFkClubId());
        assertEquals("refFirstname refMiddlename refLastname".toUpperCase(), referee.getFullName());
        // assertEquals("12345678".toUpperCase(), referee.getMobileNumber());
        // assertEquals("r@mail.com".toUpperCase(), referee.getEmailAddress());

    }

    @Test
    @Rollback(true)
    public void deleteClub() {
        // Create new club with address
        Address address = new Address("clubstreetname", "45", "b", "0889", "oslo", "Norway");
        int clubId = sportsTeamRepository.createClub(new Club(null, "newSportsClub", "newBandy", "CK", "bandyStadium", address, "http://club.homepage.org"));
        Club club = sportsTeamRepository.getClub(clubId);
        assertNotNull(club);
        int addressId = club.getAddress().getId();
        assertNotNull(addressId);

        // Check that when deleting address do not delete the club
        int deletedAddressRows = sportsTeamRepository.deleteAddress(addressId);
        assertTrue(deletedAddressRows == 1);
        club = sportsTeamRepository.getClub(clubId);
        assertNotNull(club);
        // Delete the club and check that the address is also deleted
        int deletedClubRows = sportsTeamRepository.deleteClub(clubId);
        assertTrue(deletedClubRows == 1);
        club = sportsTeamRepository.getClub(clubId);
        assertNull(club);
        // verify that club address is deleted
        Address addressDeleted = sportsTeamRepository.getAddress(addressId);
        assertNull(addressDeleted);
    }

    @Test
    public void newClubWithServeralDepartments() {
        int clubIdDep1 = sportsTeamRepository.createClub(new Club(null, "clubname", "department1", "CK", "bandyStadium", null, "http://club.homepage.org"));
        int clubIdDep2 = sportsTeamRepository.createClub(new Club(null, "clubname", "department2", "CK", "bandyStadium", null, "http://club.homepage.org"));
        assertTrue(clubIdDep1 != clubIdDep2);
        // Clean up
        int deletedClubRows = sportsTeamRepository.deleteClub(clubIdDep1);
        assertTrue(deletedClubRows == 1);

        // try to delete twice
        int deletedClubRowsTwice = sportsTeamRepository.deleteClub(clubIdDep1);
        assertTrue(deletedClubRowsTwice == 0);

        deletedClubRows = sportsTeamRepository.deleteClub(clubIdDep2);
        assertTrue(deletedClubRows == 1);
    }

    @Test
    public void newClubSeveralTeams() {
        int clubId = sportsTeamRepository.createClub(new Club(null, "newSportsClub", "newBandy", "CK", "bandyStadium", null, "http://club.homepage.org"));
        int teamId1 = sportsTeamRepository.createTeam(TestData.createTeam("team1", clubId));
        int teamId2 = sportsTeamRepository.createTeam(TestData.createTeam("team2", clubId));
        assertTrue(teamId1 != teamId2);
    }

    @Test
    public void newClubsSameDepartments() {
        int clubId1 = sportsTeamRepository.createClub(new Club(null, "club1", "bandy", "CK", "bandyStadium", null, "http://club.homepage.org"));
        int clubId2 = sportsTeamRepository.createClub(new Club(null, "club2", "bandy", "CK", "bandyStadium", null, "http://club.homepage.org"));
        assertTrue(clubId1 != clubId2);
        // Clean up
        int deletedClubRows = sportsTeamRepository.deleteClub(clubId1);
        assertTrue(deletedClubRows == 1);
        deletedClubRows = sportsTeamRepository.deleteClub(clubId2);
        assertTrue(deletedClubRows == 1);
    }

    @Test(expected = ApplicationException.class)
    public void newClubDuplicate() {
        // create new club
        sportsTeamRepository.createClub(new Club(null, "clubname3", "duplicate", "CK", "bandyStadium", null, "http://club.homepage.org"));
        // try to create a duplicate
        sportsTeamRepository.createClub(new Club(null, "clubname3", "duplicate", "CK", "bandyStadium", null, "http://club.homepage.org"));
    }

    @Test
    public void newClubAndTeamNameOnly() {
        int clubId = sportsTeamRepository.createClub(new Club(null, "clubName", "bandy", null, null, null, null));
        assertTrue(clubId > 0);
        sportsTeamRepository.createTeam(TestData.createTeam("newTeam", clubId));
        assertTrue(clubId > 0);
        // Clean up
        int deletedClubRows = sportsTeamRepository.deleteClub(clubId);
        assertTrue(deletedClubRows == 1);
    }

    @Test
    public void newClubsSameTeamName() {
        int clubId1 = sportsTeamRepository.createClub(new Club(null, "clubname1", "bandy", "CK1", "bandyStadium1", null, "http://club1.homepage.org"));
        assertTrue(clubId1 > 0);
        sportsTeamRepository.createTeam(TestData.createTeam("newTeam", clubId1));
        int clubId2 = sportsTeamRepository.createClub(new Club(null, "clubname2", "bandy", "CK2", "bandyStadium2", null, "http://club2.homepage.org"));
        assertTrue(clubId2 > 0);
        assertTrue(clubId1 != clubId2);
        sportsTeamRepository.createTeam(TestData.createTeam("newTeam", clubId2));
        // Clean up
        int deletedClubRows = sportsTeamRepository.deleteClub(clubId1);
        assertTrue(deletedClubRows == 1);
        deletedClubRows = sportsTeamRepository.deleteClub(clubId2);
        assertTrue(deletedClubRows == 1);
    }

    @Test(expected = ApplicationException.class)
    public void newTeamInvalidClub() {
        try {
            Team team = new Team();
            team.setName("team-name");
            team.setGender("MALE");
            sportsTeamRepository.createTeam(team);
            assertTrue(false);
        } catch (ApplicationException ae) {
            // assertEquals("Club must be set for creating new Team!",
            // ae.getMessage());
            throw ae;
        }
    }

    @Test
    @Transactional()
    public void newTeamWithContactPersons() {
        int clubId = sportsTeamRepository.createClub(new Club(null, "newSportsClub", "newBandy", "CK", "bandyStadium", null, "http://club.homepage.org"));
        Team newTeam = TestData.createTeam("team1", clubId);
        int teamId = sportsTeamRepository.createTeam(newTeam);
        Contact teamlead = new Contact(null, null, "teamLead", "middleName", "lastName", "M", "11111111", "p1@email.no", null);
        teamlead.setFkTeamId(teamId);
        int teamleadId = sportsTeamRepository.createContact(teamlead);
        sportsTeamRepository.createContactRoleTypeLnk(teamleadId, Role.RoleTypesEnum.TEAMLEAD.name());

        Team teamUpdate = sportsTeamRepository.getTeam(teamId);
        assertEquals("team1", teamUpdate.getName());
        assertNotNull(teamUpdate.getTeamLead());
        assertEquals("teamLead".toUpperCase(), teamUpdate.getTeamLead().getFirstName());
        assertNull(teamUpdate.getCoach());

        sportsTeamRepository.deleteContactRoleTypeLnk(teamleadId, Role.RoleTypesEnum.TEAMLEAD.name());
        teamUpdate = sportsTeamRepository.getTeam(teamId);
        assertNull(teamUpdate.getTeamLead());

        // Only for testing the method, check if it crash
        sportsTeamRepository.deleteAllContactRoleTypeLnk(teamleadId);

    }

    @Test(expected = ApplicationException.class)
    public void newTeamDuplicate() {
        int clubId = sportsTeamRepository.createClub(new Club(null, "newSportsClub", "newBandy", "CK", "bandyStadium", null, "http://club.homepage.org"));
        Team team = new Team("newTeam", null, 2004, null);
        team.setFkClubId(clubId);
        team.setFkLeagueId(22);
        sportsTeamRepository.createTeam(team);
        try {
            Team teamDuplicate = new Team("newTeam", null, 2004, null);
            teamDuplicate.setFkClubId(clubId);
            team.setFkLeagueId(22);
            sportsTeamRepository.createTeam(teamDuplicate);
            assertTrue(false);
        } catch (ApplicationException ae) {
            // assertEquals("PreparedStatementCallback; SQL []; Duplicate entry '147-newTeam' for key 'fk_club_id'; nested exception is com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException: Duplicate entry '147-newTeam' for key 'fk_club_id'",
            // ae.getMessage());
            throw ae;
        }

        // Clean up
        int deletedClubRows = sportsTeamRepository.deleteClub(clubId);
        assertTrue(deletedClubRows == 1);
    }

    @Test
    public void newTeamUpdate() {
        int clubId = sportsTeamRepository.createClub(new Club(null, "newSportsClub", "newBandy", "CK", "bandyStadium", null, "http://club.homepage.org"));
        Team newTeam = TestData.createTeam("myTeam", clubId);
        List<Type> leagueTypes = sportsTeamRepository.getLeagueTypes(1);
        newTeam.setFkLeagueId(leagueTypes.get(0).getId());
        int teamId = sportsTeamRepository.createTeam(newTeam);
        Team teamUpdate = sportsTeamRepository.getTeam(teamId);
        assertEquals("myTeam", teamUpdate.getName());
        assertNull(teamUpdate.getCoach());
        assertNull(teamUpdate.getTeamLead());
        assertNotNull(teamUpdate.getLeague());
        assertNotNull(teamUpdate.getLeague().getId());
        assertNotNull(teamUpdate.getLeague().getName());
        teamUpdate.setName("updatedTeam");
        sportsTeamRepository.updateTeam(teamUpdate);
        Team team = sportsTeamRepository.getTeam(teamUpdate.getId());
        assertEquals("updatedTeam", team.getName());
    }

    @Test
    public void newAndUpdateAddress() {
        Address newAddress = new Address("streetname", "45", "b", "0889", "oslo", "Norway");
        long addressId = sportsTeamRepository.createAddress(newAddress);
        Address address = sportsTeamRepository.getAddress(Long.valueOf(addressId).intValue());
        assertTrue(address.isAddressValid());
        assertEquals("Streetname 45B".toUpperCase(), address.getFullStreetName());
        assertEquals("0889", address.getPostCode());
        assertEquals("Oslo".toUpperCase(), address.getCity());
        assertEquals("Norway".toUpperCase(), address.getCountry());

        // Test update address
        address.setStreetName("updated streetname");
        address.setStreetNumber("34");
        address.setStreetNumberPostfix("a");
        address.setPostCode("70000");
        address.setCity("gøteborg");
        address.setCountry("sweden");
        sportsTeamRepository.updateAddress(address);
        address = sportsTeamRepository.getAddress(Long.valueOf(addressId).intValue());
        assertTrue(address.isAddressValid());
        assertEquals("Updated Streetname 34A".toUpperCase(), address.getFullStreetName());
        assertEquals("70000", address.getPostCode());
        assertEquals("gøteborg".toUpperCase(), address.getCity());
        assertEquals("sweden".toUpperCase(), address.getCountry());

        // Clean up
        int deletedAddressRows = sportsTeamRepository.deleteAddress(Long.valueOf(addressId).intValue());
        assertTrue(deletedAddressRows == 1);
    }

    @Test(expected = ApplicationException.class)
    public void newContactDuplicate() {
        int contactId1 = sportsTeamRepository.createContact(new Contact(null, null, "firstName", "middleName", "lastName", "M", "11111111", "p1@email.no", null));
        assertTrue(contactId1 > 0);
        sportsTeamRepository.createContact(new Contact(null, null, "firstName", "middleName", "lastName", "M", "11111111", "p1@email.no", null));
        assertTrue(false);
    }

    /**
     * FIXME, fails on hudson for some unknown reason
     */
    @Ignore
    @Test
    public void newAndUpdateContact() {
        int clubId = sportsTeamRepository.createClub(new Club(null, "club1", "football", "CK", "bandyStadium", null, "http://club.homepage.org"));
        int teamId = sportsTeamRepository.createTeam(TestData.createTeam("team1", clubId));
        Team team = sportsTeamRepository.getTeam(teamId);
        Address address = new Address("streetname", "25", "c", "7777", "city", "country");
        // Create contact 1
        Contact contact = new Contact(team, null, "firstName", "middleName", "lastName", "M", "11111111", "p1@email.no", address);
        contact.setFkStatusId(1);
        int contactId1 = sportsTeamRepository.createContact(contact);
        Contact contact1 = sportsTeamRepository.getContact(contactId1);
        assertTrue(contact1.getId().intValue() == contactId1);
        assertNotNull(contact1);
        assertNotNull(contact1.getAddress());
        assertNotNull(contact1.getAddress().getId());
        assertNotNull(contact1.getFkAddressId());
        assertNotNull(contact1.getFkStatusId());
        assertNotNull(contact1.getStatus().getId());
        assertEquals("ACTIVE", contact1.getStatus().getName());
        assertFalse(contact1.hasTeamRoles());
        assertEquals("Firstname Middlename Lastname".toUpperCase(), contact1.getFullName());
        assertEquals("M", contact1.getGender());
        assertEquals("Streetname 25C".toUpperCase(), contact1.getAddress().getFullStreetName());
        assertEquals("City".toUpperCase(), contact1.getAddress().getCity());
        assertEquals("7777", contact1.getAddress().getPostCode());
        assertEquals("Country".toUpperCase(), contact1.getAddress().getCountry());
        assertTrue(contact1.getAddress().isAddressValid());

        // update contact
        contact1.setEmailAddress("new@email.com");
        contact1.setMobileNumber("11223344");
        contact1.getAddress().setStreetName("mystreet name");
        contact1.getAddress().setStreetNumber("22");
        contact1.getAddress().setStreetNumberPostfix("A");
        contact1.getAddress().setPostCode("9999");
        contact1.getAddress().setCity("bergen");
        contact1.getAddress().setCountry("norge");
        contact1.setFkStatusId(new Status(3, "QUIT").getId());
        int rows = sportsTeamRepository.updateContact(contact1);
        assertTrue(rows == 1);
        Contact updatedContact = sportsTeamRepository.getContact(contactId1);
        assertTrue(updatedContact.getId().intValue() == contactId1);
        assertEquals("QUIT", updatedContact.getStatus().getName());
        assertNotNull(updatedContact.getStatus().getId());
        assertEquals("new@email.com", updatedContact.getEmailAddress());
        assertEquals("11223344", updatedContact.getMobileNumber());
        assertEquals("Mystreet Name 22A".toUpperCase(), updatedContact.getAddress().getFullStreetName());
        assertEquals("9999", updatedContact.getAddress().getPostCode());
        assertEquals("bergen".toUpperCase(), updatedContact.getAddress().getCity());
        assertEquals("norge".toUpperCase(), updatedContact.getAddress().getCountry());
        assertTrue(updatedContact.getAddress().isAddressValid());

        // Must cleanup
        int deletedContactRows = sportsTeamRepository.deleteContact(contactId1);
        assertTrue(deletedContactRows == 1);
        Address addressDeleted = sportsTeamRepository.getAddress(contact1.getAddress().getId());
        assertNull(addressDeleted);
    }

    @Test
    public void newContactTwoNew() {
        int clubId = sportsTeamRepository.createClub(new Club(null, "club1", "football", "CK", "bandyStadium", null, "http://club.homepage.org"));
        Team createteam = TestData.createTeam("team1", clubId);
        assertNull(createteam.getFkLeagueId());
        createteam.setLeague(new League(3, null));
        int teamId = sportsTeamRepository.createTeam(createteam);
        Team team = sportsTeamRepository.getTeam(teamId);
        Address address = new Address("Gogata", "25", "c", "5080", "Bergen", "Norway");
        // Create contact 1
        int contactId1 = sportsTeamRepository.createContact(new Contact(team, null, "firstName1", "middleName", "lastName1", "M", "11111111", "p1@email.no", address));
        assertTrue(contactId1 > 0);
        // Create contact 2
        int contactId2 = sportsTeamRepository.createContact(new Contact(team, null, "firstName2", "middleName", "lastName2", "M", "22334455", "p2@email.no", address));
        assertTrue(contactId2 > 0);
        assertTrue(contactId1 != contactId2);

        // Must cleanup
        int deletedContactRows = sportsTeamRepository.deleteContact(contactId1);
        assertTrue(deletedContactRows == 1);
        deletedContactRows = sportsTeamRepository.deleteContact(contactId2);
        assertTrue(deletedContactRows == 1);
        Address addressDeleted = sportsTeamRepository.getAddress(address.getId());
        assertNull(addressDeleted);
        int deletedTeamRows = sportsTeamRepository.deleteTeam(teamId);
        assertTrue(deletedTeamRows == 1);
        int deletedClubRows = sportsTeamRepository.deleteClub(clubId);
        assertTrue(deletedClubRows == 1);
    }

    @Test
    public void crudPerson() {
        Address address = new Address("Gogata", "25", "c", "5080", "Bergen", "Norway");
        // Create person
        Person newPerson = new Person();
        newPerson.setFirstName("person first name");
        newPerson.setMiddleName("person middle name");
        newPerson.setLastName("person last name");
        newPerson.setDateOfBirth(new Date());
        newPerson.setEmailAddress("person@email.com");
        newPerson.setMobileNumber("99887766");
        newPerson.setAddress(address);
        int personId = sportsTeamRepository.createPerson(newPerson);
        assertTrue(personId > 0);
        Person person = sportsTeamRepository.getPerson(personId);
        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("Person first name person middle name person last name", person.getFullName());
        assertNotNull(person.getDateOfBirth());
        assertEquals("MALE", person.getGender());
        assertNotNull(person.getDateOfBirth());
        assertEquals("person@email.com", newPerson.getEmailAddress());
        assertEquals("99887766", newPerson.getMobileNumber());
        assertEquals("NORWAY".toUpperCase(), person.getAddress().getCountry());
        assertEquals("GOGATA 25C".toUpperCase(), person.getAddress().getFullStreetName());
        assertEquals("5080", person.getAddress().getPostCode());
        assertTrue(person.getAddress().isAddressValid());

        // Update person
        person.setMobileNumber("11223344");
        person.setEmailAddress("updated@mail.com");
        int rows = sportsTeamRepository.updatePerson(person);
        assertEquals(1, rows);
        Person updatedPerson = sportsTeamRepository.getPerson(personId);
        assertNotNull(updatedPerson);
        assertEquals("Person first name person middle name person last name", updatedPerson.getFullName());
        assertEquals("person@email.com", newPerson.getEmailAddress());
        assertNotNull(newPerson.getDateOfBirth());
        assertEquals("99887766", newPerson.getMobileNumber());

        // Must cleanup
        int deletedContactRows = sportsTeamRepository.deletePerson(personId);
        assertTrue(deletedContactRows == 1);
    }

    @Test(expected = ApplicationException.class)
    public void newPlayerDuplicate() {
        int clubId = sportsTeamRepository.createClub(new Club(null, "newSportsClub", "newBandy", "CK", "bandyStadium", null, "http://club.homepage.org"));
        int teamId = sportsTeamRepository.createTeam(TestData.createTeam(clubId));
        Player newPlayer = new Player(null, "firstName", "middleName", "lastName", "M", null);
        newPlayer.setFkTeamId(teamId);
        int playerId = sportsTeamRepository.createPlayer(newPlayer);
        assertTrue(playerId > 0);
        sportsTeamRepository.createPlayer(newPlayer);
    }

    @Test
    public void newPlayer() {
        int clubId = sportsTeamRepository.createClub(new Club(null, "newSportsClub", "newBandy", "CK", "bandyStadium", null, "http://club.homepage.org"));
        int teamId = sportsTeamRepository.createTeam(TestData.createTeam(clubId));
        Address address = new Address("streetname", "25", "c", "4455", "city", "country");
        Player newPlayer = new Player(null, "firstName", "middleName", "lastName", "M", address);
        newPlayer.setFkTeamId(teamId);
        newPlayer.setDateOfBirth(new Date());
        newPlayer.setJerseyNumber(22);
        newPlayer.setMobileNumber("+478888888822");
        newPlayer.setEmailAddress("player@email.com");
        newPlayer.setSchoolName("MySchool");
        newPlayer.setFkStatusId(1);
        assertNull(newPlayer.getFkPlayerPositionId());
        int playerId = sportsTeamRepository.createPlayer(newPlayer);
        Player player = sportsTeamRepository.getPlayer(playerId);
        assertNotNull(player);
        assertNotNull(player.getTeam());
        assertFalse(player.hasParents());
        assertEquals("Firstname Middlename Lastname".toUpperCase(), player.getFullName());
        assertEquals("M", player.getGender());
        assertNotNull(player.getDateOfBirth());
        assertEquals("Country".toUpperCase(), player.getAddress().getCountry());
        assertEquals("Streetname 25C".toUpperCase(), player.getAddress().getFullStreetName());
        assertEquals("4455", player.getAddress().getPostCode());
        assertTrue(player.getAddress().isAddressValid());
        assertEquals("player@email.com", player.getEmailAddress());
        assertEquals("+478888888822", player.getMobileNumber());
        assertEquals(22, player.getJerseyNumber());
        assertEquals("MySchool", player.getSchoolName());
        assertEquals("ACTIVE", player.getStatus().getName());
        assertNotNull(player.getStatus().getId());
        assertNull(player.getPosition());
        // assertEquals("", player.getFkPlayerPositionId());

        // Update player
        player.setEmailAddress("new@email.com");
        player.setMobileNumber("11223344");
        player.setFkPlayerPositionId(2);
        int rows = sportsTeamRepository.updatePlayer(player);
        assertTrue(rows == 1);
        Player updatedPlayer = sportsTeamRepository.getPlayer(playerId);
        assertEquals("new@email.com", updatedPlayer.getEmailAddress());
        assertEquals("11223344", updatedPlayer.getMobileNumber());
        assertEquals("DEFENDER", updatedPlayer.getPosition());

        // Must cleanup
        int deletedPlayerRows = sportsTeamRepository.deletePlayer(playerId);
        assertTrue(deletedPlayerRows == 1);
        Address addressDeleted = sportsTeamRepository.getAddress(player.getAddress().getId());
        assertNull(addressDeleted);
        int deletedTeamRows = sportsTeamRepository.deleteTeam(teamId);
        assertTrue(deletedTeamRows == 1);
        int deletedClubRows = sportsTeamRepository.deleteClub(clubId);
        assertTrue(deletedClubRows == 1);
    }

    @Test
    public void newPlayerTwoNew() {
        int clubId = sportsTeamRepository.createClub(new Club(null, "newSportsClub", "newBandy", "CK", "bandyStadium", null, "http://club.homepage.org"));
        int teamId = sportsTeamRepository.createTeam(TestData.createTeam("team name", clubId));
        Team team = sportsTeamRepository.getTeam(teamId);
        Address address = new Address("mystreet", "25", "c", "5039", "Bergen", "Norway");
        Player player = new Player(team, "firstName1", "middleName", "lastName1", "M", address);
        player.setDateOfBirth(new Date());
        player.setFkTeamId(teamId);
        int playerId1 = sportsTeamRepository.createPlayer(player);
        assertTrue(playerId1 > 0);

        Player player2 = new Player(team, "firstName2", "middleName", "lastName2", "M", address);
        player2.setDateOfBirth(new Date());
        player2.setFkTeamId(teamId);
        int playerId2 = sportsTeamRepository.createPlayer(player2);
        assertTrue(playerId2 > 0);
        assertTrue(playerId1 != playerId2);

        // Clean up
        int deletedPlayerRows = sportsTeamRepository.deletePlayer(playerId1);
        assertTrue(deletedPlayerRows == 1);
        deletedPlayerRows = sportsTeamRepository.deletePlayer(playerId2);
        assertTrue(deletedPlayerRows == 1);
        Address addressDeleted = sportsTeamRepository.getAddress(address.getId());
        assertNull(addressDeleted);
        int deletedTeamRows = sportsTeamRepository.deleteTeam(teamId);
        assertTrue(deletedTeamRows == 1);
        int deletedClubRows = sportsTeamRepository.deleteClub(clubId);
        assertTrue(deletedClubRows == 1);
    }

    @Test
    public void newRefereeWithoutAddress() {
        Referee newReferee = new Referee("firstName", "middleName", "lastName");
        newReferee.setMobileNumber("+4712345678");
        newReferee.setEmailAddress("new.referee@mail.com");
        newReferee.setGender("M");
        int refereeId = sportsTeamRepository.createReferee(newReferee);
        assertTrue(refereeId > 0);
    }

    @Test
    public void newReferee() {
        Address address = new Address("streetname", "25", "c", "2233", "city", "country");
        Referee newReferee = new Referee("firstName", "middleName", "lastName");
        newReferee.setAddress(address);
        newReferee.setMobileNumber("+4712345678");
        newReferee.setEmailAddress("new.referee@mail.com");
        newReferee.setGender("M");
        int refereeId = sportsTeamRepository.createReferee(newReferee);
        Referee referee = sportsTeamRepository.getReferee(refereeId);
        assertNotNull(referee);
        assertNull(referee.getClub());
        assertEquals("Firstname Middlename Lastname".toUpperCase(), referee.getFullName());
        assertEquals("M", referee.getGender());
        assertEquals("+4712345678", referee.getMobileNumber());
        assertEquals("new.referee@mail.com", referee.getEmailAddress());
        assertTrue(referee.getAddress().isAddressValid());
        assertNotNull(referee.getAddress().getId());
        assertEquals("Country".toUpperCase(), referee.getAddress().getCountry());
        assertEquals("Streetname 25C".toUpperCase(), referee.getAddress().getFullStreetName());
        assertEquals("2233", referee.getAddress().getPostCode());

        // update referee
        referee.setMobileNumber("77665544");
        referee.setEmailAddress("new@mail.org");
        sportsTeamRepository.updateReferee(referee);
        Referee updatedReferee = sportsTeamRepository.getReferee(refereeId);
        assertEquals("77665544", updatedReferee.getMobileNumber());
        assertEquals("new@mail.org", updatedReferee.getEmailAddress());

        // Must cleanup
        int addressId = referee.getAddress().getId();
        int deletedRefereeRows = sportsTeamRepository.deleteReferee(refereeId);
        assertTrue(deletedRefereeRows == 1);
        Address addressDeleted = sportsTeamRepository.getAddress(addressId);
        assertNull(addressDeleted);
    }

    @Test
    public void newRefereeTwoNew() {
        Address address = new Address("streetname", "25", "c", "3344", "city", "country");
        Referee newReferee1 = new Referee("firstName1", "middleName", "lastName1");
        newReferee1.setAddress(address);
        newReferee1.setMobileNumber("+4712345678");
        newReferee1.setEmailAddress("new.referee@mail.com");
        newReferee1.setGender("M");
        int refereeId1 = sportsTeamRepository.createReferee(newReferee1);
        assertTrue(refereeId1 > 0);

        Referee newReferee2 = new Referee("firstName2", "middleName", "lastName2");
        newReferee2.setAddress(address);
        newReferee2.setMobileNumber("+4712345678");
        newReferee2.setEmailAddress("new.referee@mail.com");
        newReferee2.setGender("M");
        int refereeId2 = sportsTeamRepository.createReferee(newReferee2);
        assertTrue(refereeId2 > 0);
        assertTrue(refereeId2 != refereeId1);

        // Must cleanup
        int deletedRefereeRows = sportsTeamRepository.deleteReferee(refereeId1);
        assertTrue(deletedRefereeRows == 1);
        deletedRefereeRows = sportsTeamRepository.deleteReferee(refereeId2);
        assertTrue(deletedRefereeRows == 1);
        Address addressDeleted = sportsTeamRepository.getAddress(address.getId());
        assertNull(addressDeleted);
    }

    @Test
    public void createTrainingsEveryWensdayfor14weeks() {
        int clubId = sportsTeamRepository.createClub(new Club(null, "newSportsClub", "newBandy", "CK", "bandyStadium", null, "http://club.homepage.org"));
        int teamId = sportsTeamRepository.createTeam(TestData.createTeam("team name", clubId));
        int numberOfRepetitions = 12;
        Season season = sportsTeamRepository.getCurrentSeason();
        assertEquals("2015-2016", season.getPeriod());
        DateTime dt = new DateTime(2015, 1, 7, 18, 30);
        DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
        System.out.println(fmt.print(dt));
        dt.dayOfWeek().getAsShortText();
        for (int i = 1; i <= numberOfRepetitions; i++) {
            dt = dt.plusDays(7);
            // System.out.println(dt.toString() + " " +
            // dt.dayOfWeek().getAsShortText() + " " + dt.getWeekOfWeekyear());
            Training newTraining1 = new Training(season, dt.toDate(), new Date(System.currentTimeMillis() + 360000), null, "place");
            newTraining1.setFkSeasonId(season.getId());
            newTraining1.setFkTeamId(teamId);
            int newTrainingId1 = sportsTeamRepository.createTraining(newTraining1);
            assertTrue(newTrainingId1 > 0);
        }
        List<Training> trainings = sportsTeamRepository.getTrainings(teamId, season.getId());
        assertEquals(numberOfRepetitions, trainings.size());
    }

    @Test
    public void newTraining() {
        Season season = sportsTeamRepository.getCurrentSeason();
        assertEquals("2015-2016", season.getPeriod());
        long startTime = System.currentTimeMillis();
        // new training 1
        Training newTraining1 = new Training(season, new Date(startTime), new Date(System.currentTimeMillis() + 360000), null, "place");
        newTraining1.setFkSeasonId(season.getId());
        int newTrainingId1 = sportsTeamRepository.createTraining(newTraining1);
        Training training = sportsTeamRepository.getTraining(newTrainingId1);
        assertNotNull(training.getId());
        assertNotNull(training.getStartDate());
        assertEquals(Utility.formatTime(startTime, "dd.MM.yyyy hh:mm"), Utility.formatTime(training.getStartDate().getTime(), "dd.MM.yyyy hh:mm"));
        assertNotNull(training.getEndDate());
        assertNotNull(training.getFkSeasonId());
        assertEquals("2015-2016", training.getSeason().getPeriod());
        assertEquals("place", training.getVenue());

        // training = sportsTeamRepository.getTrainingByDate(1, startTime);
        // assertNull(training);

        int clubId = sportsTeamRepository.createClub(new Club(null, "newSportsClub", "newBandy", "CK", "bandyStadium", null, "http://club.homepage.org"));
        int teamId = sportsTeamRepository.createTeam(TestData.createTeam("team name", clubId));
        Player newPlayer = new Player(null, "newPlayerFirstname", null, "newPlayerLastName", "M", null);
        newPlayer.setFkTeamId(teamId);
        int playerId = sportsTeamRepository.createPlayer(newPlayer);
        int lnkRows = sportsTeamRepository.createPlayerLink(PlayerLinkTableTypeEnum.TRAINING, playerId, training.getId());
        // FIXME
        // assertEquals(1, lnkRows);
        // Training training2 =
        // sportsTeamRepository.getTraining(newTrainingId1);
        // assertEquals(1, training2.getNumberOfSignedPlayers().intValue());
    }

    @Test
    public void newTrainingTwoNew() {
        Season season = sportsTeamRepository.getSeason("2014/2015");
        assertNotNull(season.getId());
        int clubId = sportsTeamRepository.createClub(new Club(null, "newSportsClub", "newBandy", "CK", "bandyStadium", null, "http://club.homepage.org"));
        int teamId = sportsTeamRepository.createTeam(TestData.createTeam("team name", clubId));
        Team team = sportsTeamRepository.getTeam(teamId);
        long startTime = System.currentTimeMillis();
        // new training 1
        Training newTraining1 = new Training(season, new Date(startTime), new Date(System.currentTimeMillis()), team, "place");
        newTraining1.setFkSeasonId(season.getId());
        int newTrainingId1 = sportsTeamRepository.createTraining(newTraining1);
        // new training 2
        Training newTraining2 = new Training(season, new Date(startTime + 3600000), new Date(System.currentTimeMillis()), team, "place");
        int newTrainingId2 = sportsTeamRepository.createTraining(newTraining2);
        assertTrue(newTrainingId2 > 0);
        assertTrue(newTrainingId2 != newTrainingId1);

        Training training = sportsTeamRepository.getTrainingByDate(team.getId(), startTime);
        assertNull(training);
        // assertEquals("2014/2015", training.getSeason().getPeriod());
        // assertEquals("place", training.getVenue());
        // // assertEquals(startTime, training.getStartTime());
        // assertEquals("team name", training.getTeam().getName());
        // assertEquals(0,
        // training.getNumberOfParticipatedPlayers().intValue());
        Player newplayer = new Player(team, "firstName", "middleName", "lastName", "M", null);
        newplayer.setDateOfBirth(new Date());
        newplayer.setFkTeamId(teamId);
        int playerId = sportsTeamRepository.createPlayer(newplayer);

        Player player = sportsTeamRepository.getPlayer(playerId);
        assertNotNull(player);
        // register player for training
        this.sportsTeamRepository.createPlayerLink(PlayerLinkTableTypeEnum.TRAINING, playerId, newTrainingId1);
        training = sportsTeamRepository.getTraining(newTrainingId1);
        assertEquals(1, training.getNumberOfSignedPlayers().intValue());

        // unregister player for training
        this.sportsTeamRepository.deletePlayerLink(PlayerLinkTableTypeEnum.TRAINING, playerId, newTrainingId1);
        training = sportsTeamRepository.getTraining(newTrainingId1);
        assertEquals(0, training.getNumberOfSignedPlayers().intValue());

        // Reasign player
        this.sportsTeamRepository.createPlayerLink(PlayerLinkTableTypeEnum.TRAINING, playerId, newTrainingId1);
        assertEquals(1, sportsTeamRepository.getNumberOfSignedPlayers(PlayerLinkTableTypeEnum.TRAINING.name(), newTrainingId1).intValue());

        // delete training
        int deletedTrainingRows = sportsTeamRepository.deleteTraining(newTrainingId1);
        assertTrue(deletedTrainingRows == 1);

        // check that team and player are not deleted upon training delete
        assertNotNull(sportsTeamRepository.getTeam(teamId));
        assertNotNull(sportsTeamRepository.getPlayer(playerId));
        assertEquals(0, sportsTeamRepository.getNumberOfSignedPlayers(PlayerLinkTableTypeEnum.TRAINING.name(), newTrainingId1).intValue());

        // Clean up after test case
        int deleteClubRows = sportsTeamRepository.deleteClub(clubId);
        assertTrue(deleteClubRows == 1);
        // int deletedTeamRows = sportsTeamRepository.deleteTeam(teamId);
        // assertTrue(deletedTeamRows == 1);
        // int deletedPlayerRows = sportsTeamRepository.deletePlayer(playerId);
        // assertTrue(deletedPlayerRows == 1);
    }

    @Test
    public void getMatchStatus() {
        Status status = sportsTeamRepository.getMatchStatus(2);
        assertEquals(2, status.getId().intValue());
        assertEquals("PLAYED", status.getName());
    }

    @Test
    public void getMatchListByLeague() {
        assertNotNull(sportsTeamRepository.getMatchListByLeague(1, 2));
    }

    @Test
    public void getMatchListByTeamName() {
        List<Match> matchListByLeague = sportsTeamRepository.getMatchListByTeamName("Ullevål", 22, 2);
        assertNotNull(matchListByLeague);
        assertEquals(0, matchListByLeague.size());
    }

    @Test
    public void getMatchListForClub() {
        List<Match> matchList = sportsTeamRepository.getMatchListForClub(210, 1);
        assertNotNull(matchList);
        assertEquals(0, matchList.size());
    }

    @Test
    public void newMatchWithoutTeam() {
        Season season = sportsTeamRepository.getSeason("2013/2014");
        // Match 1
        Match newMatch1 = new Match(season, new Date(System.currentTimeMillis() - TestConstants.DAY_MS), null, new Team("homeTeam"), new Team("awayTeam"), "home-venue", null);
        newMatch1.setFkLeagueId(2);
        newMatch1.setFkSeasonId(season.getId());
        newMatch1.setMatchStatus(sportsTeamRepository.getMatchStatus("NOT PLAYED"));
        int matchId = sportsTeamRepository.createMatch(newMatch1);
        assertTrue(matchId > 0);
        Match match = sportsTeamRepository.getMatch(matchId);
        assertNotNull(match);
        assertNull(match.getTeam());
        // assertNull(match.getFkTeamId());
        assertNotNull(match.getFkLeagueId());
        assertEquals("homeTeam - awayTeam", match.getTeamVersus());

    }

    @Test
    public void newMatchDynamicUpdates() {
        int clubId = sportsTeamRepository.createClub(new Club(null, "club4", "football", "CK", "bandyStadium", null, "http://club.homepage.org"));
        int teamId = sportsTeamRepository.createTeam(TestData.createTeam("team1", clubId));
        Season season = sportsTeamRepository.getSeason("2013/2014");
        // Match 1
        Match newMatch1 = new Match(season, new Date(System.currentTimeMillis() - TestConstants.DAY_MS), null, new Team("homeTeam"), new Team("awayTeam"), "home-venue", null);
        newMatch1.setFkLeagueId(2);
        newMatch1.setFkTeamId(teamId);
        newMatch1.setFkSeasonId(season.getId());
        newMatch1.setMatchStatus(sportsTeamRepository.getMatchStatus("NOT PLAYED"));
        int matchId1 = sportsTeamRepository.createMatch(newMatch1);

        // Match 2
        Match newMatch2 = new Match(season, new Date(System.currentTimeMillis() + TestConstants.DAY_MS), null, new Team("awayTeam"), new Team("homeTeam"), "away-venue",
                new Referee("referee-firstname", null, "referee-lastname"));
        newMatch2.setFkLeagueId(2);
        newMatch2.setFkTeamId(teamId);
        newMatch2.setFkSeasonId(season.getId());
        newMatch2.setMatchStatus(sportsTeamRepository.getMatchStatus("NOT PLAYED"));
        int matchId2 = sportsTeamRepository.createMatch(newMatch2);
        assertTrue(matchId2 > 0);
        assertTrue(matchId1 != matchId2);

        Match match = sportsTeamRepository.getMatch(matchId1);
        assertNotNull(match.getId());
        assertNotNull(match.getFkLeagueId());
        assertNotNull(match.getFkSeasonId());
        assertNotNull(match.getLeague().getLeagueCategory());
        assertEquals("2013-2014", match.getSeason().getPeriod());
        assertEquals("team1", match.getTeam().getName());
        assertEquals("homeTeam - awayTeam", match.getTeamVersus());
        assertEquals("NOT PLAYED", match.getMatchStatus().getName());
        // assertFalse(match.isPlayed());
        assertEquals(MatchTypesEnum.LEAGUE, match.getMatchType());
        assertEquals("0 - 0", match.getResult());
        assertNull(match.getReferee());
        assertEquals(0, match.getNumberOfSignedPlayers().intValue());
        assertEquals(0, match.getNumberOfGoalsHome().intValue());
        assertEquals(0, match.getNumberOfGoalsAway().intValue());
        assertEquals("0 - 0", match.getResult());
        assertEquals(0, match.getNumberOfSignedPlayers().intValue());

        // Registrer referee
        Referee referee = new Referee("rfirstName", "rmiddleName", "rlastName");
        referee.setGender("M");
        referee.setMobileNumber("+4711223344");
        referee.setEmailAddress("referee@mail.com");
        int refereeId = sportsTeamRepository.createReferee(referee);
        sportsTeamRepository.registrerRefereeForMatch(refereeId, matchId1);
        match = sportsTeamRepository.getMatch(matchId1);
        assertEquals("Rfirstname Rmiddlename Rlastname".toUpperCase(), match.getReferee().getFullName());

        // Registrer player
        Player player = new Player(null, "pfirstName", "pmiddleName", "plastName", "M", null);
        player.setDateOfBirth(new Date());
        player.setFkTeamId(teamId);
        int playerId = sportsTeamRepository.createPlayer(player);
        sportsTeamRepository.createPlayerLink(PlayerLinkTableTypeEnum.MATCH, playerId, matchId1);
        match = sportsTeamRepository.getMatch(matchId1);
        assertEquals(1, match.getNumberOfSignedPlayers().intValue());

        // Unregistrer player
        int deletePlayerLinkRows = sportsTeamRepository.deletePlayerLink(PlayerLinkTableTypeEnum.MATCH, playerId, matchId1);
        assertEquals(1, deletePlayerLinkRows);
        match = sportsTeamRepository.getMatch(matchId1);
        assertEquals(0, match.getNumberOfSignedPlayers().intValue());

        // Then recreate player link in order to verify that it is removed upon
        // match delete at end of this test
        sportsTeamRepository.createPlayerLink(PlayerLinkTableTypeEnum.MATCH, playerId, matchId1);

        // Match update score
        sportsTeamRepository.updateGoals(matchId1, 2, true);
        sportsTeamRepository.createMatchEvent(new MatchEvent(matchId1, 23, "newTeam", "player hometeam", MatchEventTypesEnum.GOAL_HOME.toString(), "2"));
        sportsTeamRepository.updateGoals(matchId1, 3, false);
        sportsTeamRepository.createMatchEvent(new MatchEvent(matchId1, 43, "newTeam", "player awayteam", MatchEventTypesEnum.GOAL_AWAY.toString(), "3"));

        match = sportsTeamRepository.getMatch(matchId1);
        assertEquals("2 - 3", match.getResult());

        List<MatchEvent> matchEventList = sportsTeamRepository.getMatchEventList(matchId1);
        assertEquals(2, matchEventList.size());
        assertEquals(matchId1, matchEventList.get(0).getFkMatchId());
        assertEquals("newTeam", matchEventList.get(0).getTeamName());
        assertEquals("player hometeam", matchEventList.get(0).getPlayerName());
        assertEquals(MatchEventTypesEnum.GOAL_HOME.name(), matchEventList.get(0).getMatchEventTypeName());
        assertEquals(23, matchEventList.get(0).getPlayedMinutes().intValue());
        assertEquals("2", matchEventList.get(0).getValue());

        assertEquals(matchId1, matchEventList.get(1).getFkMatchId());
        assertEquals("newTeam", matchEventList.get(1).getTeamName());
        assertEquals("player awayteam", matchEventList.get(1).getPlayerName());
        assertEquals(MatchEventTypesEnum.GOAL_AWAY.name(), matchEventList.get(1).getMatchEventTypeName());
        assertEquals(43, matchEventList.get(1).getPlayedMinutes().intValue());
        assertEquals("3", matchEventList.get(1).getValue());

        int updateDMatchStatusRows = sportsTeamRepository.updateMatchStatus(matchId1, 2);
        assertTrue(updateDMatchStatusRows == 1);
        match = sportsTeamRepository.getMatch(matchId1);
        assertEquals("PLAYED", match.getMatchStatus().getName());

        // Clean up
        // int deletedMatchEventsRows =
        // sportsTeamRepository.deleteMatchEvents(matchId);
        // assertTrue(deletedMatchEventsRows == 2);
        int deletedMatchRows = sportsTeamRepository.deleteMatch(matchId1);
        // Should delete, match, match events, and player link
        assertEquals(1, deletedMatchRows);
        deletedMatchRows = sportsTeamRepository.deleteMatch(matchId2);
        assertEquals(1, deletedMatchRows);

        // Check that team and player not are deleted upon match delete
        assertNotNull(sportsTeamRepository.getTeam(teamId));
        assertNotNull(sportsTeamRepository.getPlayer(playerId));

        // int deletedPlayerRows = sportsTeamRepository.deletePlayer(playerId);
        // assertTrue(deletedPlayerRows == 1);
        // int deletedRefereeRows =
        sportsTeamRepository.deleteReferee(refereeId);
        // assertTrue(deletedRefereeRows == 1);
        // int deletedTeamRows = sportsTeamRepository.deleteTeam(teamId);
        // assertTrue(deletedTeamRows == 1);
        // Everything will be deleted upon club delete
        int deletedClubRows = sportsTeamRepository.deleteClub(clubId);
        assertTrue(deletedClubRows == 1);
    }

    @Test
    public void getSeason() {
        Season season = sportsTeamRepository.getSeason(1);
        assertEquals("2013-2014", season.getPeriod());
        season = sportsTeamRepository.getSeason("2014-2015");
        assertEquals("2014-2015", season.getPeriod());
    }

    /**
     * FIXME, fails on hudson for some unknown reason
     */
    @Ignore
    @Test
    public void newCup() {
        Season season = sportsTeamRepository.getSeason("2014/2015");
        Cup newCup = new Cup(null, new Date(), "Cup Name", "arranging club name", "cup-venue", new Date());
        newCup.setFkSeasonId(season.getId());
        Calendar cupStartDate = Calendar.getInstance();
        cupStartDate.set(Calendar.YEAR, 2014);
        cupStartDate.set(Calendar.MONTH, 2);
        cupStartDate.set(Calendar.DAY_OF_MONTH, 27);
        newCup.setStartDate(cupStartDate.getTime());
        int cupId = sportsTeamRepository.createCup(newCup);
        assertTrue(cupId > 0);
        Cup cup = sportsTeamRepository.getCup(cupId);
        assertNotNull(cup);
        assertNotNull(cup.getFkSeasonId());
        assertEquals("2014-2015", cup.getSeason().getPeriod());
        assertEquals("Cup Name", cup.getCupName());
        assertEquals("arranging club name", cup.getClubName());
        assertEquals("cup-venue", cup.getVenue());
        assertNotNull(cup.getStartDate());
        assertNotNull(cup.getDeadlineDate());
        assertEquals(0, cup.getNumberOfSignedPlayers().intValue());
        assertEquals(0, cup.getNumberOfRegistreredTeams().intValue());

        int clubId = sportsTeamRepository.createClub(new Club(null, "newSportsClub", "newBandy", "CK", "bandyStadium", null, "http://club.homepage.org"));
        int teamId = sportsTeamRepository.createTeam(TestData.createTeam("team name", clubId));
        Team team = sportsTeamRepository.getTeam(teamId);
        Match cupMatch = Match.createCupMatch();
        cupMatch.setFkSeasonId(season.getId());
        cupMatch.setStartDate(new Date());
        cupMatch.setTeam(team);
        cupMatch.setFkTeamId(team.getId());
        cupMatch.setHomeTeam(new Team("home-team"));
        cupMatch.setAwayTeam(new Team("away-team"));
        cupMatch.setVenue("venue");
        int matchId = sportsTeamRepository.createMatch(cupMatch);
        int createCupMatchLnk = sportsTeamRepository.createCupMatchLnk(cupId, matchId);
        assertTrue(createCupMatchLnk > -1);

        // registrer team for cup
        int teamCuplinkId = sportsTeamRepository.createTeamCupLink(teamId, cupId);
        assertTrue(teamCuplinkId > 0);
        int numberOfRegistreredTeams = sportsTeamRepository.getNumberOfRegistreredTeamsForCup(cupId);
        assertEquals(1, numberOfRegistreredTeams);

        List<Item> registreredTeamsForCup = sportsTeamRepository.getCupTeamList(cupId);
        assertEquals(1, registreredTeamsForCup.size());
        assertEquals("team name ", registreredTeamsForCup.get(0).getValue());

        // unregistrer team for cup
        int deleteTeamCuplink = sportsTeamRepository.deleteTeamCupLink(teamId, cupId);
        assertEquals(1, deleteTeamCuplink);
        numberOfRegistreredTeams = sportsTeamRepository.getNumberOfRegistreredTeamsForCup(cupId);
        assertEquals(0, numberOfRegistreredTeams);

        Player newPlayer = new Player(team, "newPlayerFirstname", null, "newPlayerLastName", "M", null);
        newPlayer.setFkTeamId(team.getId());
        int playerId = sportsTeamRepository.createPlayer(newPlayer);
        int linkId = sportsTeamRepository.createPlayerLink(PlayerLinkTableTypeEnum.CUP, playerId, cup.getId());
        assertTrue(linkId > 0);
        cup = sportsTeamRepository.getCup(cup.getId());
        assertEquals(1, cup.getNumberOfSignedPlayers().intValue());

        assertEquals("27.03.2014", Utility.formatTime(cup.getStartDate().getTime(), "dd.MM.YYYY"));
        Calendar newStartDate = Calendar.getInstance();
        newStartDate.set(Calendar.YEAR, 2014);
        newStartDate.set(Calendar.MONTH, 3);
        newStartDate.set(Calendar.DAY_OF_MONTH, 23);
        cup.setStartDate(newStartDate.getTime());
        sportsTeamRepository.updateCup(cup);
        cup = sportsTeamRepository.getCup(cup.getId());
        assertEquals("23.04.2014", Utility.formatTime(cup.getStartDate().getTime(), "dd.MM.YYYY"));

        int deletedCupRows = sportsTeamRepository.deleteCup(cupId);
        assertTrue(deletedCupRows == 1);
    }

    // @Test
    // public void newTournament() {
    // Season season = sportsTeamRepository.getSeason("2014/2015");
    // assertNotNull(season.getId());
    // Tournament newTournament = new Tournament(season,
    // System.currentTimeMillis(), "Tournament Name", "Organizer club name",
    // "place",
    // System.currentTimeMillis());
    // int tournamentId = sportsTeamRepository.createTournament(newTournament);
    // Tournament tournament = sportsTeamRepository.getTournament(tournamentId);
    // assertEquals("Tournament Name", tournament.getTournamentName());
    // assertEquals("Organizer club name", tournament.getOrganizerName());
    // assertEquals("place", tournament.getVenue());
    //
    // int clubId = sportsTeamRepository.createClub(new Club(null,
    // "newSportsClub", "newBandy", "CK", "bandyStadium", null,
    // "http://club.homepage.org"));
    // Club club = sportsTeamRepository.getClub(clubId);
    // Integer teamId = sportsTeamRepository.createTeam(new Team("team name",
    // club, 2004, "Male"));
    // assertTrue(teamId.intValue() > 0);
    //
    // List<Tournament> list = sportsTeamRepository.getTournaments(teamId,
    // 2014);
    // assertEquals(0, list.size());
    // sportsTeamRepository.tournamentRegistration(tournamentId, teamId);
    // List<Tournament> tournaments =
    // sportsTeamRepository.getTournaments(teamId, 2014);
    // assertEquals(1, tournaments.size());
    // assertEquals("Tournament Name", tournaments.get(0).getTournamentName());
    // int tournamentUnRegistrationRows =
    // sportsTeamRepository.tournamentUnRegistration(tournamentId, teamId);
    // assertEquals(1, tournamentUnRegistrationRows);
    // tournaments = sportsTeamRepository.getTournaments(teamId, 2014);
    // assertEquals(0, tournaments.size());
    //
    // int deletedTournamentRows =
    // sportsTeamRepository.deleteTournament(tournamentId);
    // assertTrue(deletedTournamentRows == 1);
    // }

    @Test
    public void addActivityToSeason() {
        Season season = sportsTeamRepository.getSeason("2014/2015");
        Assert.assertNotNull(season);
        Assert.assertNotNull(season.getId());
        int clubId = sportsTeamRepository.createClub(new Club(null, "newSportsClub", "newBandy", "CK", "bandyStadium", null, "http://club.homepage.org"));
        int teamId = sportsTeamRepository.createTeam(TestData.createTeam("team name", clubId));
        Match newMatch = new Match();
        newMatch.setFkSeasonId(season.getId());
        newMatch.setStartDate(new Date(System.currentTimeMillis() - 60000));
        newMatch.setFkTeamId(teamId);
        newMatch.setHomeTeam(new Team("homeTeam"));
        newMatch.setAwayTeam(new Team("awayTeam"));
        newMatch.setVenue("venue");
        newMatch.setMatchStatus(sportsTeamRepository.getMatchStatus("NOT PLAYED"));
        sportsTeamRepository.createMatch(newMatch);

        // Training newTraining = new Training(season,
        // System.currentTimeMillis(), System.currentTimeMillis(), team,
        // "place");
        // sportsTeamRepository.createTraining(newTraining);
        // FIXME
    }

    @Test
    public void getLeague() {
        League league = sportsTeamRepository.getLeague(2);
        assertNotNull(league.getName());
        assertNotNull(league.getStatus().getId());
        assertNotNull(league.getLeagueCategory());
        assertEquals("ACTIVE", league.getStatus().getName());
    }

    @Test
    public void getLeagueCategory() {
        LeagueCategory leagueCategory = sportsTeamRepository.getLeagueCategory(2);
        assertNotNull(leagueCategory.getName());
        assertNotNull(leagueCategory.getStatus().getId());
        assertEquals("ACTIVE", leagueCategory.getStatus().getName());
        assertEquals("BANDY", leagueCategory.getSportType());
        assertNotNull(leagueCategory.getLeagueRule());
    }

    @Test
    public void getLeagueRule() {
        LeagueRule leagueRule = sportsTeamRepository.getLeagueRule(2);
        assertNotNull(leagueRule);
        assertNotNull(leagueRule.getDescription());
        assertEquals("rule Lillegutt", leagueRule.getName());
        assertEquals("male", leagueRule.getGender());
        assertEquals(0, leagueRule.getMatchExtraPeriodTimeMinutes().intValue());
        assertEquals(7, leagueRule.getNumberOfPlayers().intValue());
        assertEquals(0, leagueRule.getNumberOfSubstitutes().intValue());
        assertEquals(25, leagueRule.getMatchPeriodTimeMinutes().intValue());
        assertEquals(0, leagueRule.getMatchExtraPeriodTimeMinutes().intValue());
        assertEquals(13, leagueRule.getPlayerAgeMax().intValue());
        assertEquals(12, leagueRule.getPlayerAgeMin().intValue());

    }

    @Test
    @Ignore
    // H2 db sql syntax problem
    public void leagueTable() {
        Statistic statistic = sportsTeamRepository.getLeagueTable(22, 2);
        assertNotNull(statistic);
        assertEquals(0, statistic.getMatchStatisticList().size());
    }

    @Test
    public void deletePlayerLinkNotExisting() {
        int rows = sportsTeamRepository.deletePlayerLink(PlayerLinkTableTypeEnum.MATCH, 2, 4);
        assertTrue(rows == 0);
    }

    @Test(expected = ApplicationException.class)
    public void createPlayerLinkDuplicate() {
        sportsTeamRepository.createPlayerLink(PlayerLinkTableTypeEnum.MATCH, 2, 4);
        sportsTeamRepository.createPlayerLink(PlayerLinkTableTypeEnum.MATCH, 2, 4);
    }

    @Test
    public void getCurrentSeason() {
        Season currentSeason = sportsTeamRepository.getCurrentSeason();
        assertNotNull(currentSeason);
        assertNotNull(currentSeason.getId());
        assertNotNull(currentSeason.getStartTime());
        assertNotNull(currentSeason.getEndTime());
    }

    @Test
    public void createTeamContactPersons() {
        int clubId = sportsTeamRepository.createClub(new Club(null, "newSportsClub", "newBandy", "CK", "bandyStadium", null, "http://club.homepage.org"));
        int teamId = sportsTeamRepository.createTeam(TestData.createTeam("myTeam", clubId));
        Contact contact = new Contact(null, null, "teamLead", "middleName", "lastName", "M", "11111111", "p1@email.no", null);
        contact.setFkTeamId(teamId);
        int contactId = sportsTeamRepository.createContact(contact);

        sportsTeamRepository.createContactRoleTypeLnk(contactId, Role.RoleTypesEnum.TEAMLEAD.name());
        Contact teamlead = sportsTeamRepository.getTeamContactPerson(teamId, Role.RoleTypesEnum.TEAMLEAD.name());
        assertNotNull(teamlead);
        assertEquals("teamlead".toUpperCase(), teamlead.getFirstName());

        sportsTeamRepository.deleteContactRoleTypeLnk(contactId, Role.RoleTypesEnum.TEAMLEAD.name());
        teamlead = sportsTeamRepository.getTeamContactPerson(teamId, Role.RoleTypesEnum.TEAMLEAD.name());
        assertNull(teamlead);

        Contact contact2 = sportsTeamRepository.getContact(contactId);
        assertNotNull(contact2);
    }

    @Test
    public void getPlayerStatus() {
        List<Type> playerStatusTypes = sportsTeamRepository.getPlayerStatusTypes();
        Status status = sportsTeamRepository.getPlayerStatus(playerStatusTypes.get(0).getId());
        assertNotNull(status);
        assertNotNull(status.getId());
        assertNotNull(status.getName());
    }

    @Test
    public void searchClubs() {
        List<Club> clubs = sportsTeamRepository.searchClubs("not existing club name", "*");
        assertTrue(clubs.size() == 0);
    }

    @Test
    public void searchTeams() {
        List<Team> teams = sportsTeamRepository.searchTeams(null, "not existing team name", null);
        assertTrue(teams.size() == 0);
        teams = sportsTeamRepository.searchTeams(1, "not existing team name", null);
        assertTrue(teams.size() == 0);
        teams = sportsTeamRepository.searchTeams(1, "not existing team name", 4);
        assertTrue(teams.size() == 0);
        // teams = sportsTeamRepository.searchTeams(null, "ullevål", null);
        // assertEquals(1,teams.size());
    }

    @Test
    public void findMatch() {
        Match match = sportsTeamRepository.findMatch(new Date(), "home team name", "away team name");
        assertNull(match);
    }

    @Test
    public void getContactStatus() {
        List<Type> contactStatusTypes = sportsTeamRepository.getContactStatusTypes();
        Status status = sportsTeamRepository.getContactStatus(contactStatusTypes.get(0).getId());
        assertNotNull(status);
        assertNotNull(status.getId());
        assertNotNull(status.getName());
    }

    @Test
    public void createSeason() {
        Season season = new Season();
        season.setStartTime(System.currentTimeMillis());
        season.setEndTime(DateUtils.addMonths(new Date(), 6).getTime());
        int id = sportsTeamRepository.createSeason(season);
        assertTrue(id > 0);
    }

    @Test
    public void createContactRole() {
        assertNull(sportsTeamRepository.getContactRoleTypeLnkId(1, Role.RoleTypesEnum.TEAMLEAD.name()));
        Contact teamlead = new Contact(null, null, "teamLead", "middleName", "lastName", "M", "11111111", "p1@email.no", null);
        int contactId = sportsTeamRepository.createContact(teamlead);
        assertTrue(sportsTeamRepository.createContactRoleTypeLnk(contactId, Role.RoleTypesEnum.TEAMLEAD.name()) > 0);
        assertNotNull(sportsTeamRepository.getContactRoleTypeLnkId(contactId, Role.RoleTypesEnum.TEAMLEAD.name()));
        assertEquals(1, sportsTeamRepository.updateContactRoleTypeLnk(contactId, Role.RoleTypesEnum.TEAMLEAD.name(), Role.RoleTypesEnum.COACH.name()));
        assertNotNull(sportsTeamRepository.getContactRoleTypeLnkId(contactId, Role.RoleTypesEnum.COACH.name()));
        assertTrue(sportsTeamRepository.createContactRoleTypeLnk(contactId, Role.RoleTypesEnum.COACH.name()) == 0);
        assertTrue(sportsTeamRepository.deleteContactRoleTypeLnk(contactId, Role.RoleTypesEnum.COACH.name()) == 1);
        assertNull(sportsTeamRepository.getContactRoleTypeLnkId(contactId, Role.RoleTypesEnum.COACH.name()));
    }

    @Test
    // @Ignore
    public void createVolunteerTask() {
        int clubId = sportsTeamRepository.createClub(new Club(null, "newSportsClub", "newBandy", "CK", "bandyStadium", null, "http://club.homepage.org"));
        int teamId = sportsTeamRepository.createTeam(TestData.createTeam("myTeam", clubId));
        Contact contact = new Contact("per", null, "anker");
        contact.setFkTeamId(teamId);
        contact.setMobile("12345678");
        contact.setEmail("cail.com");
        int contactId = sportsTeamRepository.createContact(contact);
        // create new task
        VolunteerTask newVolunteerTask = new VolunteerTask();
        newVolunteerTask.setStartDate(new Date());
        newVolunteerTask.setEndDate(new Date());
        newVolunteerTask.setDeadlineDate(new Date());
        newVolunteerTask.setFkClubId(clubId);
        newVolunteerTask.setFkSeasonId(1);
        newVolunteerTask.setFkTaskStatusId(1);
        newVolunteerTask.setTaskName("Kioskvakt");
        newVolunteerTask.setDescription("kioskvakt på klubbhuset");
        int volunteerTaskId = sportsTeamRepository.createVolunteerTask(newVolunteerTask);
        // Check task
        VolunteerTask volunteerTask = sportsTeamRepository.getVolunteerTask(volunteerTaskId);
        assertEquals("newSportsClub newBandy", volunteerTask.getClub().getFullName());
        assertNotNull(volunteerTask.getStartDate());
        assertNotNull(volunteerTask.getEndDate());
        assertNotNull(volunteerTask.getDeadlineDate());
        assertEquals(1, volunteerTask.getFkTaskStatusId().intValue());
        assertEquals("OPEN", volunteerTask.getStatus().getName());
        assertEquals(1, volunteerTask.getFkSeasonId().intValue());
        assertNotNull(volunteerTask.getSeason());
        assertEquals(clubId, volunteerTask.getFkClubId().intValue());
        assertNotNull(volunteerTask.getClub());
        assertEquals("Kioskvakt", volunteerTask.getName());
        assertEquals("kioskvakt på klubbhuset", volunteerTask.getDescription());

        // Update volunteerTask
        VolunteerTask updatedTask = sportsTeamRepository.getVolunteerTask(volunteerTaskId);
        updatedTask.setTaskName("kioskvakt 2003");
        int updateVolunteerTaskRows = sportsTeamRepository.updateVolunteerTask(updatedTask);
        assertTrue(updateVolunteerTaskRows == 1);
        updatedTask = sportsTeamRepository.getVolunteerTask(volunteerTaskId);
        assertEquals("kioskvakt 2003", updatedTask.getName());

        // Assign task to person
        updatedTask.setFkAssignedToPersonId(contactId);
        updateVolunteerTaskRows = sportsTeamRepository.updateVolunteerTask(updatedTask);
        assertTrue(updateVolunteerTaskRows == 1);
        VolunteerTask assignedTask = sportsTeamRepository.getVolunteerTask(volunteerTaskId);
        assertEquals(contactId, assignedTask.getFkAssignedToPersonId().intValue());
        assertEquals("PER ANKER", assignedTask.getAssignee().getFullName());

        Integer assignContactToTask = sportsTeamRepository.assignContactToTask(assignedTask.getId(), contactId);
        assertTrue(assignContactToTask == 1);
        Integer unsignContactFromTask = sportsTeamRepository.unsignContactFromTask(assignedTask.getId(), contactId);
        assertTrue(unsignContactFromTask == 1);

        // Create sub task
        SubTask newSubTask = new SubTask();
        newSubTask.setStartDate(new Date());
        newSubTask.setEndDate(new Date());
        newSubTask.setDeadlineDate(new Date());
        newSubTask.setFkParentTaskId(newVolunteerTask.getId());
        newSubTask.setFkTaskStatusId(1);
        newSubTask.setTaskName("Vakt 1");
        newSubTask.setDescription("Vakt lørdag fra 1200 -1600");
        newSubTask.setFkParentTaskId(volunteerTask.getId());
        int subTaskId = sportsTeamRepository.createSubTask(newSubTask);
        // Check sub task
        SubTask subTask = sportsTeamRepository.getSubTask(subTaskId);
        assertEquals(volunteerTask.getId().intValue(), subTask.getFkParentTaskId().intValue());
        assertEquals("kioskvakt 2003", subTask.getParentTask().getTaskName());
        assertNotNull(subTask.getStartDate());
        assertNotNull(subTask.getEndDate());
        assertNotNull(subTask.getDeadlineDate());
        assertEquals(1, subTask.getFkTaskStatusId().intValue());
        assertEquals("OPEN", subTask.getStatus().getName());
        assertEquals("Vakt 1", subTask.getName());
        assertEquals("Vakt lørdag fra 1200 -1600", subTask.getDescription());

        // Update and assign sub task to person
        subTask.setFkAssignedToPersonId(contactId);
        subTask.setTaskName("DagVakt 1");
        subTask.setDescription("Vakt mandag fra 1200 - 1600");
        updateSubTaskRows = sportsTeamRepository.updateSubTask(subTask);
        assertTrue(updateSubTaskRows == 1);
        subTask = sportsTeamRepository.getSubTask(subTaskId);
        assertEquals("DagVakt 1", subTask.getName());
        assertEquals("Vakt mandag fra 1200 - 1600", subTask.getDescription());
        assertEquals(contactId, subTask.getFkAssignedToPersonId().intValue());
        assertEquals("PER ANKER", subTask.getAssignee().getFullName());

        int assignContactToSubTask = sportsTeamRepository.assignContactToSubTask(subTaskId, contactId);
        assertTrue(assignContactToSubTask == 1);

        // check that sub task is assigen to parent task
        VolunteerTask volunteerTaskAssigned = sportsTeamRepository.getVolunteerTask(volunteerTaskId);
        assertNotNull(volunteerTaskAssigned);
        assertEquals(1, volunteerTaskAssigned.getSubTaskList().size());
        assertEquals("DagVakt 1", volunteerTaskAssigned.getSubTaskList().get(0).getName());
        assertTrue(volunteerTaskAssigned.getSubTaskList().get(0).isAssigned());
        assertEquals("PER ANKER", volunteerTaskAssigned.getSubTaskList().get(0).getAssignee().getFullName());

        // unassign sub task
        int unsignContactFromSubTask = sportsTeamRepository.unsignContactFromSubTask(subTaskId, contactId);
        assertTrue(unsignContactFromSubTask == 1);

        // Create task group
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.setName("Week 45");
        taskGroup.setDescription("All tasks for week 45");
        int taskGroupId = sportsTeamRepository.createTaskGroup(taskGroup);
        // Check task group
        TaskGroup taskGroupNew = sportsTeamRepository.getTaskGroup(taskGroupId);
        assertEquals("Week 45", taskGroupNew.getName());
        assertEquals("All tasks for week 45", taskGroupNew.getDescription());
        assertTrue(taskGroupNew.getSubTaskList().size() == 0);

        // Add sub task to task group
        int addSubTaskToGroupId = sportsTeamRepository.addSubTaskToGroup(subTaskId, taskGroupId);
        assertTrue(addSubTaskToGroupId > 0);
        taskGroupNew = sportsTeamRepository.getTaskGroup(taskGroupId);
        assertEquals("DagVakt 1", taskGroupNew.getSubTaskList().get(0).getName());

        // Remove sub task from task group
        int removedRows = sportsTeamRepository.removeSubTaskFromGroup(subTaskId, taskGroupId);
        assertTrue(removedRows == 1);
        taskGroupNew = sportsTeamRepository.getTaskGroup(taskGroupId);
        assertTrue(taskGroupNew.getSubTaskList().size() == 0);

        // Get sub task in task group

        // Asign task to team
        // int assignTaskToTeam =
        // sportsTeamRepository.assignTeamToSubTask(subTask.getId(), teamId);
        // assertTrue(assignTaskToTeam > 0);
        //
        // // Asign sub task to person (contact)
        // Contact contact = new Contact(null, null, "teamLead", "middleName",
        // "lastName", "M", "11111111", "p1@email.no", null);
        // contact.setFkTeamId(teamId);
        // int contactId = sportsTeamRepository.createContact(contact);
        // assertTrue(contactId > 0);
        // int assignSubTaskToContact =
        // sportsTeamRepository.assignContactToSubTask(subTask.getId(),
        // contactId);
        // assertTrue(assignSubTaskToContact > 0);
        //
        // // get volunteertask
        // VolunteerTask task =
        // sportsTeamRepository.getVolunteerTask(volunteerTaskId);
        // assertEquals(1, task.getTaskList().size());
        // assertEquals("Vakt 1", task.getTaskList().get(0).getTaskName());
        //
        // // Unassign
        // int unsignSubTaskToContact =
        // sportsTeamRepository.unsignContactFromSubTask(subTask.getId(),
        // contactId);
        // assertTrue(unsignSubTaskToContact == 1);
        // int unsignTaskFromTeam =
        // sportsTeamRepository.unsignTeamFromSubTask(subTask.getId(), teamId);
        // assertTrue(unsignTaskFromTeam == 1);

        // Set task status to finished
        // int rows = sportsTeamRepository.updateVolunteerTask(task);
    }

    @Test
    public void newUser() {
        User newUser = new User("test-username", "user-pwd", "user.mail@mail.com");
        List<String> roles = new ArrayList<String>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");
        newUser.setRoles(roles);
        int id = sportsTeamRepository.createUser(newUser);
        User user = sportsTeamRepository.getUser("test-username");
        assertNotNull(user.getId());
        assertEquals("test-username", user.getUserName());
        assertEquals("user-pwd", user.getPassword());
        assertEquals("user.mail@mail.com", user.getEmail());
        assertTrue(user.isActivated());
        assertEquals("[ROLE_USER, ROLE_ADMIN]", user.getRoles().toString());

        user.setPassword("changed-pwd");
        user.setActivated(false);
        sportsTeamRepository.changeUserPwd(user);
        user = sportsTeamRepository.getUser("test-username");
        assertEquals("test-username", user.getUserName());
        assertEquals("changed-pwd", user.getPassword());
        assertFalse(user.isActivated());

        int deleteUserRows = sportsTeamRepository.deleteUser(id);
        assertTrue(deleteUserRows == 1);
    }

    @Test
    public void adminUser() {
        User user = sportsTeamRepository.getUser("admin");
        assertEquals("admin", user.getUserName());
        assertTrue(user.isActivated());
        assertTrue(user.isAdmin());
        assertFalse(user.isUser());
        assertFalse(user.isGuest());
    }

    @Test
    public void listSeasons() {
        List<Season> seasons = sportsTeamRepository.getSeasons();
        assertEquals(4, seasons.size());
    }

    @Test
    public void deleteSeasons() {
        int r = sportsTeamRepository.deleteSeason(222);
        assertEquals(0, r);
    }

    @Test
    public void listUsers() {
        List<User> users = sportsTeamRepository.getUsers();
        assertEquals(3, users.size());
        assertEquals(1, users.get(0).getRoles().size());
    }

    @Test
    public void getTeamScheduledTasks() {
        List<ScheduledTask> tasks = sportsTeamRepository.getTeamScheduledTasks();
        assertEquals(1, tasks.size());
        assertEquals("UIL 2003", tasks.get(0).getName());
        assertTrue(tasks.get(0).isEnabled());
        assertNotNull(tasks.get(0).getId());
        assertNotNull(tasks.get(0).getTeamId());
    }

    @Test
    public void enableTeamScheduledTasks() {
        List<ScheduledTask> tasks = sportsTeamRepository.getTeamScheduledTasks();
        assertEquals(1, tasks.size());
        assertEquals("UIL 2003", tasks.get(0).getName());
        assertTrue(tasks.get(0).isEnabled());
        assertNotNull(tasks.get(0).getId());
        assertNotNull(tasks.get(0).getTeamId());
        Integer updatedRows = sportsTeamRepository.enableScheduledTask(tasks.get(0).getId(), 0);
        assertTrue(updatedRows == 1);
        updatedRows = sportsTeamRepository.enableScheduledTask(tasks.get(0).getId(), 1);
        assertTrue(updatedRows == 1);
    }

    @Test
    public void runQuery() {
        sportsTeamRepository.runQuery("SELECT * FROM contacts", Contact.class);
    }

    @Test
    public void getImages() {
        List<ImageDetail> images = sportsTeamRepository.getImages(99);
        assertNotNull(images);
        assertEquals(8, images.size());
        assertEquals("20160318_210015.jpg", images.get(0).getName());
        assertEquals("/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads", images.get(0).getFilePath());
        assertEquals("/web/static/gallery/", images.get(0).getMappedAbsoluteFilePath());
        assertEquals(0, images.get(0).getSize().intValue());
        assertEquals("type", images.get(0).getType());
        assertEquals("Kveldsmat, GoMorgen Youghurt byttet ut med 5 fiskekaker", images.get(0).getDescription());
        assertEquals(1, images.get(0).getId().intValue());
        assertEquals("geo location", images.get(0).getGeoLocation());
        assertNotNull(images.get(0).getCreatedDate());
    }

    @Test
    public void CRUDImage() {
        ImageDetail img = new ImageDetail();
        img.setName("unit-test.jpg");
        img.setCreatedDate(new Date());
        img.setFilePath("/tmp");
        img.setMappedAbsoluteFilePath("/web/upload/galley");
        img.setSize(1234567890L);
        img.setGeoLocation("here");
        img.setDescription("unit-test");
        int imageId = sportsTeamRepository.createImage(img);
        assertTrue(imageId != 0);

        ImageDetail image = sportsTeamRepository.getImage(imageId);
        assertEquals(imageId, image.getId().intValue());
        assertEquals("unit-test.jpg", image.getName());
        assertEquals(".web.upload.galley", image.getCanonicalPath());
        assertNotNull(image.getCreatedDate());

        assertTrue(sportsTeamRepository.deleteImage(image.getId()));
    }

}
