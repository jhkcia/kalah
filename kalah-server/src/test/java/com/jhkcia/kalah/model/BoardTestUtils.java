package com.jhkcia.kalah.model;

import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

public class BoardTestUtils {
    public static Board setPits(Board board, int[] pitStoneCount) {
        List<Pit> pits = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            Pit pit = new Pit(i);
            pit.setStones(pitStoneCount[i]);
            pits.add(pit);
        }
        ReflectionTestUtils.setField(board, "pits", pits);

        return board;
    }

    public static Board setStatus(Board board, GameStatus status) {
        ReflectionTestUtils.setField(board, "status", status);

        return board;
    }
}
