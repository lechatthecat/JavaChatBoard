package com.blogspot.noteoneverything.chatboard.service;

import java.util.Date;
import java.util.Collection;

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

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserRepository userRepository;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    public boolean delete(Long id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        try {
            long updatedID = jdbcTemplate.update("DELETE FROM users WHERE id=" + id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Transactional
    @Override
    public void createUser(User user) {
        Date now = new Date();
        user.setCreated(now);
        user.setUpdated(now);
        user.setIsDeleted(false);
        user.setRole(new Role());
        user.getRole().setId(2); // USER_ROLE
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User findById(long id) {
        return userRepository.getOne(id);
    }

    @Override
    public Collection<User> findUsersByName(String name) {
        return userRepository.findUsersByName(name);
    }

    @Override
    public Collection<User> findUsersByEmail(String email) {
        return userRepository.findUsersByEmail(email);
    }
}