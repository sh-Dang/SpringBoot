package com.sinse.orderservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    private final RestClient restClient;
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    public OrderController(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> getOrders() {
        Map responseBody;
        try {
            // Expect a Map (JSON object) from the productservice
            responseBody = restClient.get()
                    .uri("http://localhost:7777/products")
                    .retrieve()
                    .body(Map.class);
        } catch (RestClientException e) {
            log.error("Error while calling productservice: {}", e.getMessage());
            return ResponseEntity.status(500)
                    .body(Map.of("result", "productservice 호출 중 오류 발생", "error", e.getMessage()));
        }

        List<?> products = Collections.emptyList();
        // The actual list of products is inside the "data" key
        if (responseBody != null && responseBody.get("data") instanceof List) {
            products = (List<?>) responseBody.get("data");
        }

        return ResponseEntity.ok(Map.of("result", "상품정보 땡겨온 주문목록", "products", products));
    }
}
