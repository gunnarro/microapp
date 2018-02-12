package com.gunnarro.useraccount.service;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.dietmanager.config.DefaultTestConfig;
import com.gunnarro.dietmanager.config.TestMariDBDataSourceConfiguration;
import com.gunnarro.dietmanager.config.TestRepositoryConfiguration;
import com.gunnarro.useraccount.domain.user.LocalUser;
import com.gunnarro.useraccount.domain.user.Role;
import com.gunnarro.useraccount.service.impl.UserAccountServiceImpl;

@ContextConfiguration(classes = { TestMariDBDataSourceConfiguration.class, TestRepositoryConfiguration.class, UserAccountServiceImpl.class })
@Transactional(timeout = 10)
public class UserAccountServiceTest extends DefaultTestConfig {

    @Autowired
    protected UserAccountService userAccountService;

    @Before
    public void setUp() throws Exception {
        // Because of security we have to set user and pwd before every unit
        // test
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("admin", "uiL2oo3");
        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(ctx);
        ctx.setAuthentication(authRequest);
    }

    @After
    public void terminate() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void getUser() {
        LocalUser user = userAccountService.getUser(5);
        Assert.assertEquals("pappa", user.getUsername());
    }

    @Test
    public void saveUser() {
        LocalUser newUser = new LocalUser();
        newUser.setUsername("unit-test");
        newUser.setPassword("unit-test-pwd");
        newUser.setPasswordRepeat("unit-test-pwd");
        newUser.setRoles(Arrays.asList(new Role(1, "ROLE_USER")));
        newUser.setEmail("unit-test@mail.com");
        Assert.assertTrue(newUser.isUser());
        int userId = userAccountService.saveUser(newUser);
        LocalUser user = userAccountService.getUser(userId);
        Assert.assertEquals("unit-test", user.getUsername());
        Assert.assertEquals(
                "[Role [id=1, name=ROLE_ADMIN, privileges=[Privilege [id=1, name=READ_PRIVILEGE], Privilege [id=2, name=WRITE_PRIVILEGE], Privilege [id=4, name=BLOGG_READ_PRIVILEGE], Privilege [id=5, name=BLOGG_WRITE_PRIVILEGE], Privilege [id=6, name=ACCOUNT_READ_PRIVILEGE], Privilege [id=7, name=ACCOUNT_WRITE_PRIVILEGE]]]]",
                user.getRoles().toString());
        Assert.assertEquals("unit-test@mail.com", user.getEmail());
        // password is encrypted
        Assert.assertNotEquals("unit-test-pwd", user.getPassword());
    }

}
