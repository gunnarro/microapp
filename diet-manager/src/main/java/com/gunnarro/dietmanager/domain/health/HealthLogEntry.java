package com.gunnarro.dietmanager.domain.health;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.gunnarro.dietmanager.domain.BaseDomain;

public class HealthLogEntry extends BaseDomain {

    private static final long serialVersionUID = 3799683509174086447L;

    // DateTime birthDate = new
    // DateTime().withDayOfMonth(22).withMonthOfYear(1).withYear(2002);//
    // withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillis(0);

    private String comment;
    @NotNull
    @Min(10)
    @Max(220)
    private Double height = 0d;
    private Double referenceHeight;
    private String heightMetric = "cm";
    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date logDate = new Date();
    private Integer monthsOld;
    private int userId;
    @NotNull
    @Min(1)
    @Max(150)
    @NumberFormat(style = Style.NUMBER, pattern = "###.#")
    private Double weight = 0d;
    private Double referenceWeight;
    private String weightMetric = "kg";
    private Integer trendWeight = 0;

    /**
     * 
     */
    public HealthLogEntry() {
    }

    /**
     * @param height
     * @param logDate
     * @param weight
     */
    public HealthLogEntry(Date logDate, Double height, Double weight) {
        this.logDate = logDate;
        this.height = height;
        this.weight = weight;
    }

    /**
     * Calculates bmi. bmi = weight / Math.pow(height, 2)
     * 
     * @return
     */
    public double getBmi() {
        // convert to meter
        double h = height / 100;
        return weight / Math.pow(h, 2);
    }

    /**
     * The reference data is based on the age of the person Calculates bmi. bmi
     * = weight / Math.pow(height, 2)
     * 
     * @return
     */
    public double getReferenceBmi() {
        // convert to meter
        double h = referenceHeight / 100;
        return referenceWeight / Math.pow(h, 2);
    }

    public String getComment() {
        return comment;
    }

    public Double getHeight() {
        return height;
    }

    public String getHeightMetric() {
        return heightMetric;
    }

    public Date getLogDate() {
        return logDate;
    }

    public Integer getMonthsOld() {
        return monthsOld;
    }

    public int getUserId() {
        return userId;
    }

    public Double getWeight() {
        return weight;
    }

    public String getWeightMetric() {
        return weightMetric;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setHeightMetric(String heightMetric) {
        this.heightMetric = heightMetric;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public void setMonthsOld(Integer monthsOld) {
        this.monthsOld = monthsOld;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setWeightMetric(String weightMetric) {
        this.weightMetric = weightMetric;
    }

    public Double getReferenceHeight() {
        return referenceHeight;
    }

    public void setReferenceHeight(Double referenceHeight) {
        this.referenceHeight = referenceHeight;
    }

    public Double getReferenceWeight() {
        return referenceWeight;
    }

    public void setReferenceWeight(Double referenceWeight) {
        this.referenceWeight = referenceWeight;
    }

    /**
     * 1 : increase from previous measurement 0 : equal previous measurement -1
     * : decrease from previous measurement
     * 
     * @return
     */
    public Integer getTrendWeight() {
        return trendWeight;
    }

    public void setTrendWeight(Integer trendWeight) {
        this.trendWeight = trendWeight;
    }

    @Override
    public String toString() {
        return "HealthLogEntry [userId=" + userId + ", logDate=" + logDate + ", weight=" + weight + ", height=" + height + ", monthsOld=" + monthsOld
                + ", weightMetric=" + weightMetric + ", heightMetric=" + heightMetric + ", comment=" + comment + "]";
    }

}
