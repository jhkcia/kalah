package com.jhkcia.kalah.controller.mapper;

import com.jhkcia.kalah.controller.dto.BoardDto;
import com.jhkcia.kalah.controller.dto.BoardListDto;
import com.jhkcia.kalah.model.Board;

public class BoardMapper {

    public static BoardDto convertToDto(Board board) {
        int[] pits = new int[Board.PITS_COUNT];
        for (int i = 0; i < Board.PITS_COUNT; i++) {
            pits[i] = board.getPitByIndex(i).getStones();
        }

        return new BoardDto(board.getId(), board.getPlayer1(), board.getPlayer2(), board.getCurrentTurnPlayer(), board.getWinnerPlayer(), board.getStatus(), pits);
    }

    public static BoardListDto convertToListDto(Board board) {
        return new BoardListDto(board.getId(), board.getPlayer1(), board.getPlayer2(), board.getCurrentTurnPlayer(), board.getWinnerPlayer(), board.getStatus());
    }

}
