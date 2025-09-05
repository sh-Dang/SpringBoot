package com.sinse.jwtlogin.controller;

import com.sinse.jwtlogin.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Slf4j
@Controller
public class MemberController {

    private final AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    MemberController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/member/login")
    public ResponseEntity<?> login(String username, String password) throws Exception{
        log.debug("로그인 요청처리를 개발자가 정의한 컨트롤러에서 받음");

        //사용자 인증에  성공이 되면, 토큰 발급
        //Provider가 id, password검증
        //manager의 authenticate()로 한다.
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication =  authenticationManager.authenticate(token);

        //Access Token
        String accessToken = jwtUtil.generateAccessToken(authentication.getName());

        //리프레시 토큰
        String refreshToken = jwtUtil.generateAccessToken(authentication.getName());

        return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
    }

    @GetMapping("/member/mypage")
    public ResponseEntity<?> mypage(){
        log.debug("회원의 마이페이지 접근 성공");
        return ResponseEntity.ok("접근 성공");
    }
}
