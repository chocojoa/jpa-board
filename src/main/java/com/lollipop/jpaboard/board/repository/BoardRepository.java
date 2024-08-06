package com.lollipop.jpaboard.board.repository;

import com.lollipop.jpaboard.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BoardRepository extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board> {

    @Override
    @EntityGraph(attributePaths = {"user"})
    Page<Board> findAll(Specification<Board> spec, Pageable pageable);
}
