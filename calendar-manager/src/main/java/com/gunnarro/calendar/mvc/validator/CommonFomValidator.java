package com.gunnarro.calendar.mvc.validator;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.gunnarro.calendar.domain.view.CommonFormInputData;

public class CommonFomValidator implements Validator {

	public boolean supports(Class<?> clazz) {
        return CommonFormInputData.class.isAssignableFrom(clazz);
    }
 
    public void validate(Object target, Errors errors) {
//    	 ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "error.id", "Id is required.");
//         ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.name", "Name is required.");
         ValidationUtils.rejectIfEmptyOrWhitespace(errors, "toDate", "error.toDate", "ToDate is required.");
    }
}
