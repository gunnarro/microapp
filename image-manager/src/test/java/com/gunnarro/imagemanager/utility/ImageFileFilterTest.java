package com.gunnarro.imagemanager.utility;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.gunnarro.imagemanager.utility.ImageFileFilter;

public class ImageFileFilterTest {

    @Test
    public void filerImages() {
        ImageFileFilter filter = new ImageFileFilter();
        Assert.assertFalse(filter.accept(new File(".")));
        Assert.assertFalse(filter.accept(new File(new File(".").getAbsolutePath() + "\\src\\test\\java\\com\\gunnarro\\sportsteam\\utility\\ImageFileFilterTest.java")));
        Assert.assertTrue(filter.accept(new File(new File(".").getAbsolutePath() + "\\src\\main\\webapp\\resources\\images\\logo-pepilie.gif")));
    }
}
