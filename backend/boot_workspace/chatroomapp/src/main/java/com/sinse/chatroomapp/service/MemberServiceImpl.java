package com.sinse.chatroomapp.service;

import com.sinse.chatroomapp.domain.Member;
import com.sinse.chatroomapp.repository.MemberDAO;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    private MemberDAO memberDAO;
    MemberServiceImpl(MemberDAO memberDAO){
        this.memberDAO = memberDAO;
    }

    @Override
    public Member loginCheck(String id, String password) {
        return memberDAO.loginCheck(id, password);
    }
}
