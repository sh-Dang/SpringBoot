package com.sinse.electroshop.controller.store;

import com.sinse.electroshop.controller.dto.StoreDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class StoreController {

    @PostMapping("/store/login")
    public String login(StoreDTO storeDTO) {
        log.debug(storeDTO.toString());
        return "store/index";
    }

}
