package com.gunnarro.dietmanager.domain.diet;

import java.util.List;
import java.util.Map;

import com.gunnarro.dietmanager.domain.BaseDomain;

public class DietMenu extends BaseDomain {

    private static final long serialVersionUID = 1L;

    public static final String BREAKFAST = "Frokost";
    public static final String DESSERT = "Dessert";
    public static final String DINNER = "Middag";
    public static final String DINNER_ACCESSORIES = "Middag Tilbehør";
    public static final String DINNER_PORTION = "Middag Porsjon";
    public static final String EVENING = "Kveldsmat";
    public static final String LUNCH = "Lunsj";
    public static final String MEAL_BETWEEN = "Mellom måltid";
    private List<Rule> dietRules;
    private boolean isActive;
    private Map<String, List<MenuItem>> menuCategoriesMap;
    private String periode;

    /**
     * default constructor
     */
    public DietMenu() {
    }

    public static String getBreakfast() {
        return BREAKFAST;
    }

    public static String getDessert() {
        return DESSERT;
    }

    public static String getDinner() {
        return DINNER;
    }

    public static String getDinnerAccessories() {
        return DINNER_ACCESSORIES;
    }

    public static String getDinnerPortion() {
        return DINNER_PORTION;
    }

    public static String getEvening() {
        return EVENING;
    }

    public static String getLunch() {
        return LUNCH;
    }

    public static String getMealBetween() {
        return MEAL_BETWEEN;
    }

    public List<MenuItem> getBreakfastMenuItems() {
        return menuCategoriesMap.get(BREAKFAST);
    }

    public List<MenuItem> getDessertMenuItems() {
        return menuCategoriesMap.get(DESSERT);
    }

    public List<Rule> getDietRules() {
        return dietRules;
    }

    public List<MenuItem> getDinnerAccessoriesMenuItems() {
        return menuCategoriesMap.get(DINNER_ACCESSORIES);
    }

    public List<MenuItem> getDinnerMenuItems() {
        return menuCategoriesMap.get(DINNER);
    }

    public List<MenuItem> getDinnerPortionMenuItems() {
        return menuCategoriesMap.get(DINNER_PORTION);
    }

    public List<MenuItem> getEveningMenuItems() {
        return menuCategoriesMap.get(EVENING);
    }

    public List<MenuItem> getLunchMenuItems() {
        return menuCategoriesMap.get(LUNCH);
    }

    public List<MenuItem> getMealBetweenMenuItems() {
        return menuCategoriesMap.get(MEAL_BETWEEN);
    }

    public Map<String, List<MenuItem>> getMenuCategoriesMap() {
        return menuCategoriesMap;
    }

    public String getPeriode() {
        return periode;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setDietRules(List<Rule> dietRules) {
        this.dietRules = dietRules;
    }

    public void setMenuCategoriesMap(Map<String, List<MenuItem>> menuCategoriesMap) {
        this.menuCategoriesMap = menuCategoriesMap;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

}
