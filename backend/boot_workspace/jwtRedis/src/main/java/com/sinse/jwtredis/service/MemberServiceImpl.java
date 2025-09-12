package com.sinse.jwtredis.service;

import com.sinse.jwtredis.domain.Member;
import com.sinse.jwtredis.domain.Role;
import com.sinse.jwtredis.dto.MemberDTO;
import com.sinse.jwtredis.repository.MemberJpaRepository;
import com.sinse.jwtredis.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberJpaRepository memberJpaRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(MemberDTO memberDTO) {
        if (memberJpaRepository.findById(memberDTO.getId()).isPresent()) {
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }

        Role userRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("USER 역할을 찾을 수 없습니다. 데이터베이스를 확인해주세요."));

        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setPassword(passwordEncoder.encode(memberDTO.getPwd()));
        member.setName(memberDTO.getName());
        member.setEmail(memberDTO.getEmail());
        member.setRole(userRole);

        memberJpaRepository.save(member);
    }
}
