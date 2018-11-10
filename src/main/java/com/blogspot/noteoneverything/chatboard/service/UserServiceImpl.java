package com.blogspot.noteoneverything.chatboard.service;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.blogspot.noteoneverything.chatboard.dao.UserRepository;
import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.model.Role;
import com.blogspot.noteoneverything.chatboard.util.Constants;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserRepository userRepository;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean deleteUser(Long id) {
        try {
            this.userRepository.deleteById(id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteUser(User user) {
        try {
            this.userRepository.deleteById(user.getId());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Transactional
    @Override
    public boolean createUser(User user) {
        try {
            Date now = new Date();
            user.setCreated(now);
            user.setUpdated(now);
            user.setIsDeleted(false);
            user.setRole(new Role());
            user.getRole().setId(Constants.USER_ROLE);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            this.userRepository.save(user);
            return true;
        }catch(DataAccessException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findUsersByName(String name) {
        return userRepository.findUsersByName(name);
    }

    @Override
    public List<User> findUsersByEmail(String email) {
        return userRepository.findUsersByEmail(email);
    }
}