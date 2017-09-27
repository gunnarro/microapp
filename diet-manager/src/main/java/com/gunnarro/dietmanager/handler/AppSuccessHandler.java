package com.gunnarro.dietmanager.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.gunnarro.dietmanager.mvc.controller.AppAuthenticationEntryPoint;
import com.gunnarro.useraccount.repository.table.user.RolesTable.RolesEnum;

/**
 */
public class AppSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AppAuthenticationEntryPoint.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
        if (LOG.isDebugEnabled()) {
            LOG.debug("redirect to: " + targetUrl);
        }
        if (response.isCommitted()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Can't redirect");
            }
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /*
     * This method extracts the roles of currently logged-in user and returns
     * appropriate URL according to his/her role.
     */
    protected String determineTargetUrl(Authentication authentication) {
        String url = "";
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();
        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        if (isAdmin(roles) || isUser(roles)) {
            url = "/home";
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("User is not authorized");
            }
            url = "/access-denied";
        }
        return url;
    }

    private boolean isUser(List<String> roles) {
        if (roles.contains(RolesEnum.ROLE_USER.name())) {
            return true;
        }
        return false;
    }

    private boolean isAdmin(List<String> roles) {
        if (roles.contains(RolesEnum.ROLE_ADMIN.name())) {
            return true;
        }
        return false;
    }

    @Override
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    @Override
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}
