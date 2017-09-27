package com.gunnarro.sportsteam.mvc.validator;

import static org.junit.Assert.*;
import org.junit.Test;


public class EmailValidatorTest {

    @Test
    public void emailAddressValid() {
        EmailValidator emailValidator = new EmailValidator();
        boolean valid = emailValidator.isValid("valid.email@address.no", null);
        assertTrue(valid); 
    }
    
    @Test
    public void emailAddressInvalid() {
        EmailValidator emailValidator = new EmailValidator();
        boolean valid = emailValidator.isValid("invalid_emailaddress.no", null);
        assertFalse(valid); 
    }
}
