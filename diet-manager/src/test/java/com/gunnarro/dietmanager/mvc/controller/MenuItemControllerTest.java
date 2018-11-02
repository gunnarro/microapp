package com.gunnarro.dietmanager.mvc.controller;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.dietmanager.domain.diet.MenuItem;
import com.gunnarro.dietmanager.service.DietManagerService;
import com.gunnarro.useraccount.domain.user.LocalUser;

public class MenuItemControllerTest extends SpringTestSetup {

    @Mock
    private AuthenticationFacade authenticationFacadeMock;

    private MenuItemController controller;

    @Mock
    private DietManagerService dietManagerServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new MenuItemController();
        controller.setDietManagerService(dietManagerServiceMock);
        controller.setAuthenticationFacade(authenticationFacadeMock);
    }

    @Test
    public void viewMenuItem() throws Exception {
        when(dietManagerServiceMock.getDietMenuItem(1)).thenReturn(new MenuItem());
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(1));
        ModelAndView modelAndView = controller.viewMenuItem(1);
        Assert.assertEquals("diet/view-menu-item", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        MenuItem menuItem = (MenuItem) modelAndView.getModel().get("menuItem");
        Assert.assertNotNull(menuItem);
    }

}