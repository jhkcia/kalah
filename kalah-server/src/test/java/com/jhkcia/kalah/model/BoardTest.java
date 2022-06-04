package com.jhkcia.kalah.model;

import com.jhkcia.kalah.excaption.BoardIsFullException;
import com.jhkcia.kalah.excaption.InvalidPitException;
import com.jhkcia.kalah.excaption.InvalidSowException;
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
    }

    @Test
    public void testGetPitByIndex() {
        Board board = new Board("user1");

        for (int i = 0; i < 14; i++) {
            Pit pit = board.getPitByIndex(i);
            assertEquals(i, pit.getId());
            if (i == 6 || i == 13) {
                assertEquals(0, pit.getStones());
            } else {
                assertEquals(4, pit.getStones());
            }
        }
        Exception exception = Assert.assertThrows(InvalidPitException.class, () -> board.getPitByIndex(-1));
        Assert.assertEquals("Pit -1 is not valid.", exception.getMessage());
        exception = Assert.assertThrows(InvalidPitException.class, () -> board.getPitByIndex(14));
        Assert.assertEquals("Pit 14 is not valid.", exception.getMessage());

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

    @Test
    public void testShouldNotSowNotStartedBoard() {
        Board board = new Board("user1");

        Exception exception = Assert.assertThrows(InvalidSowException.class, () -> board.sowSeeds("user1", 1));

        Assert.assertEquals("Can not sow pit while game is not playing.", exception.getMessage());
    }

    @Test
    public void testShouldNotSowInvalidTurn() {
        Board board = new Board("user1");
        board.join("user2");

        Exception exception = Assert.assertThrows(InvalidSowException.class, () -> board.sowSeeds("user2", 1));

        Assert.assertEquals("It is not your turn.", exception.getMessage());
    }

    @Test
    public void testShouldNotSowInvalidPitIndex() {
        Board board = new Board("user1");
        board.join("user2");

        Exception exception = Assert.assertThrows(InvalidPitException.class, () -> board.sowSeeds("user1", -1));
        Exception exception2 = Assert.assertThrows(InvalidPitException.class, () -> board.sowSeeds("user1", 14));

        Assert.assertEquals("Pit -1 is not valid.", exception.getMessage());
        Assert.assertEquals("Pit 14 is not valid.", exception2.getMessage());
    }

    @Test
    public void testShouldNotSowOtherSidePit() {
        Board board = new Board("user1");
        board.join("user2");

        Exception exception = Assert.assertThrows(InvalidSowException.class, () -> board.sowSeeds("user1", 9));

        Assert.assertEquals("You can only sow your pits.", exception.getMessage());
    }

    @Test
    public void testShouldNotSowStorePit() {
        Board board = new Board("user1");
        board.join("user2");

        Exception exception = Assert.assertThrows(InvalidSowException.class, () -> board.sowSeeds("user1", 6));

        Assert.assertEquals("Can not sow store pit.", exception.getMessage());
    }

    @Test
    public void testShouldNotSowEmptyPit() {
        Board board = new Board("user1");
        board.join("user2");
        BoardTestUtils.setPits(board, new int[]{0, 4, 0, 5, 5, 5, 1, 4, 4, 4, 4, 4, 4, 0});

        Exception exception = Assert.assertThrows(InvalidSowException.class, () -> board.sowSeeds("user1", 2));

        Assert.assertEquals("Can not sow empty pit.", exception.getMessage());
    }

    @Test
    public void testShouldNotSowFinishedGame() {
        Board board = new Board("user1");
        board.join("user2");
        BoardTestUtils.setStatus(board, GameStatus.Finished);
        Exception exception = Assert.assertThrows(InvalidSowException.class, () -> board.sowSeeds("user1", 6));

        Assert.assertEquals("Can not sow pit while game is not playing.", exception.getMessage());
    }

    @Test
    public void testShouldCanStoreStoneOnOwnStore() {
        Board board = new Board("user1");
        board.join("user2");

        board.sowSeeds("user1", 4);

        assertPitsEquals(new int[]{4, 4, 4, 4, 0, 5, 1, 5, 5, 4, 4, 4, 4, 0}, board);
        assertEquals("user2", board.getCurrentTurn());
        assertEquals(GameStatus.Playing, board.getStatus());
        assertNull(board.getWinner());
    }

    @Test
    public void testShouldNotStoreStoneOnOpponentsStore() {
        Board board = new Board("user1");
        board.join("user2");
        BoardTestUtils.setPits(board, new int[]{4, 4, 4, 4, 0, 8, 0, 4, 4, 4, 4, 4, 4, 0});

        board.sowSeeds("user1", 5);

        assertPitsEquals(new int[]{5, 4, 4, 4, 0, 0, 1, 5, 5, 5, 5, 5, 5, 0}, board);
        assertEquals("user2", board.getCurrentTurn());
        assertEquals(GameStatus.Playing, board.getStatus());
        assertNull(board.getWinner());
    }

    @Test
    public void testShouldCaptureOpponentsStonesWhenLastSeedLandInOwnEmptyHouse() {
        Board board = new Board("user1");
        board.join("user2");
        BoardTestUtils.setPits(board, new int[]{4, 4, 4, 4, 0, 5, 1, 0, 6, 5, 5, 5, 5, 0});

        board.sowSeeds("user1", 0);

        assertPitsEquals(new int[]{0, 5, 5, 5, 0, 5, 8, 0, 0, 5, 5, 5, 5, 0}, board);
        assertEquals("user2", board.getCurrentTurn());
        assertEquals(GameStatus.Playing, board.getStatus());
        assertNull(board.getWinner());
    }

    @Test
    public void testShouldGetAdditionalMoveWhenLastSeedLandInPlayerStore() {
        Board board = new Board("user1");
        board.join("user2");

        board.sowSeeds("user1", 2);

        assertPitsEquals(new int[]{4, 4, 0, 5, 5, 5, 1, 4, 4, 4, 4, 4, 4, 0}, board);
        assertEquals("user1", board.getCurrentTurn());
        assertEquals(GameStatus.Playing, board.getStatus());
        assertNull(board.getWinner());
    }

    @Test
    public void testEndGameWhenThereIsNoSeed() {
        Board board = new Board("user1");
        board.join("user2");
        BoardTestUtils.setPits(board, new int[]{0, 0, 0, 0, 0, 2, 20, 0, 6, 5, 5, 5, 5, 0});

        board.sowSeeds("user1", 5);

        assertPitsEquals(new int[]{0, 0, 0, 0, 0, 0, 21, 0, 0, 0, 0, 0, 0, 27}, board);
        assertEquals(null, board.getCurrentTurn());
        assertEquals(GameStatus.Finished, board.getStatus());
        assertEquals("user2", board.getWinner());
    }

    @Test
    public void testEndGameInDraw() {
        Board board = new Board("user1");
        board.join("user2");
        BoardTestUtils.setPits(board, new int[]{0, 0, 0, 0, 0, 2, 23, 0, 3, 5, 5, 5, 5, 0});

        board.sowSeeds("user1", 5);

        assertPitsEquals(new int[]{0, 0, 0, 0, 0, 0, 24, 0, 0, 0, 0, 0, 0, 24}, board);
        assertEquals(null, board.getCurrentTurn());
        assertEquals(GameStatus.Finished, board.getStatus());
        assertEquals(null, board.getWinner());
    }

    @Test
    public void testSowPit() {
        Board board = new Board("user1");
        board.join("user2");

        board.sowSeeds("user1", 0);

        assertPitsEquals(new int[]{0, 5, 5, 5, 5, 4, 0, 4, 4, 4, 4, 4, 4, 0}, board);
        assertEquals("user2", board.getCurrentTurn());
        assertEquals(GameStatus.Playing, board.getStatus());
        assertNull(board.getWinner());
    }

    //TODO test combination of rules!

    private void assertPitsEquals(int[] expectedPits, Board board) {
        for (int i = 0; i < expectedPits.length; i++) {
            assertEquals(String.format("Stones on pit %d are not equal.", i), expectedPits[i], board.getPitByIndex(i).getStones());
        }
    }

}
