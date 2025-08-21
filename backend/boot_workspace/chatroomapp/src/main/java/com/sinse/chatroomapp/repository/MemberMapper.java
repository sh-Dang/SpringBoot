package com.sinse.chatroomapp.repository;

import com.sinse.chatroomapp.domain.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    public Member loginCheck(String id, String password);
}
