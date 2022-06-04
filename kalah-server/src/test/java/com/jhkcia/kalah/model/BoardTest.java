package com.jhkcia.kalah.model;

import com.jhkcia.kalah.model.Board;
import com.jhkcia.kalah.model.GameStatus;
import com.jhkcia.kalah.model.Pit;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class BoardTest {
    @Test
    public void testInitializing() {
        Board board = new Board("user1");
        assertEquals(GameStatus.NotStart, board.getStatus());
        assertEquals("user1", board.getPlayer1());
        assertEquals(null, board.getPlayer2());
        assertEquals(null, board.getWinner());
        for (int i = 0; i < 13; i++) {
            Pit pit = board.getPitByIndex(i);
            assertEquals(i, pit.getId());
            if (i == 6 || i == 13) {
                assertEquals(0, pit.getStones());
            } else {
                assertEquals(4, pit.getStones());
            }
        }
    }
}
