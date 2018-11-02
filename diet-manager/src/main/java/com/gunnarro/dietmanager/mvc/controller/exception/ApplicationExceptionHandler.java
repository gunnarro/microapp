package com.gunnarro.dietmanager.mvc.controller.exception;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * TODO NOT IN USE
 * 
 * @author admin
 *
 */
// @Configuration
// @ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    // private static final Logger LOG =
    // LoggerFactory.getLogger(ApplicationExceptionHandler.class);
    //
    // @ExceptionHandler(ApplicationException.class)
    // public String handleApplicationException(HttpServletRequest request,
    // Exception ex) {
    // LOG.error("ApplicationException occured at URL=" +
    // request.getRequestURL());
    // LOG.error("Exception:", ex);
    // return "application-error";
    // }
    //
    // @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException
    // occured")
    // @ExceptionHandler(IOException.class)
    // public void handleIOException() {
    // LOG.error("IOException handler executed");
    // // returning 404 error code
    // }
    //
    // @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Access denied")
    // @ExceptionHandler(SecurityException.class)
    // public void handleSpringSecurityException() {
    // LOG.error("SecurityException handler executed");
    // // returning 404 error code
    // }
    //
    // @ExceptionHandler(SQLException.class)
    // public String handleSQLException(HttpServletRequest request, Exception
    // ex) {
    // LOG.info("SQLException occured: URL=" + request.getRequestURL());
    // return "application-error";
    // }
    //
    // @ExceptionHandler({ RestApplicationException.class })
    // protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e,
    // WebRequest request) {
    // RestApplicationException restEx = (RestApplicationException) e;
    // HttpHeaders headers = new HttpHeaders();
    // headers.setContentType(MediaType.APPLICATION_JSON);
    // return handleExceptionInternal(e, restEx.getRestError(), headers,
    // HttpStatus.UNPROCESSABLE_ENTITY, request);
    // }

}
