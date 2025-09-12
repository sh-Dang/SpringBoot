package com.sinse.jwtredis.controller;

import com.sinse.jwtredis.dto.MemberDTO;
import com.sinse.jwtredis.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MemberDTO memberDTO) {
        log.info("Register request received for user: {}", memberDTO.getId());
        memberService.register(memberDTO);
        return ResponseEntity.ok("Registration successful. Please check your email for verification.");
    }

    // Note: The actual login logic will be handled by the JwtAuthenticationFilter.
    // This endpoint is just a placeholder.
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberDTO memberDTO) {
        // This will not be executed because the filter intercepts the request.
        return ResponseEntity.ok("Login endpoint reached. If you see this, the filter is not working.");
    }

    // Example of a secured endpoint
    @PostMapping("/info")
    public ResponseEntity<String> memberInfo() {
        return ResponseEntity.ok("You are an authenticated user.");
    }
}
