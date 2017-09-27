package com.gunnarro.sportsteam.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.service.SportsTeamService;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

public class ClubControllerTest extends SpringTestSetup {

    @Mock
    private SportsTeamService sportsTeamServiceMock;

    private ClubController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new ClubController();
        controller.setSportsTeamService(sportsTeamServiceMock);
    }

    @Test
    public void listClubs() throws Exception {
        when(sportsTeamServiceMock.getClubs()).thenReturn(Arrays.asList(new Club(1, "uil", "bandy")));
        // assertNotNull(
        ModelAndView modelAndView = controller.listClubs();
        Assert.assertEquals("club/list-clubs", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        List<Club> list = (List<Club>) modelAndView.getModel().get("clubList");
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("uil", list.get(0).getName());
        Assert.assertEquals("bandy", list.get(0).getDepartmentName());
    }

    @Test
    public void viewClub() {
        int clubId = 22;
        when(sportsTeamServiceMock.getClub(clubId)).thenReturn(new Club(clubId, "name", "department"));
        ModelAndView modelAndView = controller.viewClub(clubId);
        Assert.assertEquals("club/view-club", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel().get("club"));
    }

    @Test(expected = ApplicationException.class)
    public void viewClubError() {
        when(sportsTeamServiceMock.getTeam(222)).thenReturn(null);
        controller.viewClub(222);
    }

}