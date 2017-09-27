package com.gunnarro.calendar.utility;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Ignore;
import org.junit.Test;

public class DateFilterTest {

    @Test
    public void filterByDate() {
        assertTrue(DateFilter.filterByDate("day", new Date(), 0));
        assertFalse(DateFilter.filterByDate("day", new Date(), 1));
        assertFalse(DateFilter.filterByDate("day", new Date(), -1));
        
        Date todayPluss5days = DateUtils.addDays(new Date(), 5);
        assertTrue(DateFilter.filterByDate("day", todayPluss5days, 5));
        assertFalse(DateFilter.filterByDate("day", todayPluss5days, 6));
        assertFalse(DateFilter.filterByDate("day", todayPluss5days, 4));
        
        Date datePluss1year = DateUtils.addYears(new Date(), 1);
        assertFalse(DateFilter.filterByDate("day", datePluss1year, 0));
    }

    @Ignore
    @Test
    public void isDateInWeek() {
        // to day is in current week
        assertTrue(DateFilter.filterByDate("week", new Date(), 0));
        Date datePluss9Days = DateUtils.addDays(new Date(), 8);
        // today plus 9 days is not in current week
        assertFalse(DateFilter.filterByDate("week", datePluss9Days, 0));
        // today plus 9 days should match next week
        assertTrue(DateFilter.filterByDate("week", datePluss9Days, 1));
        // today plus 9 days should not match 2 week past
        assertFalse(DateFilter.filterByDate("week", datePluss9Days, 2));
        Date datePluss1year = DateUtils.addYears(new Date(), 1);
        assertFalse(DateFilter.filterByDate("week", datePluss1year, 0));
    }

    @Test
    public void isDateInMonth() {
        assertTrue(DateFilter.filterByDate("month", new Date(), 0));
        Date datePluss31Days = DateUtils.addDays(new Date(), 31);
        assertFalse(DateFilter.filterByDate("month", datePluss31Days, 0));
        assertTrue(DateFilter.filterByDate("month", datePluss31Days, 1));
        assertFalse(DateFilter.filterByDate("month", datePluss31Days, 2));
        Date datePluss1Year = DateUtils.addYears(new Date(), 1);
        assertFalse(DateFilter.filterByDate("month", datePluss1Year, 0));
    }

    @Test
    public void isDateInYear() {
        assertTrue(DateFilter.filterByDate("year", new Date(), 0));
        Date datePluss365Days = DateUtils.addDays(new Date(), 365);
        assertFalse(DateFilter.filterByDate("year", datePluss365Days, 0));
        Date datePluss1year = DateUtils.addYears(new Date(), 1);
        assertFalse(DateFilter.filterByDate("year", datePluss1year, 0));
    }

}
