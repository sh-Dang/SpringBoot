package com.sinse.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

    //REST요청 보낼수 있는 객체
    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }

}
