package com.chatservice.admin.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CannotCreateResourceException.class)
    public ResponseEntity<Map<String, Object>> handleCannotCreate(CannotCreateResourceException ex) {
        // Optional logging for business exceptions
        logger.warn("\nResource creation failed. Check The DTO First, Then Debug Further: \ncode={}, \nmessage={}", ex.getErrorCode(), ex.getErrorMessageKey());

        Map<String, Object> body = new HashMap<>();
        body.put("errorCode", ex.getErrorCode());
        body.put("message", ex.getErrorMessageKey());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handlePersonalIdNotFound(PersonalIDNotFoundException ex){

        logger.warn("\nCould Not Resolve Personal Id. Check Personal Id First, Then Debug Further: \ncode={}, \nmessage={}", ex.getErrorCode(), ex.getErrorMessageKey());
        Map<String, Object> body = new HashMap<>();
        body.put("errorCode", ex.getErrorCode());
        body.put("message", ex.getErrorMessageKey());

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleUserDiscoveryClientException(UserDiscoveryClientException ex){
        Map<String, Object> body = new HashMap<>();
        body.put("errorCode", ex.getErrorCode());
        body.put("message", ex.getErrorMessageKey());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleUnexpected(Exception ex){
        logger.error("Unexpected Error", ex);
        Map<String, Object> body = new HashMap<>();
        body.put("errorCode", "INTERNAL_ERROR");
        body.put("message", "Something Went Wrong");

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
