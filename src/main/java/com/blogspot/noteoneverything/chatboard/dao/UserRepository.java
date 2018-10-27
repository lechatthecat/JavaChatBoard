package com.blogspot.noteoneverything.chatboard.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogspot.noteoneverything.chatboard.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    User findByName(String name);
    @Query("select u from User u where u.email = :email and is_deleted = 0 ORDER BY u.id DESC")
    User findByEmail(@Param("email") String email);
    @Query("select u from User u where u.name = :name and is_deleted = 0")
    Collection<User> findUsersByName(@Param("name") String name);
    @Query("select u from User u where u.email = :email and is_deleted = 0")
    Collection<User> findUsersByEmail(@Param("email") String email);
}