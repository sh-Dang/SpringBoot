package com.sinse.customlogindb.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

//비밀번호를 생성 해주는 객체
@Slf4j
@Component
public class PasswordCreater {
    public String getCryptPassword(String password) {
        //salt를 적용하여 비밀번호를 암호화 시켜주는 객체
        //salt를 사용하므로 매번 같은 문자열 일지라도 매번 생성할 때마다 달라짐
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String result = bCryptPasswordEncoder.encode(password);

        return result;
    }
}
