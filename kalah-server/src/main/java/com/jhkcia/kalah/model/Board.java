package com.jhkcia.kalah.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Board {
    @Id
    private long id;
    private String player1;
    private String player2;
    private String winner;
    private GameStatus status;
    @ElementCollection
    private List<Pit> pits;

    private static Integer PITS_COUNT = 14;

    public Board(String player1) {
        this.player1 = player1;
        this.status = GameStatus.NotStart;
        this.pits = new ArrayList<>(PITS_COUNT);
        for (int i = 0; i < PITS_COUNT; i++) {
            pits.add(new Pit(i));
        }
    }

    public Board() {

    }

    public Pit getPitByIndex(int index) {
        return this.pits.get(index);
    }

    public long getId() {
        return id;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

}
