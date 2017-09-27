package com.gunnarro.sportsteam.domain.activity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StatusTest {

    @Test
    public void testConstructor() {
        Status status = new Status(1, "NOT PLAYED");
        assertEquals("1", status.getId().toString());
        assertEquals("NOT PLAYED", status.getName());
    }
}
