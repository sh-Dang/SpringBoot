package com.sinse.electroshop.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController {

    @GetMapping("/shop/main")
    public String index() {
        return "electro/index";
    }
}
