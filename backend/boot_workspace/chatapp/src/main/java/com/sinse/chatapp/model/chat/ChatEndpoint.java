package com.sinse.chatapp.model.chat;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@ServerEndpoint("/ws/echo")
@Component
public class ChatEndpoint {
    //접속자 감지 메서드
    /*웹 소켓에서는 더이상 java SE시절의 Socket객체가 통신을 담당하지 않고
      Session 객체가 담당
      접속자마다 1:1대응하는 Thread 제어가 필요없음
    * */
    @OnOpen
    public void onOpen(Session session) {
        log.debug("오픈..!"+session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.debug("onMessage: "+session.getId()+","+message);
        //클라이언트에게 메시지 전송
        session.getBasicRemote().sendText("server : "+message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        log.debug("onClose: "+session.getId()+","+closeReason);
    }
    @OnError
    public void onError(Session session, Throwable thr) {
        thr.printStackTrace();
    }
}
