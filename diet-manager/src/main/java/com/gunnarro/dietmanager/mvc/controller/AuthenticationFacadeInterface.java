package com.gunnarro.dietmanager.mvc.controller;

import org.springframework.security.core.Authentication;

import com.gunnarro.useraccount.domain.user.LocalUser;

public interface AuthenticationFacadeInterface {

    /**
     * 
     * @return
     */
    Authentication getAuthentication();

    /**
     * 
     * @return
     */
    LocalUser getLoggedInUser();
}
