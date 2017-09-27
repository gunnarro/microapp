package com.gunnarro.sportsteam.domain;

import java.util.Date;

public class ImageDetail {
    private Integer id;
    private Date createdDate;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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
}
