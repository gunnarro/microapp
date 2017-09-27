package com.gunnarro.dietmanager.domain.diet;

import com.gunnarro.dietmanager.domain.BaseDomain;

public class Rule extends BaseDomain {

    private static final long serialVersionUID = 4209527812273206476L;
    private Integer fkDietPlanId;
    private boolean isActive = true;

    /**
     * Default constructor
     */
    public Rule() {

    }

    public Integer getFkDietPlanId() {
        return fkDietPlanId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setFkDietPlanId(Integer fkDietPlanId) {
        this.fkDietPlanId = fkDietPlanId;
    }

}
