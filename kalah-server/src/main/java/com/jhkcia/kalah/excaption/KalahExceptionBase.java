package com.jhkcia.kalah.excaption;

import org.springframework.http.HttpStatus;

public abstract class KalahExceptionBase extends RuntimeException {

    public KalahExceptionBase(String message) {
        super(message);
    }

}
