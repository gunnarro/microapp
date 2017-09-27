package com.gunnarro.imagemanager.domain;

import java.io.Serializable;

import com.gunnarro.imagemanager.endpoint.rest.RestResponse;

public class AlertResponse extends RestResponse implements Serializable {

    private static final long serialVersionUID = 1232323L;
    private String result;

    /**
     * Default constructor used by json mapping
     */
    public AlertResponse() {
    }

    public AlertResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
