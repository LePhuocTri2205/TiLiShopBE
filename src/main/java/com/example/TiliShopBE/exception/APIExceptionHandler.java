package com.example.TiliShopBE.exception;

import ch.qos.logback.core.util.ReentryGuard;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleBadRequest(){
        String message = "Bad request. Please check your input and try again.";
        return ResponseEntity.ok(message);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handleBadCredentialsException(BadCredentialsException exception) {
        return ResponseEntity.status(401).body(exception.getMessage());
    }
}
