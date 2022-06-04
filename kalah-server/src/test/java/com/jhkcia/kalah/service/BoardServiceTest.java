package com.jhkcia.kalah.service;

import com.jhkcia.kalah.excaption.BoardNotFoundException;
import com.jhkcia.kalah.model.Board;
import com.jhkcia.kalah.model.GameStatus;
import com.jhkcia.kalah.repository.BoardRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class BoardServiceTest {
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testNewBoard() {
        Board board = boardService.newBoard("user1");

        Assert.assertNotNull(board);
        Assert.assertTrue(board.getId() > 0);
        Assert.assertEquals("user1", board.getPlayer1());
        Assert.assertNull(board.getPlayer2());
        Assert.assertNull(board.getWinner());
        Assert.assertEquals(GameStatus.NotStart, board.getStatus());
    }

    @Test
    public void testGetNewUserBoardsShouldBeEmpty() {
        List<Board> boards = boardService.getUserBoards("user1");

        Assert.assertNotNull(boards);
        Assert.assertTrue(boards.isEmpty());
    }

    @Test
    public void testGetUserBoardsShouldReturnUserCreatedBoards() {
        Board board = boardService.newBoard("user1");

        List<Board> boards = boardService.getUserBoards("user1");

        Assert.assertNotNull(boards);
        Assert.assertEquals(1, boards.size());
        Assert.assertEquals(board.getId(), boards.get(0).getId());
    }

    @Test
    public void testGetUserBoardsShouldReturnUserJoinedBoards() {
        Board board = new Board();
        board.setPlayer1("user1");
        board.setPlayer2("user2");
        boardRepository.save(board);

        List<Board> boards = boardService.getUserBoards("user2");

        Assert.assertNotNull(boards);
        Assert.assertEquals(1, boards.size());
        Assert.assertEquals(board.getId(), boards.get(0).getId());
    }

    @Test
    public void testGetAvailableBoardsShouldShowJustEmptyBoards() {
        Board board = new Board();
        board.setPlayer1("user1");
        board.setPlayer2("user2");
        boardRepository.save(board);
        Board board2 = new Board();
        board2.setPlayer1("user1");
        boardRepository.save(board2);

        List<Board> boards = boardService.getAvailableBoards("user2");

        Assert.assertNotNull(boards);
        Assert.assertEquals(1, boards.size());
        Assert.assertEquals(board2.getId(), boards.get(0).getId());
    }

    @Test
    public void testGetAvailableBoardsShouldNotShowCurrentUserBoards() {
        Board board = new Board();
        board.setPlayer1("user1");
        boardRepository.save(board);

        List<Board> boards = boardService.getAvailableBoards("user1");

        Assert.assertNotNull(boards);
        Assert.assertEquals(0, boards.size());
    }

    @Test
    public void testJoinBoard() {
        Board board = boardService.newBoard("user1");

        Board newBoard = boardService.joinBoard("user2", board.getId());

        Assert.assertNotNull(newBoard);
        Assert.assertEquals(board.getId(), newBoard.getId());
        Assert.assertEquals("user1", newBoard.getCurrentTurn());
        Assert.assertEquals("user1", newBoard.getPlayer1());
        Assert.assertEquals("user2", newBoard.getPlayer2());
    }

    @Test
    public void testJoinNotExistBoard() {
        Exception exception = Assert.assertThrows(BoardNotFoundException.class, () -> boardService.joinBoard("user3", -1));

        Assert.assertEquals("Board Not Found.", exception.getMessage());
    }
}
