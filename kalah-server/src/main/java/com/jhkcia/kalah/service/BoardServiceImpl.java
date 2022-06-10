package com.jhkcia.kalah.service;

import com.jhkcia.kalah.excaption.BoardNotFoundException;
import com.jhkcia.kalah.model.Board;
import com.jhkcia.kalah.repository.BoardRepository;
import com.jhkcia.kalah.service.external.BoardNotificationSender;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final BoardNotificationSender boardNotificationSender;

    public BoardServiceImpl(BoardRepository boardRepository, BoardNotificationSender boardNotificationSender) {
        this.boardRepository = boardRepository;
        this.boardNotificationSender = boardNotificationSender;
    }

    @Override
    public Board newBoard(String username) {
        Board board = new Board(username);

        return boardRepository.save(board);
    }

    @Override
    public List<Board> getAvailableBoards(String username) {
        return boardRepository.findAllByPlayer2IsNullAndPlayer1Not(username);
    }

    @Override
    public List<Board> getUserBoards(String username) {
        return boardRepository.findAllByPlayer1OrPlayer2(username, username);
    }

    @Override
    public Board joinBoard(String username, long boardId) {
        Board selectedBoard = boardRepository.findById(boardId).map((board -> {
            board.join(username);
            return boardRepository.save(board);
        })).orElseThrow(BoardNotFoundException::new);

        this.boardNotificationSender.notifyUpdate(selectedBoard.getId(), "JOINED");

        return selectedBoard;
    }

    @Override
    public Board sowSeeds(String username, long boardId, int pitIndex) {
        Board selectedBoard = boardRepository.findById(boardId).map((board -> {
            board.sowSeeds(username, pitIndex);
            return boardRepository.save(board);
        })).orElseThrow(BoardNotFoundException::new);

        this.boardNotificationSender.notifyUpdate(selectedBoard.getId(), "SOW_SEEDS");
        return selectedBoard;
    }

    @Override
    public Board getBoard(long boardId) {
        return boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
    }
}
