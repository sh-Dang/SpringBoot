package com.sinse.chatroomapp.config;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import lombok.extern.slf4j.Slf4j;

//OnOpen시점에 HttpSession에 접근 => Session과 연결하기위한 설정 클래스
@Slf4j
public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        log.debug(httpSession.toString());
        if (httpSession != null) {
            log.debug("config의 httpSession if문에도 들어왔어");
            log.debug(HttpSessionConfigurator.class.getName());
            config.getUserProperties().put(HttpSession.class.getName(), httpSession);
        }
    }
}