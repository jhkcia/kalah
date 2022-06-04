package com.jhkcia.kalah.excaption;

public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException() {
        super("Board Not Found.");
    }
}
