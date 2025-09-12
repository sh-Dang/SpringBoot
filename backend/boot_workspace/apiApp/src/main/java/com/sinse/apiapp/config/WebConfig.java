package com.sinse.apiapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*
* CORS 정책 위반 에러에 대한 처리
* 요청시 원래의 IP, PORT가 다른 쪽 서버에 요청을 시도할때
* 브라우저는 다른 출처로 생각.
* 해결책) Spring Boot서버가 클라이언트에게 Access-Controller-Allow_Origin 헤더 값을 전송
* 이 시점부터 브라우저가 클라이언트의 요청 시 block 시키지 않음
* 출처가 다른 서버에 요청을 시도하려면, 해당서버로부터 허락 헤더값을 승인받아야 함
* */

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            //내부 익명클래스 오버라이드

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") //모든 요청 경로 허용
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET","POST", "PUT","DELETE", "OPTIONS")
                        .allowCredentials(true);


            }
        };
    }
}
