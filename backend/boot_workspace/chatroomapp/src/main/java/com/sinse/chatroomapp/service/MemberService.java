package com.sinse.chatroomapp.service;

import com.sinse.chatroomapp.domain.Member;

public interface MemberService {
    public Member loginCheck(String id, String password);
}
