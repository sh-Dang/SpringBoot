package com.sinse.stopmchat.controller;

import com.sinse.stopmchat.dto.ChatMessage;
import com.sinse.stopmchat.dto.ChatRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
public class ChatController {
    private Set<String> connectedUsers = new ConcurrentHashMap<>().newKeySet();
    private Map<String, ChatRoom> roomStorage = new ConcurrentHashMap<>();
    /**favicon처리
     * 1) static 디렉토리에 실제로 favicon이미지 보유
     * 2) 컨트롤러에 요청을 처리하되, 이미지를 반환하지 않고 void
     * */
    @GetMapping("favicon.ico")
    @ResponseBody
    public void favicon() {
    }

    /**클라이언트의 접속 요청 처리
      클라이언트가 7777/app/connect로 접속하여 메서드 실행

      고전적인 방식의 WebSocket에서는 클라이언트가 전송한 프로토콜에 의해 if문을 사용했지만,
      STOMP에서는 전통적인 방식이 아닌, 마치 웹 요청을 처리하듯 클라이언트의 요청을 구분할 수 있음
      따라서 개발자가 별도의 프로토콜을 설계할 필요가 없다.
    **/
    @MessageMapping("/connect")
    @SendTo("/topic/users") //이 메서드 실행의 결과는 내부적으로 ObjectMapper에 의한 JSON문자열이다
    // 개발자가 직접 반복문으로 Broadcasting을 수행하는 것이 아닌 /topic/users 구독자에게 자동으로 전송됨
    public Set<String> connect(ChatMessage chatMessage) { //그럼 ChatMessage 객체가 필요하네
        log.debug(chatMessage.getSender()+"의 접속요청 받음");
        connectedUsers.add(chatMessage.getSender());
        return connectedUsers;
    }

    /** 클라이언트의 메시지 전송요청 처리**/
    @MessageMapping("/chat.send")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        log.debug(chatMessage.toString()+"의 메시지 보내기 요청 받음");
        return chatMessage;
    }

    /** 클라이언트의 방 만들기 요청 처리**/
    @MessageMapping("/room.create")
    @SendTo("/topic/rooms")
    public Collection<ChatRoom> createRoom(ChatMessage chatMessage) {
        ChatRoom chatRoom = new ChatRoom();
        //고유 ID
        String roomId = UUID.randomUUID().toString();
        chatRoom.setRoomId(roomId);

        //방 이름
        chatRoom.setRoomName(chatMessage.getContent());
        log.debug(chatMessage.getSender()+"의 " +chatMessage.getContent()+"방 생성요청 처리");
        roomStorage.put(roomId, chatRoom);
        log.debug("지금 있는 방들의 목록은 " + roomStorage.values().toString());
        return roomStorage.values();
    }

    /** 클라이언트의 방 들어가기 요청 처리**/
    @MessageMapping("/room.enter")
    @SendTo("/topic/rooms")
    public Collection<ChatRoom> enterRoom(ChatMessage chatMessage) {
        //방을 검색하여, 발견된 방의 Set에 유저명 넣기
        ChatRoom chatRoom = roomStorage.get(chatMessage.getRoomId());

        if(chatRoom != null){//방이 존재한다면
            chatRoom.getUsers().add(chatMessage.getSender());
        }

        return roomStorage.values();
    }

    /** 클라이언트의 방 나가기 요청 메시지 처리**/
    @MessageMapping("/room.leave")
    @SendTo("/topic/rooms")
    public Collection<ChatRoom> leaveRoom(ChatMessage chatMessage) {
        //서버의 방 목록 중 하나를 찾아내서, 그 안의 Set 유저정보에서 sender를 제거하면 됨
        ChatRoom chatRoom = roomStorage.get(chatMessage.getRoomId());
        if(chatRoom != null){
            chatRoom.getUsers().remove(chatMessage.getSender());
        }
        return roomStorage.values();
    }

    /**방 목록 요청 메시지 처리**/
    @MessageMapping("/room.list")
    @SendTo("/topic/rooms")
    public Collection<ChatRoom> listRoom() {
        return roomStorage.values();
    }
}
