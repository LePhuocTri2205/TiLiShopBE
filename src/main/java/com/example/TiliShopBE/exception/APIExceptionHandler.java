package com.example.TiliShopBE.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleBadRequest(){
        String message = "Bad request. Please check your input and try again.";
        return ResponseEntity.ok(message);
    }
}
