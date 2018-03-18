package com.gunnarro.dietmanager.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.gunnarro.dietmanager.domain.activity.ActivityLog;

/**
 * @author admin
 */
@Service(value = "activityService")
public interface ActivityService {

    /**
     * 
     * @param logEntryId
     * @return
     */
    @PreAuthorize("hasAuthority('BLOGG_READ_PRIVILEGE')")
    public List<ActivityLog> getActivityLogs(Integer userId);

    /**
     * 
     * @param logEntryId
     * @return
     */
    @PreAuthorize("hasAuthority('BLOGG_READ_PRIVILEGE')")
    public ActivityLog getActivityLog(Integer userId, int activityId);

    /**
     * 
     * @param log
     * @return
     */
    @PreAuthorize(value = "hasAuthority('BLOGG_WRITE_PRIVILEGE')")
    public int saveActivityLog(ActivityLog activityLog);

    /**
     * 
     * @param id
     * @return
     */
    @PreAuthorize(value = "hasAuthority('BLOGG_WRITE_PRIVILEGE')")
    public int deleteActivityLog(Integer userId, Integer activityId);

}
