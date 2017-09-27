package com.gunnarro.sportsteam.domain.legaue;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.gunnarro.sportsteam.domain.league.League;

public class LeagueTest {
    
    @Test
    public void shortNameSpecialcase() {
        League g12league = new League();
        g12league.setName("Gutter 12 år avd 13 (runde 2)");
        assertEquals("Gutter 12 år avd 13 (runde 2)", g12league.getName());
        assertEquals("G 12 år avd 13", g12league.getShortName());
        
        League j13league = new League();
        j13league.setName("Jenter 13 år 2.div avd 03 (runde 5) ");
        assertEquals("Jenter 13 år 2.div avd 03 (runde 5) ", j13league.getName());
        assertEquals("J 13 år 2.div avd 03", j13league.getShortName());
        
        
    }

}
