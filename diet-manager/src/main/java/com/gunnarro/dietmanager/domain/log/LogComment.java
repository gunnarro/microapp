package com.gunnarro.dietmanager.domain.log;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.gunnarro.dietmanager.domain.BaseDomain;

public class LogComment extends BaseDomain {

    private static final long serialVersionUID = 8461304356156397577L;
    private String content;
    private String contentHtml;
    private String createdByUser;
    private String lastModifiedByUser;
    private boolean isPrivate = false;

    /**
     * default constructor
     */
    public LogComment() {
        // for unit tests
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

    public String getCreatedByUser() {
        return createdByUser;
    }

    public String getLastModifiedByUser() {
        return lastModifiedByUser;
    }

    public void setContent(@NotNull @Size(min = 5, max = 4096) String content) {
        this.content = content;
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

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    @Override
    public String toString() {
        return "LogComment [id=" + getId() + ", content=" + content + ", createdByUser=" + createdByUser + "]";
    }

}
