package com.sinse.producservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.debug("파일경로는 {}", uploadDir);
        //웹 클라이언트가 어떤 url로 접근하게 될지 결정
        //http://localhost:7777/resources
        registry.addResourceHandler("/productapp/resource/**")
                .addResourceLocations("file:"+ uploadDir+"/");

    }
}
