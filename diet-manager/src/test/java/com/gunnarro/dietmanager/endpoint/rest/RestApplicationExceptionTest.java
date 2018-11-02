package com.gunnarro.dietmanager.endpoint.rest;

import org.junit.Assert;
import org.junit.Test;

public class RestApplicationExceptionTest {

    @Test
    public void errorCode() {
        RestApplicationException ex = new RestApplicationException(405);
        Assert.assertEquals("405", ex.getRestError().getCode());
        Assert.assertEquals("Object not found!", ex.getRestError().getMessage());
        Assert.assertEquals("Object not found! id=405", ex.getMessage());
    }

    @Test
    public void errorMessage() {
        RestApplicationException ex = new RestApplicationException("error msg");
        Assert.assertEquals("-1", ex.getRestError().getCode());
        Assert.assertEquals("error msg", ex.getRestError().getMessage());
        Assert.assertEquals("error msg", ex.getMessage());
    }

    @Test
    public void restError() {
        RestApplicationException ex = new RestApplicationException(new RestError("222", "rest error"));
        Assert.assertEquals("222", ex.getRestError().getCode());
        Assert.assertEquals("rest error", ex.getRestError().getMessage());
        Assert.assertEquals("rest error", ex.getMessage());
    }
}
