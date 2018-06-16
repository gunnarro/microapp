package com.gunnarro.dietmanager.service;

import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.dietmanager.config.DefaultTestConfig;
import com.gunnarro.dietmanager.config.TestMariDBDataSourceConfiguration;
import com.gunnarro.dietmanager.config.TestRepositoryConfiguration;
import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.dietmanager.mvc.controller.AuthenticationFacade;
import com.gunnarro.dietmanager.service.impl.FileUploadServiceImpl;
import com.gunnarro.dietmanager.service.impl.LogEventServiceImpl;
import com.gunnarro.useraccount.domain.user.LocalUser;

@ContextConfiguration(classes = { TestMariDBDataSourceConfiguration.class, TestRepositoryConfiguration.class, LogEventServiceImpl.class, AuthenticationFacade.class, FileUploadServiceImpl.class })
@Transactional(timeout = 10)
public class LogEventServiceTest extends DefaultTestConfig {

    @Autowired
    protected LogEventServiceImpl logEventService;

    @Mock
    private AuthenticationFacade authenticationFacadeMock;

    @Before
    public void setUp() throws Exception {
        // Because of security we have to set user and pwd before every unit
        // test
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("admin", "uiL2oo3");
        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(ctx);
        ctx.setAuthentication(authRequest);

        // logEventService.setAuthenticationFacade(authenticationFacadeMock);

        // create mock
        LocalUser user = new LocalUser();
        user.setId(1);
        user.setUsername("admin");
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(user);
    }

    @After
    public void terminate() {
        SecurityContextHolder.clearContext();
    }

    // @Test(expected = SecurityException.class)
    // public void hasPermission_access_denied() {
    // logEventService.checkPermission(3, "guest");
    // Assert.assertTrue(false);
    // }
    //
    // @Test
    // public void hasPermission_access_ok() {
    // Assert.assertTrue(logEventService.checkPermission(3, "pepilie"));
    // }

    @Test
    public void getLogEventsWithComments() {
        LogEntry logEvent = logEventService.getLogEvent(5, 4);
        Assert.assertNotNull(logEvent);
        Assert.assertEquals(5, logEvent.getFkUserId().intValue());
        Assert.assertEquals(4, logEvent.getId().intValue());
        Assert.assertEquals("INFO", logEvent.getLevel());
        Assert.assertEquals("title pappa", logEvent.getTitle());
        Assert.assertEquals("log event created by pappa", logEvent.getContent());
        Assert.assertEquals(2, logEvent.getLogComments().size());
        Assert.assertEquals(2, logEvent.getLogComments().get(0).getId().intValue());
        Assert.assertNotNull(logEvent.getLogComments().get(0).getCreatedDate());
        Assert.assertEquals("added comment 1", logEvent.getLogComments().get(0).getContent());
    }

//    @Test
//    public void logEventCRUD() {
//        int adminUserId = 1;
//        // Read
//        Assert.assertEquals(2, logEventService.getAllLogEvents(adminUserId).size());
//        LogEntry log = new LogEntry();
//        log.setFkUserId(adminUserId);
//        log.setTitle("title");
//        log.setContent("content");
//        // Create
//        int logEventId = logEventService.saveLogEvent(log);
//        Assert.assertEquals(3, logEventService.getAllLogEvents(adminUserId).size());
//        LogEntry logEvent = logEventService.getLogEvent(adminUserId, logEventId);
//        Assert.assertEquals("title", logEvent.getTitle());
//        Assert.assertEquals("content", logEvent.getContent());
//        Assert.assertEquals(1, logEvent.getFkUserId().intValue());
//        // Update
//        logEvent.setContent("updated content");
//        logEventService.saveLogEvent(logEvent);
//        LogEntry updatedLogEvent = logEventService.getLogEvent(adminUserId, logEventId);
//        Assert.assertEquals("updated content", updatedLogEvent.getContent());
//        // Delete
//        int deletedRows = logEventService.deleteLogEvent(adminUserId, logEventId);
//        Assert.assertEquals(1, deletedRows);
//        Assert.assertEquals(2, logEventService.getAllLogEvents(adminUserId).size());
//    }
//
//    @Test
//    public void getLogEventsFiltered() {
//        Assert.assertEquals(0, logEventService.getLogEvents(99, "", "").size());
//        Assert.assertEquals(0, logEventService.getLogEvents(99, null, null).size());
//        Assert.assertEquals(0, logEventService.getLogEvents(99, "title", "*dd*").size());
//        Assert.assertEquals(0, logEventService.getLogEvents(99, "type", "INFO").size());
//        Assert.assertEquals(0, logEventService.getLogEvents(99, "content", "*").size());
//    }
//
//    /**
//     * users must see each other log events
//     */
//    @Ignore
//    @Test
//    public void noLogEventesForUser() {
//        int userId = 3;
//        Assert.assertEquals(0, logEventService.getAllLogEvents(userId).size());
//        Assert.assertNull(logEventService.getLogEvent(userId, 5));
//        Assert.assertNull(logEventService.getLogEvent(userId, 3));
//    }
}
