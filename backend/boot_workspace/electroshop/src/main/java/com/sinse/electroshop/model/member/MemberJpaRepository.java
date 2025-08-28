package com.sinse.electroshop.model.member;

import com.sinse.electroshop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Integer> {
    //jpa특징임
    /// 메서드 명 정의는 자유로우나, 매개변수, 메서드명에서 사용되는 필드명은
    /// 반드시 모델객체의 멤버변수와 일치해야 한다.
    public Member findByIdAndPassword(String id,String password);

}
