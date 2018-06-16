package com.gunnarro.dietmanager.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gunnarro.dietmanager.service.exception.ApplicationException;
import com.gunnarro.useraccount.domain.user.LocalUser;

@Controller
public class LoginController extends BaseController {

    public static final String HOME_PAGE = "/diet/log/events";
    public static final String ADMIN_PAGE = "/admin";
    public static final String LOGIN_PAGE = "/login";

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/home")
    public String home() {
        String redirectUrl = null;
        try {
            LocalUser user = authenticationFacade.getLoggedInUser();
            if (LOG.isDebugEnabled()) {
                LOG.debug("logged in as user: " + user);
            }
            if (user == null) {
                // this was an ANONYMOUS user, i.e, not logged in
                redirectUrl = HOME_PAGE;
            } else {
                // direct non activated users to the default start page
                if (!user.isActivated()) {
                    redirectUrl = HOME_PAGE;
                }

                if (user.isAdmin()) {
                    redirectUrl = ADMIN_PAGE;
                } else if (user.isUser()) {
                    redirectUrl = HOME_PAGE;
                } else {
                    // Not authenticated direct back to login page
                    redirectUrl = LOGIN_PAGE;
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("redirect user to: " + redirectUrl);
            }
            return "redirect:" + redirectUrl;
        } catch (ApplicationException ae) {
            LOG.error("", ae);
            // Will direct user to the error page
            throw ae;
        }
    }

    /**
     * @return the login page
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * @return the select profile page
     */
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
    
    /**
     * @return the login page
     */
    @GetMapping("/error")
    public String error() {
        return "application-error";
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }

    @GetMapping("/access-denied")
    public String denied() {
        return "access-denied";
    }

    /**
     * Log out user and redirects to the login page
     * 
     * Note! If using Spring Security's CSRF protection, you must POST to log
     * out.
     *
     * @param request
     * @param response
     * @return the login page
     */
    // @RequestMapping(value = "/perform-logout", method = RequestMethod.GET)
    // public String logout(HttpServletRequest request, HttpServletResponse
    // response) {
    // LOG.debug("start logout user...");
    // Authentication auth = authenticationFacade.getAuthentication();
    // if (LOG.isDebugEnabled()) {
    // LOG.debug("auth: " + auth);
    // String clientIp = request.getHeader("X-FORWARDED-FOR") != null ?
    // request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
    // LOG.debug("clientIp: " + clientIp);
    // }
    // if (auth != null) {
    // // The logout part is configured in spring-security.xml
    // // SecurityContextLogoutHandler ss = new
    // // SecurityContextLogoutHandler();
    // // ss.setClearAuthentication(true);
    // // ss.setInvalidateHttpSession(true);
    // // ss.logout(request, response, auth);
    // if (LOG.isDebugEnabled()) {
    // LOG.debug("logged out: " + auth.getName());
    // LOG.debug("logged in user: " + authenticationFacade.getLoggedInUser());
    // }
    // }
    // return "redirect:/login?loggedout";
    // }

    /**
     * logout performs Invalidates HTTP Session ,then unbinds any objects bound
     * to it. Removes the Authentication from the SecurityContext to prevent
     * issues with concurrent requests. Explicitly clears the context value from
     * the current thread.
     * 
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/perform-logout-user")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        logoutLocalUser(request, response);
        // logoutFaceBook();
        return "redirect:login?loggedout";
    }

    private void logoutLocalUser(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = authenticationFacade.getAuthentication();
        LOG.debug("logout: " + auth.getPrincipal());
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

    // @RequestMapping(value = { "/perform-logout-user" }, method =
    // RequestMethod.GET)
    // public String logout(HttpServletRequest request, Model model, Principal
    // principal) throws ServletException {
    // LOG.debug("logout.... " + principal);
    // if (null != principal) {
    // HttpSession session = request.getSession(false);
    // if (session != null) {
    // session.invalidate();
    // }
    // SecurityContext context = SecurityContextHolder.getContext();
    // context.setAuthentication(null);
    // SecurityContextHolder.clearContext();
    // }
    // return "redirect:/login?loggedout";
    // }

    @GetMapping("/releasenotes")
    public String releasenotes() {
        return "release-notes";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

}
