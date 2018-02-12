package com.gunnarro.dietmanager.mvc.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.dietmanager.domain.health.HealthLogEntry;
import com.gunnarro.dietmanager.domain.health.ReferenceData;
import com.gunnarro.dietmanager.service.DietManagerService;
import com.gunnarro.useraccount.domain.user.LocalUser;

public class BodyMeasurementsControllerTest extends SpringTestSetup {

    @Mock
    private AuthenticationFacade authenticationFacadeMock;

    private BodyMeasurementsController controller;

    @Mock
    private DietManagerService dietManagerServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new BodyMeasurementsController();
        controller.setDietManagerService(dietManagerServiceMock);
        controller.setAuthenticationFacade(authenticationFacadeMock);
    }

    @Test
    public void viewWeightGraph() throws Exception {
        when(dietManagerServiceMock.getBodyMeasurementLogs(99)).thenReturn(new ArrayList<HealthLogEntry>());
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(1));
        ModelAndView modelAndView = controller.getChart();
        assertEquals("log/view-graph", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel());
    }

    @Test
    public void viewWeightDetails() throws Exception {
        ArrayList<HealthLogEntry> logList = new ArrayList<HealthLogEntry>();
        logList.add(new HealthLogEntry(new Date(), 160.0, 45.3));
        logList.add(new HealthLogEntry(new Date(), 160.1, 46.3));
        logList.add(new HealthLogEntry(new Date(), 160.2, 44.3));
        when(dietManagerServiceMock.getBodyMeasurementLogs(99)).thenReturn(logList);
        when(dietManagerServiceMock.getGrowthReferenceDataForDateOfBirth(99)).thenReturn(new ReferenceData());
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(99));
        ModelAndView modelAndView = controller.getWeightDetails();
        assertEquals("log/view-weight-details", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel());
        assertNotNull(modelAndView.getModel().get("referenceData"));
        assertNotNull(modelAndView.getModel().get("myStatistic"));
    }

    @Test
    public void viewWeightLog() throws Exception {
        ArrayList<HealthLogEntry> logList = new ArrayList<HealthLogEntry>();
        logList.add(new HealthLogEntry(new Date(), 160.0, 45.3));
        logList.add(new HealthLogEntry(new Date(), 160.1, 46.3));
        logList.add(new HealthLogEntry(new Date(), 160.2, 44.3));
        when(dietManagerServiceMock.getBodyMeasurementLogs(99)).thenReturn(logList);
        when(dietManagerServiceMock.getGrowthReferenceDataForDateOfBirth(99)).thenReturn(new ReferenceData());
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(99));
        ModelAndView modelAndView = controller.getLog();
        assertEquals("log/view-weight-log", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel());
        List<HealthLogEntry> list = (List<HealthLogEntry>) modelAndView.getModel().get("logs");
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals(0, list.get(0).getTrendWeight().intValue());
    }

}