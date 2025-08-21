package com.sinse.bootwebsocket.model.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinse.bootwebsocket.dto.ChatMessage;
import com.sinse.bootwebsocket.dto.ChatRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

//javaee 순수 api로 serverEndpoint를 구현했던 클래스와 같은 역할을 수행하는 클래스
//단, 스프링 기반 api로 구현해본다.
@Component
@Slf4j
public class ChatWebSocketHandler implements WebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    //총 접속자 목록
    //다중 쓰레드 환경에서 동시성문제가 발생하지 않도록설정된 ConcurrentHashMap을 사용해서 만들자
    //일부러 Set을 사용후 동시성문제를 해결할 필요성이 사라짐
    private final Set<String> connectedUsers = new ConcurrentHashMap<>().newKeySet();

    //접속자 목록을 모아놓은 Session의 집합체(내부적으로 보유한 민감정보들을 다룰 session())
    //민감정보를 포함하지 않는 client용 userSession은 connectedUser로 관리
    private final Set<WebSocketSession> sessions = new ConcurrentHashMap<>().newKeySet();

    //방 목록을 저장할 수 있는 Set
    private final Map<String, ChatRoom> roomStorage =  new ConcurrentHashMap<>();//thread 안정성이 높다 : concurrent

    //javaee api의 @OnOpen과 동일
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("새 클라이언트가 연결됨"+ session.getId());
        sessions.add(session);//실질적인 접속자 추가
    }

    /**거의 모든 클라이언트의 요청마다 서버는 접속한 클라이언트들을 대상으로 메시지를 전송해야 하므로
     *  반복문을 수시로 Session 수만큼 돌려야 한다...따라서 공통적인 코드이므로, 아예  메서드로 정의한다**/
    //내부적으로 동작할 로직이기 때문에 public보다는 private로 선언, 관리하는 것이 안정적이다.
    private void broadcast(String destination, Object data) throws Exception {
        Map map = new HashMap();
        String json = objectMapper.writeValueAsString(
                Map.of("destination", destination, "body", data)
        );
        log.debug(json);
        //서버에 현재 접속해 있는 모든 클라이언트의 session만큼 반복
        for(WebSocketSession session:sessions){
            if(session.isOpen()) {
                session.sendMessage(new TextMessage(json));
            }
        }
    }

    //javaee api의 @OnMessage와 동일
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        //계속해서 handleMessage는 동작하는거임(어제의 OnMessage처럼)
        log.debug("메시지 날아옴" + message.getPayload().toString());
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload().toString(), ChatMessage.class);
        //client가 보낸 메시지는 그냥 String일 뿐이므로, 분석을 위해서는 자바의 객체로 만들어야 함
        //client가 보낸 String이 json문자열이므로, 라이브러리중 json 문자열과 java객체간변환을 자동으로 처리해주는 jackson라이브러리를 이용하자
        switch (chatMessage.getType()) {
            //새로운 유저가 접속했으므로, 총 접속자
            case "CONNECT" -> {
                connectedUsers.add(chatMessage.getSender());
                //모든 접속된 유저들에게 접속자 목록에 대한 브로드 캐스팅
                //브로드캐스팅 시, 클라이언트가 서버가 보낸 메세지를 구분할 수 있는
                //채널 혹은 값을 포함해서 보내주자
                broadcast("/users",connectedUsers);
            }
            case "DISCONNECT" -> {
                connectedUsers.remove(chatMessage.getSender());
                broadcast("/users",connectedUsers);
            }
            case "MESSAGE" -> {
                broadcast("/messages", chatMessage);
            }
            case "ROOM_CREATE" -> {
                //방을 생성
                String uuid = UUID.randomUUID().toString();
                ChatRoom chatRoom = new ChatRoom();
                chatRoom.setRoomId(uuid); // UUID
                //콘텐트(ChatMessage의 형식에 맞추어서 받음)
                chatRoom.setRoomName(chatMessage.getContent()); // Content(방 제목)
                roomStorage.put(uuid, chatRoom);//Map 생성자는 (key 값, 담으려는정보)
                broadcast("/rooms",roomStorage.values()); // values() : 식별하는 key값을 제거한 value만 남겨넘겨주는  Map<>의 메서드
            }
            case "ROOM_LIST" -> {

            }
            case "ROOM_ENTER" -> {
                //Map에 모여있는 룸들 중, 클라이언트가 참여하기를 원하는 룸을 검색하자
                ChatRoom chatRoom = roomStorage.get(chatMessage.getRoomId());
                if(chatRoom!=null){
                    chatRoom.getUser().add(chatMessage.getSender()); //참여자로 등록
                }
                broadcast("/rooms",roomStorage.values());
            }
            case "ROOM_LEAVE" -> {
                ChatRoom chatRoom = roomStorage.get(chatMessage.getRoomId());
                if(chatRoom!=null){
                    chatRoom.getUser().remove(chatMessage.getSender()); //room에서 sender를 제거
                }
                broadcast("/rooms",roomStorage.values());
            }
        }
    }

    //javaee api의 @OnError와 동일
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    //javaee api의 @OnClose와 동일
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
