package com.sinse.jwtredis.repository;

import com.sinse.jwtredis.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Integer> {
    // Spring Data JPA will automatically implement this method based on the method name.
    Optional<Member> findById(String id);
}
