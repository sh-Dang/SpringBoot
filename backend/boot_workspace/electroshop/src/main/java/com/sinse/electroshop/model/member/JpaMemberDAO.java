package com.sinse.electroshop.model.member;

import com.sinse.electroshop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaMemberDAO implements  MemberDAO {
    private final MemberJpaRepository memberJpaRepository;


    @Override
    public Member login(Member member) {
        return memberJpaRepository.findByIdAndPassword(member.getId(), member.getPassword());
    }

}
