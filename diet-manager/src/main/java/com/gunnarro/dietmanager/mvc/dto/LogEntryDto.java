package com.gunnarro.dietmanager.mvc.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LogEntryDto implements Serializable {

    private static final long serialVersionUID = 3799683509174086447L;

    private Integer id;
    private Integer fkUserId;
    private String createdByUser;
    @NotNull
    @Size(min = 4, max = 30, message = "Tittel må være mellom 4 og 30 tegn.")
    private String title = "";
    @NotNull
    @Size(min = 5, max = 4096, message = "Beskrivelse må være mellom 5 og 496 tegn.")
    private String content = "";
    @NotNull
    private String level = "INFO";

    private long createdTime;
    private long lastModifiedTime;

    /**
     * default constructor
     */
    public LogEntryDto() {
    }

    public boolean isNew() {
        return id == null ? true : false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(Integer fkUserId) {
        this.fkUserId = fkUserId;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public void setCreatedDate(Date createdDate) {
        setCreatedTime(createdDate.getTime());
    }

    public Date getCreatedDate() {
        return new Date(createdTime);
    }

    public Date getLastModifiedDate() {
        return new Date(lastModifiedTime);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "LogEntryDto [id=" + id + ", fkUserId=" + fkUserId + ", createdByUser=" + createdByUser + ", title=" + title + ", content=" + content
                + ", level=" + level + ", createdTime=" + createdTime + ", lastModifiedTime=" + lastModifiedTime + "]";
    }

}
