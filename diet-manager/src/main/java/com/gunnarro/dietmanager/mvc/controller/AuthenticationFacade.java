package com.gunnarro.dietmanager.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.security.SocialUser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.gunnarro.dietmanager.service.exception.ApplicationException;
import com.gunnarro.useraccount.domain.user.CustomSocialUser;
import com.gunnarro.useraccount.domain.user.LocalUser;

@Component
public class AuthenticationFacade implements AuthenticationFacadeInterface {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFacade.class);

    @Override
    public Authentication getAuthentication() {
        LOG.debug(".....getAuth...");
        Authentication authentication = null;
        if (SecurityContextHolder.getContext() != null) {
            authentication = SecurityContextHolder.getContext().getAuthentication();
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("authentication: " + authentication);
        }
        return authentication;
    }

    /**
     * Method get logged in user credentials will fail, when called for users
     * who are not logged in.
     */
    @Override
    public LocalUser getLoggedInUser() {
        LocalUser user = null;
        if (getAuthentication() != null) {
            if (getAuthentication().getPrincipal() instanceof LocalUser) {
                user = (LocalUser) getAuthentication().getPrincipal();
            } else if (getAuthentication().getPrincipal() instanceof SocialUser) {
                return mapSocialUserToLocalUser((CustomSocialUser) getAuthentication().getPrincipal());
            }
        }
        if (user == null || StringUtils.isEmpty(user.getUsername())) {
            throw new ApplicationException(ApplicationException.NOT_LOGGED_IN);
        }
        return user;
    }

    private LocalUser mapSocialUserToLocalUser(CustomSocialUser socialUser) {
        LocalUser localUser = new LocalUser();
        localUser.setId(socialUser.getId());
        localUser.setUserId(socialUser.getUserId());
        localUser.setUsername(socialUser.getUsername());
        localUser.setPassword(socialUser.getPassword());
        // Collection<GrantedAuthority> authorities =
        // socialUser.getAuthorities();
        // List<Role> roles = new ArrayList<Role>();
        // Role role = new Role(2, RolesEnum.ROLE_USER.name());
        // List<Privilege> privileges = new ArrayList<Privilege>();
        // for (GrantedAuthority auth : authorities) {
        // privileges.add(new Privilege(1, auth.getAuthority()));
        // }
        // role.setPrivileges(privileges);
        // roles.add(role);
        localUser.setRoles(socialUser.getRoles());
        if (LOG.isDebugEnabled()) {
            LOG.debug(localUser.toString());
        }
        return localUser;
    }
}