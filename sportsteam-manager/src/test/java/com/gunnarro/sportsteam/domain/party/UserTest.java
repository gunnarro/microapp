package com.gunnarro.sportsteam.domain.party;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.gunnarro.sportsteam.service.exception.ApplicationException;

public class UserTest {

    @Test
    public void isAdmin() {
        User user = new User("name", "pwd","email@sportsteam.no");
        assertTrue(user.isActivated());
        assertFalse(user.isAdmin());
        assertFalse(user.isUser());
        assertFalse(user.isGuest());
        user.setRoles(Arrays.asList("ROLE_ADMIN"));
        assertTrue(user.isAdmin());
        assertFalse(user.isUser());
        assertFalse(user.isGuest());
    }

    @Test
    public void isUser() {
        User user = new User("name", "pwd", "email@sportsteam.no");
        assertTrue(user.isActivated());
        assertFalse(user.isAdmin());
        assertFalse(user.isUser());
        assertFalse(user.isGuest());
        user.setRoles(Arrays.asList("ROLE_USER"));
        assertTrue(user.isUser());
        assertFalse(user.isAdmin());
        assertFalse(user.isGuest());
    }

    @Test(expected = ApplicationException.class)
    public void checkPasswordEmpty() {
        User user = new User("name", "pwd", "email@sportsteam.no");
        user.checkPasword();
    }

    @Test(expected = ApplicationException.class)
    public void checkPasswordDiffers() {
        User user = new User("name", "pwd", "email@sportsteam.no");
        user.setPassword("1234");
        user.setPasswordRepeat("123");
        user.checkPasword();
    }

    @Test
    public void checkPasswordOK() {
        User user = new User("name", "pwd", "email@sportsteam.no");
        user.setPassword("1234");
        user.setPasswordRepeat("1234");
        user.checkPasword();
        assertTrue(true);
    }
}
