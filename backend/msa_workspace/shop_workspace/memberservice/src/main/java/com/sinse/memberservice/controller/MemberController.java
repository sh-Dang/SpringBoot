package com.sinse.memberservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MemberController {
    @GetMapping("/members")
    public ResponseEntity<Map> members(){
        return ResponseEntity.ok(Map.of("result", "회원가입 성공"));
    }
}
