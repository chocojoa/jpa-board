package com.lollipop.jpaboard.board.service;

import com.lollipop.jpaboard.board.dto.BoardDTO;
import com.lollipop.jpaboard.board.entity.Board;
import com.lollipop.jpaboard.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardDTO> getAllBoards() {
        return boardRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    public Optional<BoardDTO> getBoardById(Long id) {
        return boardRepository.findById(id).map(this::convertEntityToDto);
    }

    public BoardDTO createBoard(BoardDTO boardDTO) {
        Board board = new Board();
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setAuthor(boardDTO.getAuthor());
        Board savedBoard = boardRepository.save(board);
        return convertEntityToDto(savedBoard);
    }

    public Optional<BoardDTO> updateBoard(Long id, BoardDTO boardDTO) {
        return boardRepository.findById(id).map(board -> {
            board.setTitle(boardDTO.getTitle());
            board.setContent(boardDTO.getContent());
            board.setAuthor(boardDTO.getAuthor());
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
        boardDTO.setAuthor(board.getAuthor());
        boardDTO.setCreatedAt(board.getCreatedAt());
        boardDTO.setUpdatedAt(board.getUpdatedAt());
        return boardDTO;
    }
}
