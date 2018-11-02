package com.gunnarro.dietmanager.domain.health;

public class ReferenceData {

    private Integer id;
    private char gender;
    private int month;
    private double bmiP25;
    private double bmiP50;
    private double bmiP75;

    private double weightP25;
    private double weightP50;
    private double weightP75;

    private double heightP25;
    private double heightP50;
    private double heightP75;

    public int getAgeYears() {
        return month / 12;
    }

    public int getAgeMonths() {
        return month % 12;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getBmiP25() {
        return bmiP25;
    }

    public void setBmiP25(double bmiP25) {
        this.bmiP25 = bmiP25;
    }

    public double getBmiP50() {
        return bmiP50;
    }

    public void setBmiP50(double bmiP50) {
        this.bmiP50 = bmiP50;
    }

    public double getBmiP75() {
        return bmiP75;
    }

    public void setBmiP75(double bmiP75) {
        this.bmiP75 = bmiP75;
    }

    public double getWeightP25() {
        return weightP25;
    }

    public void setWeightP25(double weightP25) {
        this.weightP25 = weightP25;
    }

    public double getWeightP50() {
        return weightP50;
    }

    public void setWeightP50(double weightP50) {
        this.weightP50 = weightP50;
    }

    public double getWeightP75() {
        return weightP75;
    }

    public void setWeightP75(double weightP75) {
        this.weightP75 = weightP75;
    }

    public double getHeightP25() {
        return heightP25;
    }

    public void setHeightP25(double heightP25) {
        this.heightP25 = heightP25;
    }

    public double getHeightP50() {
        return heightP50;
    }

    public void setHeightP50(double heightP50) {
        this.heightP50 = heightP50;
    }

    public double getHeightP75() {
        return heightP75;
    }

    public void setHeightP75(double heightP75) {
        this.heightP75 = heightP75;
    }

}
