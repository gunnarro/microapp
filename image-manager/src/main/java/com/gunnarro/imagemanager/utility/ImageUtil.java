package com.gunnarro.imagemanager.utility;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     * 
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param imgWidth absolute width in pixels
     * @param imgHeight absolute height in pixels
     * @throws IOException
     */
    public static void resizeToFixedSize(BufferedImage inputImage, String outputImagePath, int imgWidth, int imgHeight) throws IOException {
        // hack for openshift heap limitations, have only 500MB
        // reads input image
        scaleImage(inputImage, outputImagePath, imgWidth, imgHeight);
    }

    /**
     * Resizes an image by a percentage of original size (proportional).
     * 
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param percent a double number specifies percentage of the output image
     *            over the input image.
     * @throws IOException
     */
    public static void resizePercentage(BufferedImage inputImage, String outputImagePath, double percent) throws IOException {
        int imgWidth = (int) (inputImage.getWidth() * percent);
        int imgHeight = (int) (inputImage.getHeight() * percent);
        // creates output image
        scaleImage(inputImage, outputImagePath, imgWidth, imgHeight);
    }

    private static void scaleImage(BufferedImage inputImage, String outputImagePath, int imgWidth, int imgHeight) throws IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("img: " + imgWidth + " x " + imgHeight + ", type: " + inputImage.getType());
        }
        // Openshift hack, since java heap is only 500 MB, typically 250 MB.
        Runtime.getRuntime().gc();
        LOG.info("Run GC");
        LOG.info("free memory : " + Runtime.getRuntime().freeMemory() / 1000 + " MB");
        LOG.info("max memory  : " + Runtime.getRuntime().maxMemory() / 1000 + " MB");
        LOG.info("total memory: " + Runtime.getRuntime().totalMemory() / 1000 + " MB");
        BufferedImage outputImage = new BufferedImage(imgWidth, imgHeight, inputImage.getType());
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, imgWidth, imgHeight, null);
        g2d.dispose();
        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);
        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }
}
