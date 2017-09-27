package com.gunnarro.calendar.mvc.controller;

import org.springframework.security.core.Authentication;

import com.gunnarro.calendar.domain.party.User;

public interface AuthenticationFacadeInterface {

    Authentication getAuthentication();
    
    User getLoggedInUser();
}
