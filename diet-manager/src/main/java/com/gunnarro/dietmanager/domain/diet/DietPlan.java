package com.gunnarro.dietmanager.domain.diet;

import java.util.List;

import com.gunnarro.dietmanager.domain.BaseDomain;
import com.gunnarro.dietmanager.domain.statistic.KeyValuePair;
import com.gunnarro.dietmanager.domain.view.KeyValuePairList;

public class DietPlan extends BaseDomain {

    private static final long serialVersionUID = 1L;

    private List<KeyValuePairList> dietPlanGoals;
    private List<KeyValuePair> dietPlanRules;
    private boolean isActive;
    private List<KeyValuePairList> menu;
    private String periode;
    private List<KeyValuePairList> planItems;

    /**
     * Default constructor
     */
    public DietPlan() {
    }

    public DietPlan(String name, String description, List<KeyValuePairList> planItems) {
        super();
        super.setName(name);
        this.description = description;
        this.planItems = planItems;
    }

    public List<KeyValuePairList> getDietPlanGoals() {
        return dietPlanGoals;
    }

    public List<KeyValuePair> getDietPlanRules() {
        return dietPlanRules;
    }

    public List<KeyValuePairList> getMenu() {
        return menu;
    }

    public String getPeriode() {
        return periode;
    }

    public List<KeyValuePairList> getPlanItems() {
        return planItems;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setDietPlanGoals(List<KeyValuePairList> dietPlanGoals) {
        this.dietPlanGoals = dietPlanGoals;
    }

    public void setDietPlanRules(List<KeyValuePair> dietPlanRules) {
        this.dietPlanRules = dietPlanRules;
    }

    public void setMenu(List<KeyValuePairList> menu) {
        this.menu = menu;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public void setPlanItems(List<KeyValuePairList> planItems) {
        this.planItems = planItems;
    }

}
