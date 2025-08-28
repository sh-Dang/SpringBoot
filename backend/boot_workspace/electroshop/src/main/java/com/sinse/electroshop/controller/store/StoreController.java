package com.sinse.electroshop.controller.store;

import com.sinse.electroshop.controller.dto.StoreDTO;
import com.sinse.electroshop.domain.Store;
import com.sinse.electroshop.exception.StoreNotFoundException;
import com.sinse.electroshop.model.store.StoreService;
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
public class StoreController {


    private final StoreService storeService;

    //Lombok을 사용하지 않을 경우 아래와 같이 매개변수 있는 생성자를 명시하면 된다..자동 주입
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }


    //로그인폼 요청 처리
    @GetMapping("/store/loginform")
    public String loginForm() {
        return "store/storeRegister";
    }

    //상점의 로그인 요청 처리
    @PostMapping("/store/register")
    @ResponseBody
    public ResponseEntity<String> register(StoreDTO storeDTO) {

        log.debug(storeDTO.toString());

        //파라미터가 담겨있는 객체는 DTO이므로, Model 객체인 Store로 옮기자
        Store store = new Store();
        store.setBusinessId(storeDTO.getId());
        store.setPassword(storeDTO.getPwd());
        store.setStoreName(storeDTO.getStoreName());

        storeService.register(store);

        return ResponseEntity.ok("success");
    }

    //상점의 로그인 요청 처리
    @PostMapping("/store/login")
    @ResponseBody
    public ResponseEntity<String> login(StoreDTO storeDTO, HttpSession httpSession) throws StoreNotFoundException {

        log.debug(storeDTO.toString());

        //파라미터가 담겨있는 객체는 DTO이므로, Model 객체인 Store로 옮기자
        Store store = new Store();
        store.setBusinessId(storeDTO.getId());
        store.setPassword(storeDTO.getPwd());
        store.setStoreName(storeDTO.getStoreName());

        Store obj = storeService.login(store);
        log.debug("로그인하려고 지금 담아온 로그인 store객체는"+obj.toString());

        //세션에 담기
        httpSession.setAttribute("store", obj);

        return ResponseEntity.ok("success");
    }


    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<String> handleException(StoreNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
