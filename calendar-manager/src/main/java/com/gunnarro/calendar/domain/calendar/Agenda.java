package com.gunnarro.calendar.domain.calendar;

import java.util.List;

import com.gunnarro.calendar.domain.BaseDomain;
import com.gunnarro.calendar.domain.view.KeyValuePairList;

public class Agenda extends BaseDomain {

    private static final long serialVersionUID = 11212L;

    private String name;
    private String description;
    private List<KeyValuePairList> items;
    private Integer eventId;
    private String text;

    public Agenda(String name, List<KeyValuePairList> items) {
        this.name = name;
        this.items = items;
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

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public List<KeyValuePairList> getItems() {
        return items;
    }

    public void setItems(List<KeyValuePairList> items) {
        this.items = items;
    }

    public String getText() {
        StringBuffer sb = new StringBuffer();
        for (KeyValuePairList k : items) {
            sb.append(k.getKey()).append("\n");
            for (Object s : k.getValue()) {
                sb.append(s).append("\n");
            }
            sb.append("\n");
        }
        text = sb.toString();
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Agenda [name=" + name + ", text=" + text + "]";
    }

}
