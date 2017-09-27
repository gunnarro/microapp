package com.gunnarro.sportsteam.domain.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class VolunteerTaskTest {

    @Test
    public void testConstructor() {
        VolunteerTask newVolunteerTask = new VolunteerTask();
        newVolunteerTask.setStartDate(new Date(System.currentTimeMillis()));
        newVolunteerTask.setEndDate(new Date());
        newVolunteerTask.setDeadlineDate(new Date());
        newVolunteerTask.setFkClubId(23);
        newVolunteerTask.setFkSeasonId(1);
        newVolunteerTask.setFkTaskStatusId(1);
        newVolunteerTask.setTaskName("Kioskvakt");
        newVolunteerTask.setDescription("kioskvakt på klubbhuset");
        assertNull(newVolunteerTask.getSubTaskList());
        assertNull(newVolunteerTask.getSeason());
        assertNull(newVolunteerTask.getClub());
        assertEquals("volunteer_task", newVolunteerTask.getType());
        assertTrue(newVolunteerTask.isToBePerformedToday());
    }

    @Test
    public void assigend() {
        VolunteerTask newVolunteerTask = new VolunteerTask();
        assertFalse(newVolunteerTask.isAssigned());
        newVolunteerTask.setFkAssignedToPersonId(23);
        assertTrue(newVolunteerTask.isAssigned());
        assertNull(newVolunteerTask.getAssignee());
    }
}
