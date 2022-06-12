package com.jhkcia.kalah.exception;

public class InvalidUsernameException extends KalahExceptionBase {
    public InvalidUsernameException() {
        super("Username is not valid.");
    }
}
