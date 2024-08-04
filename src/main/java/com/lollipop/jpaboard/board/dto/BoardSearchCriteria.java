package com.lollipop.jpaboard.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSearchCriteria {

    private int page = 0;  // 기본값 설정
    private int size = 10; // 기본값 설정
    private String title;
    private String author;
}
