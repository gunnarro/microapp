package com.gunnarro.sportsteam.domain.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;

import com.gunnarro.sportsteam.domain.activity.Season;

public class TaskTest {

    @Test
    public void fkKeys() {
        Task task = new Task();
        assertNull(task.getFkClubId());
        assertNull(task.getFkAssignedToPersonId());
    }

    @Test
    public void testConstructor() {
        Task task = new Task();
        task.setFkClubId(23);
        task.setTaskName("task-name");
        task.setDescription("task-description");
        task.setDeadlineDate(new Date(System.currentTimeMillis()));
        task.setStartDate(new Date(System.currentTimeMillis()));
        task.setSeason(new Season(1, 1, 1));
        assertEquals(23, task.getFkClubId().intValue());
        assertNull(task.getFkTeamId());
        assertEquals("task-name", task.getTaskName());
        assertEquals("task-description", task.getDescription());
        assertEquals("volunteer_task", task.getType());
        assertNotNull(task.getSeason());
        assertNotNull(task.getStartDate());
        assertNotNull(task.getDeadlineDate());
        assertFalse(task.isFinished());
    }
}
