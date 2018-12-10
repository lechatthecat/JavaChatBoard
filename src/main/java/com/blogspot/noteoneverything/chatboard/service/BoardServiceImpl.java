package com.blogspot.noteoneverything.chatboard.service;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;
import com.blogspot.noteoneverything.chatboard.service.BoardService;
import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;
import com.blogspot.noteoneverything.chatboard.model.BoardUser;
import com.blogspot.noteoneverything.chatboard.dao.BoardRepository;
import com.blogspot.noteoneverything.chatboard.dao.BoardResponseRepository;
import com.blogspot.noteoneverything.chatboard.dao.BoardUserRepository;
import com.blogspot.noteoneverything.chatboard.dao.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class BoardServiceImpl implements BoardService{
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardResponseRepository boardResponseRepository;
    @Autowired
    private BoardUserRepository boardUserRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public boolean createBoard(Board board) {
        Date now = new Date();
        try{
            board.setCreated(now);
            board.setUpdated(now);
            this.boardRepository.save(board);
            return true;
        }catch(DataAccessException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteBoard(Board board) {
        try{
            this.boardRepository.deleteBoardById(board.getId());
            return true;
        }catch(DataAccessException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteBoard(long id) {
        try{
            this.boardRepository.deleteBoardById(id);
            return true;
        }catch(DataAccessException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public BoardResponse createBoardResponse(Board board, BoardResponse boardResponse) {
        Date now = new Date();
        boardResponse.setBoard(board);
        boardResponse.setCreated(now);
        boardResponse.setUpdated(now);
        User user = userRepository.findByName(boardResponse.getSender());
        boardResponse.setUser(user);
        this.boardResponseRepository.save(boardResponse);
        boardResponse.setUserImagePath(user.getUserMainImage().getPath());
        return boardResponse;
    }

    @Override
    @Transactional
    public BoardResponse createBoardResponse(BoardResponse boardResponse) {
        Date now = new Date();
        boardResponse.setCreated(now);
        boardResponse.setUpdated(now);
        User user = userRepository.findByName(boardResponse.getSender());
        boardResponse.setUser(user);
        this.boardResponseRepository.save(boardResponse);
        boardResponse.setUserImagePath(user.getUserMainImage().getPath());
        return boardResponse;
    }

    @Override
    @Transactional
    public boolean deleteBoardResponse(BoardResponse boardResponse) {
        try{
            this.boardResponseRepository.deleteBoardResponseById(boardResponse.getId());
            return true;
        }catch(DataAccessException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteBoardResponse(long id) {
        try{
            this.boardResponseRepository.deleteBoardResponseById(id);
            return true;
        }catch(DataAccessException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public Board findBoardById(long id){
        return boardRepository.findBoardById(id);
    };

    @Override
    @Transactional
    public Board findBoardByIdWithUser(long id){
        return boardRepository.findBoardByIdWithUser(id);
    }

    @Override
    @Transactional
    public List<Board> findBoardsByUser(User user){
        return boardRepository.findBoardsByUser(user);
    };

    @Override
    @Transactional
    public List<Board> findBoardsByUser(User user, Pageable pageable){
        return boardRepository.findBoardsByUser(user, pageable);
    };

    @Override
    @Transactional
    public List<Board> findBoardsByUserOfBoardResponses(long user_id, int limit){
        return boardRepository.findBoardsByUserOfBoardResponses(user_id, limit);
    }

    @Override
    @Transactional
    public List<Board> findBoardsByUserOfBoardResponses(long user_id){
        return boardRepository.findBoardsByUserOfBoardResponses(user_id);
    }

    @Override
    @Transactional
    public BoardResponse findBoardResponseByIdWithUser(long id){
        return boardResponseRepository.findBoardResponseByIdWithUser(id);
    }

    @Override
    @Transactional
    public List<BoardResponse> findBoardResponsesByBoard(Board board){
        return boardResponseRepository.findBoardResponsesByBoard(board);
    };

    @Override
    @Transactional
    public List<BoardResponse> findBoardResponsesByBoard(Board board, Pageable pageable){
        return boardResponseRepository.findBoardResponsesByBoard(board, pageable);
    };

    @Override
    @Transactional
    public List<BoardResponse> findBoardResponsesByUser(User user){
        return boardResponseRepository.findBoardResponsesByUser(user);
    };

    @Override
    @Transactional
    public List<BoardResponse> findBoardResponsesByUser(User user, Pageable pageable){
        return boardResponseRepository.findBoardResponsesByUser(user, pageable);
    };

    @Override
    @Transactional
    public List<BoardResponse> findBoardResponsesByBoardAndUser(Board board, User user){
        return boardResponseRepository.findBoardResponsesByBoardAndUser(board, user);
    };

    @Override
    @Transactional
    public List<BoardResponse> findBoardResponsesByBoardAndUser(Board board, User user, Pageable pageable){
        return boardResponseRepository.findBoardResponsesByBoardAndUser(board, user, pageable);
    };

    @Override
    @Transactional
    public List<BoardUser> findBoardUsersByBoard(Board board){
        return boardUserRepository.findBoardUsersByBoard(board);
    }

    @Override
    @Transactional
    public List<BoardUser> findBoardUsersByBoard(Board board, Pageable pageable){
        return boardUserRepository.findBoardUsersByBoard(board, pageable);
    }
    
    @Override
    @Transactional
    public List<BoardUser> findBoardUsersByUser(User user){
        return boardUserRepository.findBoardUsersByUser(user);
    }
    
    @Override
    @Transactional
    public List<BoardUser> findBoardUsersByUser(User user, Pageable pageable){
        return boardUserRepository.findBoardUsersByUser(user, pageable);
    }
    
    @Override
    @Transactional
    public boolean deleteBoardRUserById(long id){
        return boardUserRepository.deleteBoardRUserById(id);
    }

    @Override
    @Transactional
    public Long getCountOfBoardsByUser(User user){
        return  boardRepository.getCountOfBoardsByUser(user);
    }

    @Override
    @Transactional
    public Long getCountBoardsByUserOfBoardResponses(long user_id, int limit){
        return  boardRepository.getCountBoardsByUserOfBoardResponses(user_id, limit);
    }

    @Override
    @Transactional
    public Long getCountBoardsByUserOfBoardResponses(long user_id){
        return  boardRepository.getCountBoardsByUserOfBoardResponses(user_id);
    }

    @Override
    @Transactional
    public List<Board> getPublicBoards(){
        return  boardRepository.getPublicBoards();
    }

    @Override
    @Transactional
    public List<Board> getPublicBoards(Pageable pageable){
        return  boardRepository.getPublicBoards(pageable);
    }

    @Override
    @Transactional
    public Page<Board> getPublicBoardPages(Pageable pageable){
        return  boardRepository.getPublicBoardPages(pageable);
    }

}