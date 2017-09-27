package com.gunnarro.sportsteam.mvc.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String emailAddress, ConstraintValidatorContext context) {
        return validateEmailAddress(emailAddress);
    }

    private boolean validateEmailAddress(String emailAddress) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }
}
