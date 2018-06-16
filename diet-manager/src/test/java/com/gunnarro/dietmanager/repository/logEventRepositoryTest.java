package com.gunnarro.dietmanager.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.dietmanager.config.DefaultTestConfig;
import com.gunnarro.dietmanager.config.TestMariDBDataSourceConfiguration;
import com.gunnarro.dietmanager.config.TestRepositoryConfiguration;
import com.gunnarro.dietmanager.domain.log.LogComment;
import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.dietmanager.utility.Utility;

@ContextConfiguration(classes = { TestMariDBDataSourceConfiguration.class, TestRepositoryConfiguration.class })
@Transactional
@Rollback
 @Ignore
public class logEventRepositoryTest extends DefaultTestConfig {

    @Autowired
    private LogEventRepository logEventRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void hasPermission_access_denied() {
        assertFalse(logEventRepository.hasPermission(200, "per"));
    }

    @Test
    public void hasPermission_access_ok() {
        assertTrue(logEventRepository.hasPermission(3, "pepilie"));
    }

    @Test
    public void CRUDEventLog() {
        int userId = 5;
        LogEntry newLog = new LogEntry();
        newLog.setFkUserId(userId);
        newLog.setTitle("title...");
        newLog.setContent("content");
        newLog.setLevel("INFO");
        // Create
        Integer id = logEventRepository.createLogEvent(newLog);
        assertTrue(id > 0);
        LogEntry logEvent = logEventRepository.getLogEvent(userId, id);
        assertEquals(id, logEvent.getId());
        assertNotNull(newLog.getCreatedDate());
        assertNotNull(newLog.getLastModifiedDate());
        assertTrue(newLog.getCreatedDate().equals(newLog.getLastModifiedDate()));
        assertEquals("INFO", logEvent.getLevel());
        assertEquals("title...", logEvent.getTitle());
        assertEquals("content", logEvent.getContent());
        assertEquals("<p>content</p>\n", logEvent.getContentHtml());
        assertEquals(5, logEvent.getFkUserId().intValue());
        assertEquals("pappa", logEvent.getCreatedByUser());
        // Update
        logEvent.setLevel("CONFLICT");
        logEvent.setTitle("title...updated");
        logEvent.setContent("content...updated");
        logEventRepository.updateLogEvent(logEvent);
        assertTrue(id > 0);
        logEvent = logEventRepository.getLogEvent(userId, id);
        assertEquals(id, logEvent.getId());
        assertEquals("CONFLICT", logEvent.getLevel());
        assertEquals("title...updated", logEvent.getTitle());
        assertEquals("content...updated", logEvent.getContent());
        assertEquals(5, logEvent.getFkUserId().intValue());
        // assertTrue(logEvent.getCreatedDate().before(logEvent.getLastModifiedDate()));
        // Delete
        Integer rows = logEventRepository.deleteLogEvent(userId, id);
        assertEquals(1, rows.intValue());
    }

    @Test
    public void getLogComments() {
        assertEquals(2, logEventRepository.getLogComments(4).size());
        // for ( LogComment comment: logEventRepository.getLogComments(4)) {
        // System.out.println(comment);
        // }
    }

    @Test
    public void createLogComment() {
        int userId = 5;
        LogEntry newLog = new LogEntry();
        newLog.setFkUserId(userId);
        newLog.setTitle("title...");
        newLog.setContent("content...");
        newLog.setLevel("INFO");
        // Create log event
        Integer id = logEventRepository.createLogEvent(newLog);
        LogComment comment = new LogComment();
        comment.setFkLogId(id);
        comment.setContent("comment 1");
        comment.setFkUserId(3);
        // create log comment
        logEventRepository.createLogComment(comment);
        // read log event with comment
        LogEntry logEntry = logEventRepository.getLogEvent(userId, id);
        assertEquals(id, logEntry.getId());
        assertEquals(1, logEntry.getNumberOfComments());
        assertEquals("guest", logEntry.getLogComments().get(0).getCreatedByUser());
        assertNotNull(logEntry.getLogComments().get(0).getCreatedDate());
        assertEquals("comment 1", logEntry.getLogComments().get(0).getContent());
        assertEquals(3, logEntry.getLogComments().get(0).getFkUserId().intValue());
    }

    @Test
    public void eventLogLogDateNotToday() {
        int userId = 5;
        LogEntry newLog = new LogEntry();
        newLog.setFkUserId(userId);
        newLog.setTitle("title1...");
        newLog.setContent("content1...");
        newLog.setLevel("INFO");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        newLog.setCreatedDate(cal.getTime());

        Integer id = logEventRepository.createLogEvent(newLog);
        assertTrue(id > 0);
        LogEntry logEvent = logEventRepository.getLogEvent(userId, id);
        assertEquals(id, logEvent.getId());
        assertNotNull(newLog.getCreatedDate());
        assertNotNull(newLog.getLastModifiedDate());
        assertFalse(newLog.getCreatedDate().equals(newLog.getLastModifiedDate()));
        assertEquals("01.02.2016", Utility.formatTime(newLog.getCreatedTime(), "dd.MM.yyyy"));
    }

    @Test
    public void count() {
        assertEquals(5, logEventRepository.count("SELECT count(*) FROM event_log"));
    }

    @Ignore
    @Test
    public void getMyLastStatusReport() {
        LogEntry log = new LogEntry();
        log.setFkUserId(4);
        log.setTitle("report week 47");
        log.setContent("report content");
        log.setLevel("REPORT");
        logEventRepository.createLogEvent(log);
        LogEntry myLastStatusReport = logEventRepository.getMyLastStatusReport(1);
        assertEquals("report week 47", myLastStatusReport.getTitle());
    }

    @Ignore
    @Test
    public void getRecentEventLog() {
        LogEntry recentLogEvent = logEventRepository.getRecentLogEvent(3, "CONFLICT", 7);
        System.out.println(recentLogEvent);
    }

    @Test
    public void getAllLogEvents() {
        Page<LogEntry> page = logEventRepository.getAllLogEvents(4, 0, 25);
        System.out.println(page.toString());
        assertEquals(1, page.getContent().size());
        assertNull(page.getContent().get(0).getLogComments());
    }

    // @Test
    // public void followersGetLog() {
    // int followerId = 6; // mamma user id
    // List<LogEntry> allLogEvents =
    // logEventRepository.getAllLogEvents(followerId);
    // assertEquals(1, allLogEvents.size());
    // assertEquals("log event created by mamma",
    // allLogEvents.get(0).getContent());
    // // add so mamma can see log events for pepilie
    // logEventRepository.createFollowerForUser(4, 6);
    // List<Integer> grantedUserIdsForFollower =
    // logEventRepository.getGrantedUserIdsForFollower(followerId);
    // assertEquals("[4]", grantedUserIdsForFollower.toString());
    // allLogEvents = logEventRepository.getAllLogEvents(followerId);
    // System.out.println(allLogEvents);
    // assertEquals(2, allLogEvents.size());
    // assertEquals("log event created by pepilie",
    // allLogEvents.get(0).getContent());
    // assertEquals("log event created by mamma",
    // allLogEvents.get(1).getContent());
    // }

    @After
    public void tearDown() throws Exception {
    }
}
