package com.gunnarro.dietmanager.domain.diet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class MenuItemTest {

    @Test
    public void defaultTest() {
        MenuItem menuItem = new MenuItem();
        assertFalse(menuItem.hasConflict());
        menuItem.setCausedConflict(1);
        assertTrue(menuItem.hasConflict());
    }

    @Test
    public void imageLink() {
        MenuItem menuItem = new MenuItem();
        assertNull(menuItem.getImageTumbLink());
        assertNull(menuItem.getImageTumbLink());
        assertNull(menuItem.getImageLink());

        menuItem.setImageLink("image-manager/web/static/gallery/guest/20160711_101015.jpg");
        assertEquals("image-manager/web/static/gallery/guest/tumbs/20160711_101015.jpg", menuItem.getImageTumbLink());
        assertEquals("image-manager/web/static/gallery/guest/20160711_101015.jpg", menuItem.getImageLink());

        menuItem.setImageLink("");
        assertNull(null, menuItem.getImageTumbLink());
        assertEquals("", menuItem.getImageLink());

        menuItem.setImageLink("/");
        assertEquals("/tumbs/", menuItem.getImageTumbLink());
        assertEquals("/", menuItem.getImageLink());

        menuItem.setImageLink(".");
        assertNull(menuItem.getImageTumbLink());
        assertEquals(".", menuItem.getImageLink());
    }

    @Test
    public void checkSelectionTrends() {
        MenuItem menuItem = new MenuItem();
        assertNull(menuItem.getSelectionTrends());
        menuItem.setSelectionTrends(MenuItem.generateDefaltSelectionTrends(7, "dd.MM.yyyy"));
        menuItem.createSelectionTrends(Arrays.asList("30.05.2016", "31.05.2016", "23.05.2016", "31.05.2016"));
        assertTrue(menuItem.getSelectionTrends().size() == 7);
    }

}
