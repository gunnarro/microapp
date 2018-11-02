package com.gunnarro.dietmanager.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.gunnarro.dietmanager.domain.togetherness.TogethernessLog;

/**
 * ref http://websystique.com/spring-security/spring-security-4-method-security-
 * using-preauthorize-postauthorize-secured-el/
 * 
 * @author admin
 *
 */
public interface TogethernessService {

    /**
     * 
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('BLOGG_WRITE_PRIVILEGE')")
    public int deleteLog(Integer userId, Integer id);

    /**
     * 
     * @param logEntryId
     * @return
     */
    @PreAuthorize("hasAuthority('BLOGG_READ_PRIVILEGE')")
    public TogethernessLog getLog(Integer userId, int logId);

    /**
     * 
     * @param userId
     * @return
     */
    @PreAuthorize("hasAuthority('BLOGG_READ_PRIVILEGE')")
    public List<TogethernessLog> getLogs(Integer userId);

    /**
     * 
     * @param log
     * @return
     */
    @PreAuthorize("hasAuthority('BLOGG_WRITE_PRIVILEGE')")
    public int saveLog(TogethernessLog log);

}
