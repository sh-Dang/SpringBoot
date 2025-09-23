package com.sinse.memberservice.security;

import com.sinse.memberservice.handler.OAuth2SuccessHandler;
import com.sinse.memberservice.model.member.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, OAuth2SuccessHandler oAuth2SuccessHandler) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/memberapp/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                )
                /*
                * 아래의 oauth2Login 등록되는 시점부터 Security 6의 OAuth2 기반 인증이 시작
                * OAuth2AuthorizationRequestRedirectFilter가 이 시점부터 작동
                * 주의할 점) 이 필터가 리다이렉트 할 Provider의 요청주소가 이미 정해진 형식
                * /oauth2/authorization/{providerId}로 정해져있기 때문에, 혹시나 요청시 스프링 시큐리티가 이해할 수 없는 접두어가 있다면
                * 반드시 제거해줘야 이 필터가 동작
                *
                * */
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(ui->ui.userService(customOAuth2UserService))
                        /*
                        * .defaultSuccessUrl("/memberapp/login/ok", true): 이렇게 true를 사용하면, Spring Security는 다른 설정을 무시하고 무조건 이 URL로 리다이렉트합니다.
                        * 그래서 이전에 successHandler가 실행될 기회가 없었습니다.
                        * .defaultSuccessUrl("/memberapp/login/ok"): 이렇게 true 없이 사용하면, 이 URL은 "대체" 또는 "기본" 리다이렉트 URL로만 설정됩니다.
                        * 그리고 successHandler가 지정되어 있으면 `successHandler`에게 우선권이 주어집니다.
                        * */
                        .successHandler(oAuth2SuccessHandler)
                        .defaultSuccessUrl("/memberapp/login/ok")
                );

        return http.build();
    }
}
