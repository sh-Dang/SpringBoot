package com.sinse.chatroomapp.domain;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

@Data
public class Member {
    private int member_id;
    private String id;
    private String password;
    private String name;
    private String email;
    private String regdate;
}
