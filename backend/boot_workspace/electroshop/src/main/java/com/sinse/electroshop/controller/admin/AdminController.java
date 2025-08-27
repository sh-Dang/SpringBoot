package com.sinse.electroshop.controller.admin;

import com.sinse.electroshop.domain.Product;
import com.sinse.electroshop.model.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin/main")
    public String main() {
        return "store/index";
    }

    //물건 등록 폼 가져오기
    @GetMapping("/admin/registerform")
    public String getRegisterForm() {
        return "store/product/register";
    }

    @PostMapping("/admin/register")
    @ResponseBody
    public ResponseEntity<String> registerProduct(Product product) {
        log.debug("===========가져온 product는"+product.toString());
        productService.registerProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("등록성공");
    }

    @GetMapping("/admin/products")
    public String products() {
        return "store/product/list";
    }

    @GetMapping("/admin/detail")
    public String detail() {
        return "store/product/detail";
    }

}
