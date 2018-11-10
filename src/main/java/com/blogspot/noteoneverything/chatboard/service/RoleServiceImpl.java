package com.blogspot.noteoneverything.chatboard.service;

import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;
import com.blogspot.noteoneverything.chatboard.service.BoardService;
import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.model.Role;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;
import com.blogspot.noteoneverything.chatboard.model.BoardUser;
import com.blogspot.noteoneverything.chatboard.dao.RoleRepository;
import com.blogspot.noteoneverything.chatboard.dao.BoardResponseRepository;
import com.blogspot.noteoneverything.chatboard.dao.BoardUserRepository;
import org.springframework.data.domain.Pageable;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;

    public Role findById(long id){
        return roleRepository.getOne(id);
    };
}