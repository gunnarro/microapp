package com.gunnarro.dietmanager.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gunnarro.dietmanager.endpoint.rest.RestResponse;

/**
 * Common domain object
 * 
 * @author admin
 * 
 */
public abstract class BaseDomain extends RestResponse implements Serializable {

    private static final long serialVersionUID = -4340377387275807526L;
    private long createdTime = System.currentTimeMillis();
    private long lastModifiedTime = System.currentTimeMillis();
    // All foreign keys used in the DB model
    private Integer id;
    private Integer fkUserId;
    private Integer fkLogId;
    private String sortByValue;
    protected String description;
    protected String name;
    private boolean enabled;

    /**
     * default constructor
     */
    public BaseDomain() {
    }

    public void setCreatedDate(Date createdDate) {
        if (createdDate != null) {
            this.createdTime = createdDate.getTime();
        }
    }

    public Date getCreatedDate() {
        if (this.createdTime > 0) {
            return new Date(this.createdTime);
        } else {
            return null;
        }
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public String getDescription() {
        return description;
    }

    public Integer getFkUserId() {
        return fkUserId;
    }

    public Integer getId() {
        return id;
    }

    public Date getLastModifiedDate() {
        return new Date(lastModifiedTime);
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public String getName() {
        return name;
    }

    public String getSortByValue() {
        return sortByValue;
    }

    public boolean hasName() {
        return StringUtils.isNotBlank(name);
    }

    @JsonIgnore
    public boolean isNew() {
        return this.id == null;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFkUserId(Integer fkUserId) {
        this.fkUserId = fkUserId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSortByValue(String sortByValue) {
        this.sortByValue = sortByValue;
    }

    public Integer getFkLogId() {
        return fkLogId;
    }

    public void setFkLogId(Integer fkLogId) {
        this.fkLogId = fkLogId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "BaseDomain [createdTime=" + createdTime + ", fkUserId=" + fkUserId + ", fkLogId=" + fkLogId + ", id=" + id + ", lastModifiedTime="
                + lastModifiedTime + ", sortByValue=" + sortByValue + ", description=" + description + ", name=" + name + ", enabled=" + enabled + "]";
    }

}
