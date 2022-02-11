package com.epam.esm.exception;

import com.epam.esm.security.JwtAuthenticationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String CODE_SOMETHING_WRONG = "000";
    public static final String CODE_RESOURCE_EXISTS = "001";
    public static final String CODE_RESOURCE_NOT_CHECK = "002";
    public static final String CODE_WRONG_PATH_ID = "003";
    public static final String CODE_RESOURCE_NO_LINKS = "004";
    public static final String CODE_UNAUTHORIZED = "005";
    public static final String CODE_FORBIDDEN = "006";
    public static final String CODE_BAD_CREDENTIALS = "007";

    private final static Logger LOG = LogManager.getLogger(GlobalExceptionHandler.class);
    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception, Locale locale) {
        String message = messageSource.getMessage(MessageLocal.MESSAGE_SOMETHING_WRONG, new Object[]{}, locale);
        return handleExceptionTemplate(exception, HttpStatus.BAD_REQUEST, message, CODE_SOMETHING_WRONG);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ResourceNotFoundException exception, Locale locale) {
        String message = messageSource.getMessage(MessageLocal.MESSAGE_RESOURCE_NOT_FOUND, new Object[]{}, locale) +
                " (id = " + exception.getResourceId() + ")";
        return handleExceptionTemplate(exception, HttpStatus.NOT_FOUND, message,
                Integer.toString(exception.getResourceId()));
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleException(ResourceAlreadyExistsException exception, Locale locale) {
        String message = messageSource.getMessage(MessageLocal.MESSAGE_RESOURCE_ALREADY_EXISTS, new Object[]{},
                locale) + " (name = " + exception.getMessage() + ")";
        return handleExceptionTemplate(exception, HttpStatus.CONFLICT, message, CODE_RESOURCE_EXISTS);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException exception, Locale locale) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String message = messageSource.getMessage(fieldErrors.get(0), locale);
        return handleExceptionTemplate(exception, HttpStatus.BAD_REQUEST, message, CODE_RESOURCE_NOT_CHECK);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleException(ConstraintViolationException exception, Locale locale) {
        String message = messageSource.getMessage(MessageLocal.MESSAGE_ID_MIN, new Object[]{}, locale);
        return handleExceptionTemplate(exception, HttpStatus.BAD_REQUEST, message, CODE_WRONG_PATH_ID);
    }

    @ExceptionHandler(ResourceHasLinksException.class)
    public ResponseEntity<ErrorResponse> handleException(ResourceHasLinksException exception, Locale locale) {
        String message = messageSource.getMessage(MessageLocal.MESSAGE_RESOURCE_HAS_LINKS, new Object[]{}, locale) +
                " (id = " + exception.getResourceId() + ")";
        return handleExceptionTemplate(exception, HttpStatus.CONFLICT, message,
                Integer.toString(exception.getResourceId()));
    }

    @ExceptionHandler(ResourceNoLinksException.class)
    public ResponseEntity<ErrorResponse> handleException(ResourceNoLinksException exception, Locale locale) {
        String message = messageSource.getMessage(MessageLocal.MESSAGE_RESOURCE_NO_LINKS, new Object[]{}, locale);
        return handleExceptionTemplate(exception, HttpStatus.NOT_FOUND, message, CODE_RESOURCE_NO_LINKS);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleException(JwtAuthenticationException exception, Locale locale) {
        String message = messageSource.getMessage(exception.getMessage(), new Object[]{}, locale);
        return handleExceptionTemplate(exception, HttpStatus.UNAUTHORIZED, message, CODE_UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleException(AccessDeniedException exception, Locale locale) {
        String message = messageSource.getMessage(MessageLocal.MESSAGE_FORBIDDEN, new Object[]{}, locale);
        return handleExceptionTemplate(exception, HttpStatus.FORBIDDEN, message, CODE_FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleException(BadCredentialsException exception, Locale locale) {
        String message = messageSource.getMessage(MessageLocal.MESSAGE_BAD_CREDENTIALS, new Object[]{}, locale);
        return handleExceptionTemplate(exception, HttpStatus.UNAUTHORIZED, message, CODE_BAD_CREDENTIALS);
    }

    private ResponseEntity<ErrorResponse> handleExceptionTemplate(Exception exception, HttpStatus httpStatus,
                                                                  String message, String errorCode) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message,
                httpStatus.value() + errorCode);
        LOG.error(exception);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

}
