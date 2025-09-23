package com.sinse.memberservice.model.member;

import com.sinse.memberservice.domain.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProviderRepository extends JpaRepository<Provider, Integer> {
    //프로바이더의 이름으로 pk값 가져오기
    public Provider findByProviderName(String name);
}
