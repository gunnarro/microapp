package com.gunnarro.sportsteam.service;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
@TransactionConfiguration(defaultRollback = true)
@Ignore
public class SportsTeamServiceManualTest {

    private static final String UIL_DATA_FILES_PATH = "C:\\code\\openshift\\git\\jbossews\\data\\uil";
    private static final String NBF_DATA_FILES_PATH = "C:\\code\\openshift\\git\\jbossews\\data\\nbf";

    @Autowired
    @Qualifier("sportsTeamService")
    protected SportsTeamService sportsTeamService;

    @Test
    public void loadCups() {
        boolean isLoaded = sportsTeamService.loadData(UIL_DATA_FILES_PATH, "cups.xml");
        Assert.assertTrue(isLoaded);
    }

    @Test
    public void loadMatches() {
        // sportsTeamService.deleteAllData(-1);
        boolean isLoaded = sportsTeamService.loadData(NBF_DATA_FILES_PATH, "allekamper_18_10_13_pr_bane.xls");
        // boolean isLoaded = sportsTeamService.loadData(NBF_DATA_FILES_PATH,
        // "allekamper_21_10_14_pr_bane.xlsx");
        Assert.assertTrue(isLoaded);
    }

    @Ignore
    @Test
    public void loadClubData() {
        boolean isLoaded = sportsTeamService.loadData(UIL_DATA_FILES_PATH, "club-without-teams.xml");
        Assert.assertTrue(isLoaded);
        Club club = sportsTeamService.getClub("Ullevål Idretts Lag", "Bandy");
        assertNotNull(club);
        // assertEquals("Ullevål Idretts Lag", club.getName());
        // assertEquals("Bandy", club.getDepartmentName());
        // assertEquals("UIL", club.getClubNameAbbreviation());
        // assertEquals("Bergbanen", club.getStadiumName());
        // assertEquals("http://www.uil.no", club.getHomePageUrl());
        // assertEquals("sognsveien 155".toUpperCase(),
        // club.getAddress().getFullStreetName());
        // assertEquals("0805", club.getAddress().getPostCode());
        // assertEquals("OSLO", club.getAddress().getCity());
        // assertEquals("NORWAY", club.getAddress().getCountry());
    }

    @Ignore
    @Test
    public void loadTeamData() {
        try {
            // Have to set user
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("team", "2003");
            SecurityContext ctx = SecurityContextHolder.createEmptyContext();
            SecurityContextHolder.setContext(ctx);
            ctx.setAuthentication(authRequest);

            Integer clubId = sportsTeamService.saveClub(new Club("Ullevål Idretts Lag", "Bandy"));
            assertTrue(clubId == 1);
            boolean isLoaded = sportsTeamService.loadData(UIL_DATA_FILES_PATH, "team-2003.xml");
            Assert.assertTrue(isLoaded);
            Team team = sportsTeamService.getTeam(clubId, "UIL 2003 Laget");
            assertNotNull(team);
            assertNotNull(team.getClub());
            assertNotNull(team.getCoach());
            assertNotNull(team.getTeamLead());
            assertEquals("22", Integer.toString(team.getNumberOfContacts()));
            assertEquals("16", Integer.toString(team.getNumberOfPlayers()));
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Ignore
    @Test(expected = ApplicationException.class)
    public void loadTeamDataMissingClubElement() {
        sportsTeamService.loadData(UIL_DATA_FILES_PATH, "team-2002-missing-club.xml");
    }

    @Ignore
    @Test(expected = ApplicationException.class)
    public void loadDataInvalidFileName() {
        sportsTeamService.loadData(UIL_DATA_FILES_PATH, "data-2003.xml");
    }

    @Ignore
    @Test(expected = ApplicationException.class)
    public void loadDataFileNotFond() {
        sportsTeamService.loadData(UIL_DATA_FILES_PATH, "club-not-existing.xml");
    }

    @Ignore
    @Test(expected = ApplicationException.class)
    public void loadDataInvalidXml() {
        sportsTeamService.loadData(UIL_DATA_FILES_PATH, "club-invalid.xml");
    }

    @Ignore
    @Test
    public void loadDataMissingTeams() {
        boolean isOk = sportsTeamService.loadData("C:\\code\\git\\openshift\\jbossews\\data\\uil", "club-without-teams.xml");
        Assert.assertTrue(isOk);
    }

}
