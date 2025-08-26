package com.sinse.electroshop.model.member;

import com.sinse.electroshop.domain.Member;
import com.sinse.electroshop.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberDAO memberDAO;


    @Override
    public Member authenticate(Member member) throws Exception{
        //DB를 포함한 로그인 처리
        Member obj = memberDAO.login(member);
        if(obj == null){//일치하는 정보가 없으면
            throw new MemberNotFoundException("로그인 정보가 일치하지 않습니다.");
        }
        return obj;
    }
}
