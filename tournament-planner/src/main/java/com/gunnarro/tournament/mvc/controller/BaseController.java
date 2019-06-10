package com.gunnarro.tournament.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.tournament.service.TournamentPlannerService;
import com.gunnarro.tournament.service.exception.ApplicationException;

/**
 * URL security is applied in the spring security config.
 * 
 * @author admin
 *
 */
public class BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	protected AuthenticationFacade authenticationFacade;

	@Autowired
	@Qualifier("tournamentPlannerService")
	protected TournamentPlannerService tournamentPlannerService;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		// Prevent user form binding the id-field
		// dataBinder.setDisallowedFields("id");
	}

	public TournamentPlannerService getTournamentPlannerService() {
		return tournamentPlannerService;
	}

	public void setTournamentPlannerService(TournamentPlannerService tournamentPlannerService) {
		this.tournamentPlannerService = tournamentPlannerService;
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
