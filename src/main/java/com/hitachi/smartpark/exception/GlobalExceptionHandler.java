package com.hitachi.smartpark.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {InvalidRequestException.class})
    public ResponseEntity<?> handleInvalidRequestException(InvalidRequestException ex) {

        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherExceptions(Exception ex) {

        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}
