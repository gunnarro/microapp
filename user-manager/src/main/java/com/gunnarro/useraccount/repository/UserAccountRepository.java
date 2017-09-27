package com.gunnarro.useraccount.repository;

import java.util.List;

import com.gunnarro.useraccount.domain.user.LocalUser;
import com.gunnarro.useraccount.domain.user.Profile;
import com.gunnarro.useraccount.domain.user.UserLog;

public interface UserAccountRepository {

    /**
     * 
     * @param userName
     * @return
     */
    public LocalUser getUser(String userName);

    /**
     * 
     * @param userId
     * @return
     */
    public LocalUser getUser(Integer userId);

    /**
     * 
     * @param user
     * @return
     */
    public int createUser(LocalUser user);

    /**
     * 
     * @param user
     * @return
     */
    public int updateUser(LocalUser user);

    /**
     * 
     * @param id
     * @return
     */
    public int deleteUser(Integer id);

    /**
     * 
     * @return
     */
    public List<String> getUserRoles();

    /**
     * 
     * @return
     */
    public List<LocalUser> getUsers();

    /**
     * 
     * @param userId
     * @param password
     * @return
     */
    public int changeUserPwd(Integer userId, String password);

    /**
     * 
     * @param userId
     * @return
     */
    public List<UserLog> getUserLogs();

    /**
     * 
     * @param userId
     * @return
     */
    public UserLog getUserLastLogin(Integer userId);

    /**
     * 
     * @param userLog
     * @return
     */
    public int createUserLog(UserLog userLog);

    /**
     * 
     * @param userId
     * @return
     */
    public void checkIfUserIsBlocked(Integer userId) throws SecurityException;

    /**
     * 
     * @param userId
     * @param numberOfAttemptFailures
     * @return
     */
    public int updateUserLoginAttemptFailures(Integer userId, Integer numberOfAttemptFailures);

    /**
     * 
     * @param userId
     * @param numberOfAttemptSuccess
     * @return
     */
    public int updateUserLoginAttemptSuccess(Integer userId, Integer numberOfAttemptSuccess);

    /**
     * 
     * @param userLog
     * @return
     */
    public int updateUserLog(UserLog userLog);

    /**
     * 
     * @param userId
     * @return
     */
    public Profile getProfile(Integer userId);
}
