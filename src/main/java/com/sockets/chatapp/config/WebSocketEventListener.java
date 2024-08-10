package com.sockets.chatapp.config;

import com.sockets.chatapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private UserService userService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        if (headers.getUser() != null) {
            String username = headers.getUser().getName();
            logger.info("Received a new web socket connection from username: {}", username);

            // Mark user as online
            userService.markUserOnline(username);
        } else {
            logger.warn("WebSocket connection without user information.");
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        if (headers.getUser() != null) {
            String username = headers.getUser().getName();
            logger.info("User Disconnected : " + username);

            // Mark user as offline
            userService.markUserOffline(username);
        } else {
            logger.warn("WebSocket disconnection without user information.");
        }
    }
}
