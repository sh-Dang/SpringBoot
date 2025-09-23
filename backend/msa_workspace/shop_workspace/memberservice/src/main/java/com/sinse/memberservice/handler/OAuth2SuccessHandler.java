package com.sinse.memberservice.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*
* 로그인 인증이 완료될 때 동작하는 핸들러
* 개발자는 이 객체를 통해 응답정보를 구성할 수 있다
* 특히 이 객체의 메서드에는 HttpServletRequest, HttpServletResponse가 전달되므로
* 웹 전용 응답정보를 만들기에 최적이다
* JWT(body-AccessToken, Cookie-RefreshToken)
* */
@Slf4j
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // TODO: 핸들러로 OAuth2인증 마무리 하기
        log.debug("핸들러로 로그인 OAuth2인증 마무리 하기");
        response.sendRedirect("/memberapp/login/ok");
    }
}
