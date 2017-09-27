package com.gunnarro.calendar.utility;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/**
 * Utility class for filter by date
 * 
 * 
 * @author admin
 *
 */
public class DateFilter {

    /**
     * 
     * @param period valid option are: day, week, month and year
     * @param startDate date to filter
     * @param amount number of days to add to start date
     * @return
     */
    public static boolean filterByDate(String period, Date startDate, int amount) {
        if (period.equals("day") && DateUtils.isSameDay(startDate, DateUtils.addDays(new Date(), amount))) {
            return true;
        } else if (period.equals("week") && isDateInWeek(startDate, amount)) {
            return true;
        } else if (period.equals("month") && isDateInMonth(startDate, amount)) {
            return true;
        } else if (period.equals("year") && isDateInYear(startDate, amount)) {
            return true;
        }
        return false;
    }

    private static int getWeekNumber(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    private static int getMonthNumber(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    private static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    private static boolean isDateInWeek(Date date, int amount) {
        if (isDateInYear(date, amount > 0 ? amount / 52 : 0)) {
            Date week = DateUtils.addWeeks(new Date(), amount);
            return getWeekNumber(date) == getWeekNumber(week);
        }
        return false;
    }

    private static boolean isDateInMonth(Date date, int amount) {
        if (isDateInYear(date, amount > 0 ? amount / 12 : 0)) {
            Date month = DateUtils.addMonths(new Date(), amount);
            return getMonthNumber(date) == getMonthNumber(month);
        }
        return false;
    }

    private static boolean isDateInYear(Date date, int amount) {
        Date year = DateUtils.addYears(new Date(), amount);
        return getYear(date) == getYear(year);
    }

}
