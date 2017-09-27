package com.gunnarro.dietmanager.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.gunnarro.dietmanager.domain.log.LogComment;
import com.gunnarro.dietmanager.domain.log.LogEntry;

/**
 * ref http://websystique.com/spring-security/spring-security-4-method-security-
 * using-preauthorize-postauthorize-secured-el/
 * 
 * @author admin
 *
 */
public interface LogEventService {

    /**
     * 
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('BLOGG_WRITE_PRIVILEGE')")
    public int deleteLogEvent(Integer userId, Integer id);

    /**
     * 
     * @param logEntryId
     * @return
     */
    @PreAuthorize("hasAuthority('BLOGG_READ_PRIVILEGE')")
    public List<LogEntry> getAllLogEvents(Integer userId);

    /**
     * 
     * @param logEntryId
     * @return
     */
    @PreAuthorize("hasAuthority('BLOGG_READ_PRIVILEGE')")
    public LogEntry getLogEvent(Integer userId, int logEntryId);

    /**
     * 
     * @param userId
     * @return
     */
    @PreAuthorize("hasAuthority('BLOGG_READ_PRIVILEGE')")
    public List<LogEntry> getLogEvents(Integer userId);

    /**
     * 
     * @param log
     * @return
     */
    @PreAuthorize("hasAuthority('BLOGG_WRITE_PRIVILEGE')")
    public int saveLogEvent(LogEntry log);

    /**
     * 
     * @param userId
     * @param filterBy
     * @param filterValue
     * @return
     */
    @PreAuthorize("hasAuthority('BLOGG_WRITE_PRIVILEGE')")
    public List<LogEntry> getLogEvents(Integer userId, String filterBy, String filterValue);

    /**
     * 
     * @param logComment
     * @return
     */
    @PreAuthorize("hasAuthority('BLOGG_WRITE_PRIVILEGE')")
    public int saveLogEventComment(LogComment logComment);

}
