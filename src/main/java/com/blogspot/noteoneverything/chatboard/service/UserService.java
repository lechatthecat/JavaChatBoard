package com.blogspot.noteoneverything.chatboard.service;

import com.blogspot.noteoneverything.chatboard.model.User;
import java.util.Collection;

public interface UserService {
    void createUser(User user);
    boolean delete(Long id);
    User findByName(String name);
    User findById(long id);
    Collection<User> findUsersByName(String name);
    Collection<User> findUsersByEmail(String email);
}