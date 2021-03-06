package com.gunnarro.useraccount.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.gunnarro.useraccount.domain.user.LocalUser;

/**
 * Method level security should be applied methods in this interface. Only users
 * with admin rights should be granted access to the user account domain.
 * 
 * @author admin
 *
 */
public interface UserAccountService {

    @PreAuthorize("hasRole('ROLE_ADMIN') AND hasAuthority('ACCOUNT_WRITE_PRIVILEGE')")
    public boolean changeUserPassword(Integer userId, String newPassword, String newPasswordRepeat);

    /**
     * 
     * @param username
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') AND hasAuthority('ACCOUNT_READ_PRIVILEGE')")
    public LocalUser getUser(String username);

    /**
     * 
     * @param userId
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') AND hasAuthority('ACCOUNT_READ_PRIVILEGE')")
    public LocalUser getUser(Integer userId);

    /**
     * 
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') AND hasAuthority('ACCOUNT_READ_PRIVILEGE')")
    public List<LocalUser> getUsers();

    /**
     * 
     * @param user
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') AND hasAuthority('ACCOUNT_WRITE_PRIVILEGE')")
    public int saveUser(LocalUser user);

    /**
     * 
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') AND hasAuthority('ACCOUNT_READ_PRIVILEGE')")
    public List<String> getUserRoles();

    /**
     * 
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') AND hasAuthority('ACCOUNT_WRITE_PRIVILEGE')")
    public int deleteUser(Integer id);

    public int createSocialUser(String username, String password, String email, String providerName);

    /**
     * 
     * @param userId
     * @param ipAddress
     * @param userAgent
     */
    public void loginFailed(Integer userId, String ipAddress, String userAgent);

    /**
     * 
     * @param userId
     * @param ipAddress
     * @param userAgent
     */
    public void loginSucceeded(Integer userId, String ipAddress, String userAgent);

}
