package com.epam.esm.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger LOG = LogManager.getLogger(GlobalExceptionHandler.class);
    public static final String MESSAGE_RESOURCE_NOT_FOUND = "message.resource.NotFound";
    public static final String MESSAGE_SOMETHING_WRONG = "message.somethingWrong";
    public static final String MESSAGE_RESOURCE_ALREADY_EXISTS = "message.resource.AlreadyExists";
    public static final String CODE_SOMETHING_WRONG = "000";
    public static final String CODE_RESOURCE_EXISTS = "001";
    public static final String CODE_RESOURCE_NOT_CHECK = "002";
    public static final String CODE_WRONG_PATH_ID = "003";
    public static final String MESSAGE_ID_MIN = "message.path.id.min";


    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception, Locale locale) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(messageSource.getMessage(MESSAGE_SOMETHING_WRONG, new Object[]{}, locale));
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value() + CODE_SOMETHING_WRONG);
        LOG.error(exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ResourceNotFoundException exception, Locale locale) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(messageSource.getMessage(MESSAGE_RESOURCE_NOT_FOUND, new Object[]{}, locale) +
                " (id = " + exception.getResourceId() + ")");
        errorResponse.setCode(HttpStatus.NOT_FOUND.value() + Integer.toString(exception.getResourceId()));
        LOG.error(exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleException(ResourceAlreadyExistsException exception, Locale locale) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setMessage(messageSource.getMessage(MESSAGE_RESOURCE_ALREADY_EXISTS, new Object[]{}, locale) +
                " (name = " + exception.getMessage() + ")");
        errorResponse.setCode(HttpStatus.CONFLICT.value() + CODE_RESOURCE_EXISTS);
        LOG.error(exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException exception, Locale locale) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        errorResponse.setMessage(messageSource.getMessage(fieldErrors.get(0), locale));
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value() + CODE_RESOURCE_NOT_CHECK);
        LOG.error(exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleException(ConstraintViolationException exception, Locale locale) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(messageSource.getMessage(MESSAGE_ID_MIN, new Object[]{}, locale));
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value() + CODE_WRONG_PATH_ID);
        LOG.error(exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
