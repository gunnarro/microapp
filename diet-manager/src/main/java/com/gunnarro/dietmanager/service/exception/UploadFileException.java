package com.gunnarro.dietmanager.service.exception;

public class UploadFileException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UploadFileException(String message) {
        super(message);
    }

    public UploadFileException(String message, Throwable cause) {
        super(message, cause);
    }

}
