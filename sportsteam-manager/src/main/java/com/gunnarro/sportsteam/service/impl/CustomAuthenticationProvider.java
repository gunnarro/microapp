package com.gunnarro.sportsteam.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.gunnarro.sportsteam.service.LoginService;
import com.gunnarro.sportsteam.service.exception.ApplicationException;
import com.gunnarro.sportsteam.utility.Utility;

/**
 * NOT IN USE
 * @author admin
 *
 */
@Deprecated
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private LoginService loginService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        // use the credentials to try to authenticate against the third party
        // system
        if (loginService.login(name, Utility.hashPassword(password))) {
            return new UsernamePasswordAuthenticationToken(name, Utility.hashPassword(password), new ArrayList<GrantedAuthority>());
        } else {
            throw new ApplicationException("Unable to auth against third party systems");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
