package com.gunnarro.sportsteam.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.service.SportsTeamService;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

/**
 * URL security is applied in the spring security config.
 * 
 * @author admin
 *
 */
public class DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultController.class);

    @Autowired
    protected AuthenticationFacade authenticationFacade;

    @Autowired
    @Qualifier("sportsTeamService")
    protected SportsTeamService sportsTeamService;

    public SportsTeamService getSportsTeamService() {
        return sportsTeamService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        // Prevent user form binding the id-field
        // dataBinder.setDisallowedFields("id");
    }

    public void setSportsTeamService(SportsTeamService sportsTeamService) {
        this.sportsTeamService = sportsTeamService;
    }

    @ExceptionHandler(ApplicationException.class)
    public ModelAndView handleApplicationException(HttpServletRequest request, Exception ex) {
        LOG.error("Requested URL=" + request.getRequestURI());
        LOG.error("Exception Raised=" + ex);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURI());
        modelAndView.setViewName("application-error");
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(HttpServletRequest request, Exception ex) {
        LOG.error("Requested URL=" + request.getRequestURI());
        LOG.error("Exception Raised=" + ex);
        ex.printStackTrace();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURI());
        modelAndView.setViewName("application-error");
        return modelAndView;
    }
}
