package com.gunnarro.dietmanager.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gunnarro.dietmanager.domain.activity.ActivityLog;
import com.gunnarro.dietmanager.mvc.controller.AuthenticationFacade;
import com.gunnarro.dietmanager.repository.ActivityRepository;
import com.gunnarro.dietmanager.service.ActivityService;

/**
 * 
 * @author mentos
 *
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    private static final Logger LOG = LoggerFactory.getLogger(ActivityServiceImpl.class);

    @Autowired
    protected AuthenticationFacade authenticationFacade;

    @Autowired
    private ActivityRepository activityRepository;

    private boolean checkPermission(Integer logEventId) {
        boolean hasPermission = activityRepository.hasPermission(logEventId, authenticationFacade.getAuthentication().getName());
        if (!hasPermission) {
            throw new SecurityException("Access denied");
        }
        return hasPermission;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActivityLog> getActivityLogs(Integer userId) {
        return activityRepository.getActivityLogs(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityLog getActivityLog(Integer userId, int activityId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("userId = {}, activityId = {}", +userId, activityId);
        }
        return activityRepository.getActivityLog(userId, activityId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteActivityLog(Integer userId, Integer activityId) {
        // check if user have permission to delete
        checkPermission(activityId);
        return activityRepository.deleteActivityLog(userId, activityId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int saveActivityLog(ActivityLog activityLog) {
        if (activityLog.isNew()) {
            return activityRepository.createActivityLog(activityLog);
        } else {
            // check if user have permission to update
            checkPermission(activityLog.getId());
            return activityRepository.updateActivityLog(activityLog);
        }
    }

    /**
     * For unit testing only, inject mock
     * 
     * @param activityRepository
     */
    public void setActivityRepository(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * For unit testing only, inject mock
     * 
     */
    public void setAuthenticationFacade(AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

}
