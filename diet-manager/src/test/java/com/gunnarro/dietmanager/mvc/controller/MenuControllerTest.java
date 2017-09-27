package com.gunnarro.dietmanager.mvc.controller;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.dietmanager.domain.diet.DietMenu;
import com.gunnarro.dietmanager.service.DietManagerService;
import com.gunnarro.useraccount.domain.user.LocalUser;

public class MenuControllerTest extends SpringTestSetup {

    @Mock
    private AuthenticationFacade authenticationFacadeMock;

    private MenuController controller;

    @Mock
    private DietManagerService dietManagerServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new MenuController();
        controller.setDietManagerService(dietManagerServiceMock);
        controller.setAuthenticationFacade(authenticationFacadeMock);
    }

    @Test
    public void viewMenu() throws Exception {
        when(dietManagerServiceMock.getDietMenu(1)).thenReturn(new DietMenu());
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(1));
        ModelAndView modelAndView = controller.viewMenu("1");
        Assert.assertEquals("diet/view-diet-menu", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        DietMenu dietMenu = (DietMenu) modelAndView.getModel().get("dietMenu");
        Assert.assertNotNull(dietMenu);
    }

}