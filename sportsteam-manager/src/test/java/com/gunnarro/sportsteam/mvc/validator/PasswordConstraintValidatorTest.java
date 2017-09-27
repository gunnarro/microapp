package com.gunnarro.sportsteam.mvc.validator;

import static org.junit.Assert.*;

import org.junit.Test;

public class PasswordConstraintValidatorTest {

    @Test
    public void passwordValid() {
        PasswordConstraintValidator validator = new PasswordConstraintValidator();
        boolean valid = validator.isValid("asDD23rrTTusex", null);
        assertTrue(valid); 
    }
    
    @Test
    public void passwordNotValid() {
        PasswordConstraintValidator validator = new PasswordConstraintValidator();
        boolean valid = validator.isValid("1234", null);
        assertFalse(valid); 
    }
}
