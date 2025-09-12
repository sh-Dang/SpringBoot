package com.sinse.jwtredis.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberDTO {
    private String id;
    private String pwd;
    private String name;
    private String email;
}
