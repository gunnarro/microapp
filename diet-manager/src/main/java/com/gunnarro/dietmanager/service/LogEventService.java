package com.gunnarro.dietmanager.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.gunnarro.dietmanager.domain.log.LogComment;
import com.gunnarro.dietmanager.domain.log.LogEntry;

/**
 * ref http://websystique.com/spring-security/spring-security-4-method-security-
 * using-preauthorize-postauthorize-secured-el/
 * 
 * @author admin
 *
 */
@Service(value = "logEventService")
public interface LogEventService {

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
//    @PreAuthorize("hasAuthority('BLOGG_WRITE_PRIVILEGE') and #log.fkUserId == authentication.name")
    @PreAuthorize(value = "hasAuthority('BLOGG_WRITE_PRIVILEGE')")
    public int saveLogEvent(LogEntry logEntry);


    /**
     * 
     * @param id
     * @return
     */
    @PreAuthorize(value = "hasAuthority('BLOGG_WRITE_PRIVILEGE') and @logEventService.hasAccess(#log.id, authentication.name)")
    public int deleteLogEvent(Integer userId, Integer id);
    
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
