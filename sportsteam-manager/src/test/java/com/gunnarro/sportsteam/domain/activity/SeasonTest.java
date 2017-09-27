package com.gunnarro.sportsteam.domain.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class SeasonTest {

    @Test
    public void testConstructor() {
        Season season = new Season(System.currentTimeMillis(), System.currentTimeMillis());
        assertNotNull(season.getStartTime());
        assertNotNull(season.getEndTime());
        assertEquals("2016-2016", season.getPeriod());
    }
    
    @Test
    public void testConstructorNoArgs() {
        Season season = new Season();
        assertNull(season.getStartTime());
        assertNull(season.getEndTime());
        assertNull(season.getPeriod());
    }

}
