package com.sinse.bootwebsocket.dto;

import lombok.Data;

//서버와 클라이언트 간 대화를 위한 메시지 전달객체
@Data
public class ChatMessage {
    /*
        CONNECT : 접속
        DISCONNECT : 접속해제
        MESSAGE : 채팅
        ROOM_CREATE : 방 만들기
        ROOM_LIST : 방 목록
        ROOM_ENTER : 방 입장
        ROOM_LEAVE : 방 나오기
    */
    private String type; //요청타입
    private String sender; //보낸사람
    private String content; //메시지 내용
    private String roomId;
}
