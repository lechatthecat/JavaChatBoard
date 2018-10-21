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
    @Query("select u from User u where u.email = :email ORDER BY u.id DESC LIMIT 1")
    User findByEmail(String email);
    @Query("select u from User u where u.name = :name")
    Collection<User> findUsersByName(@Param("name") String name);
    @Query("select u from User u where u.email = :email")
    Collection<User> findUsersByEmail(@Param("email") String email);
}