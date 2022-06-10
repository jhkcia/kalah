package com.jhkcia.kalah.service;

import com.jhkcia.kalah.model.Board;

import java.util.List;

public interface BoardService {
    Board newBoard(String username);

    List<Board> getAvailableBoards(String username);

    List<Board> getUserBoards(String username);

    Board joinBoard(String username, long boardId);

    Board sowSeeds(String username, long boardId, int pitIndex);

    Board getBoard(long boardId);

}
