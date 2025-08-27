package com.sinse.electroshop.websocket.dto;

import lombok.Data;

@Data
public class ChatMessage {
    private String type; //클라이언트의 요청을 구분하기 위한 구분 값
    private String sender; //메시지 발신자(자신)
    private String content; //메시지 내용
    private String roomId; //채팅방
 }
