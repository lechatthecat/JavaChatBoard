package com.blogspot.noteoneverything.chatboard.controller;

import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = String.valueOf(headerAccessor.getSessionAttributes().get("username"));
        Long b_id = Long.valueOf(headerAccessor.getSessionAttributes().get("bid").toString());
        String sb_id = Long.toString(b_id);
        if(username != null && sb_id != null) {
            logger.info("User Disconnected : " + username);

            BoardResponse boardResponse = new BoardResponse();
            boardResponse.setResponse(username + " left!");
            boardResponse.setType(BoardResponse.MessageType.LEAVE);
            boardResponse.setSender(username);

            messagingTemplate.convertAndSend("/board/public/"+sb_id, boardResponse);
        }
    }
}
