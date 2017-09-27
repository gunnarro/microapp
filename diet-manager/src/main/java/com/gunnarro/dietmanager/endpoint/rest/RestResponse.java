package com.gunnarro.dietmanager.endpoint.rest;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public abstract class RestResponse implements Serializable {

    private static final long serialVersionUID = -3789431120521785723L;
    private HttpStatus httpStatus;
    private RestError error;

    public RestResponse() {
    }

    public RestResponse(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public RestError getError() {
        return error;
    }

    public void setError(RestError error) {
        this.error = error;
    }
}