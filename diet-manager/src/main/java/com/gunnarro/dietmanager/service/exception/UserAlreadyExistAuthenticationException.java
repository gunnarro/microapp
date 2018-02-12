package com.gunnarro.dietmanager.service.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 
 * @author mentos
 *
 */
public class UserAlreadyExistAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = -486349448654622433L;

    public UserAlreadyExistAuthenticationException(final String msg) {
        super(msg);
    }
}