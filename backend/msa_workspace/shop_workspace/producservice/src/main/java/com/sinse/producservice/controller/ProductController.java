package com.sinse.producservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    // 둘 다 JSON이 날아감(status 값이 조정 가능)
    // 어떤 error가 날아오는지 불확실 한 상황에서는 List 보다는 ResponseEntity를 사용한 로그 세분화가 유의미.
    @GetMapping("/products")
    public ResponseEntity<?> products() {
        return ResponseEntity.ok(Map.of("data",List.of("노트북", "스마트폰", "태블릿", "워치")));
    }

    // 무조건 status200
    @GetMapping("/productList")
    public List<String> productList() {
        return List.of("apple", "banana", "kiwi");
    }


}
