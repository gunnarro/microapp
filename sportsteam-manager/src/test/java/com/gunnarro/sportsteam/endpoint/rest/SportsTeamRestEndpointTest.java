package com.gunnarro.sportsteam.endpoint.rest;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.SendMailResponse;
import com.gunnarro.sportsteam.domain.SendSMSResponse;
import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Match;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.league.League;
import com.gunnarro.sportsteam.domain.message.Sms;
import com.gunnarro.sportsteam.domain.party.Contact;
import com.gunnarro.sportsteam.domain.view.ActivityView;
import com.gunnarro.sportsteam.mvc.controller.SpringTestSetup;
import com.gunnarro.sportsteam.service.MessageService;
import com.gunnarro.sportsteam.service.SportsTeamService;

public class SportsTeamRestEndpointTest extends SpringTestSetup {

    private SportsTeamRestEndpoint restEndpoint;

    @Mock
    private SportsTeamService sportsTeamServiceMock;
    
    @Mock
    private MessageService messageServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        restEndpoint = new SportsTeamRestEndpoint(sportsTeamServiceMock,messageServiceMock);
    }

    @Test
    public void findClub() {
        when(sportsTeamServiceMock.getClub(2)).thenReturn(new Club());
        Assert.assertNotNull(restEndpoint.findClub(2));
    }

    @Test(expected = RestApplicationException.class)
    public void findClubNotFound() {
        when(sportsTeamServiceMock.getClub(2)).thenReturn(null);
        Assert.assertNotNull(restEndpoint.findClub(2));
    }

    @Test(expected = RestApplicationException.class)
    public void findMatchNotFound() {
        when(sportsTeamServiceMock.getMatch(2)).thenReturn(null);
        Assert.assertNotNull(restEndpoint.findMatch(2));
    }

    @Test
    public void findMatch() {
        when(sportsTeamServiceMock.getMatch(2)).thenReturn(new Match());
        Assert.assertNotNull(restEndpoint.findMatch(2));
    }

    @Test
    public void listAllClubs() {
        when(sportsTeamServiceMock.getClubs()).thenReturn(new ArrayList<Club>());
        Assert.assertNotNull(restEndpoint.listAllClubs());
    }

    @Test
    public void listAllMatches() {
        when(sportsTeamServiceMock.getMatches(1, "2014-2015")).thenReturn(new ArrayList<Match>());
        Assert.assertNotNull(restEndpoint.listAllMatches(1, "2014-2015"));
    }

    @Test(expected = RestApplicationException.class)
    public void activityRegistrerPlayerInvalidActivityType() {
        restEndpoint.activityRegistrer("invalidActivityType", 1, 1);
    }

    @Test(expected = RestApplicationException.class)
    public void activityDeRegistrerPlayerInvalidActivityType() {
        restEndpoint.activityDeRegistrer("invalidActivityType", 1, 1);
    }

    @Test
    public void activityRegistrerPlayerForMatch() {
        when(sportsTeamServiceMock.signupForMatch(1, 1)).thenReturn(1);
        Integer activityRegistrer = restEndpoint.activityRegistrer("match", 1, 1);
        Assert.assertEquals(1, activityRegistrer.intValue());
    }

    @Test
    public void activityDeRegistrerPlayerForMatch() {
        when(sportsTeamServiceMock.unsignForMatch(1, 1)).thenReturn(1);
        Integer activityRegistrer = restEndpoint.activityDeRegistrer("match", 1, 1);
        Assert.assertEquals(1, activityRegistrer.intValue());
    }

    @Test
    public void activityRegistrerPlayerForCup() {
        when(sportsTeamServiceMock.signupForCup(1, 1)).thenReturn(1);
        Integer activityRegistrer = restEndpoint.activityRegistrer("cup", 1, 1);
        Assert.assertEquals(1, activityRegistrer.intValue());
    }

    @Test
    public void activityDeRegistrerPlayerForCup() {
        when(sportsTeamServiceMock.unsignForCup(1, 1)).thenReturn(1);
        Integer activityRegistrer = restEndpoint.activityDeRegistrer("cup", 1, 1);
        Assert.assertEquals(1, activityRegistrer.intValue());
    }

    @Test
    public void activityRegistrerPlayerForTraining() {
        when(sportsTeamServiceMock.signupForTraining(1, 1)).thenReturn(1);
        Integer activityRegistrer = restEndpoint.activityRegistrer("training", 1, 1);
        Assert.assertEquals(1, activityRegistrer.intValue());
    }

    @Test
    public void activityDeRegistrerPlayerForTraining() {
        when(sportsTeamServiceMock.unsignForTraining(1, 1)).thenReturn(1);
        Integer activityRegistrer = restEndpoint.activityDeRegistrer("training", 1, 1);
        Assert.assertEquals(1, activityRegistrer.intValue());
    }

    @Test
    public void cupRegistrerTeam() {
        when(sportsTeamServiceMock.cupRegistrerTeam(1, 1)).thenReturn(1);
        Integer cupRegistrer = restEndpoint.cupRegistrerTeam(1, 1);
        Assert.assertEquals(1, cupRegistrer.intValue());
    }

    @Test
    public void cupUnRegistrerTeam() {
        when(sportsTeamServiceMock.cupUnRegistrerTeam(1, 1)).thenReturn(1);
        Integer cupRegistrer = restEndpoint.cupUnRegistrerTeam(1, 1);
        Assert.assertEquals(1, cupRegistrer.intValue());
    }

    @Test
    public void playerAddParent() {
        when(sportsTeamServiceMock.playerAddParent(1, 2)).thenReturn(5);
        Integer id = restEndpoint.playerAddParent(1, 2);
        Assert.assertEquals(5, id.intValue());
    }

    @Test
    public void playerRemoveParent() {
        when(sportsTeamServiceMock.playerRemoveParent(1, 2)).thenReturn(5);
        Integer id = restEndpoint.playerRemoveParent(1, 2);
        Assert.assertEquals(5, id.intValue());
    }

    @Test
    public void assignSubTaskToPerson() {
        when(sportsTeamServiceMock.assignSubTaskToPerson(1, 1)).thenReturn(1);
        Integer id = restEndpoint.assignSubTaskToPerson(1, 1);
        Assert.assertEquals(1, id.intValue());
    }
    
    @Test
    public void unsignSubTaskToPerson() {
        when(sportsTeamServiceMock.unsignPersonFromSubTask(1, 1)).thenReturn(1);
        Integer id = restEndpoint.unsignSubTaskToPerson(1, 1);
        Assert.assertEquals(1, id.intValue());
    }
    
    @Test
    public void assignTaskToPerson() {
        when(sportsTeamServiceMock.assignTaskToPerson(1, 1)).thenReturn(1);
        Integer id = restEndpoint.assignTaskToPerson(1, 1);
        Assert.assertEquals(1, id.intValue());
    }
    
    @Test
    public void unsignTaskToPerson() {
        when(sportsTeamServiceMock.unsignPersonFromTask(1, 1)).thenReturn(1);
        Integer id = restEndpoint.unsignTaskToPerson(1, 1);
        Assert.assertEquals(1, id.intValue());
    }
    
    @Test
    public void sendMail() {
        ActivityView activity1 = new ActivityView();
        activity1.setPlace("bergbanen");
        activity1.setStatus("not started");
        activity1.setStartDate(new Date());
        activity1.setType("match");
        activity1.setDescription("uil - sif");
        
        ActivityView activity2 = new ActivityView();
        activity2.setPlace("ullern kunsstis");
        activity2.setStatus("not started");
        activity2.setStartDate(new Date());
        activity2.setType("match");
        activity2.setDescription("ullern - uil");
        
        Team team = new Team(23,"uil 2003 team");
        League league = new League(22, "lillegutt");
        team.setLeague(league);
        team.setClub(new Club("Uil", "bandy"));
        team.setTeamLead(new Contact("firstname", null, "lastname"));
        // Mock service call
        String[] to = new String[] { "gunnar_ronneberg@yahoo.no" };
        String subject = team.getClub().getClubNameAbbreviation() + " " + team.getName() + " Agenda for uke " + Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        when(messageServiceMock.sendEmail(to, subject, "sent email")).thenReturn(true);
        when(sportsTeamServiceMock.getActivitiesUpcomingWeek(23)).thenReturn(Arrays.asList(activity1,activity2));
        when(sportsTeamServiceMock.getSeason("2014")).thenReturn(new Season());
        when(sportsTeamServiceMock.composeEmail(team)).thenReturn("sent email");
        when(sportsTeamServiceMock.getTeam(23)).thenReturn(team);
        SendMailResponse sendMailResponse = restEndpoint.sendMail(23, "current", "upcoming");
        Assert.assertTrue(sendMailResponse.getResult().contains("Sent email"));
    }

    @Test
    public void sendSMS() {
    	Sms sms = new Sms(new String[]{"45465500","45465501"}, "SporTsTeaM", "Only testing from unit test");
        when(messageServiceMock.sendSMS(sms)).thenReturn(true);
        SendSMSResponse sendSMS = restEndpoint.sendSMS(23);
        Assert.assertEquals("false",sendSMS.getResult());
    }
    
}

