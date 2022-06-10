package com.jhkcia.kalah.controller;

import com.jhkcia.kalah.dto.BoardDto;
import com.jhkcia.kalah.dto.BoardListDto;
import com.jhkcia.kalah.dto.SowSeedDto;
import com.jhkcia.kalah.excaption.InvalidUsernameException;
import com.jhkcia.kalah.mapper.BoardMapper;
import com.jhkcia.kalah.service.BoardService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    private void validateUsername(String username) {
        if (!StringUtils.hasText(username)) {
            throw new InvalidUsernameException();
        }
    }

    @GetMapping("/available")
    public List<BoardListDto> getAvailableBoards(@RequestHeader("username") String username) {
        validateUsername(username);

        return boardService.getAvailableBoards(username)
                .stream().map(BoardMapper::convertToListDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/self")
    public List<BoardListDto> getUserBoards(@RequestHeader("username") String username) {
        validateUsername(username);

        return boardService.getUserBoards(username)
                .stream().map(BoardMapper::convertToListDto)
                .collect(Collectors.toList());
    }

    @PostMapping()
    public BoardDto addBoard(@RequestHeader("username") String username) {
        validateUsername(username);

        return BoardMapper.convertToDto(boardService.newBoard(username));
    }

    @PostMapping("/{boardId}/join")
    public BoardDto joinBoard(@RequestHeader("username") String username, @PathVariable("boardId") Long boardId) {
        validateUsername(username);

        return BoardMapper.convertToDto(boardService.joinBoard(username, boardId));
    }

    @PostMapping("/{boardId}/sowSeeds")
    public BoardDto sowSeeds(@RequestHeader("username") String username, @PathVariable("boardId") Long boardId, @RequestBody SowSeedDto sowSeedDto) {
        validateUsername(username);

        return BoardMapper.convertToDto(boardService.sowSeeds(username, boardId, sowSeedDto.getPitIndex()));
    }

    @GetMapping("/{boardId}")
    public BoardDto getBoard(@RequestHeader("username") String username, @PathVariable("boardId") Long boardId) {
        validateUsername(username);
        return BoardMapper.convertToDto(boardService.getBoard(boardId));
    }
}
