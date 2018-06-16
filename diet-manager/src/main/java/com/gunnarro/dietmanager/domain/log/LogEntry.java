package com.gunnarro.dietmanager.domain.log;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.gunnarro.dietmanager.domain.BaseDomain;

public class LogEntry extends BaseDomain {

    public enum logTypeEnum {
        ACTIVITY, CONFLICT, INFO, REPORT
    }

    private static final long serialVersionUID = 3799683509174086447L;

    private String content;
    private String contentHtml;
    private String createdByUser;
    private String lastModifiedByUser;
    private String level;
    private String title;
    private boolean isPrivate = false;
    private List<LogComment> logComments;
    private int numberOfComments = 0;
    private List<ImageResource> resources;

    /**
     * default constructor
     */
    public LogEntry() {
        // for unit tests
    }

    /**
     * default constructor
     */
    public LogEntry(Integer id) {
        super.setId(id);
    }

    /**
     * @param title
     * @param content
     * @param level
     */
    public LogEntry(String title, String content, String level) {
    }

    public List<LogComment> getLogComments() {
        return logComments;
    }

    public void setLogComments(List<LogComment> logComments) {
        this.logComments = logComments;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public String getLastModifiedByUser() {
        return lastModifiedByUser;
    }

    public String getLevel() {
        return level;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(@NotNull @Size(min = 5, max = 4096) String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    @Override
    public void setCreatedDate(Date createdDate) {
        super.setCreatedTime(createdDate.getTime());
    }

    public void setLastModifiedByUser(String lastModifiedByUser) {
        this.lastModifiedByUser = lastModifiedByUser;
    }

    public void setLevel(@NotNull String level) {
        this.level = level;
    }

    public void setTitle(@NotNull @Size(min = 5, max = 30) String title) {
        this.title = title;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public int getNumberOfComments() {
        if (this.logComments != null) {
            this.numberOfComments = logComments.size();
        }
        return this.numberOfComments;
    }

    public List<ImageResource> getResources() {
        return resources;
    }

    public void setResources(List<ImageResource> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return "LogEntry [id=" + getId() + ", content=" + content + ", level=" + level + ", title=" + title + ", logComments=" + logComments
                + ", numberOfComments=" + numberOfComments + "]";
    }

}
