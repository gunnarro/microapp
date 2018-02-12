package com.gunnarro.dietmanager.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * https://developers.google.com/maps/documentation/javascript/geolocation
 * 
 * @author admin
 *
 */
@Controller
public class GeoLocationController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(GeoLocationController.class);

    /**
     * @return the login page
     */
    @RequestMapping(value = "/mylocation", method = RequestMethod.GET)
    public String login() {
        return "view-my-location";
    }

}
