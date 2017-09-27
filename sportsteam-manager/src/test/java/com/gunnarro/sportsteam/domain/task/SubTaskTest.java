package com.gunnarro.sportsteam.domain.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;

import com.gunnarro.sportsteam.domain.activity.Season;

public class SubTaskTest {

    @Test
    public void testConstructor() {
        SubTask subTask = new SubTask();
        subTask.setFkClubId(23);
        subTask.setTaskName("task-name");
        subTask.setDescription("task-description");
        subTask.setDeadlineDate(new Date(System.currentTimeMillis()));
        subTask.setStartDate(new Date(System.currentTimeMillis()));
        subTask.setSeason(new Season(1, 1, 1));
        assertEquals(23, subTask.getFkClubId().intValue());
        assertNull(subTask.getFkTeamId());
        assertEquals("task-name", subTask.getTaskName());
        assertEquals("task-description", subTask.getDescription());
        assertEquals("volunteer_task", subTask.getType());
        assertNotNull(subTask.getSeason());
        assertNotNull(subTask.getStartDate());
        assertNotNull(subTask.getDeadlineDate());
        assertFalse(subTask.isFinished());
    }
}
