package com.jhkcia.kalah.exception;

public class BoardIsFullException extends KalahExceptionBase {
    public BoardIsFullException(Long boardId) {
        super(String.format("Could not join board %d, Board is full.", boardId));
    }
}
