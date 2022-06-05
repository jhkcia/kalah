package com.jhkcia.kalah.model;

import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

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

    public static Board setFieldValue(Board board, String fieldName, Object value) {
        ReflectionTestUtils.setField(board, fieldName, value);

        return board;
    }

    public static void assertPitsEquals(int[] expectedPits, Board board) {
        for (int i = 0; i < expectedPits.length; i++) {
            assertEquals(String.format("Stones on pit %d are not equal.", i), expectedPits[i], board.getPitByIndex(i).getStones());
        }
    }

}
