package com.gunnarro.calendar.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.gunnarro.calendar.domain.party.User;

@Component
public class AuthenticationFacade implements AuthenticationFacadeInterface {

	 private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFacade.class);

	    @Override
	    public Authentication getAuthentication() {
	        return SecurityContextHolder.getContext().getAuthentication();
	    }

	    /**
	     * Method get logged in user credentials will fail, when called for users
	     * who are not logged in.
	     */
	    @Override
	    public User getLoggedInUser() {
	        if (getAuthentication() != null) {
	            User user = null;
	            if (getAuthentication().getPrincipal() instanceof User) {
	                user = (User) getAuthentication().getPrincipal();
	            }
	            if (user == null || StringUtils.isEmpty(user.getUserName())) {
	                if (LOG.isDebugEnabled()) {
	                    LOG.debug("principals: " + getAuthentication().getPrincipal());
	                }
	                return null;
	            }
	            return user;
	        }
	        if (LOG.isDebugEnabled()) {
	            LOG.debug("User is not logged in!");
	        }
	        return null;
	    }
	    
}