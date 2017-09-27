package com.gunnarro.imagemanager.utility;

import java.io.File;
import java.io.FileFilter;

public class ImageFileFilter implements FileFilter {

    private static final String[] IMAGE_FILE_EXTENSIONS = new String[] { "jpg", "gif", "png" };

    public boolean accept(File file) {
        if (file.isFile()) {
            for (String suffix : IMAGE_FILE_EXTENSIONS) {
                if (file.getPath().toLowerCase().endsWith(suffix))
                    return true;
            }
        }
        return false;
    }
}
