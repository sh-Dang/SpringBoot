package com.sinse.producservice.controller;

import com.sinse.producservice.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
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

    @PostMapping(value="/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(@ModelAttribute ProductDTO productDTO, @RequestPart List<MultipartFile> files) {
        log.debug("등록요청 들어옴");
        log.debug("넘겨받은 이미지 수는 "+files.size());
        log.debug("넘겨받은 서브카테고리야~ "+productDTO.getSubCategoryDTO().getSubCategoryId());
        log.debug("넘겨받은 상품명은" +  productDTO.getProductName());
        log.debug("넘겨받은 브랜드" +  productDTO.getBrand());
        log.debug("넘겨받은 가격은" +  productDTO.getPrice());
        log.debug("넘겨받은 할인가는" +  productDTO.getDiscount());
        log.debug("넘겨받은 상세설명은" +  productDTO.getDetail());
        return ResponseEntity.ok(Map.of("result", "업로드 성공"));
    }


}
