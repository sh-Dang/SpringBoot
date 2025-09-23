package com.sinse.memberservice.model.member;

import com.sinse.memberservice.domain.Member;
import com.sinse.memberservice.domain.Provider;
import com.sinse.memberservice.domain.Role;
import com.sinse.memberservice.handler.OAuth2SuccessHandler;
import com.sinse.memberservice.util.UserInfoExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

/*
 * 시큐리티의 OAuth2 프레임웍을 이용하면 Provider에게 토큰을 받는 과정까지 Security가 담당
 * 개발자는 토큰이 전달된 시점부터 관여하면 됨.
 * 이미 회원가입 했는지 여부를 판단하기에 적절한 컬럼은 id, provider_id(google=1..etc)
 * 1)Provider를 조회
 * 2)Provider가 유저에게 할당한 고유 ID
 * 3)DB회원이 없다면 자동가입
 *
 * */
@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final JpaProviderRepository jpaProviderRepository;
    private final JpaMemberRepository jpaMemberRepository;
    private final JpaRoleRepository jpaRoleRepository;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    public CustomOAuth2UserService(
            JpaProviderRepository jpaProviderRepository
            , JpaMemberRepository jpaMemberRepository
            , JpaRoleRepository jpaRoleRepository
            , OAuth2SuccessHandler oAuth2SuccessHandler) {
        this.jpaProviderRepository = jpaProviderRepository;
        this.jpaMemberRepository = jpaMemberRepository;
        this.jpaRoleRepository = jpaRoleRepository;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        //1) provider 유형 얻기(google, naver, kakao)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.debug("당신이 가입한 프로바이더는 ===={}", registrationId);
        Provider provider = jpaProviderRepository.findByProviderName(registrationId);//pk반환
        if (provider == null) {
            throw new OAuth2AuthenticationException("회원가입 정보를 확인해 주세요(알려지지 않은 provider입니다.");
        }

        //회원정보 꺼내기..
        OAuth2User user = super.loadUser(userRequest);
        Map<String, Object> attributes = user.getAttributes();
        log.debug("유저정보는 ======={}", attributes.toString());

        //이미 회원인지 아닌지 판단
        //회원 아니면 => 강제가입, 회원이면 로그인처리(혹시 이메일, 닉네임 변경되었다면 최슨으로 수정)
        String providerId = UserInfoExtractor.getProviderId(registrationId, attributes);//SNS에서 부여한 아이디
        String email = UserInfoExtractor.getEmail(registrationId, attributes);
        String name = UserInfoExtractor.getName(registrationId, attributes);

        Member existing = jpaMemberRepository.findByProviderAndId(provider, (String) attributes.get("id"));

        //SNS검증 됐으나 우리 DB회원ㅇ ㅣ아니라면(자동가입)
        Member member = null;
        if (existing == null) {
            member = new Member();//가입에 사용할 모델 객체
            member.setProvider(provider);
            member.setId(providerId);
            member.setEmail(email);
            member.setName(name);

            Role role = jpaRoleRepository.findByRoleName("USER");//거의 상수이므로 고정
            member.setRole(role);
            jpaMemberRepository.save(member);

        } else {//회원이라면
            member = existing;

            //attr에서 꺼내온 이메일이 현재 우리 db의 이메일 정보와 다른 경우
            if (email != null && !email.equals(member.getEmail())) {
                member.setEmail(email);//최신 email로 업데이트
            }
            if (name != null && !name.equals(member.getName())) {
                member.setName(name);//최신 name으로 업데이트
            }
            jpaMemberRepository.save(member);
        }
        return user;
    }

}
