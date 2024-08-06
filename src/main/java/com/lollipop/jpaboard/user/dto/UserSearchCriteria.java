package com.lollipop.jpaboard.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchCriteria {

    private int page = 0;  // 기본값 설정
    private int size = 10; // 기본값 설정
    private String username;
    private String email;
}
