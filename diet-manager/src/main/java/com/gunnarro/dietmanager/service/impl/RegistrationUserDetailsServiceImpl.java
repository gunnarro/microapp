package com.gunnarro.dietmanager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gunnarro.dietmanager.mvc.dto.UserRegistrationForm;
import com.gunnarro.dietmanager.service.UserService;
import com.gunnarro.dietmanager.service.exception.UserAlreadyExistAuthenticationException;
import com.gunnarro.useraccount.domain.user.LocalUser;
import com.gunnarro.useraccount.service.UserAccountService;

/**
 * This service is used to register new user to local database.This is used when
 * a new user want to sign up into application or if a new social user want to
 * register into application.
 *
 */
// @Service
public class RegistrationUserDetailsServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationUserDetailsServiceImpl.class);

    @Autowired
    private UserDetailsService localUserDetailsService;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public LocalUser findLocalUserById(String userId) {
        try {
            return (LocalUser) localUserDetailsService.loadUserByUsername(userId);
        } catch (UsernameNotFoundException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e.getMessage());
            }
            return null;
        }
    }

    @Override
    // @Transactional(value = "transactionManager")
    public LocalUser registerNewUser(final UserRegistrationForm userRegistrationForm) throws UserAlreadyExistAuthenticationException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start register new user: " + userRegistrationForm);
        }
        // use email address as user id
        String userId = userRegistrationForm.getEmail();
        // Check if user already exist
        if (findLocalUserById(userId) != null) {
            throw new UserAlreadyExistAuthenticationException("User already exist! userId: " + userId);
        }
        // create new user
        userAccountService.createSocialUser(userId, userRegistrationForm.getPassword(), userRegistrationForm.getEmail(),
                userRegistrationForm.getSocialProvider());
        try {
            // load and return created user
            return findLocalUserById(userId);
        } catch (Exception e) {
            // something has failed during registration, log error and simply
            LOG.error(null, e);
            return null;
        }
    }

    /**
     * For unit test only, used to set mock
     * 
     * @param localUserDetailsService
     */
    public void setLocalUserDetailsService(UserDetailsService localUserDetailsService) {
        this.localUserDetailsService = localUserDetailsService;
    }

}
