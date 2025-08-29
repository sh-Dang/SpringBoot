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

import java.util.Collections;
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
        //SimpMessageHeaderAccessor 객체를 이용하면 WebSocket의 Session 에 들어있는 정보를 추출
        //HttpSession 에서 사용자 로그인 정보인 Member를 꺼내보자
        //STOMP 기반으로 HttpSession을 꺼내려면 인터셉터 객체를 구현 및 등록해야 함

        // ====== 여기서 content null 체크 & 안전한 변환 ======
        String content = chatMessage.getContent();
        int product_id = 0;

        if (content != null && !content.isEmpty()) {
            try {
                product_id = Integer.parseInt(content);
            } catch (NumberFormatException e) {
                log.error("chatMessage content가 숫자가 아닙니다: {}", content);
                return Collections.emptySet(); // 처리 중단
            }
        } else {
            log.error("chatMessage content가 null 또는 비어있습니다.");
            return Collections.emptySet(); // 처리 중단
        }
        // ==================================================

        ChatRoom chatRoom = null; //발견된 방

        if(simpMessageHeaderAccessor.getSessionAttributes().get("member") !=null) { //일반 회원이라면..
            Member member = (Member) simpMessageHeaderAccessor.getSessionAttributes().get("member");
            log.debug("웹소켓 Session에서 꺼낸 정보는 " + member.getName());
            //log.debug("클라이언트 접속과 동시에 보낸 메시지 " + message.getContent());

            //일반 회원은 개설된 방에 참여하면 됨..하지만, 일반회원이 들어갈 방을 알아야 함?
            for(ChatRoom room : roomStorage.values()){
                if(room.getProductId()==product_id){
                    chatRoom=room; //고객이 보고있는, 즉 참여중인 방 발견
                    break;
                }
            }

            // ChatRoom이 존재할 경우에만 고객 추가
            if(chatRoom != null) {
                chatRoom.getCustomers().add(member.getId());
            } else {
                log.warn("해당 상품({})에 대한 채팅방이 존재하지 않습니다.", product_id);
                return Collections.emptySet(); // 빈 Set 반환
            }

        }else if(simpMessageHeaderAccessor.getSessionAttributes().get("store") !=null){//상점회원이라면...
            Store store = (Store) simpMessageHeaderAccessor.getSessionAttributes().get("store");
            log.debug("웹소켓 Session에서 꺼낸 정보는 " + store.getStoreName());
            //log.debug("클라이언트 접속과 동시에 보낸 메시지 " + message.getContent());

            //룸을 추가하기 전에 중복여부 판단하기
            boolean exist=false; //중복 여부를 판단할수 있는 기준 변수

            for(ChatRoom room : roomStorage.values()){
                if(room.getProductId()==product_id){ //해당 상품과 관련된 방 발견!!
                    exist=true;
                    chatRoom=room;
                    break;
                }
            }

            //중복이 없을때만 방 추가
            if (exist == false) {
                chatRoom = new ChatRoom();
                chatRoom.setRoomId(UUID.randomUUID().toString());//방의 구분 고유 ID
                chatRoom.setProductId(product_id); //어떤 상품에 대한 채팅방인지...
                chatRoom.getCustomers().add(store.getBusinessId());
                //생성된 방을 전체 룸 리스트에 추가하기
                roomStorage.put(chatRoom.getRoomId(), chatRoom);
            } else {
                //방이 이미 존재하면 해당 방에 참여
                if(chatRoom != null) {
                    chatRoom.getCustomers().add(store.getBusinessId());
                }
            }
        }

        // ChatRoom이 존재할 경우에만 고객 목록 반환
        if(chatRoom != null) {
            return chatRoom.getCustomers();
        } else {
            log.warn("ChatRoom을 찾을 수 없거나 생성할 수 없습니다.");
            return Collections.emptySet(); // 빈 Set 반환
        }
    }

    @MessageMapping("/chat.send") // localhost:9999/app/room_create
    @SendTo("/topic/messages") //topic/messages를 구독한 모든 이들에게 전송
    public ChatMessage send(ChatMessage chatMessage) {
        log.debug("======================="+chatMessage.getSender()+"클라이언트가 전송한 메시지" + chatMessage.getContent());
        return chatMessage;
    }
}