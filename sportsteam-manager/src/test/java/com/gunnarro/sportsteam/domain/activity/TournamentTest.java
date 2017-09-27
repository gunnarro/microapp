package com.gunnarro.sportsteam.domain.activity;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TournamentTest {

    @Test
    public void init() {
        Tournament t = new Tournament("unit test tournament");
        Assert.assertEquals("unit test tournament", t.getTournamentName());
        Assert.assertEquals("unit test tournament", t.getName());
        Assert.assertNull(t.getGroups());
        Assert.assertNull(t.getFinalsSetup());
        Assert.assertFalse(t.isQuarterFinalsFinished());
        Assert.assertFalse(t.isSemiFinalsFinished());
        Assert.assertFalse(t.isBronseFinalFinished());
        Assert.assertFalse(t.isGoldFinalFinished());
    }

    @Test
    public void findGroup() {
        Tournament t = new Tournament("unit test tournament");
        Group g1 = new Group(1, "group-1", new ArrayList<Match>());
        Group g2 = new Group(2, "group-2", new ArrayList<Match>());
        List<Group> groups = new ArrayList<Group>();
        groups.add(g1);
        groups.add(g2);
        t.setGroups(groups);

        Assert.assertEquals("group-1", t.getGroupByName(g1.getName()).getName());
        Assert.assertEquals("group-1", t.getGroupById(g1.getId()).getName());
    }

}
