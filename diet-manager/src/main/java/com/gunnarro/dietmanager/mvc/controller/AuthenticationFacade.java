package com.gunnarro.dietmanager.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.gunnarro.dietmanager.service.exception.ApplicationException;
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
	 * Method get logged in user credentials will fail, when called for users who
	 * are not logged in.
	 */
	@Override
	public LocalUser getLoggedInUser() {
		LocalUser user = null;
		if (getAuthentication() != null) {
			if (getAuthentication().getPrincipal() instanceof LocalUser) {
				user = (LocalUser) getAuthentication().getPrincipal();
			}
		}
		if (user == null || StringUtils.isEmpty(user.getUsername())) {
			throw new ApplicationException(ApplicationException.NOT_LOGGED_IN);
		}
		return user;
	}

}