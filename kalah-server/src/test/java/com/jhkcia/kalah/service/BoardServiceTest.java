package com.jhkcia.kalah.service;

import com.jhkcia.kalah.exception.BoardNotFoundException;
import com.jhkcia.kalah.model.Board;
import com.jhkcia.kalah.model.GameStatus;
import com.jhkcia.kalah.repository.BoardRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.jhkcia.kalah.model.BoardTestUtils.assertPitsEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class BoardServiceTest {
    @Autowired
    private BoardService boardService;

    @MockBean
    BoardNotificationSender notificationSender;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testNewBoard() {
        Board board = boardService.newBoard("user1");

        Assert.assertNotNull(board);
        Assert.assertTrue(board.getId() > 0);
        Assert.assertEquals("user1", board.getPlayer1());
        Assert.assertNull(board.getPlayer2());
        Assert.assertNull(board.getWinnerPlayer());
        Assert.assertEquals(GameStatus.NotStart, board.getStatus());

        Mockito.verify(notificationSender, never()).notifyUpdate(anyLong(), any());
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
        Assert.assertEquals("user1", newBoard.getCurrentTurnPlayer());
        Assert.assertEquals("user1", newBoard.getPlayer1());
        Assert.assertEquals("user2", newBoard.getPlayer2());

        Mockito.verify(notificationSender, times(1)).notifyUpdate(board.getId(), "JOINED");

    }

    @Test
    public void testJoinNotExistBoard() {
        Exception exception = Assert.assertThrows(BoardNotFoundException.class, () -> boardService.joinBoard("user3", -1));

        Assert.assertEquals("Board Not Found.", exception.getMessage());
    }

    @Test
    public void testSowSeeds() {
        Board board = boardService.newBoard("user1");
        board = boardService.joinBoard("user2", board.getId());

        board = boardService.sowSeeds("user1", board.getId(), 0);

        assertPitsEquals(new int[]{0, 5, 5, 5, 5, 4, 0, 4, 4, 4, 4, 4, 4, 0}, board);
        assertEquals("user2", board.getCurrentTurnPlayer());
        assertEquals(GameStatus.Playing, board.getStatus());
        assertNull(board.getWinnerPlayer());

        Mockito.verify(notificationSender, times(1)).notifyUpdate(board.getId(), "SOW_SEEDS");

    }

    @Test
    public void testSowSeedsOfNotExistBoard() {
        Exception exception = Assert.assertThrows(BoardNotFoundException.class, () -> boardService.sowSeeds("user3", -1, 2));

        Assert.assertEquals("Board Not Found.", exception.getMessage());

        Mockito.verify(notificationSender, never()).notifyUpdate(anyLong(), any());

    }

    @Test
    public void testGetBoard() {
        Board sampleBoard = boardService.newBoard("user1");

        Board board = boardService.getBoard(sampleBoard.getId());
        
        Assert.assertNotNull(board);
        Assert.assertEquals(sampleBoard.getId(), board.getId());
        Assert.assertEquals("user1", board.getPlayer1());
        Assert.assertNull(board.getPlayer2());
        Assert.assertNull(board.getWinnerPlayer());
        Assert.assertEquals(GameStatus.NotStart, board.getStatus());

        Mockito.verify(notificationSender, never()).notifyUpdate(anyLong(), any());

    }
}
