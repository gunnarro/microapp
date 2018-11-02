package com.gunnarro.dietmanager.domain.diet;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class DietMenuTest {

    @Test
    public void checkBaseDomain() {
        DietMenu dietMenu = new DietMenu();
        Assert.assertTrue(dietMenu.isNew());
        Assert.assertNotNull(dietMenu.getLastModifiedDate());
        Assert.assertNotNull(dietMenu.getCreatedDate());
        Assert.assertFalse(dietMenu.hasName());

        dietMenu.setCreatedDate(new Date());
        Assert.assertTrue(dietMenu.getCreatedTime() == dietMenu.getCreatedDate().getTime());
    }

    @Test
    public void checkDietMenu() {
        DietMenu dietMenu = new DietMenu();
        Map<String, List<MenuItem>> map = new HashMap<String, List<MenuItem>>();
        map.put(DietMenu.getBreakfast(), Arrays.asList(new MenuItem()));
        map.put(DietMenu.getLunch(), Arrays.asList(new MenuItem()));
        map.put(DietMenu.getDinner(), Arrays.asList(new MenuItem()));
        map.put(DietMenu.getDinnerAccessories(), Arrays.asList(new MenuItem()));
        map.put(DietMenu.getDessert(), Arrays.asList(new MenuItem()));
        map.put(DietMenu.getDinnerPortion(), Arrays.asList(new MenuItem()));
        map.put(DietMenu.getEvening(), Arrays.asList(new MenuItem()));
        map.put(DietMenu.getMealBetween(), Arrays.asList(new MenuItem()));
        dietMenu.setMenuCategoriesMap(map);
        Assert.assertNotNull(dietMenu.getBreakfastMenuItems());
        Assert.assertNotNull(dietMenu.getLunchMenuItems());
        Assert.assertNotNull(dietMenu.getDinnerMenuItems());
        Assert.assertNotNull(dietMenu.getDinnerAccessoriesMenuItems());
        Assert.assertNotNull(dietMenu.getDinnerMenuItems());
        Assert.assertNotNull(dietMenu.getDinnerPortionMenuItems());
        Assert.assertNotNull(dietMenu.getEveningMenuItems());
        Assert.assertNotNull(dietMenu.getMealBetweenMenuItems());
        Assert.assertFalse(dietMenu.isActive());
    }

}
