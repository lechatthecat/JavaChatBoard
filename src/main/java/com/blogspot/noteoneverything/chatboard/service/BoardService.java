package com.blogspot.noteoneverything.chatboard.service;

import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService{
    public long createBoard(Board board);
    public BoardResponse createBoardResponse(Board board, BoardResponse boardResponse);
    public BoardResponse createBoardResponse(BoardResponse boardResponse);
    public boolean deleteBoard(Board board);
    public boolean deleteBoardResponse(BoardResponse boardResponse);
    public boolean deleteBoard(long id);
    public boolean deleteBoardResponse(long id);
    public Board findBoardById(long id);
    public Board findBoardByIdWithUser(long id);
    public List<Board> findBoardsByUser(User user);
    public List<Board> findBoardsByUser(User user, Pageable pageable);
    public List<Board> findBoardsByUserOfBoardResponses(long user_id, int limit);
    public List<Board> findBoardsByUserOfBoardResponses(long user_id);
    public BoardResponse findBoardResponseByIdWithUser(long id);
    public List<BoardResponse> findBoardResponsesByBoard(Board board);
    public List<BoardResponse> findBoardResponsesByBoard(Board board, Pageable pageable);
    public List<BoardResponse> findBoardResponsesByUser(User user);
    public List<BoardResponse> findBoardResponsesByUser(User user, Pageable pageable);
    public List<BoardResponse> findBoardResponsesByBoardAndUser(Board board, User user);
    public List<BoardResponse> findBoardResponsesByBoardAndUser(Board board, User user, Pageable pageable);
    public Long getCountOfBoardsByUser(User user);
    public Long getCountBoardsByUserOfBoardResponses(long user_id, int limit);
    public Long getCountBoardsByUserOfBoardResponses(long user_id);
    public List<Board> getPublicBoards();
    public List<Board> getPublicBoards(Pageable pageable);
    public Page<Board> getPublicBoardPages(Pageable pageable);
    public Long getHowManyUnseenResponses(Board board, User user);
    public Long getHowManyUnseenResponses(User user);
    public HashMap<String,HashMap<String, String>> getLatestResponseTimePerBoard(long user_id);
}