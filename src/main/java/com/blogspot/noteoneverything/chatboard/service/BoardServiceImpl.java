package com.blogspot.noteoneverything.chatboard.service;

import java.util.List;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;
import com.blogspot.noteoneverything.chatboard.service.BoardService;
import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;
import com.blogspot.noteoneverything.chatboard.dao.BoardRepository;
import com.blogspot.noteoneverything.chatboard.dao.BoardResponseRepository;
import com.blogspot.noteoneverything.chatboard.dao.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Service
public class BoardServiceImpl implements BoardService{
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardResponseRepository boardResponseRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public long createBoard(Board board) {
        Date now = new Date();
        try{
            board.setCreated(now);
            board.setUpdated(now);
            this.boardRepository.save(board);
            return this.boardRepository.save(board).getId();
        }catch(DataAccessException e){
            e.printStackTrace();
            return 0;
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

    @Override
    @Transactional
    public Long getHowManyUnseenResponses(Board board, User user){
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        Root<BoardResponse> from = cq.from(BoardResponse.class);
        cq.select(qb.count(from));
        Predicate boardCondition = qb.equal(from.get("board"), board);
        Predicate userCondition = qb.notEqual(from.get("user"), user);
        Predicate condition = qb.and(boardCondition, userCondition);
        cq.where(condition);
        return em.createQuery(cq).getSingleResult();
    }

    @Override
    @Transactional
    public Long getHowManyUnseenResponses(User user){
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        Root<BoardResponse> from = cq.from(BoardResponse.class);
        cq.select(qb.count(from));
        Predicate userCondition = qb.notEqual(from.get("user"), user);
        cq.where(userCondition);
        return em.createQuery(cq).getSingleResult();
    }

    private Specification<BoardResponse> boardResponseUserEqual(User user){
        return new Specification<BoardResponse>() {
            @Override
            public Predicate toPredicate(Root<BoardResponse> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("user"), user);
            }
        };
    }

    public HashMap<String,HashMap<String, String>> getLatestResponseTimePerBoard(long user_id){
        HashMap<String,HashMap<String,String>> latestResponses = new HashMap<>();
        List<BoardResponse> boardResponses = boardResponseRepository.getLatestResponsePerBoard(user_id);
        for (BoardResponse br : boardResponses) {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", String.valueOf(br.getUser().getId()));
            map.put("time",br.getCreated());
            latestResponses.put(String.valueOf(br.getBoard().getId()),map);
        }
        return latestResponses;
    }
}