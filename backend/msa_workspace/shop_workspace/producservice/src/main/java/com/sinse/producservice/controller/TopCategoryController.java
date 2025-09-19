package com.sinse.producservice.controller;

import com.sinse.producservice.domain.TopCategory;
import com.sinse.producservice.model.category.TopCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productapp")
public class TopCategoryController {

    private final TopCategoryService topCategoryService;
    public TopCategoryController(TopCategoryService topCategoryService) {
        this.topCategoryService = topCategoryService;
    }

    @GetMapping("/topcategories")
    public ResponseEntity<?> selectAll() {
        List<TopCategory> topCategoryList = topCategoryService.selectAll();

        return ResponseEntity.ok(Map.of("result", topCategoryList));
    }
}
