package com.gunnarro.dietmanager.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        List<LogEntry> list = new ArrayList<>();
        for (int i = 0; i<30; i++) {
            list.add(new LogEntry(i));
        }
        Pageable pageSpecification = new PageRequest(0, 5, new Sort(Sort.Direction.ASC, "id"));
        Page<LogEntry> page = new PageImpl(list, pageSpecification, list.size());
        when(logEventServiceMock.getAllLogEvents(ADMIN_USER_ID, 1, 5)).thenReturn(page);
        ModelAndView modelAndView = controller.getLogEvents(1, 5);
        Assert.assertEquals("log/view-event-logs", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        PageWrapper p = (PageWrapper) modelAndView.getModel().get("page");
        Assert.assertNotNull(p.getContent());
        Assert.assertEquals(29, ((LogEntry)p.getLastElement()).getId().intValue());
        Assert.assertEquals(30, p.getContent().size());
        Assert.assertEquals(1, p.getNumber());
        Assert.assertEquals(30, p.getTotalElements());
        Assert.assertEquals(5, p.getItems().size());
        Assert.assertEquals(6, p.getTotalPages());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void viewLogEventsAsPlainText() throws Exception {
        Pageable pageSpecification = new PageRequest(1, 25, new Sort(Sort.Direction.ASC, "id"));
        Page<LogEntry> page = new PageImpl<>(new ArrayList<>(), pageSpecification, 100);
        when(logEventServiceMock.getAllLogEvents(ADMIN_USER_ID, 1, 25)).thenReturn(page);
        ModelAndView modelAndView = controller.viewLogEventsAsPlainText();
        Assert.assertEquals("log/view-event-logs-txt", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        Page<LogEntry> p = (Page<LogEntry>) modelAndView.getModel().get("page");
        Assert.assertNotNull(p.getContent());
        Assert.assertEquals(1, p.getNumber());
        Assert.assertEquals(100, p.getTotalElements());
        Assert.assertEquals(4, p.getTotalPages());
    }

    @Test
    public void initNewLogEventForm() throws Exception {
        String redirectUrl = controller.initNewLogEventForm(new ExtendedModelMap());
        Assert.assertEquals("log/edit-event-log", redirectUrl);
    }
}