package com.gunnarro.dietmanager.domain.health;

import java.util.Date;

public class Trend {

    private Date fromDate;
    private Date toDate;
    private int numberOfMeasurements;
    private int numberOfUp;
    private int numberOfDown;
    private int numberOfUpStep;
    private int numberOfDownStep;
    private int numberOfNeutral;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getNumberOfMeasurements() {
        return numberOfMeasurements;
    }

    public void setNumberOfMeasurements(int numberOfMeasurements) {
        this.numberOfMeasurements = numberOfMeasurements;
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

    public void increaseUp() {
        this.numberOfUp++;
    }

    public void increaseDownStep() {
        this.numberOfDownStep++;
    }

    public void increaseUpStep() {
        this.numberOfUpStep++;
    }

    public void increaseDown() {
        this.numberOfDown++;
    }

    public void increaseNeutral() {
        this.numberOfNeutral++;
    }

    public int getNumberOfUpStep() {
        return numberOfUpStep;
    }

    public int getNumberOfDownStep() {
        return numberOfDownStep;
    }

}
