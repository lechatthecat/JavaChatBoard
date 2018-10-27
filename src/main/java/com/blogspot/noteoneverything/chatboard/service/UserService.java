package com.blogspot.noteoneverything.chatboard.service;

import com.blogspot.noteoneverything.chatboard.model.User;
import java.util.Collection;

public interface UserService {
    boolean createUser(User user);
    boolean deleteUser(User user);
    boolean deleteUser(Long id);
    User findByName(String name);
    User findById(long id);
    Collection<User> findUsersByName(String name);
    Collection<User> findUsersByEmail(String email);
}