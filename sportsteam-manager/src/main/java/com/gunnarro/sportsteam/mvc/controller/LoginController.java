package com.gunnarro.sportsteam.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gunnarro.sportsteam.domain.party.User;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

@Controller
public class LoginController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    // public String home(HttpServletRequest request, Model model) {
    public String home() {
//        if (LOG.isDebugEnabled()) {
//            LOG.debug(request.getContextPath());
//        }
        try {
            User user = authenticationFacade.getLoggedInUser();
            if (LOG.isDebugEnabled()) {
                LOG.debug("user: " + user);
            }
            if (user == null) {
                // this was an ANONYMOUS user, i.e, not logged in
                return "redirect:/listclubs";
            }
            // direct non activated users to the default start page
            if (!user.isActivated()) {
                return "redirect:/listclubs";
            }

            if (user.isAdmin()) {
                return "redirect:/admin";
            } else if (user.isUser()) {
                return "redirect:/user";
            } else {
                // direct all other user roles to the default start page
                return "redirect:/listclubs";
            }
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            return "redirect:/listclubs";
        }

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        return "logout";
    }

    @RequestMapping(value = "/denied")
    public String denied() {
        return "denied";
    }

}
