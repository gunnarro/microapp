package com.gunnarro.calendar.endpoint.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 404
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Requested Resource not found!")
public class RestApplicationException extends RuntimeException {

    private static final long serialVersionUID = 25235234L;
    private RestError error;

    public RestApplicationException(int id) {
        super("Object not found! id=" + id);
    }

    public RestApplicationException(String errMsg) {
        super(errMsg);
    }

    public RestApplicationException(RestError error) {
        super(error.getMessage());
        this.error = error;
    }

    public RestError getRestError() {
        return error;
    }

    public void setError(RestError error) {
        this.error = error;
    }

}
