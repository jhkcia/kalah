package com.jhkcia.kalah.model;

import javax.persistence.Embeddable;

@Embeddable
public class Pit {
    private static final int INITIAL_STONE_COUNT = 4;
    private static final int SECOND_STORE_INDEX = 13;
    private static final int FIRST_STORE_INDEX = 6;
    private int id;
    private int stones;

    public Pit() {

    }

    public Pit(Integer id) {
        this.id = id;
        if (isHouse()) {
            this.stones = INITIAL_STONE_COUNT;
        }
    }

    public boolean isStore() {
        return this.getId() == FIRST_STORE_INDEX || this.getId() == SECOND_STORE_INDEX;
    }

    public boolean isHouse() {
        return !isStore();
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
}
