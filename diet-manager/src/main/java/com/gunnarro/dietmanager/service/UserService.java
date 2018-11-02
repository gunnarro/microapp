package com.gunnarro.dietmanager.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.gunnarro.dietmanager.mvc.dto.UserRegistrationForm;
import com.gunnarro.dietmanager.service.exception.UserAlreadyExistAuthenticationException;
import com.gunnarro.useraccount.domain.user.LocalUser;

@Service
public interface UserService {

    /**
     * 
     * @param UserRegistrationForm
     * @return
     * @throws UserAlreadyExistAuthenticationException
     */
    public UserDetails registerNewUser(UserRegistrationForm userRegistrationForm);

    /**
     * 
     * @param userId
     * @return
     */
    public LocalUser findLocalUserById(String userId);

}
