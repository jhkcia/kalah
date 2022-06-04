package com.jhkcia.kalah.model;

import com.jhkcia.kalah.excaption.InvalidSowException;

import javax.persistence.Embeddable;

@Embeddable
public class Pit {
    private static final int INITIAL_STONE_COUNT = 4;
    private static final int SECOND_STORE_INDEX = 13;
    private static final int FIRST_STORE_INDEX = 6;
    private static final int[] FIRST_HOUSE_INDEXES = new int[]{0, 1, 2, 3, 4, 5};
    private static final int[] SECOND_HOUSE_INDEXES = new int[]{7, 8, 9, 10, 11, 12};
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

    public boolean belongsToPlayer(int playerIndex) {
        return (playerIndex == 0 && this.getId() <= FIRST_STORE_INDEX && this.getId() >= 0) ||
                (playerIndex == 1 && this.getId() > FIRST_STORE_INDEX && this.getId() <= SECOND_STORE_INDEX);
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

    public int getOppositePitIndex() {
        if (isHouse()) {
            return 12 - getId();
        } else {
            return -1;// TODO return exception
        }
    }

    public static int getStoreIndex(int playerIndex) {
        if (playerIndex == 0) {
            return FIRST_STORE_INDEX;
        } else if (playerIndex == 1) {
            return SECOND_STORE_INDEX;
        }
        return -1; //TODO return exception
    }

    public static int[] getStoreIndexes(int playerIndex) {
        if (playerIndex == 0) {
            return FIRST_HOUSE_INDEXES;
        } else if (playerIndex == 1) {
            return SECOND_HOUSE_INDEXES;
        }
        return new int[]{}; //TODO return exception
    }

    public void removeAllStones() {
        this.setStones(0);
    }
}
