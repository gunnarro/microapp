package com.gunnarro.calendar.domain.calendar;

import com.gunnarro.calendar.domain.BaseDomain;

public class CalendarConfig extends BaseDomain {

    /**
     * 
     */
    private static final long serialVersionUID = 1976L;

    private String url;
    private String format;
    private String description;
    private String teamName;
    private boolean isEnabled;

    public CalendarConfig() {
        super();
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "CalendarConfig [url=" + url + ", format=" + format + ", description=" + description + ", teamName=" + teamName + ", getId()=" + getId() + ", getName()="
                + getName() + "]";
    }

}
