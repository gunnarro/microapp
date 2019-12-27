package com.gunnarro.followup.repository;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.followup.config.DefaultTestConfig;
import com.gunnarro.followup.config.TestMariDBDataSourceConfiguration;
import com.gunnarro.followup.config.TestRepositoryConfiguration;
import com.gunnarro.followup.domain.activity.Activity;
import com.gunnarro.followup.domain.activity.ActivityLog;

@ContextConfiguration(classes = { TestMariDBDataSourceConfiguration.class, TestRepositoryConfiguration.class })
@Transactional
@Rollback
public class ActivityRepositoryTest extends DefaultTestConfig {

	@Autowired
	private ActivityRepository activityRepository;

	@BeforeEach
	public void setUp() throws Exception {
	}

	@Test
	public void getActivityLogs() {
		List<ActivityLog> activityLogs = activityRepository.getActivityLogs(1);
		System.out.println(activityLogs);
		Assertions.assertNotNull(activityLogs);
	}

	@Test
	public void createActivityLog() {
		ActivityLog activityLog = new ActivityLog();
		activityLog.setFkUserId(1);
		activityLog.setEmotions(2);
		activityLog.setIntensitivity(4);
		activityLog.setFromTime(LocalTime.now());
		activityLog.setToTime(LocalTime.now().plusHours(1));
		Activity a = new Activity();
		a.setId(1);
		activityLog.setActivity(a);
		Assertions.assertTrue(activityRepository.createActivityLog(activityLog) > 1);
	}

	@Test
	public void deleteActivityLog() {
		Assertions.assertTrue(activityRepository.deleteActivityLog(1, 1) == 1);
	}
}
