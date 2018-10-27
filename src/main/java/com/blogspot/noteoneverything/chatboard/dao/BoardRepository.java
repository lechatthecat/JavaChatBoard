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

@Repository
public interface BoardRepository extends JpaRepository<Board,Long>{
    @Query("select b from Board b where b.user = :user and is_deleted = 0")
    Collection<Board> findBoardsByUser(@Param("user") User user);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Board b set is_deleted = 1 where b.id = :id")
    boolean deleteBoardById(@Param("id") long id);
}