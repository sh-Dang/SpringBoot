package com.sinse.chatroomapp.model.dto;

import com.sinse.chatroomapp.domain.Member;
import lombok.Data;

import java.util.Set;

@Data
public class EnterRoomResponse {
    private String responseType;
    private Room room;
}