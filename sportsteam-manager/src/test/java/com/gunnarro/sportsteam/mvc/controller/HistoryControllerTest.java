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

import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.service.SportsTeamService;

public class HistoryControllerTest extends SpringTestSetup {

    @Mock
    private SportsTeamService sportsTeamServiceMock;

    private HistoryController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new HistoryController();
        controller.setSportsTeamService(sportsTeamServiceMock);
    }

    @Test
    public void listSeasons() throws Exception {
        when(sportsTeamServiceMock.getSeasons()).thenReturn(Arrays.asList(new Season(System.currentTimeMillis(), System.currentTimeMillis())));
        // assertNotNull(
        ModelAndView modelAndView = controller.listSeasons();
        Assert.assertEquals("history/list-seasons", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        List<Season> list = (List<Season>) modelAndView.getModel().get("seasonList");
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
    }

}