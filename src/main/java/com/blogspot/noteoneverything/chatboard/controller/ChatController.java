package com.blogspot.noteoneverything.chatboard.controller;

import com.blogspot.noteoneverything.chatboard.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.blogspot.noteoneverything.chatboard.dao.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import com.blogspot.noteoneverything.chatboard.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import java.security.Principal;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
@Controller
public class ChatController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.private.{username}")
    @SendTo("/topic/public")
    public void sendPrivateMessage(@Payload ChatMessage chatMessage, @DestinationVariable("username") String username, Principal principal) {
        simpMessagingTemplate.convertAndSend("/user/" + username + "/direct/chat.message", chatMessage);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByName(principal.getUsername());
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}
