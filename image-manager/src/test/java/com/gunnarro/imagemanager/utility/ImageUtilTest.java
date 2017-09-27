package com.gunnarro.imagemanager.utility;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ImageUtilTest {

    String inputImagePath = "c:/images/20160711_221809.jpg";
    String outputImageFixedPath = "c:/images/tumb/20160711_221809_Fixed.jpg";
    String outputImageSmallerPath = "c:/images/tumb/20160711_221809_Smaller.jpg";
    String outputImageBiggerPath = "c:/images/tumb/20160711_221809_Bigger.jpg";
    String outputImageTumbPath = "c:/images/tumb/20160711_221809_Tumb.jpg";

    @Test
    public void resizeImageFixedWidth() throws IOException {
        // resize to a fixed width (not proportional)
        int scaledWidth = 1024;
        int scaledHeight = 768;
        ImageUtil.resizeToFixedSize(ImageIO.read(new File(inputImagePath)), outputImageFixedPath, scaledWidth, scaledHeight);
    }

    @Test
    public void resizeImageTumb() throws IOException {
        // resize to a fixed width (not proportional)
        int scaledWidth = 48;
        int scaledHeight = 48;
        ImageUtil.resizeToFixedSize(ImageIO.read(new File(inputImagePath)), outputImageTumbPath, scaledWidth, scaledHeight);
    }

    @Test
    public void resizeImagePercentageSmaller() throws IOException {
        // resize smaller by 50%
        double percent = 0.5;
        ImageUtil.resizePercentage(ImageIO.read(new File(inputImagePath)), outputImageSmallerPath, percent);

    }

    @Test
    public void resizeImagePercentagBigger() throws IOException {
        // resize bigger by 50%
        double percent = 1.5;
        ImageUtil.resizePercentage(ImageIO.read(new File(inputImagePath)), outputImageBiggerPath, percent);
    }

}
