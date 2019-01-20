package com.blogspot.noteoneverything.chatboard.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.blogspot.noteoneverything.chatboard.service.BoardService;
import com.blogspot.noteoneverything.chatboard.service.UserService;
import com.blogspot.noteoneverything.chatboard.service.RoleService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.messaging.simp.user.SimpSession;

import com.blogspot.noteoneverything.chatboard.dao.UserRepository;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;
import com.blogspot.noteoneverything.chatboard.model.User;

@Controller
public class ChatController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BoardService boardService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat.sendMessage/{b_id}")
    public void sendMessage(Principal userPrincipal, @Payload BoardResponse boardResponse, @DestinationVariable String b_id) {
        //To do. Functionality to check if the sender is authorized to write in the board
        //To do: Validation and escape
        UserDetails principal = (UserDetails)((Authentication) userPrincipal).getPrincipal();
        User user = userRepository.findByName(principal.getUsername());
        Board b = boardService.findBoardByIdWithUser(Long.parseLong(b_id)); 
        boardResponse.setUser(user);
        boardResponse.setUserImagePath(user.getUserMainImage().getPath());
        boardResponse.setBoard(b);
        boardResponse.setSender(user.getName());
        boardResponse = boardService.createBoardResponse(boardResponse);
        simpMessagingTemplate.convertAndSend("/board/public/"+b_id, boardResponse);
    }

    @MessageMapping("/chat.addUser/{b_id}")
    public void addUser(Principal userPrincipal, @Payload BoardResponse boardResponse,
                            SimpMessageHeaderAccessor headerAccessor, @DestinationVariable String b_id) {
        // To do: functionality to save current board members somewhere 
        //        and load the members info when board page is loaded.
        UserDetails principal = (UserDetails)((Authentication) userPrincipal).getPrincipal();
        User user = userRepository.findByName(principal.getUsername());
        boardResponse.setUser(user);
        boardResponse.setUserImagePath(user.getUserMainImage().getPath());
        boardResponse.setSender(user.getName());
        boardResponse.setResponse(boardResponse.getSender() + " joined!");
        // Add username and board_id in web socket session
        headerAccessor.getSessionAttributes().put("username", user.getName());
        headerAccessor.getSessionAttributes().put("bid", boardResponse.getBId());
        simpMessagingTemplate.convertAndSend("/board/public/"+b_id, boardResponse);
    }
    
}
