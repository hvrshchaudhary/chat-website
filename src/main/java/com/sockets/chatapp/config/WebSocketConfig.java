package com.sockets.chatapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // Enables WebSocket message handling, backed by a message broker.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register the "/ws" endpoint, enabling the SockJS protocol.
        // SockJS is used to enable fallback options for browsers that don’t support WebSocket.
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Configure a message broker that will be used to route messages from one client to another.

        // Use "/app" prefix for messages that are bound for methods annotated with @MessageMapping.
        registry.setApplicationDestinationPrefixes("/app");

        // Use "/topic" prefix for messages that are bound for the message broker.
        // This prefix will be used to define all the destinations that will be handled by the broker.
        registry.enableSimpleBroker("/topic");
    }
}
