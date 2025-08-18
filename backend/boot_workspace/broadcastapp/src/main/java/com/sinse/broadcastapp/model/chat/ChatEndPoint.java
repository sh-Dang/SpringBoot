package com.sinse.broadcastapp.model.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinse.broadcastapp.dto.ResponseChat;
import com.sinse.broadcastapp.dto.ResponseConnect;
import com.sinse.broadcastapp.dto.User;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/* =====================================================================
* 가) 웹 소켓을 구현하는 방법
*   1) 순수 javaEE의 API를 이용하는 방법
*   2) Spring이 지원하는 API를 이용하는 방법
*
* 나) 메시지 교환 방법
*   1) 개발자가 직업 프로토콜을 설계하는 방법
*   2) STOMP 프로토콜을 이용하는 방법
*       - WebSocket 위에서 메시지를 주고받기 위한 메시지 프로토콜
* =====================================================================*/
@Slf4j
@ServerEndpoint("/ws/multi")
@Component
public class ChatEndPoint {
    //유저에게 보낼 데이터가 아닌, 서버측에서 사용할 접속자 정보
    private static Set<Session> userList = new HashSet<>();

    //접속자 명단 중복을 허용하지 않는 set을 많이 사용(collectionframework 中)
    private static Set<String> userIdList = new HashSet();

    //java <--> json 간의 변환을 담당하는 객체
    private static ObjectMapper objectMapper = new ObjectMapper();

    //연결 감지
    @OnOpen
    public void onOpen(Session session) throws IOException {
        log.debug("OnOpen메서드 호출, 생성된 세션의 id는 "+ session.getId());

        //서버에서 사용할 Set에 채우기
        userList.add(session);

//        접속과 동시에 클라이언트에게 서버의 접속자 명단을 전송
//        User user = new User();
//        user.setId(session.getId());
//        user.setName("이세형");

        //접속과 동시에 클라이언트에게 접속자정보를 구성해서 보내자(프로토콜 형식에 맞게)
        /*
        * {
        *   "responseType" : "connect",
        *   "data" : [
        *       "1", "2", "3"
        *   ]
        *
        * }
//        * */
//        StringBuffer sb = new StringBuffer();
//        sb.append("{");
        ResponseConnect responseConnect = new ResponseConnect();
        responseConnect.setResponseType("connect");
        userIdList.add(session.getId());
        responseConnect.setData(userIdList);

        String json = objectMapper.writeValueAsString(responseConnect);
        session.getAsyncRemote().sendText(json);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        log.debug("클라이언트가 전송한 메시지는 "+ message);

        JsonNode jsonNode = objectMapper.readTree(message);
        String requestType = jsonNode.get("requestType").asText();
        if(requestType.equals("chat")){
            log.debug("클라이언트가 채팅하기를 원합니다.내가살기위해서~");
            //클라이언트에게 메시지 전송
            /*
            {
                "responseType":"chat",
                "sender" : "세션id",
                "data":"나 배고파"
            }
            */
            ResponseChat responseChat = new ResponseChat();
            responseChat.setResponseType("chat");
            responseChat.setSender(session.getId());
            String data = jsonNode.get("data").asText();
            responseChat.setData(data);
            String json = objectMapper.writeValueAsString(responseChat);

            for(Session session2:userList) {
                session2.getAsyncRemote().sendText(json);
            }
        }
    }

    @OnClose
    public void onClose(Session session) throws Exception {
        userList.remove(session); //Session이 끊기면 Set에서 제거
        userIdList.remove(session.getId()); //Session id 정보도 함께 제거
    }

    @OnError
    public void onError(Session session, Throwable error) throws Exception {
        userList.remove(session); //Session이 끊기면 Set에서 제거
        userIdList.remove(session.getId()); //Session id 정보도 함께 제거
    }
}
