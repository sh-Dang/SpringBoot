package com.sinse.chatroomapp.controller;

import com.sinse.chatroomapp.domain.Member;
import com.sinse.chatroomapp.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        Member member = memberService.loginCheck(id, password);
        if(member!=null) {
            session.setAttribute("member",member);
            //세션 테스트용(주석으로 변환)
//            Member user = (Member)session.getAttribute("loginUser");
//            log.debug(user.getName());
            result = "success";
        }else{
            result = "failed";
        }
        return result;
    }

    @GetMapping("/member/chat")
    public String chat(HttpSession session){
        log.debug(session.getAttribute("member").toString());
//        String targetString = "";
//        if(session.getAttribute("loginUser")==null){
//            targetString = "member/loginform";
//        }else if(session.getAttribute("loginUser")!=null){
//            targetString = "chat/main";
//        }
//        return targetString;
/* =======================================================
        String targetString = "chat/main";
        if(session.getAttribute("loginUser")==null){
            targetString = "member/loginform";
        }
        return targetString;
 =========================================================*/
        return (session.getAttribute("member")==null) ? "member/loginform" : "chat/main";
    }

    @GetMapping("/chat/room")
    public String room(HttpSession session){
        log.debug("이것입니다."+session.getAttribute("member").toString());
        return (session.getAttribute("member")==null) ? "member/loginform" : "room/room";
    }
}
