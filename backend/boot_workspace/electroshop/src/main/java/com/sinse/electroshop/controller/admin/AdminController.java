package com.sinse.electroshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String admin() {
        return "store/index";
    }

    @GetMapping("/loginform")
    public String loginform() {
        return "store/loginform";
    }

}
