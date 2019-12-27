package com.gunnarro.followup.endpoint;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.followup.domain.log.LogEntry;
import com.gunnarro.followup.service.LogEventService;
import com.gunnarro.followup.service.exception.ApplicationException;
import com.gunnarro.useraccount.domain.user.LocalUser;

public class LogEventControllerTest extends SpringTestSetup {

    private static final int ADMIN_USER_ID = 1;
    @Mock
    private AuthenticationFacade authenticationFacadeMock;

    private LogEventController controller;

    @Mock
    private LogEventService logEventServiceMock;

    @BeforeEach
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

    @Test()
    public void deleteLogEvent() throws Exception {
        LogEntry logEntry = new LogEntry();
        logEntry.setFkUserId(ADMIN_USER_ID);
        when(logEventServiceMock.getLogEvent(ADMIN_USER_ID, 4)).thenReturn(logEntry);
        String redirectUrl = controller.deletelogEvent(4);
        Assertions.assertEquals("redirect:/diet/log/events", redirectUrl);
    }

    @Test
    public void deleteLogEventNotAllowedToDelete() throws Exception {
        when(logEventServiceMock.getLogEvent(ADMIN_USER_ID, 4)).thenReturn(null);
        Assertions.assertThrows(ApplicationException.class, () -> { controller.deletelogEvent(4); });
    }

    @Test
    public void editLogEvent() throws Exception {
        LogEntry logEntry = new LogEntry();
        logEntry.setFkUserId(ADMIN_USER_ID);
        when(logEventServiceMock.getLogEvent(ADMIN_USER_ID, 4)).thenReturn(logEntry);
        String redirectUrl = controller.initUpdateLogEventForm(4, new ExtendedModelMap());
        Assertions.assertEquals("log/edit-event-log", redirectUrl);
    }

    @Test
    public void editLogEventNotAllowedToEdit() throws Exception {
        when(logEventServiceMock.getLogEvent(ADMIN_USER_ID, 4)).thenReturn(null);
        Assertions.assertThrows(ApplicationException.class, () -> { controller.initUpdateLogEventForm(4, null); });
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getLogEvents() throws Exception {
        List<LogEntry> list = new ArrayList<>();
        for (int i = 0; i<30; i++) {
            list.add(new LogEntry(i));
        }
        Pageable pageSpecification = PageRequest.of(0, 5, Sort.by("id"));
        Page<LogEntry> page = new PageImpl(list, pageSpecification, list.size());
        when(logEventServiceMock.getAllLogEvents(ADMIN_USER_ID, 1, 5)).thenReturn(page);
        ModelAndView modelAndView = controller.getLogEvents(1, 5);
        Assertions.assertEquals("log/view-event-logs", modelAndView.getViewName());
        Assertions.assertNotNull(modelAndView.getModel());
        PageWrapper p = (PageWrapper) modelAndView.getModel().get("page");
        Assertions.assertNotNull(p.getContent());
        Assertions.assertEquals(29, ((LogEntry)p.getLastElement()).getId().intValue());
        Assertions.assertEquals(30, p.getContent().size());
        Assertions.assertEquals(1, p.getNumber());
        Assertions.assertEquals(30, p.getTotalElements());
        Assertions.assertEquals(5, p.getItems().size());
        Assertions.assertEquals(6, p.getTotalPages());
        
        Assertions.assertAll("page",
                () -> Assertions.assertEquals(30, p.getContent().size()),
                () -> Assertions.assertEquals(1, p.getNumber())
            );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void viewLogEventsAsPlainText() throws Exception {
        Pageable pageSpecification = PageRequest.of(1, 25, Sort.by("id"));
        Page<LogEntry> page = new PageImpl<>(new ArrayList<>(), pageSpecification, 100);
        when(logEventServiceMock.getAllLogEvents(ADMIN_USER_ID, 1, 25)).thenReturn(page);
        ModelAndView modelAndView = controller.viewLogEventsAsPlainText();
        Assertions.assertEquals("log/view-event-logs-txt", modelAndView.getViewName());
        Assertions.assertNotNull(modelAndView.getModel());
        Page<LogEntry> p = (Page<LogEntry>) modelAndView.getModel().get("page");
        Assertions.assertNotNull(p.getContent());
        Assertions.assertEquals(1, p.getNumber());
        Assertions.assertEquals(100, p.getTotalElements());
        Assertions.assertEquals(4, p.getTotalPages());
    }

    @Test
    public void initNewLogEventForm() throws Exception {
        String redirectUrl = controller.initNewLogEventForm(new ExtendedModelMap());
        Assertions.assertEquals("log/edit-event-log", redirectUrl);
    }
}