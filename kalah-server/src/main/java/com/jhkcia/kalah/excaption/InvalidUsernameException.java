package com.jhkcia.kalah.excaption;

public class InvalidUsernameException extends KalahExceptionBase {
    public InvalidUsernameException() {
        super("Username is not valid.");
    }
}
