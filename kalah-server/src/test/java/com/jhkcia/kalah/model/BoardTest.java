package com.jhkcia.kalah.model;

import com.jhkcia.kalah.exception.BoardIsFullException;
import com.jhkcia.kalah.exception.InvalidPitException;
import com.jhkcia.kalah.exception.InvalidSowException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static com.jhkcia.kalah.model.BoardTestUtils.assertPitsEquals;

public class BoardTest {
    @Test
    public void should_init_board() {
        Board board = new Board("user1");
        assertEquals(GameStatus.NotStart, board.getStatus());
        assertEquals("user1", board.getPlayer1());
        assertNull(board.getPlayer2());
        assertNull(board.getWinnerPlayer());
        assertNull(board.getCurrentTurnPlayer());
    }

    @Test
    public void should_get_pit_by_index() {
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
    public void should_join_board() {
        Board board = new Board("user1");

        board.join("user2");

        assertEquals("user1", board.getPlayer1());
        assertEquals("user1", board.getCurrentTurnPlayer());
        assertEquals("user2", board.getPlayer2());
        assertEquals(GameStatus.Playing, board.getStatus());
        assertNull(board.getWinnerPlayer());
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
    public void should_not_join_full_board() {
        Board board = new Board();
        board.setId(1);
        board.setPlayer1("user1");
        board.setPlayer2("user2");

        Exception exception = Assert.assertThrows(BoardIsFullException.class, () -> board.join("user3"));

        Assert.assertEquals("Could not join board 1, Board is full.", exception.getMessage());
    }

    @Test
    public void should_not_sow_not_started_board() {
        Board board = new Board("user1");

        Exception exception = Assert.assertThrows(InvalidSowException.class, () -> board.sowSeeds("user1", 1));

        Assert.assertEquals("Can not sow pit while game is not playing.", exception.getMessage());
    }

    @Test
    public void should_not_sow_on_invalid_turn() {
        Board board = new Board("user1");
        board.join("user2");

        Exception exception = Assert.assertThrows(InvalidSowException.class, () -> board.sowSeeds("user2", 1));

        Assert.assertEquals("It is not your turn.", exception.getMessage());
    }

    @Test
    public void should_not_sow_invalid_pit_index() {
        Board board = new Board("user1");
        board.join("user2");

        Exception exception = Assert.assertThrows(InvalidPitException.class, () -> board.sowSeeds("user1", -1));
        Exception exception2 = Assert.assertThrows(InvalidPitException.class, () -> board.sowSeeds("user1", 14));

        Assert.assertEquals("Pit -1 is not valid.", exception.getMessage());
        Assert.assertEquals("Pit 14 is not valid.", exception2.getMessage());
    }

    @Test
    public void should_not_sow_other_side_pit() {
        Board board = new Board("user1");
        board.join("user2");

        Exception exception = Assert.assertThrows(InvalidSowException.class, () -> board.sowSeeds("user1", 9));

        Assert.assertEquals("You can only sow your pits.", exception.getMessage());
    }

    @Test
    public void should_not_sow_store_pit() {
        Board board = new Board("user1");
        board.join("user2");

        Exception exception = Assert.assertThrows(InvalidSowException.class, () -> board.sowSeeds("user1", 6));

        Assert.assertEquals("Can not sow store pit.", exception.getMessage());
    }

    @Test
    public void should_not_sow_empty_pit() {
        Board board = new Board("user1");
        board.join("user2");
        BoardTestUtils.setPits(board, new int[]{0, 4, 0, 5, 5, 5, 1, 4, 4, 4, 4, 4, 4, 0});

        Exception exception = Assert.assertThrows(InvalidSowException.class, () -> board.sowSeeds("user1", 2));

        Assert.assertEquals("Can not sow empty pit.", exception.getMessage());
    }

    @Test
    public void should_not_sow_pit_in_finished_game() {
        Board board = new Board("user1");
        board.join("user2");
        BoardTestUtils.setStatus(board, GameStatus.Finished);
        Exception exception = Assert.assertThrows(InvalidSowException.class, () -> board.sowSeeds("user1", 6));

        Assert.assertEquals("Can not sow pit while game is not playing.", exception.getMessage());
    }

    @Test
    public void should_land_pit_on_user_store_during_sowing() {
        Board board = new Board("user1");
        board.join("user2");

        board.sowSeeds("user1", 4);

        assertPitsEquals(new int[]{4, 4, 4, 4, 0, 5, 1, 5, 5, 4, 4, 4, 4, 0}, board);
        assertEquals("user2", board.getCurrentTurnPlayer());
        assertEquals(GameStatus.Playing, board.getStatus());
        assertNull(board.getWinnerPlayer());
    }

    @Test
    public void should_not_land_pit_on_opponent_store_during_sowing() {
        Board board = new Board("user1");
        board.join("user2");
        BoardTestUtils.setPits(board, new int[]{4, 4, 4, 4, 0, 8, 0, 4, 4, 4, 4, 4, 4, 0});

        board.sowSeeds("user1", 5);

        assertPitsEquals(new int[]{5, 4, 4, 4, 0, 0, 1, 5, 5, 5, 5, 5, 5, 0}, board);
        assertEquals("user2", board.getCurrentTurnPlayer());
        assertEquals(GameStatus.Playing, board.getStatus());
        assertNull(board.getWinnerPlayer());
    }

    @Test
    public void should_capture_opponents_stones_when_last_seed_land_in_own_empty_house() {
        Board board = new Board("user1");
        board.join("user2");
        BoardTestUtils.setPits(board, new int[]{4, 4, 4, 4, 0, 5, 1, 0, 6, 5, 5, 5, 5, 0});

        board.sowSeeds("user1", 0);

        assertPitsEquals(new int[]{0, 5, 5, 5, 0, 5, 8, 0, 0, 5, 5, 5, 5, 0}, board);
        assertEquals("user2", board.getCurrentTurnPlayer());
        assertEquals(GameStatus.Playing, board.getStatus());
        assertNull(board.getWinnerPlayer());
    }

    @Test
    public void should_get_additional_move_when_last_seed_land_in_player_store() {
        Board board = new Board("user1");
        board.join("user2");

        board.sowSeeds("user1", 2);

        assertPitsEquals(new int[]{4, 4, 0, 5, 5, 5, 1, 4, 4, 4, 4, 4, 4, 0}, board);
        assertEquals("user1", board.getCurrentTurnPlayer());
        assertEquals(GameStatus.Playing, board.getStatus());
        assertNull(board.getWinnerPlayer());
    }

    @Test
    public void should_end_game_when_no_seed_exists() {
        Board board = new Board("user1");
        board.join("user2");
        BoardTestUtils.setPits(board, new int[]{0, 0, 0, 0, 0, 2, 20, 0, 6, 5, 5, 5, 5, 0});

        board.sowSeeds("user1", 5);

        assertPitsEquals(new int[]{0, 0, 0, 0, 0, 0, 21, 0, 0, 0, 0, 0, 0, 27}, board);
        assertEquals(null, board.getCurrentTurnPlayer());
        assertEquals(GameStatus.Finished, board.getStatus());
        assertEquals("user2", board.getWinnerPlayer());
    }

    @Test
    public void should_end_game_in_draw() {
        Board board = new Board("user1");
        board.join("user2");
        BoardTestUtils.setPits(board, new int[]{0, 0, 0, 0, 0, 2, 23, 0, 3, 5, 5, 5, 5, 0});

        board.sowSeeds("user1", 5);

        assertPitsEquals(new int[]{0, 0, 0, 0, 0, 0, 24, 0, 0, 0, 0, 0, 0, 24}, board);
        assertEquals(null, board.getCurrentTurnPlayer());
        assertEquals(GameStatus.Finished, board.getStatus());
        assertEquals(null, board.getWinnerPlayer());
    }

    @Test
    public void should_sow_pits() {
        Board board = new Board("user1");
        board.join("user2");

        board.sowSeeds("user1", 0);

        assertPitsEquals(new int[]{0, 5, 5, 5, 5, 4, 0, 4, 4, 4, 4, 4, 4, 0}, board);
        assertEquals("user2", board.getCurrentTurnPlayer());
        assertEquals(GameStatus.Playing, board.getStatus());
        assertNull(board.getWinnerPlayer());
    }

    @Test
    public void should_return_proper_pit_type() {
        Board board = new Board("user1");
        for (int i = 0; i < 14; i++) {
            Pit p = new Pit(i);
            if (i == 6 || i == 13) {
                Assert.assertTrue(board.isStore(p));
                Assert.assertFalse(board.isHouse(p));
            } else {
                Assert.assertFalse(board.isStore(p));
                Assert.assertTrue(board.isHouse(p));
            }
        }
    }

    @Test
    public void should_return_owner_of_pit() {
        Board board = new Board("user1");
        for (int i = 0; i < 14; i++) {
            Pit p = new Pit(i);
            if (i <= 6) {
                Assert.assertTrue(board.belongsToPlayer(p, 0));
                Assert.assertFalse(board.belongsToPlayer(p, 1));
            } else {
                Assert.assertFalse(board.belongsToPlayer(p, 0));
                Assert.assertTrue(board.belongsToPlayer(p, 1));
            }
        }
    }

    @Test
    public void should_return_opposite_pit() {
        Board board = new Board("user1");
        Assert.assertEquals(12, board.getOppositePit(board.getPitByIndex(0)).getId());
        Assert.assertEquals(11, board.getOppositePit(board.getPitByIndex(1)).getId());
        Assert.assertEquals(10, board.getOppositePit(board.getPitByIndex(2)).getId());
        Assert.assertEquals(9, board.getOppositePit(board.getPitByIndex(3)).getId());
        Assert.assertEquals(8, board.getOppositePit(board.getPitByIndex(4)).getId());
        Assert.assertEquals(7, board.getOppositePit(board.getPitByIndex(5)).getId());
        Assert.assertEquals(6, board.getOppositePit(board.getPitByIndex(6)).getId());
        Assert.assertEquals(5, board.getOppositePit(board.getPitByIndex(7)).getId());
        Assert.assertEquals(4, board.getOppositePit(board.getPitByIndex(8)).getId());
        Assert.assertEquals(3, board.getOppositePit(board.getPitByIndex(9)).getId());
        Assert.assertEquals(2, board.getOppositePit(board.getPitByIndex(10)).getId());
        Assert.assertEquals(1, board.getOppositePit(board.getPitByIndex(11)).getId());
        Assert.assertEquals(0, board.getOppositePit(board.getPitByIndex(12)).getId());
    }

    @Test
    public void should_get_store_indexes() {
        Board board = new Board("player1");
        Assert.assertEquals(6, board.getStoreIndex(0));
        Assert.assertEquals(13, board.getStoreIndex(1));
        Assert.assertEquals(-1, board.getStoreIndex(2));
    }

    @Test
    public void should_initialize_stones_correctly() {
        Board b = new Board("player1");
        for (int i = 0; i < 14; i++) {
            Pit p = b.getPitByIndex(i);
            Assert.assertEquals(i, p.getId());
            if (i == 6 || i == 13) {
                Assert.assertEquals(0, p.getStones());
            } else {
                Assert.assertEquals(4, p.getStones());
            }
        }
    }
}
