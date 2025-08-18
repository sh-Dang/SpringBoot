package com.sinse.chatroomapp.repository;

import com.sinse.chatroomapp.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOImpl implements MemberDAO {
    private MemberMapper memberMapper;
    public MemberDAOImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public Member loginCheck(String id, String password) {
        return memberMapper.loginCheck(id, password);
    }
}
