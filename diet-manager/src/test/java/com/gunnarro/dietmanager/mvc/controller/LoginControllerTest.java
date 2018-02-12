package com.gunnarro.dietmanager.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.dietmanager.service.DietManagerService;
import com.gunnarro.dietmanager.service.exception.ApplicationException;
import com.gunnarro.useraccount.domain.user.LocalUser;
import com.gunnarro.useraccount.domain.user.Role;

public class LoginControllerTest extends SpringTestSetup {

    @Mock
    private AuthenticationFacade authenticationFacadeMock;

    private LoginController controller;

    @Mock
    private DietManagerService dietManagerServiceMock;

    @Test
    public void denied() throws Exception {
        Assert.assertEquals("access-denied", controller.denied());
    }

    @Test
    public void handleApplicationException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("home");
        ModelAndView mv = controller.handleApplicationException(request, new ApplicationException("test handle application exception"));
        Assert.assertEquals("error", mv.getViewName());
        Assert.assertNotNull(mv.getModel().get("exception"));
        Assert.assertEquals("test handle application exception", ((Exception) mv.getModel().get("exception")).getMessage());
        Assert.assertEquals("home", mv.getModel().get("requestUrl"));
        Assert.assertEquals("home", mv.getModel().get("backUrl"));
    }

    @Test
    public void handleSecurityException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/home");
        ModelAndView mv = controller.handleSecurityException(request, new SecurityException("test handle security exception"));
        Assert.assertEquals("error", mv.getViewName());
        Assert.assertNotNull(mv.getModel().get("exception"));
        Assert.assertEquals("Access Denied. test handle security exception", ((Exception) mv.getModel().get("exception")).getMessage());
        Assert.assertEquals("/home", mv.getModel().get("requestUrl"));
        Assert.assertEquals("/dietmanager/home", mv.getModel().get("backUrl"));
    }

    @Test
    public void home() throws Exception {
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(null);
        Assert.assertEquals("redirect:" + LoginController.HOME_PAGE, controller.home());
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new LoginController();
        controller.setDietManagerService(dietManagerServiceMock);
        controller.setAuthenticationFacade(authenticationFacadeMock);
    }

    @Test
    public void login() throws Exception {
        Assert.assertEquals("login", controller.login());
    }

    @Test
    public void logout() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        LocalUser principal = new LocalUser(23);
        principal.setUserId("unit-test-user");
        principal.setRoles(new ArrayList<Role>());
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, principal.getAuthorities());
        when(authenticationFacadeMock.getAuthentication()).thenReturn(auth);
        request.addParameter("parameterName", "someValue");
        Assert.assertEquals("redirect:login?loggedout", controller.logout(request, null));
    }

    @Test
    public void logoutUser() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("parameterName", "someValue");
        LocalUser principal = new LocalUser(23);
        principal.setUserId("unit-test-user");
        principal.setRoles(new ArrayList<Role>());
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, principal.getAuthorities());
        when(authenticationFacadeMock.getAuthentication()).thenReturn(auth);
        Assert.assertEquals("redirect:login?loggedout", controller.logout(request, null));
    }

}