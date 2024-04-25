package com.ztpai.fishqi.exceptions;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ztpai.fishqi.customResponse.ValidationErrorResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ValidationErrorResponse response = new ValidationErrorResponse();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            response.addErrorMessage(error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(response);
    }
}