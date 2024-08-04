package com.lollipop.jpaboard.board.repository;

import com.lollipop.jpaboard.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
