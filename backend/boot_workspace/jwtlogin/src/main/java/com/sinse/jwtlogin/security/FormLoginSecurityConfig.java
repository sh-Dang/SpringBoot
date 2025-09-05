package com.sinse.jwtlogin.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinse.jwtlogin.model.member.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.Map;

/*@Configuration
@EnableWebSecurity*/
public class FormLoginSecurityConfig {

    //시큐리티가 사용할 서비스 객체 등록
    private final CustomUserDetailsService customUserDetailsService;
    private final ObjectMapper objectMapper = new  ObjectMapper();

    public FormLoginSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * 09:52
     * DaoAuthenticationProvider가 사용할 비번 검증 인코더
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 09:53
     * ID, Password 자동비교 Provider등록
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        //사용할 서비스 객체
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);

        //사용할 비밀번호 인코더
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    /**
     * 09:58
     * Filter이후 사용 할 Manager 등록
     */
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //스프링시큐리티가 기본적으로 CSRF방지
                .csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth.requestMatchers("/login", "/member/*", "/").permitAll().anyRequest().authenticated()).formLogin(form -> form
                        .loginPage("/member/login.html") //로그인 폼의 접근 url
                        .loginProcessingUrl("/member/login") //시큐리티가 로그인을 처리 할 url
                        .defaultSuccessUrl("/index.html", true) //로그인 성공 후 보여질 url

                        //비동기 방식의 요청이 들어올 때는, json으로 결과를 보여줘야 하므로
                        .successHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.setContentType("application/json;charset=utf-8");
                            Map map = new HashMap<>();
                            map.put("result", "로그인 성공");
                            map.put("user", authentication.getName());
                            //나중에 mapof

                            response.getWriter().print(objectMapper.writeValueAsString(map));
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json;charset=utf-8");
                            Map map = new HashMap<>();
                            map.put("result", "로그인 실패");
                            map.put("error", exception.getMessage());
                        })
                ).build();
    }
}
