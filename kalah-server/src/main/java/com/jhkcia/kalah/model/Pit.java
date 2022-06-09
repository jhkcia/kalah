package com.jhkcia.kalah.model;

import com.jhkcia.kalah.excaption.InvalidSowException;

import javax.persistence.Embeddable;

@Embeddable
public class Pit {
    private int id;
    private int stones;

    public Pit() {

    }

    public Pit(Integer id) {
        this.id = id;
    }

    public boolean isEmpty() {
        return this.getStones() == 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStones() {
        return stones;
    }

    public void setStones(int stones) {
        this.stones = stones;
    }

    public void removeStone() {
        this.removeStones(1);
    }

    public void removeStones(int count) {
        if (this.stones < count) {
            throw new InvalidSowException(String.format("Can not remove %d stone from pit %d.", count, this.getId()));
        }
        this.setStones(stones - count);
    }

    public void addStone() {
        this.addStones(1);
    }

    public void addStones(int count) {
        this.setStones(stones + count);
    }

    public void removeAllStones() {
        this.setStones(0);
    }
}
