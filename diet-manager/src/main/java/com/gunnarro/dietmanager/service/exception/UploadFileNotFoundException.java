package com.gunnarro.dietmanager.service.exception;

public class UploadFileNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UploadFileNotFoundException(String message) {
        super(message);
    }

    public UploadFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
