package com.jhkcia.kalah.controller.dto;

import com.jhkcia.kalah.model.GameStatus;

public class BoardListDto {
    private long id;
    private String player1;
    private String player2;
    private String currentTurn;
    private String winner;
    private GameStatus status;

    public BoardListDto(long id, String player1, String player2, String currentTurn, String winner, GameStatus status) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.currentTurn = currentTurn;
        this.winner = winner;
        this.status = status;
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

}
