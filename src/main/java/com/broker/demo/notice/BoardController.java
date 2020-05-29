package com.broker.demo.notice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {

  private final BoardService boardService;

  public BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  @GetMapping("/boards")
  public BoardPageResponse getEmergencyNotice() {
    return boardService.getBoards();
  }

  @GetMapping("/boards/{boardId}")
  public BoardResponse getEmergencyNotice(@PathVariable("boardId") Long boardId) {
    return boardService.getBoard(boardId);
  }
}
