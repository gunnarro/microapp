package com.gunnarro.useraccount.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.useraccount.domain.user.LocalUser;
import com.gunnarro.useraccount.domain.user.Profile;
import com.gunnarro.useraccount.domain.user.Role;
import com.gunnarro.useraccount.domain.user.UserLog;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
// @Ignore
public class UserAccountRepositoryTest {

    @Autowired
    @Qualifier("userAccountRepository")
    private UserAccountRepository userAccountRepository;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listUsers() {
        List<LocalUser> users = userAccountRepository.getUsers();
        assertEquals(1, users.get(0).getRoles().size());
    }

    @Test
    public void getProfile() {
        Profile profile = userAccountRepository.getProfile(5);
        assertEquals(1, profile.getId().intValue());
        assertEquals(5, profile.getUserId().intValue());
        assertEquals("Gunnar", profile.getFirstName());
        assertEquals("", profile.getMiddleName());
        assertEquals("Ronneberg", profile.getLastName());
        assertEquals("gunnar_ronneberg@yahoo.no", profile.getEmailAddress());
        assertEquals('M', profile.getGender());
        assertNotNull(profile.getDateOfBirth());
    }
    
    @Test
    public void getUser() {
        LocalUser user = userAccountRepository.getUser(5);
        assertEquals(5, user.getId().intValue());
        assertEquals("pappa", user.getUsername());
        assertEquals(1, user.getRoles().size());
        assertEquals("ROLE_USER", user.getRoles().get(0).getName());
        assertEquals("[Privilege [id=1, name=READ_PRIVILEGE], Privilege [id=2, name=WRITE_PRIVILEGE], Privilege [id=4, name=BLOGG_READ_PRIVILEGE], Privilege [id=5, name=BLOGG_WRITE_PRIVILEGE]]", user.getRoles().get(0).getPrivileges().toString());
        assertNotNull(user.getCreatedDate());
        assertNotNull(user.getLastModifiedDate());
        assertNotNull(user.getPassword());
        assertNotNull(user.getEmail());
        assertTrue(user.getPasswordRepeat() == null);
    }

    @Test
    public void CRUDUser() {
        LocalUser newUser = new LocalUser("test-username", "user-pwd", "user.mail@mail.com");
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(2, "ROLE_USER"));
        newUser.setRoles(roles);
        // create new user
        int id = userAccountRepository.createUser(newUser);
        LocalUser user = userAccountRepository.getUser("test-username");
        assertNotNull(user.getId());
        assertEquals("test-username", user.getUsername());
        assertEquals("user-pwd", user.getPassword());
        assertEquals("user.mail@mail.com", user.getEmail());
        assertTrue(user.isActivated());
        assertEquals(1, user.getRoles().size());
        assertEquals("ROLE_USER", user.getRoles().get(0).getName());
        assertEquals("[Privilege [id=1, name=READ_PRIVILEGE], Privilege [id=2, name=WRITE_PRIVILEGE], Privilege [id=4, name=BLOGG_READ_PRIVILEGE], Privilege [id=5, name=BLOGG_WRITE_PRIVILEGE]]", user.getRoles().get(0).getPrivileges().toString());
        

        // update user
        user.setPassword("new-pwd");
        user.setEmail("new-email@mail.com");
        user.setActivated(false);
//        user.setRoles(Arrays.asList("ROLE_USER"));
        userAccountRepository.updateUser(user);
        LocalUser updatedUser = userAccountRepository.getUser("test-username");
        assertNotNull(updatedUser.getId());
        assertEquals("test-username", updatedUser.getUsername());
        assertEquals("user-pwd", updatedUser.getPassword());
        assertEquals("new-email@mail.com", user.getEmail());
//        assertEquals("[ROLE_USER]", user.getRoles().toString());
        assertFalse(user.isActivated());

        // delete user
        int deleteUserRows = userAccountRepository.deleteUser(id);
        assertTrue(deleteUserRows == 1);
    }

    @Test
    public void changeUserPassword() {
        LocalUser user = userAccountRepository.getUser(5);
        assertEquals("pappa", user.getUsername());
        assertTrue(user.isActivated());
        userAccountRepository.changeUserPwd(user.getId(), "changed-pwd");
        user = userAccountRepository.getUser(5);
        assertEquals("pappa", user.getUsername());
        assertEquals("changed-pwd", user.getPassword());
    }

    @Test
    public void adminUser() {
        LocalUser user = userAccountRepository.getUser("admin");
        assertEquals("admin", user.getUsername());
        assertTrue(user.isActivated());
        assertTrue(user.isAdmin());
        assertFalse(user.isUser());
        assertFalse(user.isGuest());
    }

    @Test
    public void createUserLog() {
        UserLog userLog = new UserLog();
        userLog.setLoggedInDate(new Date());
        userLog.setUserId(3);
        int id = userAccountRepository.createUserLog(userLog);
        Assert.assertTrue(id > 0);
    }
    
    @Test
    public void getUserDatailslog() {
        List<UserLog> userLogs = userAccountRepository.getUserLogs();
        Assert.assertTrue(userLogs.size() == 0);
//        Assert.assertEquals("unit-test", userLogs.get(0).getLoggedInFromDevice());
//        Assert.assertEquals("localhost", userLogs.get(0).getLoggedInFromIpAddress());
    }
    @Ignore
    @Test
    public void userLastLogin() {
        UserLog userLastLogin = userAccountRepository.getUserLastLogin(5);
        Assert.assertNotNull(userLastLogin);
        userAccountRepository.updateUserLoginAttemptFailures(userLastLogin.getUserId(), 3);
        userAccountRepository.updateUserLoginAttemptSuccess(userLastLogin.getUserId(), 1);
        userLastLogin = userAccountRepository.getUserLastLogin(5);
        Assert.assertEquals(1, userLastLogin.getNumberOfLoginAttemptSuccess());
        Assert.assertEquals(3, userLastLogin.getNumberOfLoginAttemptFailures());
        Assert.assertNotNull( userLastLogin.getLoggedInDate());
        Assert.assertNull( userLastLogin.getLoggedInFromDevice());
        Assert.assertNull( userLastLogin.getLoggedInFromIpAddress());
    }
    
    @Ignore
    @Test
    public void updateUserAttempFailure() {
        Assert.assertEquals(1, userAccountRepository.updateUserLoginAttemptFailures(5, 3));
    }
    
    @Ignore
    @Test
    public void updateUserAttempSuccess() {
        Assert.assertEquals(1, userAccountRepository.updateUserLoginAttemptSuccess(5, 3));
    }
    
}
