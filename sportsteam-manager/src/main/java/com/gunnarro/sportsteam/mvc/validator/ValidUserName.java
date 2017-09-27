package com.gunnarro.sportsteam.mvc.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE, FIELD, ANNOTATION_TYPE}) 
@Retention(RUNTIME)
@Constraint(validatedBy = UserNameValidator.class)
@Documented
public @interface ValidUserName {
    String message() default "Invalid username";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}
