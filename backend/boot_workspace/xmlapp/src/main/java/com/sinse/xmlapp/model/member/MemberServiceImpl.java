package com.sinse.xmlapp.model.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.List;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService{
    private MemberHandler memberHandler;

    public MemberServiceImpl(MemberHandler memberHandler) {
        this.memberHandler = memberHandler;
    }
    public List<Member> parse() throws Exception{
        //스프링 부트 프로젝트의 정적자원을 먼저접근
        //        ClassPathResource 객체는 프로젝트 내의 resource디렉토리를 가리킴
        ClassPathResource resource = new ClassPathResource("static/member.xml");
        log.debug("RESOURCE="+resource);
        File file = resource.getFile();
        log.debug("GET해온 FILE="+file);

        //SAXParserFactory 생성
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //saxParser 하나 꺼내오기
        SAXParser saxParser = factory.newSAXParser();

        //파일을 대상으로 파싱 시작
        saxParser.parse(file, memberHandler); //동기방식으로 파싱 함
        log.debug("파싱완료");
        return memberHandler.getMemberList();
    }
}
