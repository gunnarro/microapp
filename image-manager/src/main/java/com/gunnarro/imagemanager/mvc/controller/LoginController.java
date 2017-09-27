package com.gunnarro.imagemanager.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gunnarro.imagemanager.domain.party.User;
import com.gunnarro.imagemanager.service.exception.ApplicationException;

@Controller
public class LoginController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
    public static final  int GUEST_USER_ID = 3;
    public static final  int ANONYMOUS_USER_ID = 3;
    
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        try {
            User user = authenticationFacade.getLoggedInUser();
            if (LOG.isDebugEnabled()) {
                LOG.debug("user: " + user);
            }
            if (user == null) {
                // this was an ANONYMOUS user, i.e, not logged in
                return "redirect:/gallery";
            }
            // direct non activated users to the default start page
            if (!user.isActivated()) {
                return "redirect:/gallery";
            }

            if (user.isAdmin()) {
                return "redirect:/admin";
            } else if (user.isUser()) {
                return "redirect:/gallery";
            } else {
                // direct all other user roles to the default start page
                return "redirect:/gallery";
            }
        } catch (ApplicationException ae) {
            // Will direct user to the error page
            throw ae;
        }
    }

    /**
     * @return the login page
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * Log out user and redirects to the login page
     *
     * @param request
     * @param response
     * @return the login page
     */
    @RequestMapping(value="/perform-logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("start logout user...");
        Authentication auth = authenticationFacade.getAuthentication();
        if (LOG.isDebugEnabled()) {
            LOG.debug("auth: " + auth);
        }
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
            if (LOG.isDebugEnabled()) {
                LOG.debug("logged out: " + auth.getName());
            }
        }
        return "redirect:/login?logout";
    }
    
    @RequestMapping(value = "/access-denied")
    public String denied() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("for user:" + authenticationFacade.getLoggedInUser());
        }
        return "access-denied";
    }
}
