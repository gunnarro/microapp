package com.gunnarro.sportsteam.mvc.controller;

import org.springframework.security.core.Authentication;

import com.gunnarro.sportsteam.domain.party.User;

public interface AuthenticationFacadeInterface {

    Authentication getAuthentication();
    
    User getLoggedInUser();
}
