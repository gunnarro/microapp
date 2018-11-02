package com.gunnarro.dietmanager.mvc.controller;

import java.net.ConnectException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.dietmanager.service.ActivityService;
import com.gunnarro.dietmanager.service.DietManagerService;
import com.gunnarro.dietmanager.service.FileUploadService;
import com.gunnarro.dietmanager.service.LogEventService;
import com.gunnarro.dietmanager.service.exception.ApplicationException;
import com.gunnarro.dietmanager.service.exception.UploadFileException;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

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
	protected DietManagerService dietManagerService;

	@Autowired
	protected LogEventService logEventService;

	@Autowired
	protected ActivityService activityService;

	@Autowired
	protected FileUploadService fileUploadService;

	public AuthenticationFacade getAuthenticationFacade() {
		return authenticationFacade;
	}

	public DietManagerService getDietManagerService() {
		return dietManagerService;
	}

	@ExceptionHandler(RuntimeException.class)
	public ModelAndView handleRuntimeException(HttpServletRequest request, RuntimeException ex) {
		return handleException(request.getRequestURI(), request.getRequestURI(), ex, ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(HttpServletRequest request, Exception ex) {
		return handleException(request.getRequestURI(), request.getRequestURI(), ex, ex.getMessage());
	}

	@ExceptionHandler(ApplicationException.class)
	public ModelAndView handleApplicationException(HttpServletRequest request, Exception ex) {
		return handleException(request.getRequestURI(), request.getRequestURI(), ex, ex.getMessage());
	}

	@ExceptionHandler(UploadFileException.class)
	public ModelAndView handleUploadFileException(HttpServletRequest request, Exception ex) {
		return handleException(request.getRequestURI(), request.getRequestURI(), ex, ex.getMessage());
	}

	@ExceptionHandler(MultipartException.class)
	public ModelAndView handleMultipartException(HttpServletRequest request, Exception ex) {
		return handleException(request.getRequestURI(), request.getRequestURI(), ex, "Upload failed!");
	}

	@ExceptionHandler(CommunicationsException.class)
	public ModelAndView handleCommunicationsException(HttpServletRequest request, Exception ex) {
		return handleException(request.getRequestURI(), request.getRequestURI(), ex,
				"Database cummunication problems!");
	}

	@ExceptionHandler(ConnectException.class)
	public ModelAndView handleConnectException(HttpServletRequest request, Exception ex) {
		return handleException(request.getRequestURI(), request.getRequestURI(), ex, "Database Connect problems!");
	}

	@ExceptionHandler(CannotGetJdbcConnectionException.class)
	public ModelAndView handleJDBCConnectionException(HttpServletRequest request, Exception ex) {
		return handleException(request.getRequestURI(), request.getRequestURI(), ex, "Technical Database problems!");
	}

	@ExceptionHandler(SecurityException.class)
	public ModelAndView handleSecurityException(HttpServletRequest request, Exception ex) {
		return handleException(request.getRequestURI(), "/dietmanager/home", ex, "Access Denied. " + ex.getMessage());
	}

	@ExceptionHandler(SQLException.class)
	public ModelAndView handleSQLException(HttpServletRequest request, Exception ex) {
		return handleException(request.getRequestURI(), request.getRequestURI(), ex, "Technical Database problems!");
	}

	/**
	 * Prevent user form binding the id-field dataBinder.setDisallowedFields("id");
	 * 
	 * @param dataBinder
	 */
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
	}

	/**
	 * for unit testing only
	 * 
	 * @param authenticationFacade
	 */
	public void setAuthenticationFacade(AuthenticationFacade authenticationFacade) {
		this.authenticationFacade = authenticationFacade;
	}

	private ModelAndView handleException(String requestUrl, String backUrl, Exception e, String errorMsg) {
		LOG.error("Requested URL=" + requestUrl);
		LOG.error("Exception Raised", e);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exception", new Exception(errorMsg));
		modelAndView.addObject("requestUrl", requestUrl);
		modelAndView.addObject("backUrl", backUrl);
		modelAndView.setViewName("error");
		return modelAndView;
	}

	/**
	 * For unit test only, inject mock
	 * 
	 * @param dietManagerService
	 */
	public void setDietManagerService(DietManagerService dietManagerService) {
		this.dietManagerService = dietManagerService;
	}

	/**
	 * For unit test only, inject mock
	 * 
	 * @param logEventService
	 */
	public void setLogEventService(LogEventService logEventService) {
		this.logEventService = logEventService;
	}
}
