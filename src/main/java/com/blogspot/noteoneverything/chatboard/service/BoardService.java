package com.blogspot.noteoneverything.chatboard.service;

import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BoardService{
    public boolean createBoard(Board board);
    public boolean createBoardResponse(BoardResponse boardResponse);
    public boolean deleteBoard(Board board);
    public boolean deleteBoardResponse(BoardResponse boardResponse);
    public boolean deleteBoard(long id);
    public boolean deleteBoardResponse(long id);
    public List<Board> findBoardsByUser(User user);
    public List<Board> findBoardsByUser(User user, Pageable pageable);
    public List<BoardResponse> findBoardResponsesByBoard(Board board);
    public List<BoardResponse> findBoardResponsesByBoard(Board board, Pageable pageable);
    public List<BoardResponse> findBoardResponsesByUser(User user);
    public List<BoardResponse> findBoardResponsesByUser(User user, Pageable pageable);
    public List<BoardResponse> findBoardResponsesByBoardAndUser(Board board, User user);
    public List<BoardResponse> findBoardResponsesByBoardAndUser(Board board, User user, Pageable pageable);
}