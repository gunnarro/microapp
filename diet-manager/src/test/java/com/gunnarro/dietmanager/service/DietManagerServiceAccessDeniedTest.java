package com.gunnarro.dietmanager.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.dietmanager.config.BeanConfiguration;
import com.gunnarro.dietmanager.config.DefaultTestConfig;
import com.gunnarro.dietmanager.config.SecurityConfiguration;
import com.gunnarro.dietmanager.config.TestMariDBDataSourceConfiguration;
import com.gunnarro.dietmanager.config.TestRepositoryConfiguration;
import com.gunnarro.dietmanager.handler.AppSuccessHandler;
import com.gunnarro.dietmanager.repository.impl.DietManagerRepositoryImpl;
import com.gunnarro.dietmanager.service.impl.DietManagerServiceImpl;

@ContextConfiguration(classes = { BeanConfiguration.class, TestMariDBDataSourceConfiguration.class, TestRepositoryConfiguration.class, SecurityConfiguration.class, DietManagerServiceImpl.class,
        DietManagerRepositoryImpl.class, AppSuccessHandler.class })
@Transactional(timeout = 10)
@Rollback
public class DietManagerServiceAccessDeniedTest extends DefaultTestConfig {

    @Autowired
    // @Qualifier("dietManagerService")
    protected DietManagerService dietManagerService;

    @Before
    public void setUp() throws Exception {
        // Because of security we have to set user and pwd before every unit
        // test
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("guest", "guest");
        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(ctx);
        ctx.setAuthentication(authRequest);
    }

    @After
    public void terminate() {
        SecurityContextHolder.clearContext();
    }

    @Test(expected = AccessDeniedException.class)
    public void deleteBodyMeasurementLog() {
        dietManagerService.deleteBodyMeasurementLog(null);
    }

    @Test(expected = AccessDeniedException.class)
    public void deleteDietMenuItem() {
        dietManagerService.deleteDietMenuItem(3);
    }

    @Test(expected = AccessDeniedException.class)
    public void saveSelectedFoodForUser() {
        dietManagerService.saveSelectedFoodForUser(1, null);
    }

    @Test(expected = AccessDeniedException.class)
    public void saveBodyMeasurementLog() {
        dietManagerService.saveBodyMeasurementLog(null);
    }

    @Test(expected = AccessDeniedException.class)
    @Ignore
    public void checkIfSelectedMealAlreadyRegistered() {
        dietManagerService.checkIfSelectedMealAlreadyRegistered(1, null, null);
    }

    @Test(expected = AccessDeniedException.class)
    public void deleteDietMenu() {
        dietManagerService.deleteDietMenu(3);
    }

    @Test(expected = AccessDeniedException.class)
    public void saveDietMenu() {
        dietManagerService.saveDietMenu(null);
    }

    @Test(expected = AccessDeniedException.class)
    public void saveDietMenuItem() {
        dietManagerService.saveDietMenuItem(null);
    }

    @Test(expected = AccessDeniedException.class)
    public void saveDietPlan() {
        dietManagerService.saveDietPlan(null);
    }

    @Test(expected = AccessDeniedException.class)
    public void deleteSelectedFoodForUser() {
        dietManagerService.deleteSelectedFoodForUser(2);
    }
}
