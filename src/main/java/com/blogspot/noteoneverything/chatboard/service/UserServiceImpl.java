package com.blogspot.noteoneverything.chatboard.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public boolean deleteUser(Long id) {
        try {
            this.userRepository.deleteById(id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Transactional
    public boolean deleteUser(User user) {
        try {
            this.userRepository.deleteById(user.getId());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional
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
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    @Transactional
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public List<User> findUsersByName(String name) {
        return userRepository.findUsersByName(name);
    }

    @Override
    @Transactional
    public List<User> findUsersByEmail(String email) {
        return userRepository.findUsersByEmail(email);
    }
}