package com.gunnarro.sportsteam.mvc.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.gunnarro.sportsteam.mvc.dto.UserDto;

public class PasswordMatcherValidator implements ConstraintValidator<PasswordMatcher, Object> {
    @Override
    public void initialize(PasswordMatcher constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserDto user = (UserDto) obj;
        return user.getPassword().equals(user.getPasswordRepeat());
    }

}
