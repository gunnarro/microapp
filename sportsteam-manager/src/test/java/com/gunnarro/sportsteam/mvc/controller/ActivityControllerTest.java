package com.gunnarro.sportsteam.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Cup;
import com.gunnarro.sportsteam.domain.activity.Match;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.activity.Training;
import com.gunnarro.sportsteam.domain.league.League;
import com.gunnarro.sportsteam.domain.task.VolunteerTask;
import com.gunnarro.sportsteam.domain.view.ActivityView;
import com.gunnarro.sportsteam.service.SportsTeamService;

@WebAppConfiguration(value = "src/main/webapp")
public class ActivityControllerTest extends SpringTestSetup {

    @Mock
    private SportsTeamService sportsTeamServiceMock;

    private ActivityController controller;

    @Autowired
    private WebApplicationContext webAppCtx;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new ActivityController();
        controller.setSportsTeamService(sportsTeamServiceMock);
    }

//    @Test
//    public void sendEmail() {
//        ActivityView activity1 = new ActivityView();
//        activity1.setPlace("bergbanen");
//        activity1.setStatus("not started");
//        activity1.setStartDate(new Date());
//        activity1.setType("match");
//        activity1.setDescription("uil - sif");
//        
//        ActivityView activity2 = new ActivityView();
//        activity2.setPlace("ullern kunsstis");
//        activity2.setStatus("not started");
//        activity2.setStartDate(new Date());
//        activity2.setType("match");
//        activity2.setDescription("ullern - uil");
//        
//        Team team = new Team(23,"uil 2003 team");
//        League league = new League(22, "lillegutt");
//        team.setLeague(league);
//        team.setClub(new Club("Uil", "bandy"));
////        team.setTeamLead(new Contact("firstname", null, "lastname"));
//        // Mock service call
//        when(sportsTeamServiceMock.getActivitiesUpcomingWeek(23)).thenReturn(Arrays.asList(activity1,activity2));
//        when(sportsTeamServiceMock.getMatches(23, "2014")).thenReturn(new ArrayList<Match>());
//        when(sportsTeamServiceMock.getCups(23, "2014")).thenReturn(new ArrayList<Cup>());
//        when(sportsTeamServiceMock.getTrainings(23, "2014")).thenReturn(new ArrayList<Training>());
//        when(sportsTeamServiceMock.getVolunteerTasks(23, "2014")).thenReturn(new ArrayList<VolunteerTask>());
//        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season());
//        when(sportsTeamServiceMock.sendEmail(null, "", "")).thenReturn(true);
//        when(sportsTeamServiceMock.getTeam(23)).thenReturn(team);
//        
//        // Call controller
//        ModelAndView modelAndView = controller.activitiesSendEmail(23, "2014", "upcomming");
//        Assert.assertEquals("activity/tab-list-activities", modelAndView.getViewName());
//        Assert.assertNotNull(modelAndView.getModel());
//    }
    
    @Ignore
    @Test
    public void listActivities() throws Exception {
        ActivityView activity = new ActivityView();
        activity.setPlace("bergbanen");
        activity.setStatus("not started");
        activity.setStartDate(new Date());
        activity.setType("match");
        activity.setDescription("uil - sif");

        // Mock service call
        when(sportsTeamServiceMock.getActivitiesUpcomingWeek(23)).thenReturn(Arrays.asList(activity));
        when(sportsTeamServiceMock.getMatches(23, "2014")).thenReturn(new ArrayList<Match>());
        when(sportsTeamServiceMock.getCups(23, "2014")).thenReturn(new ArrayList<Cup>());
        when(sportsTeamServiceMock.getTrainings(23, "2014")).thenReturn(new ArrayList<Training>());
        when(sportsTeamServiceMock.getVolunteerTasks(23, "2014")).thenReturn(new ArrayList<VolunteerTask>());
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season());
        when(sportsTeamServiceMock.getSeason("2014")).thenReturn(new Season());
        Team team = new Team();
        team.setName("uil 2003 team");
        League league = new League(22, "lillegutt");
        team.setLeague(league);
        when(sportsTeamServiceMock.getTeam(23)).thenReturn(team);

        // Call controller
        ModelAndView modelAndView = controller.listActivities(23, "2014");
        Assert.assertEquals("activity/tab-list-activities", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        Assert.assertNotNull(modelAndView.getModel().get("team"));
        Assert.assertNotNull(modelAndView.getModel().get("season"));
        Assert.assertNotNull(modelAndView.getModel().get("upcomingActivityList"));
        Assert.assertNotNull(modelAndView.getModel().get("matchList"));
        Assert.assertNotNull(modelAndView.getModel().get("cupList"));
        Assert.assertNotNull(modelAndView.getModel().get("trainingList"));
        Assert.assertNotNull(modelAndView.getModel().get("volunteerTasks"));
    }

    @Ignore
    @Test
    public void processNewCupForm() {
        // Cup cup = new Cup();
        // cup.setFkTeamId(2);
        // cup.setFkSeasonId(3);
        // when(sportsTeamServiceMock.saveCup(cup)).thenReturn(1);
        // when(sportsTeamServiceMock.getTeams(1)).thenReturn(new
        // ArrayList<Team>());
        // String view = controller.processNewCupForm(cup, new BindingResult(),
        // new SessionStatus());
        // Assert.assertEquals("redirect:/listactivities/2/3", view);
    }

    @Ignore
    @Test
    public void initUpdateCupForm() {

    }

    @Ignore
    @Test
    public void processUpdateCupForm() {

    }

    // @Test
    // public void registrerPlayerForMatch() {
    // int matchId = 11;
    // Match match = new Match();
    // match.setId(matchId);
    // match.setStatus(new Status(1, "NOT PLAYED"));
    // match.setTeam(new Team(1, "2003 team"));
    // when(sportsTeamServiceMock.getMatch(matchId)).thenReturn(match);
    // when(sportsTeamServiceMock.getMatchSignedPlayerList(match.getTeam().getId(),
    // matchId)).thenReturn(new ArrayList<Item>() {
    // });
    // ModelAndView modelAndView =
    // controller.registrerPlayer(match.getTeam().getId(), matchId, "match");
    // Assert.assertEquals("activity/activity-registrer-players",
    // modelAndView.getViewName());
    // ItemList list = (ItemList) modelAndView.getModel().get("itemList");
    // Assert.assertNotNull(list);
    // }
    //
    // @Test
    // public void registrerPlayerUnkownActivityType() {
    // try {
    // controller.registrerPlayer(1, 1, "unkownActivityType");
    // } catch (Exception e) {
    // Assert.assertEquals("ApplicationException",
    // e.getClass().getSimpleName());
    // Assert.assertEquals("ERROR: Unknown activity type: unkownActivityType",
    // e.getMessage());
    // }
    // }

    @Ignore
    @Test
    public void newCupValidationError() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webAppCtx).build();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/cup/new").param("cupName", "koas cup").param("venue", "").param("deadlineDate", "12.12.12345")
                        .param("clubName", "Stabekk")).andExpect(MockMvcResultMatchers.view().name("activity/edit-cup"))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("club", "venue"));
    }
}