package com.sinse.formloginnodb.member;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    //UserDetails 객체를 꺼내는 4가지 방법
    /*========================================
    방법 1) 세션에서 직접 꺼내기
    ====================================*/
    @GetMapping("/2")
    public String index1(HttpSession session, Model model) {
        SecurityContext context = (SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");

        Authentication auth = context.getAuthentication();
        UserDetails userDetails=(UserDetails)auth.getPrincipal();
        String id = userDetails.getUsername();
        model.addAttribute("username","session : "+id);

        return "member/index";
    }

    /*========================================
    방법 2) Authentication에서 꺼내기
    ====================================*/
    @GetMapping("/3")
    public String index2(Authentication auth, Model model) {
        UserDetails userDetails = (UserDetails)auth.getPrincipal();
        String id = userDetails.getUsername();
        model.addAttribute("username","Auth : "+id);
        return "member/index";
    }

    /*========================================
    방법 3) SecurityContextHolder에서 꺼내기
    ====================================*/
    @GetMapping("/4")
    public String index3(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String id = userDetails.getUsername();
        model.addAttribute("username","SecurityContext : "+id);
        return "member/index";
    }

    /*========================================
    방법 4) AuthenticationPrincipal사용
    ====================================*/
    @GetMapping("/")
    public String index4(@AuthenticationPrincipal UserDetails userDetails,Model model) {
        model.addAttribute("username","@AuthenticationPrincipal : "+userDetails.getUsername());
        return "member/index";
    }

    /** 스프링 security가 기본적으로 제공하는 폼 로그인 기능에서는
     * 로그인 성공시 무조건 /로 redirect 되어있기 때문에,
     * 로그인 성공 후 보여질 내용은 매핑이 필요하다.
     * */
    @GetMapping("/1")
    public String login(){
        return "member/index";
    }
}
