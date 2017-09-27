package com.gunnarro.sportsteam.domain;

import java.io.File;
import java.util.Date;

public class UploadFileInfo {

    private String name;
    private String absolutePath;
    private long size;
    private long createdTime;
    private boolean isDataLoaded;

    public UploadFileInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public Date getCreatedDate() {
        return new Date(createdTime);
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isDataLoaded() {
        return isDataLoaded;
    }

    public void setDataLoaded(boolean isDataLoaded) {
        this.isDataLoaded = isDataLoaded;
    }

    public String getCanonicalPath() {
        String path = absolutePath.replace(File.separator + name, "");
        String canonicalPath = path.replace(File.separator, ".");
        return canonicalPath;
    }

    public String getPath() {
        return getCanonicalPath().replace(".", File.separator);
    }

    public static String toPath(String canonicalPath) {
        return canonicalPath.replace(".", File.separator);
    }
}
