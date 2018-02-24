package com.gunnarro.dietmanager.endpoint.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.gunnarro.dietmanager.mvc.controller.AuthenticationFacade;
import com.gunnarro.dietmanager.mvc.dto.SocialProvider;
import com.gunnarro.dietmanager.mvc.dto.UserRegistrationForm;
import com.gunnarro.dietmanager.service.UserService;
import com.gunnarro.dietmanager.service.exception.ApplicationException;
import com.gunnarro.dietmanager.service.exception.UserAlreadyExistAuthenticationException;
import com.gunnarro.useraccount.domain.user.LocalUser;

/**
 * http://shengwangi.blogspot.no/2016/02/response-for-get-post-put-delete-in-
 * rest.html http://www.baeldung.com/exception-handling-for-rest-with-spring
 * 
 * First time login with social users id will be redirected to this page for
 * registration. That happens after social site login.
 * 
 * @author admin
 *
 */
@RestController
public class UserRegistrationController {

    private static final Logger LOG = LoggerFactory.getLogger(UserRegistrationController.class);

    @Autowired
    private AuthenticationFacade authenticationFacade;

    // @Autowired
    // @Qualifier("localUserDetailsService")
    private UserService userService;

    /**
     * If the provider user ID doesn�t match any existing connection ,
     * ProviderSignInController will redirect to a sign up URL. The default sign
     * up URL is "/signup".
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup(HttpServletRequest request, HttpServletResponse response) {
        LocalUser loggedInUser = null;
        try {
            loggedInUser = authenticationFacade.getLoggedInUser();
        } catch (ApplicationException ae) {
            // ignore, not logged inn
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("start registration...{}", loggedInUser);
        }
        UserRegistrationForm userForm = new UserRegistrationForm();
        try {
            if (loggedInUser != null) {
                userForm.setUserId(loggedInUser.getUserId());
                userForm.setEmail(loggedInUser.getEmail());
            }
        } catch (ApplicationException ae) {
            // ignoer, user is not logged in
            if (LOG.isDebugEnabled()) {
                LOG.debug(ae.getMessage());
            }
        }
        ModelAndView model = new ModelAndView();
        model.addObject("user", userForm);
        model.setViewName("registration");
        return model;
    }

    /**
     * 
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationForm registrationForm) throws URISyntaxException {
        if (registrationForm.getSocialProvider() == null) {
            registrationForm.setSocialProvider(SocialProvider.NONE.name());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("register new user: {}", registrationForm.toString());
        }
        try {
            userService.registerNewUser(registrationForm);
            return ResponseEntity.created(new URI("/home")).build();
        } catch (UserAlreadyExistAuthenticationException uaeae) {
            LOG.debug(uaeae.getMessage());
            // Return Conflict (409)
            return new ResponseEntity<>("User already exist!", HttpStatus.CONFLICT);
        }
    }

    /**
     * 
     */
    @RequestMapping(value = "/register/test", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> testRegisterUser(@RequestBody UserRegistrationForm registrationForm) throws UserAlreadyExistAuthenticationException {
        if (LOG.isDebugEnabled()) {
            LOG.debug(registrationForm.toString());
        }

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.put("Cache-Control", Arrays.asList("max-age=3600"));
        ResponseEntity<String> responseEntity = new ResponseEntity<>("User registration successfull", headers, HttpStatus.OK);
        LOG.debug(responseEntity.toString());
        return responseEntity;
    }

    @RequestMapping(value = "/register/user", method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestBody UserRegistrationForm registrationForm, UriComponentsBuilder ucBuilder) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(registrationForm.toString());
        }
        // if(userService.isUserExist(user)){
        // System.out.println("A User with name "+user.getName()+" already
        // exist");
        // return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        // }
        //
        // userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/home").buildAndExpand().toUri());
        return new ResponseEntity<>("Test user registration OK", headers, HttpStatus.CREATED);
    }
}
