package com.gunnarro.sportsteam.utility;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class ImageFileFilterTest {

    @Test
    public void filerImages() {
        System.out.println();
        ImageFileFilter filter = new ImageFileFilter();
        Assert.assertFalse(filter.accept(new File(".")));
        Assert.assertFalse(filter.accept(new File(new File(".").getAbsolutePath() + "\\src\\test\\java\\com\\gunnarro\\sportsteam\\utility\\ImageFileFilterTest.java")));
        Assert.assertTrue(filter.accept(new File(new File(".").getAbsolutePath() + "\\src\\main\\webapp\\resources\\images\\logo-sportsteam.png")));
    }
}
