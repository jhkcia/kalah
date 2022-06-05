package com.jhkcia.kalah.excaption;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class KalahExceptionHandler {
    @ExceptionHandler(KalahExceptionBase.class)
    protected ResponseEntity<String> handleInvalidInput(KalahExceptionBase ex) {

        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity handleMissingHeaderException(MissingRequestHeaderException e) {
        if(e.getHeaderName().equals("username")){
            return ResponseEntity
                    .badRequest()
                    .body("Username is not valid.");
        }

        return ResponseEntity.badRequest().body(String.format("%s header is missing from the request", e.getHeaderName()));

    }
}
