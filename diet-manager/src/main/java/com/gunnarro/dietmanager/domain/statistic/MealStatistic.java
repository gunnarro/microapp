package com.gunnarro.dietmanager.domain.statistic;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.support.GenericApplicationContextExtensionsKt;

public class MealStatistic implements Serializable, Comparable<MealStatistic> {

    private static final long serialVersionUID = 8141619760462227823L;

    private Date createdDate;
    private Date toDate;
    private Date fromDate;
    private Integer year;
    private Integer weekNumber;
    private Integer userId;
    private Integer mealsControlledByUserCount;
    private Integer mealsPreparedByUserCount;
    private Integer mealsCausedConflictCount;
    private String userName;
    private String period;

    public MealStatistic() {
    }

    /**
     * only for unit test
     * 
     * @param year
     * @param weekNumber
     */
    public MealStatistic(int year, int weekNumber) {
        this.year = year;
        this.weekNumber = weekNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMealsControlledByUserCount() {
        return mealsControlledByUserCount;
    }

    public void setMealsControlledByUserCount(Integer mealsControlledByUserCount) {
        this.mealsControlledByUserCount = mealsControlledByUserCount;
    }

    public Integer getMealsPreparedByUserCount() {
        return mealsPreparedByUserCount;
    }

    public void setMealsPreparedByUserCount(Integer mealsPreparedByUserCount) {
        this.mealsPreparedByUserCount = mealsPreparedByUserCount;
    }

    public Integer getMealsCausedConflictCount() {
        return mealsCausedConflictCount;
    }

    public void setMealsCausedConflictCount(Integer mealsCausedConflictCount) {
        this.mealsCausedConflictCount = mealsCausedConflictCount;
    }

    public String sortBy() {
        String weekNr = weekNumber.toString();
        if (weekNr.length() == 1) {
            weekNr = "0" + weekNumber.toString();
        }
        return year.toString() + "-" + weekNr;
    }

    @Override
    public int compareTo(MealStatistic o) {
        return this.sortBy().compareTo(o.sortBy());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sortBy() == null) ? 0 : sortBy().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MealStatistic other = (MealStatistic) obj;
        if (sortBy() == null) {
            if (other.sortBy() != null)
                return false;
        } else if (!sortBy().equals(other.sortBy()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "MealStatistic [createdDate=" + createdDate + ", year=" + year + ", weekNumber=" + weekNumber + ", userId=" + userId
                + ", mealsControlledByUserCount=" + mealsControlledByUserCount + ", mealsPreparedByUserCount=" + mealsPreparedByUserCount
                + ", mealsCausedConflictCount=" + mealsCausedConflictCount + ", userName=" + userName + ", period=" + period + "]";
    }

}
