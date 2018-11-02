package com.gunnarro.dietmanager.service.exception;

/**
 * 
 * @author mentos
 *
 */
public class ApplicationException extends RuntimeException {

    public static final String NOT_LOGGED_IN = "Not logged in!";

    private static final long serialVersionUID = -5086299951349235980L;

    public ApplicationException(String message) {
        super(message);
    }

}
