package com.blogspot.noteoneverything.chatboard.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import com.blogspot.noteoneverything.chatboard.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    User findByName(String name);
    @Query("select u from User u where u.email = :email and is_deleted = 0 ORDER BY u.id DESC")
    User findByEmail(@Param("email") String email);
    @Query("select u from User u where u.name = :name and is_deleted = 0")
    List<User> findUsersByName(@Param("name") String name);
    @Query("select u from User u where u.email = :email and is_deleted = 0")
    List<User> findUsersByEmail(@Param("email") String email);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User u set is_deleted = 1 where u.id = :id")
    boolean deleteUserById(@Param("id") long id);
}