package com.gunnarro.sportsteam.service.exception;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = -5086299951349235980L;

    public ValidationException(String message) {
        super(message);
    }

}
