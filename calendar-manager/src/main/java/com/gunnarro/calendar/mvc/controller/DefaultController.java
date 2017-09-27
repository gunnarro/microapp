package com.gunnarro.calendar.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.calendar.service.CalendarService;
import com.gunnarro.calendar.service.exception.ApplicationException;

/**
 * URL security is applied in the spring security config.
 * 
 * @author admin
 *
 */
public class DefaultController {

	private static final Logger LOG = LoggerFactory
			.getLogger(DefaultController.class);

	@Autowired
	protected AuthenticationFacade authenticationFacade;

	@Autowired
	@Qualifier("calendarService")
	protected CalendarService calendarService;

	public CalendarService getCalendarService() {
		return calendarService;
	}

	public void setCalendarService(CalendarService calendarService) {
		this.calendarService = calendarService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		// Prevent user form binding the id-field
		// dataBinder.setDisallowedFields("id");
	}

	@ExceptionHandler(ApplicationException.class)
	public ModelAndView handleApplicationException(HttpServletRequest request,
			Exception ex) {
		LOG.error("Requested URL=" + request.getRequestURI());
		LOG.error("Exception Raised=" + ex);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exception", ex);
		modelAndView.addObject("url", request.getRequestURI());
		modelAndView.setViewName("application-error");
		return modelAndView;
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(HttpServletRequest request,
			Exception ex) {
		LOG.error("Requested URL=" + request.getRequestURI());
		LOG.error("Exception Raised", ex);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exception", ex);
		modelAndView.addObject("url", request.getRequestURI());
		modelAndView.setViewName("application-error");
		return modelAndView;
	}
}
