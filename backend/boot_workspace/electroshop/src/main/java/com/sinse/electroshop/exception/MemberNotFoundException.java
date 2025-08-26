package com.sinse.electroshop.exception;

import com.sinse.electroshop.model.member.MemberService;

import java.io.IOException;

public class MemberNotFoundException extends Exception {
    public MemberNotFoundException(String message) {
        super(message);
    }
    public MemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public MemberNotFoundException(Throwable cause) {
        super(cause);
    }
}
