package com.jhkcia.kalah.dto;

import com.jhkcia.kalah.model.GameStatus;

public class BoardDto {
    private long id;
    private String player1;
    private String player2;
    private String currentTurn;
    private String winner;
    private GameStatus status;
    private int[] pits;

    public BoardDto(long id, String player1, String player2, String currentTurn, String winner, GameStatus status, int[] pits) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.currentTurn = currentTurn;
        this.winner = winner;
        this.status = status;
        this.pits = pits;
    }

    public long getId() {
        return id;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public String getWinner() {
        return winner;
    }

    public GameStatus getStatus() {
        return status;
    }

    public int[] getPits() {
        return pits;
    }
}
