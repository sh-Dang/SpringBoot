package com.sinse.electroshop.controller.store;

import com.sinse.electroshop.domain.Product;
import com.sinse.electroshop.model.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/product")
    @ResponseBody
    public List<Product> getProductList(Model model) {
        //서비스에게 일 시킴
        return productService.getProductList();
    }

    @GetMapping("/product/detail")
    public String getProductDetail(int product_id, Model model) {
        Product product = productService.getProductById(product_id);
        model.addAttribute("product", product);
        return "electro/product";
    }

    @PostMapping("/product/register")
    public void registerProduct(Product product) {
        productService.registerProduct(product);
    }

}
