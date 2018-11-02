package com.gunnarro.dietmanager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialAuthenticationException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

import com.gunnarro.useraccount.domain.user.CustomSocialUser;
import com.gunnarro.useraccount.domain.user.LocalUser;

/**
 * Social user details service will just delegate to local user details to
 * verify whether social user exist in local database.
 *
 */
// @Service
public class CustomSocialUserDetailsServiceImpl implements SocialUserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomSocialUserDetailsServiceImpl.class);

    @Autowired
    private UserDetailsService localUserDetailsService;

    @Override
    public SocialUserDetails loadUserByUserId(final String userId) throws UsernameNotFoundException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("social login for userId: " + userId);
        }
        try {
            LocalUser user = (LocalUser) localUserDetailsService.loadUserByUsername(userId);
            // map to social user
            CustomSocialUser socialUser = new CustomSocialUser();
            socialUser.setId(user.getId());
            socialUser.setUserId(user.getUserId());
            socialUser.setUsername(user.getUsername());
            socialUser.setPassword(user.getPassword());
            // socialUser.setRoles(user.getRoles());
            socialUser.setEnabled(user.isEnabled());
            socialUser.setAccountNonExpired(user.isAccountNonExpired());
            socialUser.setCredentialsNonExpired(user.isCredentialsNonExpired());
            socialUser.setAccountNonLocked(user.isAccountNonLocked());
            return socialUser;
        } catch (UsernameNotFoundException e) {
            throw new SocialAuthenticationException("No local user mapped with social user " + userId);
        }
    }
}
