package com.gunnarro.tournament.domain.view.list;

import java.util.List;

/**
 * Class for holding a list of items.
 * 
 * @author gunnarro
 * 
 */
public class ItemList {

    private Integer seasonId;
    private Integer activityId;
    private String activityType;

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    @Override
    public String toString() {
        return "ItemList [id=" + activityId + ", items=" + items + "]";
    }

}
