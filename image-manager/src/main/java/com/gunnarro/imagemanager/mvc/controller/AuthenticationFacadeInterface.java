package com.gunnarro.imagemanager.mvc.controller;

import org.springframework.security.core.Authentication;

import com.gunnarro.imagemanager.domain.party.User;

public interface AuthenticationFacadeInterface {

    Authentication getAuthentication();
    
    User getLoggedInUser();
}
