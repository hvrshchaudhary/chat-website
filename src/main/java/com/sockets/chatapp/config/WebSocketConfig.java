package com.sockets.chatapp.config;

import com.sockets.chatapp.security.CustomUserDetailsService;
import com.sockets.chatapp.security.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // Enables WebSocket message handling, backed by a message broker.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String authToken = accessor.getFirstNativeHeader("Authorization");
                    if (authToken != null && authToken.startsWith("Bearer ")) {
                        authToken = authToken.substring(7);
                        if (jwtTokenUtil.validateToken(authToken)) {
                            String username = jwtTokenUtil.getUsernameFromToken(authToken);
                            CustomUserDetailsService customUserDetailsService = null;
                            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) message));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        } else {
                            throw new IllegalArgumentException("Invalid Token");
                        }
                    }
                }
                return message;
            }
        });
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest");
        registry.setApplicationDestinationPrefixes("/app");
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register the "/ws" endpoint, enabling the SockJS protocol.
        // SockJS is used to enable fallback options for browsers that don’t support WebSocket.
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }


}
