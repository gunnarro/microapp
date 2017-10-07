package com.gunnarro.dietmanager.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.dietmanager.config.DataSourceConfiguration;
import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.dietmanager.repository.impl.LogEventRepositoryImpl;
import com.gunnarro.dietmanager.service.impl.LogEventServiceImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={DataSourceConfiguration.class, LogEventRepositoryImpl.class, LogEventServiceImpl.class })
@Transactional(timeout = 10)
public class LogEventServiceTest {

    @Autowired
    protected LogEventService logEventService;

    @Before
    public void setUp() throws Exception {
        // Because of security we have to set user and pwd before every unit
        // test
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("admin", "uiL2oo3");
        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(ctx);
        ctx.setAuthentication(authRequest);
    }

    @After
    public void terminate() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void logEventCRUD() {
        int adminUserId = 1;
        // Read
        Assert.assertEquals(2, logEventService.getAllLogEvents(adminUserId).size());
        LogEntry log = new LogEntry();
        log.setFkUserId(adminUserId);
        log.setTitle("title");
        log.setContent("content");
        // Create
        int logEventId = logEventService.saveLogEvent(log);
        Assert.assertEquals(3, logEventService.getAllLogEvents(adminUserId).size());
        LogEntry logEvent = logEventService.getLogEvent(adminUserId, logEventId);
        Assert.assertEquals("title", logEvent.getTitle());
        Assert.assertEquals("content", logEvent.getContent());
        Assert.assertEquals(1, logEvent.getFkUserId().intValue());
        // Update
        logEvent.setContent("updated content");
        logEventService.saveLogEvent(logEvent);
        LogEntry updatedLogEvent = logEventService.getLogEvent(adminUserId, logEventId);
        Assert.assertEquals("updated content", updatedLogEvent.getContent());
        // Delete
        int deletedRows = logEventService.deleteLogEvent(adminUserId, logEventId);
        Assert.assertEquals(1, deletedRows);
        Assert.assertEquals(2, logEventService.getAllLogEvents(adminUserId).size());
    }

    @Test
    public void getLogEventsFiltered() {
        Assert.assertEquals(0, logEventService.getLogEvents(99, "", "").size());
        Assert.assertEquals(0, logEventService.getLogEvents(99, null, null).size());
        Assert.assertEquals(0, logEventService.getLogEvents(99, "title", "*dd*").size());
        Assert.assertEquals(0, logEventService.getLogEvents(99, "type", "INFO").size());
        Assert.assertEquals(0, logEventService.getLogEvents(99, "content", "*").size());
    }
    
    @Test
    public void noLogEventesForUser() {
        int userId = 3;
        Assert.assertEquals(0, logEventService.getAllLogEvents(userId).size());
        Assert.assertNull(logEventService.getLogEvent(userId, 5));
        Assert.assertNull(logEventService.getLogEvent(userId, 3));
    }
}
