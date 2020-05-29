package com.broker.demo.notice;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardPageResponse {

  private Integer page;
  private List<BoardResponse> boardList;
}
