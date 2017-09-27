package com.gunnarro.sportsteam.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
public class SportsTeamServiceSecureTest {

    @Autowired
    @Qualifier("sportsTeamService")
    protected SportsTeamService sportsTeamService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void terminate() {
        SecurityContextHolder.clearContext();
    }
    
    @Test(expected = BadCredentialsException.class)
    public void badCredentialsPwd() {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("admin", "wrongpwd");
        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(ctx);
        ctx.setAuthentication(authRequest);
        try {
            sportsTeamService.deleteAllCups();
        } catch (Exception e) {
//            e.printStackTrace();
            throw e;
        } 
    }

    @Test(expected = BadCredentialsException.class)
    public void badCredentialsUserName() {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("unkownuser", "pwd");
        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(ctx);
        ctx.setAuthentication(authRequest);
        try {
            sportsTeamService.deleteAllCups();
        } catch (Exception e) {
//            e.printStackTrace();
            throw e;
        } 
    }

    @Test(expected = AccessDeniedException.class)
    public void accessDenied() {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("guest", "guest");
        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(ctx);
        ctx.setAuthentication(authRequest);
        try {
            sportsTeamService.deleteAllCups();
        } catch (Exception e) {
//            e.printStackTrace();
            throw e;
        } 
    }

    // -------------------------------------------------------------------
    // Check all save methods
    // -------------------------------------------------------------------

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkDeleteAllCups() {
        sportsTeamService.deleteAllCups();
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkDeleteAllData() {
        sportsTeamService.deleteAllData(1);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkDeleteClub() {
        sportsTeamService.deleteClub(1);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkDeleteContact() {
        sportsTeamService.deleteContact(1);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkDeleteCup() {
        sportsTeamService.deleteCup(1);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkDeleteLeague() {
        sportsTeamService.deleteLeague(1);
    }

    @Ignore
    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkDeleteMatch() {
        sportsTeamService.deleteMatch(1);
    }

    @Ignore
    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkDeletePlayer() {
        sportsTeamService.deletePlayer(1);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkDeleteReferee() {
        sportsTeamService.deleteReferee(1);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkDeleteSubTask() {
        sportsTeamService.deleteSubTask(1);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkDeletetaskGroup() {
        sportsTeamService.deleteTaskGroup(1);
    }

    @Ignore
    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkDeleteTeam() {
        sportsTeamService.deleteTeam(1);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkDeleteTraining() {
        sportsTeamService.deleteTraining(1);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkDeleteUser() {
        sportsTeamService.deleteUser(1);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkDeleteVolunteerTask() {
        sportsTeamService.deleteVolunteerTask(1);
    }

    // -------------------------------------------------------------------
    // Check all save methods
    // -------------------------------------------------------------------

    @Ignore
    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkSaveClub() {
        sportsTeamService.saveClub(null);
    }

    @Ignore
    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkSaveContact() {
        sportsTeamService.saveContact(null);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkSaveContactRole() {
        sportsTeamService.saveContactRole(null, null, null);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkSaveCup() {
        sportsTeamService.saveCup(null);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkSaveLeague() {
        sportsTeamService.saveLeague(null);
    }

    @Ignore
    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkSaveMatch() {
        sportsTeamService.saveMatch(null);
    }

    @Ignore
    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkSavePlayer() {
        sportsTeamService.savePlayer(null);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkSaveReferee() {
        sportsTeamService.saveReferee(null);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkSaveSubTask() {
        sportsTeamService.saveSubTask(null);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkSaveTaskGroup() {
        sportsTeamService.saveTaskGroup(null);
    }

    @Ignore
    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkSaveTeam() {
        sportsTeamService.saveTeam(null);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkSaveTraining() {
        sportsTeamService.saveTraining(null);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkSaveUser() {
        sportsTeamService.saveUser(null);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void checkSaveVolunteerTask() {
        sportsTeamService.saveVolunteerTask(null);
    }
}
