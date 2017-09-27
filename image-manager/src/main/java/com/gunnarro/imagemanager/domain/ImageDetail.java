package com.gunnarro.imagemanager.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ImageDetail {
    private Integer id;
    private Integer userId;
    private Date createdDate;
    private Date lastModifiedDate;
    private String title;
    private String name;
    private String description;
    private String filePath;
    private String mappedAbsoluteFilePath;
    private String geoLocation;
    private String type;
    private Long size;

    /**
     * Default constructor
     */
    public ImageDetail() {
    }

    @JsonIgnore
    public boolean isNew() {
        return this.id == null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMappedAbsoluteFilePath() {
        return mappedAbsoluteFilePath;
    }

    public void setMappedAbsoluteFilePath(String mappedAbsoluteFilePath) {
        this.mappedAbsoluteFilePath = mappedAbsoluteFilePath;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getCanonicalPath() {
        return mappedAbsoluteFilePath != null ? mappedAbsoluteFilePath.replace("/", ".") : null;
    }

    @Override
    public String toString() {
        return "ImageDetail [id=" + id + ", userId=" + userId + ", createdDate=" + createdDate + ", title=" + title + ", name=" + name + ", description=" + description
                + ", filePath=" + filePath + ", mappedAbsoluteFilePath=" + mappedAbsoluteFilePath + ", geoLocation=" + geoLocation + ", type=" + type + ", size=" + size + "]";
    }

}
