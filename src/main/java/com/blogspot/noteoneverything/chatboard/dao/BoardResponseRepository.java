package com.blogspot.noteoneverything.chatboard.dao;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;

@Repository
public interface BoardResponseRepository extends JpaRepository<BoardResponse,Long>{
    @Query("select br from BoardResponse br where br.user = :user and is_deleted = 0")
    Collection<BoardResponse> findBoardResponsesByUser(@Param("user") User user);
    @Query("select br from BoardResponse br where br.board = :board and is_deleted = 0")
    Collection<BoardResponse> findBoardResponsesByBoard(@Param("board") Board board);
    @Query("select br from BoardResponse br where br.board = :board and br.user = :user and is_deleted = 0")
    Collection<BoardResponse> findBoardResponsesByBoardAndUser(@Param("board") Board board, @Param("user") User user);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update BoardResponse br set is_deleted = 1 where br.id = :id")
    boolean deleteBoardResponseById(@Param("id") long id);
}