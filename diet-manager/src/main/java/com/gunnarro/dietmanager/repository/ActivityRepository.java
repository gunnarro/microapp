package com.gunnarro.dietmanager.repository;

import java.util.List;

import com.gunnarro.dietmanager.domain.activity.ActivityLog;

public interface ActivityRepository {

    public int updateActivityLog(ActivityLog activityLog);

    public int createActivityLog(ActivityLog activityLog);

    public ActivityLog getActivityLog(Integer userId, Integer activityLogId);

    public List<ActivityLog> getActivityLogs(Integer userId);

    public int deleteActivityLog(Integer userId, Integer ActivityLogId);

    public boolean hasPermission(Integer activityLogId, String username);

}
