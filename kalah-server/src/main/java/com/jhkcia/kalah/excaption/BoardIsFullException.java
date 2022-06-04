package com.jhkcia.kalah.excaption;

public class BoardIsFullException extends RuntimeException {
    public BoardIsFullException(Long boardId) {
        super(String.format("Could not join board %d, Board is full.", boardId));
    }
}
