package com.blogspot.noteoneverything.chatboard.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long>{
    @Query("select b from Board b where b.id = :id and is_deleted = 0")
    Board findBoardById(@Param("id") long id);
    @Query("select b from Board b where b.id = :id and is_deleted = 0")
    Board findBoardByIdWithUser(@Param("id") long id);
    @Query("select b from Board b where b.is_private = 0 and is_deleted = 0 order by b.updated desc")
    List<Board> getPublicBoards();
    @Query("select b from Board b where b.is_private = 0 and is_deleted = 0 order by b.updated desc")
    List<Board> getPublicBoards(Pageable pageable);
    @Query("select b from Board b where b.is_private = 0 and is_deleted = 0 order by b.updated desc")
    Page<Board> getPublicBoardPages(Pageable pageable);
    @Query("select b from Board b LEFT JOIN FETCH b.user where b.user = :user and b.is_deleted = 0")
    List<Board> findBoardsByUser(@Param("user") User user);
    @Query("select b from Board b LEFT JOIN FETCH b.user where b.user = :user and b.is_deleted = 0 order by b.updated desc")
    List<Board> findBoardsByUser(@Param("user") User user, Pageable pageable);
    @Query("select count(b) from Board b where b.user = :user and b.is_deleted = 0")
    Long getCountOfBoardsByUser(@Param("user") User user);
    @Query(value = "select * from boards inner join (select br1.board_id, br1.id, br1.updated from (select * from board_responses where board_id in ((select board_id from board_responses where user_id = ?1))) AS br1 "
        + "left join (select * from board_responses where board_id in (select board_id from board_responses where user_id = ?1)) AS br2 ON (br1.board_id = br2.board_id AND br1.updated < br2.updated) where br2.updated is null limit ?2) br " 
        + "on boards.id = br.board_id " 
        + "order by br.updated desc limit ?2", nativeQuery = true)
    List<Board> findBoardsByUserOfBoardResponses(long user_id, int limit);
    @Query(value = "select count(*) from boards inner join (select br1.board_id, br1.id, br1.updated from (select * from board_responses where board_id in ((select board_id from board_responses where user_id = ?1))) AS br1 "
    + "left join (select * from board_responses where board_id in (select board_id from board_responses where user_id = ?1)) AS br2 ON (br1.board_id = br2.board_id AND br1.updated < br2.updated) where br2.updated is null limit ?2) br " 
    + "on boards.id = br.board_id " 
    + "order by br.updated desc limit ?2", nativeQuery = true)
    Long getCountBoardsByUserOfBoardResponses(long user_id, int limit);
    @Query(value = "select count(*) from boards inner join (select br1.board_id, br1.id, br1.updated from (select * from board_responses where board_id in ((select board_id from board_responses where user_id = ?1))) AS br1 "
    + "left join (select * from board_responses where board_id in (select board_id from board_responses where user_id = ?1)) AS br2 ON (br1.board_id = br2.board_id AND br1.updated < br2.updated) where br2.updated is null) br " 
    + "on boards.id = br.board_id " 
    + "order by br.updated desc", nativeQuery = true)
    Long getCountBoardsByUserOfBoardResponses(long user_id);
    @Query(value = "select * from boards inner join (select br1.board_id, br1.id, br1.updated from (select * from board_responses where board_id in ((select board_id from board_responses where user_id = ?1))) AS br1 "
        + "left join (select * from board_responses where board_id in (select board_id from board_responses where user_id = ?1)) AS br2 ON (br1.board_id = br2.board_id AND br1.updated < br2.updated) where br2.updated is null limit ?2) br " 
        + "on boards.id = br.board_id " 
        + "limit ?2", nativeQuery = true)
    List<Board> findBoardsByUserOfBoardResponses(long user_id);
    @Modifying(clearAutomatically = true)
    @Query("update Board b set is_deleted = 1 where b.id = :id")
    boolean deleteBoardById(@Param("id") long id);
}