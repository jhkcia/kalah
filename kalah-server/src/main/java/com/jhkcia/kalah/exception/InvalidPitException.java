package com.jhkcia.kalah.exception;

public class InvalidPitException extends KalahExceptionBase {
    public InvalidPitException(int pitIndex) {
        super(String.format("Pit %d is not valid.", pitIndex));
    }
}
