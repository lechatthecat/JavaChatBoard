package com.blogspot.noteoneverything.chatboard.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import com.blogspot.noteoneverything.chatboard.dao.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.model.Role;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import java.security.Principal;
import com.blogspot.noteoneverything.chatboard.service.BoardService;
import com.blogspot.noteoneverything.chatboard.service.UserService;
import com.blogspot.noteoneverything.chatboard.service.RoleService;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;
import java.lang.Exception;

@Controller
public class ChatController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BoardService boardService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Transactional
    @MessageMapping("/chat.sendMessage")
    @SendTo("/board/public")
    public BoardResponse sendMessage(@Payload BoardResponse boardResponse) {
        //To do. Functionality to check if the sender is authorized to write in the board
        boardResponse.setBoard(boardService.findBoardByIdWithUser(1));
        boardService.createBoardResponse(boardResponse);
        return boardResponse;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/board/public")
    public BoardResponse addUser(@Payload BoardResponse boardResponse,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails principal = (UserDetails) auth.getPrincipal();
        // User user = userRepository.findByName(principal.getUsername());
        // One time password?
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", boardResponse.getSender());
        return boardResponse;
    }

}
