package com.broker.demo.board;

import com.broker.demo.ApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BoardApiTest extends ApiTest {

  private static final String BOARD_LIST_PATH = "/boards";
  private static final String BOARD_PATH = "/boards/";

  private ResultActions boardListAction() throws Exception {
    return mockMvc.perform(get(BOARD_LIST_PATH)).andDo(print());
  }

  private ResultActions boardAction(Long boardId) throws Exception {
    return mockMvc.perform(get(BOARD_PATH + boardId)).andDo(print());
  }

  @Test
  public void getBoard() throws Exception {
    // given
    Long boardId = 1L;

    // when
    ResultActions resultActions = boardAction(boardId);

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("boardId").exists())
        .andExpect(jsonPath("content").exists())
        .andExpect(jsonPath("date").exists());
  }

  @Test
  public void getBoardList() throws Exception {
    // given
    Long boardId = 1L;

    // when
    ResultActions resultActions = boardListAction();

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("page").exists())
        .andExpect(jsonPath("boardList").isArray());
  }
}
