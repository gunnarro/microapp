package com.gunnarro.sportsteam.mvc.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameValidator implements ConstraintValidator<ValidEmail, String> {
    private Pattern pattern;
    private Matcher matcher;
    private static final String USER_NAME_PATTERN = "^[a-z0-9_-]{4,15}$";

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String userName, ConstraintValidatorContext context) {
        return validateUserName(userName);
    }

    private boolean validateUserName(String userName) {
        pattern = Pattern.compile(USER_NAME_PATTERN);
        matcher = pattern.matcher(userName);
        return matcher.matches();
    }
}
