package com.gunnarro.dietmanager.endpoint.rest;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gunnarro.dietmanager.mvc.controller.AuthenticationFacade;
import com.gunnarro.dietmanager.mvc.controller.SpringTestSetup;
import com.gunnarro.dietmanager.service.DietManagerService;
import com.gunnarro.useraccount.domain.user.LocalUser;

public class DietManagerDataRestEndpointTest extends SpringTestSetup {

    private DietManagerDataRestEndpoint restEndpoint;

    @Mock
    private AuthenticationFacade authenticationFacadeMock;

    @Mock
    private DietManagerService sportsTeamServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        restEndpoint = new DietManagerDataRestEndpoint(sportsTeamServiceMock, authenticationFacadeMock);
    }

    @Test
    public void getChartDataBmi() {
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(1));
        List<ChartData> list = restEndpoint.getChartDataBmi();
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void getChartDataBodymeasure() {
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(1));
        List<ChartData> list = restEndpoint.getChartDataBodymeasure();
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void getChartDataControlledby() {
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(1));
        List<ChartData> list = restEndpoint.getChartDataControlledby();
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void getChartDataCausedconflict() {
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(1));
        List<ChartData> list = restEndpoint.getChartDataCausedconflict();
        Assert.assertEquals(0, list.size());
    }
    
    @Test
    public void getChartDataPreparedby() {
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(1));
        List<ChartData> list = restEndpoint.getChartDataPreparedby();
        Assert.assertEquals(0, list.size());
    }

}
