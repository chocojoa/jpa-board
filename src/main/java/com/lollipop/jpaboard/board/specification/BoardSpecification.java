package com.lollipop.jpaboard.board.specification;

import com.lollipop.jpaboard.board.entity.Board;
import org.springframework.data.jpa.domain.Specification;

public class BoardSpecification {

    public static Specification<Board> titleContains(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Board> usernameContains(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("username"), "%" + username + "%");
    }

}
