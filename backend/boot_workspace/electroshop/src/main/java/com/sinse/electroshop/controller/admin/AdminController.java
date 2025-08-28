package com.sinse.electroshop.controller.admin;

import com.sinse.electroshop.controller.dto.ProductDTO;
import com.sinse.electroshop.domain.Product;
import com.sinse.electroshop.model.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public ResponseEntity<String> registerProduct(ProductDTO productDTO) {
        log.debug("DTO는요===============" + productDTO.toString());
        Product product = new Product();
        product.setProductName(productDTO.getProduct_name());
        product.setBrand(productDTO.getBrand());
        product.setPrice(productDTO.getPrice());
        product.setStore(productDTO.getStore());

        productService.registerProduct(product);
        return ResponseEntity.ok("success");
    }

    //JSON 객체 가져오는  메서드
    //model에 담아서 리턴 시 html에서 받을 수 있음
    @GetMapping("/admin/products")
    public String getProducts(Model model) {
        List<Product> productList = productService.getProductList();
        log.debug("======들어온 건" + productList.toString());
        model.addAttribute("productList", productList);
        return "store/product/list";
    }

}
