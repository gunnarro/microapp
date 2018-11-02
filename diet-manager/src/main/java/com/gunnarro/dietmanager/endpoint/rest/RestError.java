package com.gunnarro.dietmanager.endpoint.rest;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestError implements Serializable {

    private static final long serialVersionUID = -1304858547257751062L;
    private final String code;
    private final String message;

    public RestError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
