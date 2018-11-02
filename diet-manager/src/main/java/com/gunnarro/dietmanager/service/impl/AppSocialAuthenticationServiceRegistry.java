package com.gunnarro.dietmanager.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.social.security.SocialAuthenticationServiceRegistry;
import org.springframework.social.security.provider.SocialAuthenticationService;

/**
 * AppSocialAuthenticationServiceRegistry is inherited from
 * SocialAuthenticationServiceRegistry.This bean is used to register different
 * Social authentication services.
 * 
 * For each social provider a Social authentication service should exist and
 * which defines mapping between local user with social user. ONE_TO_ONE : For a
 * social user one local user will be associated. ONE_TO_MANY : Multiple social
 * user can be mapped with single local user. In this application we are using
 * ONE_TO_ONE mapping between local user v/s social user.
 *
 * SocialAuthenticationServiceRegistry will maintains connection repositories
 * per provider.Connection repository handles connection persistence methods
 * across all users; this will be a normal singleton bean in your application
 * context.
 *
 */
// @Service
public class AppSocialAuthenticationServiceRegistry extends SocialAuthenticationServiceRegistry {

    private List<SocialAuthenticationService<?>> authenticationServices;

    public AppSocialAuthenticationServiceRegistry(final List<SocialAuthenticationService<?>> authenticationServices) {
        this.authenticationServices = authenticationServices;
    }

    @PostConstruct
    public void init() {
        if (authenticationServices != null) {
            for (SocialAuthenticationService<?> authenticationService : authenticationServices) {
                super.addAuthenticationService(authenticationService);
            }
        }
    }

}