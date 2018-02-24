package com.gunnarro.useraccount.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gunnarro.useraccount.domain.user.LocalUser;
import com.gunnarro.useraccount.domain.user.Role;
import com.gunnarro.useraccount.domain.user.UserLog;
import com.gunnarro.useraccount.repository.UserAccountRepository;
import com.gunnarro.useraccount.repository.table.user.RolesTable.RolesEnum;
import com.gunnarro.useraccount.service.UserAccountService;
import com.gunnarro.useraccount.service.exception.ApplicationException;

/**
 * Use role based access control (RBAC)
 * 
 * @author admin
 *
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    private static final Logger LOG = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    public static final int MAX_LOGIN_ATTEMPT_FAILURES = 3;

    @Autowired
    @Qualifier("userAccountRepository")
    private UserAccountRepository userAccountRepository;

    @Autowired
    @Qualifier("pwdEncoder")
    private PasswordEncoder passwordEncoder;

    /**
     * For unit test only
     * 
     * @param taskManagerRepository
     */
    @Override
    public LocalUser getUser(String userName) {
        return userAccountRepository.getUser(userName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LocalUser> getUsers() {
        return userAccountRepository.getUsers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalUser getUser(Integer userId) {
        LocalUser localUser = userAccountRepository.getUser(userId);
        UserLog userLastLogin = userAccountRepository.getUserLastLogin(userId);
        if (userLastLogin != null && userLastLogin.getNumberOfLoginAttemptFailures() >= MAX_LOGIN_ATTEMPT_FAILURES) {
            userLastLogin.setUserBlocked(true);
        }
        return localUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createSocialUser(String username, String password, String email, String providerName) {
        if (StringUtils.isEmpty(username)) {
            throw new ApplicationException("Error Registrer user! Username can not be null or empty!");
        }

        String encryptedPwd = checkAndEncryptPassword(password, password);
        LocalUser user = new LocalUser();
        user.setUsername(username);
        user.setPassword(encryptedPwd);
        user.setEmail(email);
        user.setSocialProvider(providerName);
        user.setActivated(true);
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(2, RolesEnum.ROLE_USER.name()));
        user.setRoles(roles);

        if (LOG.isDebugEnabled()) {
            LOG.debug("created user, social data: " + user.toString());
        }
        int userId = userAccountRepository.createUser(user);
        if (LOG.isDebugEnabled()) {
            LOG.debug("created social user, userId: " + userId);
        }
        UserLog log = new UserLog();
        log.setUserId(userId);
        log.setLoggedInDate(new Date());
        // we have to create a user detail log for new users
        userAccountRepository.createUserLog(log);
        if (LOG.isDebugEnabled()) {
            LOG.debug("created user log entry: " + log);
        }
        return userId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int saveUser(LocalUser user) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(user.toString());
        }
        if (user.isNew()) {
            // only check password for new account. Password is not part in
            // updates.
            String encryptedPwd = checkAndEncryptPassword(user.getPassword(), user.getPasswordRepeat());
            if (LOG.isDebugEnabled()) {
                LOG.debug("encrypted: " + user.getPassword() + " --> " + encryptedPwd);
            }
            user.setPassword(encryptedPwd);

//            List<Role> roles = new ArrayList<>();
//            if (userDto.isAdminRole()) {
//                roles.add(new Role(1, RolesEnum.ROLE_ADMIN.name()));
//            } else if (userDto.isUserRole()) {
//                roles.add(new Role(2, RolesEnum.ROLE_USER.name()));
//            } else if (userDto.isGuestRole()) {
//                roles.add(new Role(3, RolesEnum.ROLE_GUEST.name()));
//            } else {
//                roles.add(new Role(4, RolesEnum.ROLE_ANONYMOUS.name()));
//            }
//            user.setRoles(roles);
            int userId = userAccountRepository.createUser(user);
            UserLog log = new UserLog();
            log.setUserId(userId);
            log.setLoggedInDate(new Date());
            // we have to create a user detail log for new users
            userAccountRepository.createUserLog(log);
            return userId;
        }
        return userAccountRepository.updateUser(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteUser(Integer id) {
        int deletedRows = userAccountRepository.deleteUser(id);
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted UserId: " + id + " deleted rows: " + deletedRows);
        }
        return deletedRows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getUserRoles() {
        return userAccountRepository.getUserRoles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean changeUserPassword(Integer userId, String password, String passwordRepeat) {
        String encryptedPwd = checkAndEncryptPassword(password, passwordRepeat);
        return userAccountRepository.changeUserPwd(userId, encryptedPwd) == 1 ? true : false;
    }

    /**
     * Check and return a encrypted password.
     * 
     * @param password
     * @param passwordRepeat
     * @return
     */
    private String checkAndEncryptPassword(String password, String passwordRepeat) {
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(passwordRepeat)) {
            throw new ApplicationException("Password or password repeat not set!");
        }
        if (!password.equals(passwordRepeat)) {
            throw new ApplicationException("Password missmatch!");
        }
        return passwordEncoder.encode(password);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void loginSucceeded(Integer userId, String ipAddress, String userAgent) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("login succeeded,! userId = " + userId + ", ip: " + ipAddress + ", user agent: " + userAgent);
        }
        UserLog userLastLogin = userAccountRepository.getUserLastLogin(userId);
        userAccountRepository.updateUserLoginAttemptSuccess(userId, userLastLogin.getNumberOfLoginAttemptSuccess() + 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loginFailed(Integer userId, String ipAddress, String userAgent) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("login failed, check number of failed attemts! userId = " + userId + ", ip: " + ipAddress);
        }
        UserLog userLastLogin = userAccountRepository.getUserLastLogin(userId);
        if (userLastLogin != null) {
            if (userLastLogin.getNumberOfLoginAttemptFailures() <= MAX_LOGIN_ATTEMPT_FAILURES) {
                userAccountRepository.updateUserLoginAttemptFailures(userId, userLastLogin.getNumberOfLoginAttemptFailures() + 1);
            } else {
                throw new ApplicationException("User Blocked, To many failed login attempts!");
            }
        } else {
            LOG.warn("Logged in with invalid userId: " + userId);
        }
    }

    private void resetUserLoginAttemptFailures(Integer userId) {
        userAccountRepository.updateUserLoginAttemptFailures(userId, 0);
    }

}
