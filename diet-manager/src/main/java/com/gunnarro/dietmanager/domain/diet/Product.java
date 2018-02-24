package com.gunnarro.dietmanager.domain.diet;

import com.gunnarro.dietmanager.domain.BaseDomain;

public class Product extends BaseDomain {

    private static final long serialVersionUID = -8971476408919868380L;
    private String type;
    private String category;
    private String imageLink;
    private Integer amount;
    private Integer weight;
    private Integer kcal;
    private Double fat;
    private Double carbohydrates;
    private Double fibre;
    private Double protein;

    /**
     * Default constructor used by unit tests only
     */
    public Product() {
        // Used by unit tests
    }

    public Product(String name, String description) {
        setName(name);
        setDescription(description);
    }

    // @Override
    // public void setDescription(@NotNull @Size(min = 4, max = 100) String
    // description) {
    // super.description = description;
    // }
    //
    // @Override
    // public void setName(@NotNull @Size(min = 4, max = 30) String name) {
    // this.name = name;
    // }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * 
     * @return weight in kilogram
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * Weight in kilogram
     * 
     * @param weight
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getKcal() {
        return kcal;
    }

    public void setKcal(Integer kcal) {
        this.kcal = kcal;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public Double getFibre() {
        return fibre;
    }

    public void setFibre(Double fibre) {
        this.fibre = fibre;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

}
