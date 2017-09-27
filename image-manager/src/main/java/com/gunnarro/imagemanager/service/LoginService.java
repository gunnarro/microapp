package com.gunnarro.imagemanager.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * NOT IN USE
 * @author admin
 *
 */
@Deprecated
public interface LoginService extends UserDetailsService {

    public boolean login(String userName, String password);

    public void logout(String userName);
}
