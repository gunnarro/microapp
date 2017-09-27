package com.gunnarro.tournament.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.gunnarro.tournament.domain.party.User;
import com.gunnarro.tournament.service.TournamentPlannerService;
import com.gunnarro.tournament.service.exception.ApplicationException;

@Component
public class AuthenticationFacade implements AuthenticationFacadeInterface {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFacade.class);

    @Autowired
    @Qualifier("tournamentPlannerService")
    private TournamentPlannerService tournamentPlannerService;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Method get logged in user credentials will fail, when called for
     * users who are not logged in.
     */
    @Override
    public User getLoggedInUser() {
        UserDetails userDetails = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
            userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        // String userName =
        // SecurityContextHolder.getContext().getAuthentication().getName();
        if (userDetails == null || StringUtils.isEmpty(userDetails.getUsername())) {
            // throw new
            // ApplicationException("Object Not Found, missing user name! User is not logged in!");
            if (LOG.isDebugEnabled()) {
                LOG.debug("principals: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            }
            return null;
        }
        User user = tournamentPlannerService.getUser(userDetails.getUsername());
        if (user == null) {
            throw new ApplicationException("Object Not Found, invalid username/password! " + userDetails.getUsername());
        }
        return user;
    }
}