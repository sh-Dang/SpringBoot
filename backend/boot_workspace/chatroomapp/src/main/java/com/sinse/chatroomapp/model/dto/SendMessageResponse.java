package com.sinse.chatroomapp.model.dto;

import lombok.Data;

@Data
public class SendMessageResponse {
    private String responseType;
    private String sender;
    private String data;
    private String uuid;
}
