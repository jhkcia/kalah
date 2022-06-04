package com.jhkcia.kalah.excaption;

public class InvalidPitException extends RuntimeException {
    public InvalidPitException(int pitIndex) {
        super(String.format("Pit %d is not valid.", pitIndex));
    }
}
