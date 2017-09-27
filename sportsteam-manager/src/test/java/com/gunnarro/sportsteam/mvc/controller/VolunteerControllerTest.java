package com.gunnarro.sportsteam.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.activity.Type;
import com.gunnarro.sportsteam.domain.task.SubTask;
import com.gunnarro.sportsteam.domain.task.VolunteerTask;
import com.gunnarro.sportsteam.service.SportsTeamService;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

public class VolunteerControllerTest extends SpringTestSetup {

    @Mock
    private SportsTeamService sportsTeamServiceMock;

    private VolunteerController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new VolunteerController();
        controller.setSportsTeamService(sportsTeamServiceMock);
    }

    @Test
    public void listVolunteerTasks() throws Exception {
        when(sportsTeamServiceMock.getVolunteerTasks(1, "2014")).thenReturn(Arrays.asList(new VolunteerTask()));
        when(sportsTeamServiceMock.getSeason("current")).thenReturn(new Season(1, 1, 1));
        when(sportsTeamServiceMock.getClub(1)).thenReturn(new Club("name", "dep"));
        ModelAndView modelAndView = controller.listVolunteerTasks(1, "current");
        Assert.assertEquals("volunteertask/list-volunteer-tasks-view", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel().get("volunteerTasks"));
        Assert.assertNotNull(modelAndView.getModel().get("clubName"));
    }

    @Test
    public void viewVolunteerTask() {
        VolunteerTask task = new VolunteerTask();
        task.setId(33);
        when(sportsTeamServiceMock.getVolunteerTask(33)).thenReturn(task);
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season(1, 1, 1));
        ModelAndView modelAndView = controller.viewVolunteerTask(33);
        Assert.assertNotNull(modelAndView.getModel().get("volunteerTask"));
        Assert.assertEquals("volunteertask/view-volunteer-task", modelAndView.getViewName());
    }

    @Test
    public void newVolunteerTask() {
        VolunteerTask task = new VolunteerTask();
        task.setId(33);
        when(sportsTeamServiceMock.getClubs()).thenReturn(Arrays.asList(new Club()));
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season(1, 1, 1));
        String url = controller.initNewVolunteerTaskForm(new HashMap<String, Object>());
        Assert.assertEquals("volunteertask/edit-volunteer-task", url);
    }

    @Test
    public void newVolunteerTaskError() {
        VolunteerTask task = new VolunteerTask();
        task.setId(33);
        when(sportsTeamServiceMock.getPlayerStatusTypes()).thenReturn(Arrays.asList(new Type()));
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season(1, 1, 1));
        String url = controller.initNewVolunteerTaskForm(new HashMap<String, Object>());
        Assert.assertEquals("volunteertask/edit-volunteer-task", url);
    }

    @Ignore
    @Test
    public void updateVolunteerTask() {
    }

    @Test
    public void deleteVolunteerTask() {
        VolunteerTask task = new VolunteerTask();
        task.setId(33);
        task.setFkClubId(23);
        when(sportsTeamServiceMock.getVolunteerTask(33)).thenReturn(task);
        when(sportsTeamServiceMock.deleteVolunteerTask(33)).thenReturn(1);
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season(1, 1, 1));
        String url = controller.deleteVolunteerTask(33);
        Assert.assertEquals("redirect:/listvolunteertasks/23/current", url);
    }

    @Test(expected = ApplicationException.class)
    public void deleteVolunteerTaskError() {
        VolunteerTask task = new VolunteerTask();
        task.setId(33);
        when(sportsTeamServiceMock.deleteVolunteerTask(33)).thenReturn(1);
        controller.deleteVolunteerTask(33);
    }

    // @Test
    // public void viewSubTask() {
    // VolunteerTask task = new VolunteerTask();
    // task.setId(33);
    // when(sportsTeamServiceMock.getVolunteerTask(33)).thenReturn(task);
    // when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season(1,
    // 1, 1));
    // ModelAndView modelAndView = controller.viewSubTask(33);
    // Assert.assertNotNull(modelAndView.getModel().get("volunteerTask"));
    // Assert.assertEquals("volunteertask/view-volunteer-task",
    // modelAndView.getViewName());
    // }

    @Test
    public void newSubTask() {
        SubTask subTask = new SubTask();
        subTask.setId(33);
        VolunteerTask volunteerTask = new VolunteerTask();
        subTask.setId(23);
        when(sportsTeamServiceMock.getVolunteerTask(23)).thenReturn(volunteerTask);
        when(sportsTeamServiceMock.getClubs()).thenReturn(Arrays.asList(new Club()));
        String url = controller.initNewSubTaskForm(23, new HashMap<String, Object>());
        Assert.assertEquals("volunteertask/edit-sub-task", url);
    }

    @Test
    public void newSubTaskError() {
        SubTask task = new SubTask();
        task.setId(33);
        VolunteerTask volunteerTask = new VolunteerTask();
        volunteerTask.setId(23);
        when(sportsTeamServiceMock.getVolunteerTask(23)).thenReturn(volunteerTask);
        when(sportsTeamServiceMock.getCurrentSeason()).thenReturn(new Season(1, 1, 1));
        String url = controller.initNewSubTaskForm(23, new HashMap<String, Object>());
        Assert.assertEquals("volunteertask/edit-sub-task", url);
    }

    @Ignore
    @Test
    public void updateSubTask() {
    }

    @Test
    public void deleteSubTask() {
        SubTask task = new SubTask();
        task.setId(33);
        task.setFkParentTaskId(23);
        when(sportsTeamServiceMock.getSubTask(33)).thenReturn(task);
        when(sportsTeamServiceMock.deleteSubTask(33)).thenReturn(1);
        String url = controller.deleteSubTask(33);
        Assert.assertEquals("redirect:/volunteertask/23", url);
    }

    @Test(expected = ApplicationException.class)
    public void deleteSubTaskError() {
        SubTask task = new SubTask();
        task.setId(33);
        when(sportsTeamServiceMock.deleteSubTask(33)).thenReturn(1);
        String url = controller.deleteSubTask(33);
        Assert.assertEquals("redirect:application-error", url);
    }
}