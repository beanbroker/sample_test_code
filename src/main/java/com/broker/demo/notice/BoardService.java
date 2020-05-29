package com.broker.demo.notice;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

  public BoardResponse getBoard(Long boardId) {

    BoardResponse boardResponse = new BoardResponse();
    boardResponse.setBoardId(boardId);
    boardResponse.setContent("board of board");
    boardResponse.setDate(LocalDateTime.now());
    return boardResponse;
  }

  public BoardPageResponse getBoards() {

    BoardResponse fistBoard = new BoardResponse();
    fistBoard.setBoardId(1L);
    fistBoard.setContent("first board");
    fistBoard.setDate(LocalDateTime.now());

    BoardResponse secondBoard = new BoardResponse();
    secondBoard.setBoardId(2L);
    secondBoard.setContent("second board");
    secondBoard.setDate(LocalDateTime.now());

    BoardResponse thirdBoard = new BoardResponse();
    thirdBoard.setBoardId(3L);
    thirdBoard.setContent("third board");
    thirdBoard.setDate(LocalDateTime.now());

    List<BoardResponse> boardList = new ArrayList<>();
    boardList.add(fistBoard);
    boardList.add(secondBoard);
    boardList.add(thirdBoard);

    BoardPageResponse boardPageResponse = new BoardPageResponse();

    boardPageResponse.setPage(0);
    boardPageResponse.setBoardList(boardList);

    return boardPageResponse;
  }
}
