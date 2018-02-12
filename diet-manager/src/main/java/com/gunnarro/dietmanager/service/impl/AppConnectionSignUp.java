package com.gunnarro.dietmanager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

import com.gunnarro.dietmanager.mvc.dto.UserRegistrationForm;
import com.gunnarro.dietmanager.service.UserService;
import com.gunnarro.useraccount.domain.user.LocalUser;

/**
 * If no local user associated with the given connection then connection signup
 * will create a new local user from the given connection.
 *
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 27/3/16
 */
// @Service
public class AppConnectionSignUp implements ConnectionSignUp {

    private static final Logger LOG = LoggerFactory.getLogger(AppConnectionSignUp.class);

    @Autowired
    @Qualifier("registrationUserDetailsService")
    private UserService registrationUserDetailsService;

    /**
     * if the user not exist, the providersignup controller will redirecting the
     * user to the signup page.
     */
    @Override
    public String execute(final Connection<?> connection) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("connection info: ");
            LOG.debug("providerId     : " + connection.getKey().getProviderId());
            LOG.debug("providerUserId : " + connection.getKey().getProviderUserId());
            LOG.debug("displayname    : " + connection.getDisplayName());
            LOG.debug("toString       : " + connection.getKey().toString());
            if (connection.createData() != null) {
                LOG.debug("AccessToken    : " + connection.createData().getAccessToken());
                LOG.debug("AccessToken    : " + connection.createData().getRefreshToken());
            }
        }
        LocalUser localUser = registrationUserDetailsService.findLocalUserById(connection.getDisplayName());
        if (localUser != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("found user, return: " + localUser.toString());
            }
            return localUser.getUserId();
        }
        // No local user found, create a new
        UserRegistrationForm userDetails = mapToUserRegistrationForm(connection.fetchUserProfile(), connection.getDisplayName(),
                connection.getKey().getProviderId());
        LocalUser user = (LocalUser) registrationUserDetailsService.registerNewUser(userDetails);
        if (LOG.isDebugEnabled()) {
            LOG.debug("created new social user: " + user);
        }
        return user.getUserId();
    }

    private UserRegistrationForm mapToUserRegistrationForm(final UserProfile userProfile, String displayName, String socialProvider) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Received userProfile, id: " + userProfile.getId() + ", username: " + userProfile.getUsername() + ", email: " + userProfile.getEmail());
        }
        UserRegistrationForm user = new UserRegistrationForm();
        user.setUserId(displayName);
        user.setPassword("changeme");
        user.setFirstName(userProfile.getFirstName());
        user.setLastName(userProfile.getLastName());
        user.setEmail(userProfile.getEmail());
        user.setSocialProvider(socialProvider);
        if (LOG.isDebugEnabled()) {
            LOG.debug("UserProfile Mapped to user: " + user);
        }
        return user;
    }
}