package com.lollipop.jpaboard.board.service;

import com.lollipop.jpaboard.board.dto.BoardDTO;
import com.lollipop.jpaboard.board.dto.BoardSearchCriteria;
import com.lollipop.jpaboard.board.entity.Board;
import com.lollipop.jpaboard.board.repository.BoardRepository;
import com.lollipop.jpaboard.board.specification.BoardSpecification;
import com.lollipop.jpaboard.user.entity.User;
import com.lollipop.jpaboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    public Page<BoardDTO> getAllBoards(BoardSearchCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
        Specification<Board> spec = Specification.where(null);

        if(StringUtils.hasText(criteria.getTitle())) {
            spec = spec.and(BoardSpecification.titleContains(criteria.getTitle()));
        }

        if(StringUtils.hasText(criteria.getAuthor())) {
            spec = spec.and(BoardSpecification.usernameContains(criteria.getAuthor()));
        }
        return boardRepository.findAll(spec, pageable).map(this::convertEntityToDto);
    }

    public Optional<BoardDTO> getBoardById(Long id) {
        return boardRepository.findById(id).map(this::convertEntityToDto);
    }

    public BoardDTO createBoard(BoardDTO boardDTO) {
        Board board = new Board();
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        User user = userRepository.findById(boardDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        board.setUser(user);
        Board savedBoard = boardRepository.save(board);
        return convertEntityToDto(savedBoard);
    }

    public Optional<BoardDTO> updateBoard(Long id, BoardDTO boardDTO) {
        return boardRepository.findById(id).map(board -> {
            board.setTitle(boardDTO.getTitle());
            board.setContent(boardDTO.getContent());
            User user = userRepository.findById(boardDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            board.setUser(user);
            Board updateBoard = boardRepository.save(board);
            return convertEntityToDto(updateBoard);
        });
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    private BoardDTO convertEntityToDto(Board board) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(board.getId());
        boardDTO.setTitle(board.getTitle());
        boardDTO.setContent(board.getContent());
        boardDTO.setUserId(board.getUser().getId());
        boardDTO.setUsername(board.getUser().getUsername());
        boardDTO.setCreatedAt(board.getCreatedAt());
        boardDTO.setUpdatedAt(board.getUpdatedAt());
        return boardDTO;
    }
}
