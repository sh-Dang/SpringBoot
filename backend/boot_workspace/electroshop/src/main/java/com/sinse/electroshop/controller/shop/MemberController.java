package com.sinse.electroshop.controller.shop;

import com.sinse.electroshop.controller.dto.MemberDTO;
import com.sinse.electroshop.domain.Member;
import com.sinse.electroshop.exception.MemberNotFoundException;
import com.sinse.electroshop.model.member.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
public class MemberController {
    //서비스를 보유하고 일 시키기
    MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/shop/loginform")
    public String loginform(){
        return "electro/loginform";
    }

    //로그인 요청 처리
    @PostMapping("/member/login")
    @ResponseBody
    public ResponseEntity<MemberDTO> login(MemberDTO memberDTO, HttpSession session) throws Exception {
        log.debug("로그인 시도");
        log.debug(memberDTO.toString());
        //DTO의 데이터를 Model로 옮겨야한다.
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setPassword(memberDTO.getPwd());


        //로그인 메서드 호출 후 리턴 받기 =>이제 서비스로 가자
        Member result = memberService.authenticate(member);

        //DTO정보를 클라이언트에게 보내기
        memberDTO.setPwd(null);
        memberDTO.setName(result.getName());

        //서버측에서 사용할 것이기 때문에 정보를 많이 담고 있어도 된다...?(password를 통한 검증 절차등에 필요할 수도?)
        session.setAttribute("member", result);

        return ResponseEntity.status(HttpStatus.OK).body(memberDTO);
    }

    //@ExceptionHandler를 이용하면 본 컨트롤러 내에서 발생하는 모든 예외를 처리할 수 있다.
    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleException(MemberNotFoundException e){
            /// ㄴ응답정보를 만들어주는 오브젝트
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}
