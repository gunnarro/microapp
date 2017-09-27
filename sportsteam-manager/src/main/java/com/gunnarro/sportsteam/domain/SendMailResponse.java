package com.gunnarro.sportsteam.domain;

import java.io.Serializable;

import com.gunnarro.sportsteam.endpoint.rest.RestResponse;

public class SendMailResponse extends RestResponse implements Serializable {

    private static final long serialVersionUID = 1232323L;
    private String result;

    /**
     * Default constructor used by json mapping
     */
    public SendMailResponse() {
    }

    public SendMailResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
