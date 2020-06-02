package com.example.resourcetestserver.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExampleResourceControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleResourceControllerAdvice.class);

    @ExceptionHandler(UnauthorizedClientException.class)
    public ResponseEntity<Object> handleUnauthorizedClient(final RuntimeException e, final WebRequest request) {
        LOGGER.info("UnauthorizedClientException: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<Object> handleUnauthorizedUser(final RuntimeException e, final WebRequest request) {
        LOGGER.info("UnauthorizedUserException: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(final RuntimeException e, final WebRequest request) {
        LOGGER.info("AccessDeniedException: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> handleInvalidTokenException(final RuntimeException e, final WebRequest request) {
        LOGGER.info("InvalidTokenException");
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
