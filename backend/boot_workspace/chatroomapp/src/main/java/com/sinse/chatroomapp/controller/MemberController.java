package com.sinse.chatroomapp.controller;

import com.sinse.chatroomapp.domain.Member;
import com.sinse.chatroomapp.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class MemberController {

    private MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/loginform")
    public String loginForm(){
        return "member/loginform";
    }

    @PostMapping("/member/login")
    @ResponseBody
    public String loginCheck(String id, String password, HttpSession session){
        String result = "";
        Member loginUser = memberService.loginCheck(id, password);
        if(loginUser!=null) {
            session.setAttribute("loginUser",loginUser);
            result = "success";
        }else{
            result = "failed";
        }
        return result;
    }

    @GetMapping("/member/chat")
    public String chat(){
        return "member/chat";
    }

}
