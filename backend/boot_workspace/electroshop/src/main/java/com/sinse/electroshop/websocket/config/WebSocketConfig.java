package com.sinse.electroshop.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //구독 메시지 접두어
        registry.enableSimpleBroker("/topic");

        //클라이언트가 서버로 요청을 보낼때 무조건 붙여야 하는 컨텍스트 루트
        registry.setApplicationDestinationPrefixes("/app");
    }
}
