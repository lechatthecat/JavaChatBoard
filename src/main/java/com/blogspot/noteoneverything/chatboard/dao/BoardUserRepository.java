package com.blogspot.noteoneverything.chatboard.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import com.blogspot.noteoneverything.chatboard.model.BoardUser;
import org.springframework.data.domain.Pageable;

@Repository
public interface BoardUserRepository extends JpaRepository<BoardUser,Long>{
    @Query("select bu from BoardUser bu where bu.board = :board and is_deleted = 0")
    List<BoardUser> findBoardUsersByBoard(@Param("board") Board board);
    @Query("select bu from BoardUser bu where bu.board = :board and is_deleted = 0")
    List<BoardUser> findBoardUsersByBoard(@Param("board") Board board, Pageable pageable);
    @Query("select bu from BoardUser bu where bu.user = :user and is_deleted = 0")
    List<BoardUser> findBoardUsersByUser(@Param("user") User user);
    @Query("select bu from BoardUser bu where bu.user = :user and is_deleted = 0")
    List<BoardUser> findBoardUsersByUser(@Param("user") User user, Pageable pageable);
    @Modifying(clearAutomatically = true)
    @Query("update BoardUser bu set is_deleted = 1 where bu.id = :id")
    boolean deleteBoardRUserById(@Param("id") long id);
}