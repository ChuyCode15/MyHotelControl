package com.myhotelcontrol.infra.helpers.exceptions;

import com.myhotelcontrol.infra.helpers.exceptions.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundResorceException.class, DuplicateResourceException.class})
    public ResponseEntity<ErrorResponse> handleConflict(RuntimeException e, HttpServletRequest request) {
        var error = new ErrorResponse(
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                System.currentTimeMillis(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(HttpMessageNotReadableException e, HttpServletRequest request) {
        var error = new ErrorResponse(
                "Error en el formato de los datos enviados. Revisa los valores permitidos.",
                HttpStatus.BAD_REQUEST.value(),
                System.currentTimeMillis(),
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericError(Exception e, HttpServletRequest request) {
        var error = new ErrorResponse(
                "Ocurrió un error interno inesperado. Por favor, contacte al soporte.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                System.currentTimeMillis(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}