package com.sinse.jwtredis.service;

import com.sinse.jwtredis.dto.MemberDTO;

public interface MemberService {
    void register(MemberDTO memberDTO);
}
