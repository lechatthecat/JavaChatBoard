package com.blogspot.noteoneverything.chatboard.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import com.blogspot.noteoneverything.chatboard.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    @Query("select u from User u where u.id = :id and is_deleted = 0")
    User findById(@Param("id")long id);
    @Query("select u from User u where u.name = :name and is_deleted = 0")
    User findByName(String name);
    @Query("select u from User u where u.email = :email and is_deleted = 0")
    User findByEmail(@Param("email") String email);
    @Query("select u from User u where u.name = :name and is_deleted = 0 ORDER BY u.id DESC")
    List<User> findUsersByName(@Param("name") String name);
    @Query("select u from User u where u.email = :email and is_deleted = 0 ORDER BY u.id DESC")
    List<User> findUsersByEmail(@Param("email") String email);
    @Modifying(clearAutomatically = true)
    @Query("update User u set is_deleted = 1 where u.id = :id")
    boolean deleteUserById(@Param("id") long id);
}