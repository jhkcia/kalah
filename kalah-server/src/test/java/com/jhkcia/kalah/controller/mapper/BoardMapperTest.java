package com.jhkcia.kalah.controller.mapper;

import com.jhkcia.kalah.controller.dto.BoardDto;
import com.jhkcia.kalah.controller.dto.BoardListDto;
import com.jhkcia.kalah.model.Board;
import com.jhkcia.kalah.model.BoardTestUtils;
import org.junit.Assert;
import org.junit.Test;

public class BoardMapperTest {

    @Test
    public void should_concert_entity_to_dto() {
        Board b = new Board("player1");
        BoardTestUtils.setFieldValue(b, "player2", "player2");
        BoardTestUtils.setFieldValue(b, "winnerPlayer", "player2");
        BoardTestUtils.setFieldValue(b, "currentTurnPlayer", "player2");
        BoardTestUtils.setFieldValue(b, "id", 5L);

        BoardDto boardDto = BoardMapper.convertToDto(b);

        Assert.assertEquals(b.getId(), boardDto.getId());
        Assert.assertEquals(b.getPlayer1(), boardDto.getPlayer1());
        Assert.assertEquals(b.getPlayer2(), boardDto.getPlayer2());
        Assert.assertEquals(b.getWinnerPlayer(), boardDto.getWinner());
        Assert.assertEquals(b.getStatus(), boardDto.getStatus());
        Assert.assertEquals(b.getCurrentTurnPlayer(), boardDto.getCurrentTurn());
        for (int i = 0; i < Board.PITS_COUNT; i++) {
            Assert.assertEquals(b.getPitByIndex(i).getStones(), boardDto.getPits()[i]);
        }

    }

    @Test
    public void should_concert_entity_to_list_dto() {
        Board b = new Board("player1");
        BoardTestUtils.setFieldValue(b, "player2", "player2");
        BoardTestUtils.setFieldValue(b, "winnerPlayer", "player2");
        BoardTestUtils.setFieldValue(b, "currentTurnPlayer", "player2");
        BoardTestUtils.setFieldValue(b, "id", 5L);

        BoardListDto boardDto = BoardMapper.convertToListDto(b);

        Assert.assertEquals(b.getId(), boardDto.getId());
        Assert.assertEquals(b.getPlayer1(), boardDto.getPlayer1());
        Assert.assertEquals(b.getPlayer2(), boardDto.getPlayer2());
        Assert.assertEquals(b.getWinnerPlayer(), boardDto.getWinner());
        Assert.assertEquals(b.getStatus(), boardDto.getStatus());
        Assert.assertEquals(b.getCurrentTurnPlayer(), boardDto.getCurrentTurn());

    }
}
