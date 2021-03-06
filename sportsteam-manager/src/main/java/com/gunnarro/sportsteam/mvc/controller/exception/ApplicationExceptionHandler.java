package com.gunnarro.sportsteam.mvc.controller.exception;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gunnarro.sportsteam.endpoint.rest.RestApplicationException;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

/**
 * TODO NOT IN USE
 * 
 * @author admin
 *
 */
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(ApplicationException.class)
    public String handleApplicationException(HttpServletRequest request, Exception ex) {
        LOG.error("ApplicationException occured at URL=" + request.getRequestURL());
        return "application-error";
    }

    @ExceptionHandler({ RestApplicationException.class })
    protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request) {
        RestApplicationException restEx = (RestApplicationException) e;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, restEx.getRestError(), headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(SQLException.class)
    public String handleSQLException(HttpServletRequest request, Exception ex) {
        LOG.info("SQLException occured: URL=" + request.getRequestURL());
        return "application-error";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occured")
    @ExceptionHandler(IOException.class)
    public void handleIOException() {
        LOG.error("IOException handler executed");
        // returning 404 error code
    }
    
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Access denied")
    @ExceptionHandler(SecurityException.class)
    public void handleSpringSecurityException() {
        LOG.error("SecurityException handler executed");
        // returning 404 error code
    }

}
