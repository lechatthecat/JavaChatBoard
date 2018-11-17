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
import com.blogspot.noteoneverything.chatboard.service.BoardService;
import com.blogspot.noteoneverything.chatboard.service.UserService;
import com.blogspot.noteoneverything.chatboard.service.RoleService;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;

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
    @MessageMapping("/chat.sendMessage/{b_id}")
    public void sendMessage(@Payload BoardResponse boardResponse, @DestinationVariable String b_id) {
        //To do. Functionality to check if the sender is authorized to write in the board
        boardResponse.setBoard(boardService.findBoardByIdWithUser(Long.parseLong(b_id)));
        boardResponse = boardService.createBoardResponse(boardResponse);
        simpMessagingTemplate.convertAndSend("/board/public/"+b_id, boardResponse);
    }

    @MessageMapping("/chat.addUser/{b_id}")
    public void addUser(@Payload BoardResponse boardResponse,
                               SimpMessageHeaderAccessor headerAccessor, @DestinationVariable String b_id) {
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails principal = (UserDetails) auth.getPrincipal();
        // User user = userRepository.findByName(principal.getUsername());
        // Add username in web socket session
        Long test = boardResponse.getBId();
        headerAccessor.getSessionAttributes().put("username", boardResponse.getSender());
        headerAccessor.getSessionAttributes().put("bid", boardResponse.getBId());
        simpMessagingTemplate.convertAndSend("/board/public/"+b_id, boardResponse);
    }

}
