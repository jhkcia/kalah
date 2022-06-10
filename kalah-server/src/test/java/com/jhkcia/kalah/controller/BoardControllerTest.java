package com.jhkcia.kalah.controller;

import com.jhkcia.kalah.model.Board;
import com.jhkcia.kalah.model.BoardTestUtils;
import com.jhkcia.kalah.model.GameStatus;
import com.jhkcia.kalah.service.BoardService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@RunWith(SpringRunner.class)

public class BoardControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BoardService boardService;

    @Test
    public void testCreateGame() throws Exception {
        Board sampleBoard = new Board("sample-user");
        BoardTestUtils.setFieldValue(sampleBoard, "id", 4);
        when(boardService.newBoard("sample-user")).thenReturn(sampleBoard);

        ResultActions result = mockMvc.perform(post("/api/board")
                .contentType(MediaType.APPLICATION_JSON)
                .header("username", "sample-user"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.player1").value("sample-user"))
                .andExpect(jsonPath("$.player2").isEmpty())
                .andExpect(jsonPath("$.currentTurn").isEmpty())
                .andExpect(jsonPath("$.winner").isEmpty())
                .andExpect(jsonPath("$.status").value(GameStatus.NotStart.toString()))
                .andExpect(jsonPath("$.pits").isArray())
                .andExpect(jsonPath("$.pits").value(Lists.newArrayList(4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0)));
    }

    @Test
    public void testControllerResponseWithEmptyUsername() throws Exception {
        ResultActions result = mockMvc.perform(post("/api/board")
                .header("username", "")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().is4xxClientError())
                .andExpect(content().string("Username is not valid."));
    }

    @Test
    public void testControllerResponseWithoutUsername() throws Exception {
        ResultActions result = mockMvc.perform(post("/api/board")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().is4xxClientError())
                .andExpect(content().string("Username is not valid."));
    }

    @Test
    public void testJoinBoard() throws Exception {
        Board sampleBoard = new Board("sample-user");
        BoardTestUtils.setFieldValue(sampleBoard, "id", 4);
        BoardTestUtils.setFieldValue(sampleBoard, "player2", "second-user");
        when(boardService.joinBoard("second-user", 4)).thenReturn(sampleBoard);

        ResultActions result = mockMvc.perform(post("/api/board/4/join")
                .contentType(MediaType.APPLICATION_JSON)
                .header("username", "second-user"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.player1").value("sample-user"))
                .andExpect(jsonPath("$.player2").value("second-user"));
    }

    @Test
    public void testGetAvailableBoards() throws Exception {
        Board sampleBoard = new Board("test");
        Board sampleBoard2 = new Board("test");
        when(boardService.getAvailableBoards("user")).thenReturn(Arrays.asList(sampleBoard, sampleBoard2));

        ResultActions result = mockMvc.perform(get("/api/board/available")
                .contentType(MediaType.APPLICATION_JSON)
                .header("username", "user"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetSelfBoards() throws Exception {
        Board sampleBoard = new Board("test");
        Board sampleBoard2 = new Board("test");
        when(boardService.getUserBoards("user")).thenReturn(Arrays.asList(sampleBoard, sampleBoard2));

        ResultActions result = mockMvc.perform(get("/api/board/self")
                .contentType(MediaType.APPLICATION_JSON)
                .header("username", "user"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testSowSeeds() throws Exception {
        Board sampleBoard = new Board("test");
        BoardTestUtils.setFieldValue(sampleBoard, "id", 4);
        when(boardService.sowSeeds("user", 4, 2)).thenReturn(sampleBoard);

        ResultActions result = mockMvc.perform(post("/api/board/4/sowSeeds")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pitIndex\": 2}")
                .header("username", "user"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4));
    }

    @Test
    public void testGetBoard() throws Exception {
        Board sampleBoard = new Board("test");
        BoardTestUtils.setFieldValue(sampleBoard, "id", 4);
        when(boardService.getBoard( 4)).thenReturn(sampleBoard);

        ResultActions result = mockMvc.perform(get("/api/board/4")
                .contentType(MediaType.APPLICATION_JSON)
                .header("username", "user"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4));
    }
}
