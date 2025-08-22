package com.sinse.bootwebsocket.config;

import com.sinse.bootwebsocket.model.chat.ChatTextWebSocketHandler;
import com.sinse.bootwebsocket.model.chat.ChatWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//Spring에서 지원하는 Websocket은 여기서 EndPoint지정할 수 있고
//또한 클라이언트의 요청을 처리하는 객체를 여기에서 등록해야 한다
@RequiredArgsConstructor //매개변수가있는 생성자를 자동으로 생성해주는 롬복의 기능
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    //생성자가 자동으로 정의되어 있으므로 Autowired나 명시적 생성자 주입이 필요 없어짐
    private final ChatWebSocketHandler chatWebSocketHandler;
    private final ChatTextWebSocketHandler chatTextWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatTextWebSocketHandler, "/ws")
                .setAllowedOrigins("*");
    }
}
