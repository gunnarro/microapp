package com.gunnarro.dietmanager.domain.health;

import org.junit.Assert;
import org.junit.Test;

public class ReferenceDataTest {

    @Test
    public void convertMountsToYear() {
        ReferenceData data = new ReferenceData();
        data.setMonth(179);
        Assert.assertEquals(14, data.getAgeYears());
        Assert.assertEquals(11, data.getAgeMonths());
    }
}
