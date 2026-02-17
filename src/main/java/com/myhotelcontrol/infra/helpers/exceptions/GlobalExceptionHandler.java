package com.myhotelcontrol.infra.helpers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> trataErrorClienteDuplicado(DuplicateResourceException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}