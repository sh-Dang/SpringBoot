package com.sinse.electroshop.controller.shop;

import com.sinse.electroshop.domain.Member;
import com.sinse.electroshop.domain.Store;
import com.sinse.electroshop.websocket.dto.ChatMessage;
import com.sinse.electroshop.websocket.dto.ChatRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 Spring의 STOMP를 이용하면 웹 소켓을 일반컨트롤러로 제어할 수 있음
 **/
@Slf4j
@Controller
public class ChatController {
    //서버에 접속한 모든 유저목록 //+)STOMP는 Session을 직접 제어하지 않는 툴
    private Set<String> connectedUsers = new ConcurrentHashMap<>().newKeySet();

    //서버에 존재하는 모든 방 목록(상품의 수와 일치)
    private Map<String, ChatRoom> roomStorage= new ConcurrentHashMap<>();

    /**=============================================
     * 접속 요청 처리
     * 해당상품과 관련된 방 선택 -> 그 방에 참여한 고객목록 반환
     * =============================================*/
    @SendTo("/topic/users")
    @MessageMapping("/connect") // localhost:9999/app/connect
    public Set<String> connect(ChatMessage chatMessage, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        log.debug("클라이언트 접속함");

        String roomId =  chatMessage.getContent();
        /**===============================================
         *        참여하지 않은경우 방에 참여하기
         *===============================================*/
        int product_id = Integer.parseInt(chatMessage.getContent());
        ChatRoom chatRoom = null; //발견된 방

        //SimpMessageHeaderAccessor 객체를 이용하면 WebSocket의 Session에 들어있는 정보를 추출
        if(simpMessageHeaderAccessor.getSessionAttributes().get("member") != null) {
            Member member = (Member) simpMessageHeaderAccessor.getSessionAttributes().get("member");
            log.debug("웹소켓 세션에서 꺼낸 정보는" + member.getName());
            log.debug("클라이언트 접속과 동시에 보낸 메시지" + chatMessage.getContent());
            //HttpSession에서 사용자 로그인 정보인 Member를 꺼내보자
            //STOMP 기반으로 HttpSession을 꺼내려면 인터셉터 객체를 구현 및 등록해야한다.

            for(ChatRoom room:roomStorage.values()){
                if(room.getProductId()==product_id){
                    chatRoom = room; //고객이 보고있는, 즉 참여중인 방 발견
                    break;
                }
            }


            //참여한 방의 유저를 얻어와 return하기 @SendTo
            chatRoom.getCustomers().add(member.getId());

        }else if(simpMessageHeaderAccessor.getSessionAttributes().get("store") != null) {
            Store store = (Store) simpMessageHeaderAccessor.getSessionAttributes().get("store");

            //중복검사 후 생성 해 주어야 함
            boolean isRoomExists = false;

            for(ChatRoom room : roomStorage.values()){
                if(room.getProductId()==product_id){ //해당상품의 pk를 가지는 방 발견
                    isRoomExists = true;
                    chatRoom = room;
                    break;
                }
            }

            if(isRoomExists == false) {
                chatRoom = new ChatRoom();

                String uuid = UUID.randomUUID().toString();
                chatRoom.setRoomId(uuid);
                chatRoom.setProductId(product_id);
                chatRoom.getCustomers().add(store.getBusinessId());

                //roomStorage에 생성된 방 추가
                roomStorage.put(chatRoom.getRoomId(), chatRoom);
            }

            //방 참여하기
            /// Set은 중복되지 않기 때문에 id를 넣은 이상 여러개의 객체가 생성되지 않음.
            chatRoom.getCustomers().add(store.getBusinessId());
        }
        return chatRoom.getCustomers();
    }

    @MessageMapping("/room_create") // localhost:9999/app/room_create
    public ChatRoom createRoom(){
        return null;
    }
}


