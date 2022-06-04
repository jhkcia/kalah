package com.jhkcia.kalah.model;

import com.jhkcia.kalah.excaption.BoardIsFullException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BoardTest {
    @Test
    public void testInitializing() {
        Board board = new Board("user1");
        assertEquals(GameStatus.NotStart, board.getStatus());
        assertEquals("user1", board.getPlayer1());
        assertNull(board.getPlayer2());
        assertNull(board.getWinner());
        assertNull(board.getCurrentTurn());
        for (int i = 0; i < 14; i++) {
            Pit pit = board.getPitByIndex(i);
            assertEquals(i, pit.getId());
            if (i == 6 || i == 13) {
                assertEquals(0, pit.getStones());
            } else {
                assertEquals(4, pit.getStones());
            }
        }
    }

    @Test
    public void testJoinBoard() {
        Board board = new Board("user1");

        board.join("user2");

        assertEquals("user1", board.getPlayer1());
        assertEquals("user1", board.getCurrentTurn());
        assertEquals("user2", board.getPlayer2());
        assertEquals(GameStatus.Playing, board.getStatus());
        assertNull(board.getWinner());
        for (int i = 0; i < 14; i++) {
            Pit pit = board.getPitByIndex(i);
            assertEquals(i, pit.getId());
            if (i == 6 || i == 13) {
                assertEquals(0, pit.getStones());
            } else {
                assertEquals(4, pit.getStones());
            }
        }
    }

    @Test
    public void testShouldNotJoinFullBoard() {
        Board board = new Board();
        board.setId(1);
        board.setPlayer1("user1");
        board.setPlayer2("user2");

        Exception exception = Assert.assertThrows(BoardIsFullException.class, () -> board.join("user3"));

        Assert.assertEquals("Could not join board 1, Board is full.", exception.getMessage());
    }
}
