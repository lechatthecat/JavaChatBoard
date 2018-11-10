package com.blogspot.noteoneverything.chatboard.service;

import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.model.Role;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;
import com.blogspot.noteoneverything.chatboard.model.BoardUser;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface RoleService{
    Role findById(long id);
}