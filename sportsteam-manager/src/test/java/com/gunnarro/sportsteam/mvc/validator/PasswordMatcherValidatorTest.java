package com.gunnarro.sportsteam.mvc.validator;

import static org.junit.Assert.*;
import org.junit.Test;

import com.gunnarro.sportsteam.mvc.dto.UserDto;

public class PasswordMatcherValidatorTest {

    @Test
    public void passwordMatchTrue() {
        PasswordMatcherValidator passwordValidator = new PasswordMatcherValidator();
        UserDto user = new UserDto("username", "pWd", "pWd", "myemail@sportsteam.no");
        boolean valid = passwordValidator.isValid(user, null);
        assertTrue(valid); 
    }
    
    @Test
    public void passwordMatchFalse() {
        PasswordMatcherValidator passwordValidator = new PasswordMatcherValidator();
        UserDto user = new UserDto("username", "pWd", "pwd", "myemail@sportsteam.no");
        boolean valid = passwordValidator.isValid(user, null);
        assertFalse(valid); 
    }
}
