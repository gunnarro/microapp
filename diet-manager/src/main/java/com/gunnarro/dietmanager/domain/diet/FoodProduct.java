package com.gunnarro.dietmanager.domain.diet;

import java.util.List;

import com.gunnarro.dietmanager.domain.BaseDomain;
import com.gunnarro.dietmanager.domain.statistic.KeyValuePair;

public class FoodProduct extends BaseDomain {

    private static final long serialVersionUID = 1L;

    private List<KeyValuePair> productEquivalents;
    private String type;

    public List<KeyValuePair> getProductEquivalents() {
        return productEquivalents;
    }

    public String getType() {
        return type;
    }

    public void setProductEquivalents(List<KeyValuePair> productEquivalents) {
        this.productEquivalents = productEquivalents;
    }

    public void setType(String type) {
        this.type = type;
    }

}
