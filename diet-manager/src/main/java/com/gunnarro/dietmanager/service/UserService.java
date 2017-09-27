package com.gunnarro.dietmanager.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.gunnarro.dietmanager.mvc.dto.UserRegistrationForm;
import com.gunnarro.dietmanager.service.exception.UserAlreadyExistAuthenticationException;
import com.gunnarro.useraccount.domain.user.LocalUser;

public interface UserService {

	/**
	 * 
	 * @param UserRegistrationForm
	 * @return
	 * @throws UserAlreadyExistAuthenticationException
	 */
	public UserDetails registerNewUser(UserRegistrationForm UserRegistrationForm) throws UserAlreadyExistAuthenticationException;

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public LocalUser findLocalUserById(String userId);
	    
}
