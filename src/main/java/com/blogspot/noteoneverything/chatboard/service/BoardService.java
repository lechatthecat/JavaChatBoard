package com.blogspot.noteoneverything.chatboard.service;

import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;
import java.util.Collection;

public interface BoardService{
    public boolean createBoard(Board board);
    public boolean createBoardResponse(BoardResponse boardResponse);
    public boolean deleteBoard(Board board);
    public boolean deleteBoardResponse(BoardResponse boardResponse);
    public boolean deleteBoard(long id);
    public boolean deleteBoardResponse(long id);
    public Collection<Board> findBoardsByUser(User user);
    public Collection<BoardResponse> findBoardResponsesByBoard(Board board);
    public Collection<BoardResponse> findBoardResponsesByUser(User user);
    public Collection<BoardResponse> findBoardResponsesByBoardAndUser(Board board, User user);
}