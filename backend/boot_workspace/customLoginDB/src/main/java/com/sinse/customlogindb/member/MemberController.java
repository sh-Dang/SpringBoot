package com.sinse.customlogindb.member;

import com.sinse.customlogindb.util.PasswordCreater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final PasswordCreater passwordCreater;
    @GetMapping("/")
    public String index(Model model) {
        String pwd = passwordCreater.getCryptPassword("apple");
        log.debug("생성된 암호화 비밀번호는 : " + pwd);
        return "member/index";
    }

    //개발자 커스텀 로그인 폼
    @GetMapping("/loginform")
    public String getLoginForm() {
        return "member/loginform";
    }

    @GetMapping("/main")
    public String getMain(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("user", userDetails.getUsername());
        return "member/index";
    }

    @GetMapping("/test")
    public String getTest(){
        log.debug("getTest 돌아갑니다.");
        return "member/test";
    }
}
