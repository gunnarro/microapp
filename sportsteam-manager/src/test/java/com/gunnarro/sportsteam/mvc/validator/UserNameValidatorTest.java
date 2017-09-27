package com.gunnarro.sportsteam.mvc.validator;

import static org.junit.Assert.*;
import org.junit.Test;


public class UserNameValidatorTest {

    @Test
    public void userNameValid() {
    	UserNameValidator validator = new UserNameValidator();
        boolean valid = validator.isValid("gunnarro", null);
        assertTrue(valid); 
    }
    
    @Test
    public void userNameInvalid() {
    	UserNameValidator validator = new UserNameValidator();
        boolean valid = validator.isValid("gro", null);
        assertFalse(valid); 
    }
}
