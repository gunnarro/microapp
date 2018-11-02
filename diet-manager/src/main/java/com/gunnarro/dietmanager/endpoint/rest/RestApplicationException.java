package com.gunnarro.dietmanager.endpoint.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Requested Resource not found!")
public class RestApplicationException extends RuntimeException {

    private static final long serialVersionUID = 25235234L;
    private final RestError error;

    public RestApplicationException(int id) {
        super("Object not found! id=" + id);
        error = new RestError(Integer.toString(id), "Object not found!");
    }

    public RestApplicationException(RestError error) {
        super(error.getMessage());
        this.error = error;
    }

    public RestApplicationException(String errMsg) {
        super(errMsg);
        error = new RestError(Integer.toString(-1), errMsg);
    }

    public RestError getRestError() {
        return error;
    }

}
