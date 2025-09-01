package com.sinse.customlogindb.model.member;

import com.sinse.customlogindb.domain.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 로그인 검증이 성공되면 Authentication 객체가 생성되고, 이 객체에
 * UserDetails 객체가 등록된다.
 * Spring security Framework는 클래스종류를 예측불가
 * UserDetails라는 이름의 인터페이스 미리 정의 implments받아서 확인절차
 * */
@Data
public class CustomUserDetails implements UserDetails {
    private Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}