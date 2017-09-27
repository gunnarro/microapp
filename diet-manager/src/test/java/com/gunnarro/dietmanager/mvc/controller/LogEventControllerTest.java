package com.gunnarro.dietmanager.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.dietmanager.service.LogEventService;
import com.gunnarro.dietmanager.service.exception.ApplicationException;
import com.gunnarro.useraccount.domain.user.LocalUser;

public class LogEventControllerTest extends SpringTestSetup {

    private static final int ADMIN_USER_ID = 1;
    @Mock
    private AuthenticationFacade authenticationFacadeMock;

    private LogEventController controller;

    @Mock
    private LogEventService logEventServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new LogEventController();
        controller.setLogEventService(logEventServiceMock);
        controller.setAuthenticationFacade(authenticationFacadeMock);
        LocalUser user = new LocalUser();
        user.setId(ADMIN_USER_ID);
        user.setUsername("admin");
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(user);
    }

    @Test
    public void deleteLogEvent() throws Exception {
        LogEntry logEntry = new LogEntry();
        logEntry.setFkUserId(ADMIN_USER_ID);
        when(logEventServiceMock.getLogEvent(ADMIN_USER_ID, 4)).thenReturn(logEntry);
        String redirectUrl = controller.deletelogEvent(4);
        Assert.assertEquals("redirect:/diet/log/events", redirectUrl);
    }

    @Test(expected = ApplicationException.class)
    public void deleteLogEventNotAllowedToDelete() throws Exception {
        when(logEventServiceMock.getLogEvent(ADMIN_USER_ID, 4)).thenReturn(null);
        controller.deletelogEvent(4);
    }

    @Test
    public void editLogEvent() throws Exception {
        LogEntry logEntry = new LogEntry();
        logEntry.setFkUserId(ADMIN_USER_ID);
        when(logEventServiceMock.getLogEvent(ADMIN_USER_ID, 4)).thenReturn(logEntry);
        String redirectUrl = controller.initUpdateLogEventForm(4, new ExtendedModelMap());
        Assert.assertEquals("log/edit-event-log", redirectUrl);
    }

    @Test(expected = ApplicationException.class)
    public void editLogEventNotAllowedToEdit() throws Exception {
        when(logEventServiceMock.getLogEvent(ADMIN_USER_ID, 4)).thenReturn(null);
        controller.initUpdateLogEventForm(4, null);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getLogEvents() throws Exception {
        when(logEventServiceMock.getLogEvents(4)).thenReturn(new ArrayList<LogEntry>());
        ModelAndView modelAndView = controller.getLogEvents();
        Assert.assertEquals("log/view-event-logs", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        List<LogEntry> list = (List<LogEntry>) modelAndView.getModel().get("logs");
        Assert.assertNotNull(list);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void viewLogEventsAsPlainText() throws Exception {
        when(logEventServiceMock.getAllLogEvents(4)).thenReturn(new ArrayList<LogEntry>());
        ModelAndView modelAndView = controller.viewLogEventsAsPlainText();
        Assert.assertEquals("log/view-event-logs-txt", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        List<LogEntry> list = (List<LogEntry>) modelAndView.getModel().get("logs");
        Assert.assertNotNull(list);
    }

    @Test
    public void initNewLogEventForm() throws Exception {
        String redirectUrl = controller.initNewLogEventForm(new ExtendedModelMap());
        Assert.assertEquals("log/edit-event-log", redirectUrl);
    }
}