package com.gunnarro.dietmanager.repository;

import java.util.List;

import org.springframework.data.domain.Page;

import com.gunnarro.dietmanager.domain.log.LogComment;
import com.gunnarro.dietmanager.domain.log.LogEntry;

public interface LogEventRepository {

    public LogEntry getRecentLogEvent(Integer userId, String type, Integer forLastDays);

    public int updateLogEvent(LogEntry logEntry);

    public LogEntry getMyLastStatusReport(Integer userId);

    public LogEntry getLogEvent(Integer userId, Integer logEntryId);

    public List<LogEntry> getLogEvents(Integer userId);

    public Page<LogEntry> getAllLogEvents(Integer userId, int pageNumber, int pageSize);

    public int createLogEvent(LogEntry logEntry);

    public List<LogEntry> getLogEventsFilteredByType(Integer userId, String level);

    public List<LogEntry> getLogEventsFilteredByTitle(Integer userId, String title);

    public List<LogEntry> searchLogEventsContent(Integer userId, String text);

    public int deleteLogEvent(Integer userId, Integer id);

    public int createLogComment(LogComment logComment);

    public int deleteLogComment(Integer userId, Integer id);

    public List<LogComment> getLogComments(Integer logEntryId);

    public boolean hasPermission(Integer logEventId, String username);

    public int updateLogEventLastModifiedDate(Integer logEntryId);

    public int count(String query);

}
