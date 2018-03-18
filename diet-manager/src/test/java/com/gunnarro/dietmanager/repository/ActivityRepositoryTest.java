package com.gunnarro.dietmanager.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ActivityRepositoryTest extends DefaultTestConfig {

    @Autowired
    private ActivityRepository activityRepository;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void hasPermission_access_denied() {
        assertFalse(activityRepository.hasPermission(200, "per"));
    }

    @Test
    public void hasPermission_access_ok() {
        assertTrue(activityRepository.hasPermission(3, "pepilie"));
    }

    @Test
    public void getActivityLogs() {
        assertNotNull(activityRepository.getActivityLogs(1));
    }
}
