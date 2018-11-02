package com.gunnarro.dietmanager.domain.statistic;

import java.util.Date;

public class BodyMeasurementStatistic {

    private Date startDate;
    private Date endDate;
    private Date maxWeightIncreaseDate;
    private Date maxWeightDecreaseDate;
    private double maxWeightIncrease = 0;
    private double maxWeightDecrease = 999;
    private double weight = 0;
    private double height = 0;
    private double averageWeight = 0;
    private double averageWeightUp = 0;
    private double averageWeightDown = 0;
    private double averageWeightUpStep = 0;
    private double averageWeightDownStep = 0;
    private double averageHeight = 0;
    private double maxWeight = 0;
    private double minWeight = 999;
    private double maxHeight = 0;
    private double minHeight = 999;
    private String weightMetric = "kg";
    private String heightMetric = "cm";

    private int numberOfMeasurements;
    private int numberOfMeasurementsStep;
    private int numberOfUp;
    private int numberOfDown;
    private int numberOfUpStep;
    private int numberOfDownStep;
    private int numberOfNeutral;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getAverageWeight() {
        return averageWeight;
    }

    public void setAverageWeight(double averageWeight) {
        this.averageWeight = averageWeight;
    }

    public double getAverageHeight() {
        return averageHeight;
    }

    public void setAverageHeight(double averageHeight) {
        this.averageHeight = averageHeight;
    }

    public double getAverageWeightUp() {
        return averageWeightUp;
    }

    public void setAverageWeightUp(double averageWeightUp) {
        this.averageWeightUp = averageWeightUp;
    }

    public double getAverageWeightDown() {
        return averageWeightDown;
    }

    public void setAverageWeightDown(double averageWeightDown) {
        this.averageWeightDown = averageWeightDown;
    }

    public double getAverageWeightUpStep() {
        return averageWeightUpStep;
    }

    public void setAverageWeightUpStep(double averageWeightUpStep) {
        this.averageWeightUpStep = averageWeightUpStep;
    }

    public double getAverageWeightDownStep() {
        return averageWeightDownStep;
    }

    public void setAverageWeightDownStep(double averageWeightDownStep) {
        this.averageWeightDownStep = averageWeightDownStep;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public double getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(double minWeight) {
        this.minWeight = minWeight;
    }

    public double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public double getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(double minHeight) {
        this.minHeight = minHeight;
    }

    public String getWeightMetric() {
        return weightMetric;
    }

    public void setWeightMetric(String weightMetric) {
        this.weightMetric = weightMetric;
    }

    public String getHeightMetric() {
        return heightMetric;
    }

    public void setHeightMetric(String heightMetric) {
        this.heightMetric = heightMetric;
    }

    public int getNumberOfMeasurements() {
        return numberOfMeasurements;
    }

    public void increaseNumberOfMeasurements() {
        numberOfMeasurements++;
    }

    public int getNumberOfMeasurementsStep() {
        return numberOfMeasurementsStep;
    }

    public void increaseNumberOfMeasurementsStep() {
        numberOfMeasurementsStep++;
    }

    public int getNumberOfUp() {
        return numberOfUp;
    }

    public void setNumberOfUp(int numberOfUp) {
        this.numberOfUp = numberOfUp;
    }

    public int getNumberOfDown() {
        return numberOfDown;
    }

    public void setNumberOfDown(int numberOfDown) {
        this.numberOfDown = numberOfDown;
    }

    public int getNumberOfNeutral() {
        return numberOfNeutral;
    }

    public void setNumberOfNeutral(int numberOfNeutral) {
        this.numberOfNeutral = numberOfNeutral;
    }

    public int getNumberOfUpStep() {
        return numberOfUpStep;
    }

    public void setNumberOfUpStep(int numberOfUpStep) {
        this.numberOfUpStep = numberOfUpStep;
    }

    public int getNumberOfDownStep() {
        return numberOfDownStep;
    }

    public void setNumberOfDownStep(int numberOfDownStep) {
        this.numberOfDownStep = numberOfDownStep;
    }

    public Date getMaxWeightIncreaseDate() {
        return maxWeightIncreaseDate;
    }

    public void setMaxWeightIncreaseDate(Date maxWeightIncreaseDate) {
        this.maxWeightIncreaseDate = maxWeightIncreaseDate;
    }

    public Date getMaxWeightDecreaseDate() {
        return maxWeightDecreaseDate;
    }

    public void setMaxWeightDecreaseDate(Date maxWeightDecreaseDate) {
        this.maxWeightDecreaseDate = maxWeightDecreaseDate;
    }

    public double getMaxWeightIncrease() {
        return maxWeightIncrease;
    }

    public void setMaxWeightIncrease(double maxWeightIncrease) {
        this.maxWeightIncrease = maxWeightIncrease;
    }

    public double getMaxWeightDecrease() {
        return maxWeightDecrease;
    }

    public void setMaxWeightDecrease(double maxWeightDecrease) {
        this.maxWeightDecrease = maxWeightDecrease;
    }

}
