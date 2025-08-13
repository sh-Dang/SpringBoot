package com.sinse.xmlapp.model.member;

import org.xml.sax.SAXException;

import java.util.List;

public interface MemberService {
    public List<Member> parse()throws Exception;
}
