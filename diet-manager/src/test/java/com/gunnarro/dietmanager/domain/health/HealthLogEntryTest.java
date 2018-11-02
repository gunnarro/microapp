package com.gunnarro.dietmanager.domain.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class HealthLogEntryTest {

    private Validator validator;

    @Before
    public void init() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();
    }

    @Test
    public void trend() {
        Double prev = new Double(1.3);
        Double curr = new Double(1.2);
        System.out.println("curr trend: " + curr.compareTo(prev));
        int size = Math.abs((4 - 3) * (2 - 4));
    }

    @Test
    public void heightInvalidValue() {
        HealthLogEntry log = new HealthLogEntry();
        log.setLogDate(new Date());
        log.setWeight(85d);
        log.setHeight(5.23);
        Set<ConstraintViolation<HealthLogEntry>> violations = validator.validate(log);
        assertEquals(1, violations.size());
        assertEquals("height", violations.iterator().next().getPropertyPath().toString());
        assertEquals("must be greater than or equal to 10", violations.iterator().next().getMessage());

        log.setHeight(230d);
        violations = validator.validate(log);
        assertEquals("height", violations.iterator().next().getPropertyPath().toString());
        assertEquals("must be less than or equal to 220", violations.iterator().next().getMessage());
    }

    @Test
    public void heightNotNull() {
        HealthLogEntry log = new HealthLogEntry();
        log.setLogDate(new Date());
        log.setWeight(85d);
        Set<ConstraintViolation<HealthLogEntry>> violations = validator.validate(log);
        assertEquals(1, violations.size());
        assertEquals("height", violations.iterator().next().getPropertyPath().toString());
        assertEquals("must be greater than or equal to 10", violations.iterator().next().getMessage());
    }

    @Test
    public void logDateNotNull() {
        HealthLogEntry log = new HealthLogEntry();
        log.setWeight(23.0);
        log.setHeight(170d);
        log.setLogDate(null);
        Set<ConstraintViolation<HealthLogEntry>> violations = validator.validate(log);
        assertEquals(1, violations.size());
        assertEquals("logDate", violations.iterator().next().getPropertyPath().toString());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }

    @Test
    public void validateOK() {
        HealthLogEntry log = new HealthLogEntry();
        log.setLogDate(new Date());
        log.setWeight(46.0);
        log.setHeight(160d);
        Set<ConstraintViolation<HealthLogEntry>> violations = validator.validate(log);
        for (ConstraintViolation<HealthLogEntry> error : violations) {
            System.out.println(error);
        }
        assertTrue(violations.isEmpty());
        assertEquals(17, new Double(log.getBmi()).intValue());
    }

    @Test
    public void weightInvalidValue() {
        HealthLogEntry log = new HealthLogEntry();
        log.setLogDate(new Date());
        log.setWeight(0.5);
        log.setHeight(175.2);
        Set<ConstraintViolation<HealthLogEntry>> violations = validator.validate(log);
        assertEquals(1, violations.size());
        assertEquals("weight", violations.iterator().next().getPropertyPath().toString());
        assertEquals("must be greater than or equal to 1", violations.iterator().next().getMessage());

        log.setWeight(230d);
        violations = validator.validate(log);
        assertEquals("weight", violations.iterator().next().getPropertyPath().toString());
        assertEquals("must be less than or equal to 150", violations.iterator().next().getMessage());
    }

    @Test
    public void weightNotNull() {
        HealthLogEntry log = new HealthLogEntry();
        log.setLogDate(new Date());
        log.setHeight(170d);
        Set<ConstraintViolation<HealthLogEntry>> violations = validator.validate(log);
        assertEquals(1, violations.size());
        assertEquals("weight", violations.iterator().next().getPropertyPath().toString());
        assertEquals("must be greater than or equal to 1", violations.iterator().next().getMessage());
    }

    @Test
    public void checkAgainstReferenceData() {
        HealthLogEntry log = new HealthLogEntry();
        log.setLogDate(new Date());
        log.setWeight(47.4);
        log.setHeight(160.2);
        log.setReferenceWeight(55.0);
        log.setReferenceHeight(165.2);
        assertEquals(7, (long) (log.getReferenceWeight() - log.getWeight()));
        assertEquals(5, (long) (log.getReferenceHeight() - log.getHeight()));
        assertEquals(1, (long) (log.getReferenceBmi() - log.getBmi()));
    }
}
