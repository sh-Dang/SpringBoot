package com.sinse.electroshop.websocket.dto;

import lombok.Data;

import java.util.Set;

/*
* 채팅방 정보를 담는 DTO 객체
* 1) 채팅방의 고유값
* 2) 채팅방 참여자 목록 Set
* 3) 어떤 상품에 대한 채팅방인지
* */
@Data
public class ChatRoom {
    private int product_id; //어떤 상품에 대한 채팅방인지
    private String roomId; //채팅방의 고유 값
    private Set<String> customers; //채팅방 참여자 목록
}