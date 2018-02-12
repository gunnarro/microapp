package com.gunnarro.dietmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.security.provider.SocialAuthenticationService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gunnarro.dietmanager.service.impl.AppSocialAuthenticationServiceRegistry;

@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class AppSocialAuthenticationServiceRegistryTest {

    @Autowired
    @Qualifier("appSocialAuthenticationServiceRegistry")
    private AppSocialAuthenticationServiceRegistry registry;

    @Test
    public void checkRegistry() {
        assertNotNull(registry);
        // assertEquals("[facebook]",
        // registry.registeredAuthenticationProviderIds().toString());
        SocialAuthenticationService<?> authenticationService = registry.getAuthenticationService("facebook");
        assertNotNull(authenticationService);
        assertEquals("facebook", authenticationService.getConnectionFactory().getProviderId());

        //
        // authenticationService = registry.getAuthenticationService("github");
        // assertNotNull(authenticationService);
        // assertEquals("github",
        // authenticationService.getConnectionFactory().getProviderId());
    }
}
