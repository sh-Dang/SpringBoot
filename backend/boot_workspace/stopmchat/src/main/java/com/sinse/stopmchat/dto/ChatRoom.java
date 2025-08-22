package com.sinse.stopmchat.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoom {
    private String roomId; //고유id
    private String roomName; //방 제목
    private Set<String> users = new HashSet<>(); //방 참여자 목록
}
