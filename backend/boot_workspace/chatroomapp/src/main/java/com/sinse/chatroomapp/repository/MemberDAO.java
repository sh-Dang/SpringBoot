package com.sinse.chatroomapp.repository;

import com.sinse.chatroomapp.domain.Member;

public interface MemberDAO {
    public Member loginCheck(String id, String password);
}
