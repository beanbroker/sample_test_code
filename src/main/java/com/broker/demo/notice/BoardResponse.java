package com.broker.demo.notice;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardResponse {

  private Long boardId;
  private String content;
  private LocalDateTime date;
}
