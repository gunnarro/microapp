package com.gunnarro.dietmanager.mvc.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.gunnarro.dietmanager.mvc.dto.UserRegistrationForm;
import com.gunnarro.dietmanager.service.UserService;

public class AccountController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private UserService userService;

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegisterNewUser(@Valid @ModelAttribute("user") UserRegistrationForm registrationForm, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(registrationForm.toString());
        }
        userService.registerNewUser(registrationForm);
        return "redirect:/home";
    }
}
