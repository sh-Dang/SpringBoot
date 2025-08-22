package com.sinse.bootwebsocket.model.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinse.bootwebsocket.dto.ChatMessage;
import com.sinse.bootwebsocket.dto.ChatRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/*
 Spring에서 웹소켓 Server의 Endpoint를 다루는 객체는 WebSocketHandler만 있는게 아님
    TextWebSocketHandler
     - 다루는 데이터가 Text일 경우 효율적
     - 인터페이스가 아니므로 오버라이딩 강제 X(필요한 것만)
*/
@Component
@Slf4j
public class ChatTextWebSocketHandler extends TextWebSocketHandler {

    //java - json 문자열과의 변환을 자동으로 처리해주는 객체
    private ObjectMapper objectMapper = new ObjectMapper();

    //서버에 연결되어 있는 모든 클라이언트 세션 집합
    private Set<WebSocketSession> sessions = new ConcurrentHashMap<>().newKeySet();

    //현재 서버에 접속되어 있는 모든 클라이언트 아이디 집합(클라이언트 전송용)
    private Set<String> connectedUsers = new ConcurrentHashMap<>().newKeySet();

    //전체 방목록 집합
    private Map<String, ChatRoom> roomStorage = new ConcurrentHashMap<>();

    //ObjectMapper에게 변환을 맡길 데이터 형식이 결정되어 있지 않기 때문에 Object
    private void broadcast(String destination, Object data) throws Exception {
        String json = objectMapper.writeValueAsString(
                Map.of("destination", destination, "body", data)
        );
        for (WebSocketSession session : sessions) {
            if(session.isOpen()) {
                session.sendMessage(new TextMessage(json));
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //클라이언트가 접속할 때(서버에 연결된 모든 클라이언트를 넣어야 함)
        sessions.add(session);
    }

    //@OnMessage
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //클라이언트는 java가 이해할 수 없는 json 문자열 형태로 메시지를 전송하므로
        //서버측에서는 해석이 필요하다..(parsing)
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        //클라이언트 요청 분기
        switch (chatMessage.getType()) {
            // 클라이언트가 접속 하자마자 Message전송을 보냈을 때, afterConnection동작 X
            case "CONNECT" ->{
                connectedUsers.add(chatMessage.getSender());
                broadcast("/users", connectedUsers);
            }
            case "DISCONNECT" ->{
                connectedUsers.remove(chatMessage.getSender());
                broadcast("/users", connectedUsers);
            }
            case "MESSAGE" ->{
                broadcast("/messages", chatMessage);
            }
            case "ROOM_CREATE" ->{
                String uuid = UUID.randomUUID().toString();
                ChatRoom chatRoom = new ChatRoom();
                chatRoom.setRoomId(uuid);
                chatRoom.setRoomName(chatMessage.getContent());
                roomStorage.put(uuid, chatRoom);
            }
            case "ROOM_LIST" ->{
                broadcast("/rooms", roomStorage.values());
            }
            case "ROOM_ENTER" ->{
                //방을 검색하여 검색된 방의 Set에 User를 추가
                ChatRoom chatRoom=roomStorage.get(chatMessage.getRoomId());
                if(chatRoom!=null){
                    chatRoom.getUser().add(chatMessage.getSender());
                }
                broadcast("/rooms", roomStorage.values());
            }
            case "ROOM_LEAVE" ->{

            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }
}
