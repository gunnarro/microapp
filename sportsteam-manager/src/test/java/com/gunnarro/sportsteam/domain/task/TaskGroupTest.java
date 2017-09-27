package com.gunnarro.sportsteam.domain.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TaskGroupTest {

    
    @Test
    public void testConstructor() {
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.setName("Week 45");
        taskGroup.setDescription("kioskvakter for uke 45");
        assertEquals("Week 45", taskGroup.getName());
        assertEquals("kioskvakter for uke 45", taskGroup.getDescription());
        assertNull(taskGroup.getSubTaskList());
    }
}
