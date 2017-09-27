package com.gunnarro.tournament.mvc.controller;

import org.springframework.security.core.Authentication;

import com.gunnarro.tournament.domain.party.User;

public interface AuthenticationFacadeInterface {

    Authentication getAuthentication();
    
    User getLoggedInUser();
}
