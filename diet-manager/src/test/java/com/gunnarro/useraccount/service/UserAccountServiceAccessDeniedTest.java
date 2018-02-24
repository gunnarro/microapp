package com.gunnarro.useraccount.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.dietmanager.config.BeanConfiguration;
import com.gunnarro.dietmanager.config.DefaultTestConfig;
import com.gunnarro.dietmanager.config.SecurityConfiguration;
import com.gunnarro.dietmanager.config.TestMariDBDataSourceConfiguration;
import com.gunnarro.dietmanager.config.TestRepositoryConfiguration;
import com.gunnarro.dietmanager.handler.AppSuccessHandler;
import com.gunnarro.useraccount.domain.user.LocalUser;
import com.gunnarro.useraccount.service.impl.UserAccountServiceImpl;

@ContextConfiguration(classes = { BeanConfiguration.class, TestMariDBDataSourceConfiguration.class, TestRepositoryConfiguration.class, UserAccountServiceImpl.class, SecurityConfiguration.class,
        AppSuccessHandler.class })
@Transactional(timeout = 10)
//@Ignore
public class UserAccountServiceAccessDeniedTest extends DefaultTestConfig {

    @Autowired
    protected UserAccountService userAccountService;

    @Before
    public void setUp() throws Exception {
        // Because of security we have to set user and pwd before every unit
        // test
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("pappa", "pappa");
        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(ctx);
        ctx.setAuthentication(authRequest);
    }

    @After
    public void terminate() {
        SecurityContextHolder.clearContext();
    }

    @Test(expected = AccessDeniedException.class)
    public void getUserById() {
        userAccountService.getUser(5);
    }

    @Test(expected = AccessDeniedException.class)
    public void getUserByName() {
        userAccountService.getUser("admin");
    }

    @Test(expected = AccessDeniedException.class)
    public void getUsers() {
        userAccountService.getUsers();
    }

    @Test(expected = AccessDeniedException.class)
    public void getUserRoles() {
        userAccountService.getUserRoles();
    }

    @Test(expected = AccessDeniedException.class)
    public void getDeleteUser() {
        userAccountService.deleteUser(1);
    }

    @Test(expected = AccessDeniedException.class)
    public void changeUserPassword() {
        userAccountService.changeUserPassword(1, "newPassword", "newPasswordRepeat");
    }

    @Test(expected = AccessDeniedException.class)
    public void saveUser() {
        userAccountService.saveUser(new LocalUser());
    }
}
