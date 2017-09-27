package com.gunnarro.imagemanager.endpoint.rest;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public abstract class RestResponse implements Serializable {

    private static final long serialVersionUID = -3789431120521785723L;
    private RestError error;

    public RestResponse() {
    }

    public RestError getError() {
        return error;
    }

    public void setError(RestError error) {
        this.error = error;
    }
}