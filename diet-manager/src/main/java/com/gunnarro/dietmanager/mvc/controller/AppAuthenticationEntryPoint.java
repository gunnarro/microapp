package com.gunnarro.dietmanager.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class AppAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private static final Logger LOG = LoggerFactory.getLogger(AppAuthenticationEntryPoint.class);

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * @param loginFormUrl URL where the login page can be found. Should either
     *            be relative to the web-app context path (include a leading
     *            {@code /}) or an absolute URL.
     */
    public AppAuthenticationEntryPoint(final String loginFormUrl) {
        super(loginFormUrl);
        if (LOG.isDebugEnabled()) {
            LOG.debug("login form url: " + loginFormUrl);
        }
    }

    /**
     * Performs the redirect (or forward) to the login form URL.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        LOG.debug("start redirect...: " + request.toString());
        LOG.debug("start redirect ex: " + authException.getMessage());
        // redirect to login page. Use https if forceHttps true
        String redirectUrl = buildRedirectUrlToLoginPage(request, response, authException);
        if (LOG.isDebugEnabled()) {
            LOG.debug("redirect url: " + redirectUrl);
        }
        redirectStrategy.sendRedirect(request, response, redirectUrl);
    }

}