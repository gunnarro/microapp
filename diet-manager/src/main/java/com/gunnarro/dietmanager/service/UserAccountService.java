package com.gunnarro.dietmanager.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.gunnarro.dietmanager.mvc.dto.UserDto;

/**
 * Method level security should be applied methods in this interface. Only users
 * with admin rights should be granted access to the user account domain.
 * 
 * @author admin
 *
 */
@Service
public interface UserAccountService {

    @PreAuthorize("hasRole('ROLE_ADMIN') AND hasAuthority('ACCOUNT_WRITE_PRIVILEGE')")
    public boolean changeUserPassword(Integer userId, String newPassword, String newPasswordRepeat);

    /**
     * 
     * @param username
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') AND hasAuthority('ACCOUNT_READ_PRIVILEGE')")
    public UserDto getUser(String username);

    /**
     * 
     * @param userId
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') AND hasAuthority('ACCOUNT_READ_PRIVILEGE')")
    public UserDto getUser(Integer userId);

    /**
     * 
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') AND hasAuthority('ACCOUNT_READ_PRIVILEGE')")
    public List<UserDto> getUsers();

    /**
     * 
     * @param user
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') AND hasAuthority('ACCOUNT_WRITE_PRIVILEGE')")
    public int saveUser(UserDto user);

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
