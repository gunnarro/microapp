package com.gunnarro.dietmanager.endpoint.rest;

import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gunnarro.dietmanager.domain.diet.MenuItem;
import com.gunnarro.dietmanager.mvc.controller.AuthenticationFacade;
import com.gunnarro.dietmanager.mvc.controller.SpringTestSetup;
import com.gunnarro.dietmanager.service.DietManagerService;
import com.gunnarro.useraccount.domain.user.LocalUser;

public class DietManagerRestEndpointTest extends SpringTestSetup {

    private DietManagerRestEndpoint restEndpoint;

    @Mock
    private AuthenticationFacade authenticationFacadeMock;

    @Mock
    private DietManagerService sportsTeamServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        restEndpoint = new DietManagerRestEndpoint(sportsTeamServiceMock, authenticationFacadeMock);
    }

    @Test
    public void getChartDataBmi() {
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(1));
        List<ChartData> list = restEndpoint.getChartData("bmi");
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void getChartDataBodymeasure() {
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(1));
        List<ChartData> list = restEndpoint.getChartData("bodymeasure");
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void getChartDataControlledby() {
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(1));
        List<ChartData> list = restEndpoint.getChartData("controlledby");
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void getChartDataNoHit() {
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(1));
        List<ChartData> list = restEndpoint.getChartData("unkown");
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void menuSelectionDeRegistrer() {
        when(sportsTeamServiceMock.deleteSelectedFoodForUser(99, 2)).thenReturn(1);
        when(sportsTeamServiceMock.getSelectedFoodCountForUser(99, 2)).thenReturn(1);
        Integer menuSelectionDeRegistrer = restEndpoint.menuSelectionDeRegistrer(99, 2);
        Assert.assertEquals(1, menuSelectionDeRegistrer.intValue());
    }

    @Test
    public void menuSelectionRegistrer() {
        MenuItem m = new MenuItem();
        m.setId(2);
        m.setCausedConflict(0);
        m.setCreatedDate(new Date());
        m.setControlledByUserId(5);
        when(sportsTeamServiceMock.saveSelectedFoodForUser(99, m)).thenReturn(1);
        when(sportsTeamServiceMock.getSelectedFoodCountForUser(99, 2)).thenReturn(1);
        Integer menuSelectionRegistrer = restEndpoint.menuSelectionRegistrer(99, 2);
        Assert.assertEquals(1, menuSelectionRegistrer.intValue());
    }

}
