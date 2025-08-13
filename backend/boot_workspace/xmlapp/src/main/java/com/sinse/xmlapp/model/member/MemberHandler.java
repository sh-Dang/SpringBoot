package com.sinse.xmlapp.model.member;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/*
 * XML parsing방법
 * 1)DOM - 메모리에 모든 요소를 DOM에 올리는 방식(메모리 무거움)
 *          특히 스마트폰용 웹을 동시 지원 할 경우 사용을 지양
 * 2)SAX - 실행부가 xml 문서를 위에서 아래 방향으로 진행하면서
 *           적절한 이벤트를 일으켜 메서드를 호출하는 방식
 *           처리가 까다롭다는 단점
 * */
@Slf4j
@Component
public class MemberHandler extends DefaultHandler {

    @Getter
    private List<Member> memberList;

    private Member member;

    //실행부가 어느 태그를 지나가는지를 알 수 있는 기준 변수
    private boolean isName = false;
    private boolean isAge = false;
    private boolean isJob = false;
    private boolean isTel = false;

    //문서가 시작 될 때
    @Override
    public void startDocument() throws SAXException {
        log.debug("문서시작 시점입니다.");
        memberList = new ArrayList<Member>();
    }

    //시작태그를 만났을 때
    @Override
    public void startElement(String uri, String localName, String tag, Attributes attributes) throws SAXException {
        log.debug("<" + tag + ">");
        //member를 만나면 Model 올리기
        if (tag.equals("member")) {
            member = new Member();
        } else if (tag.equals("name")) {
            isName = true;
        } else if (tag.equals("age")) {
            isAge = true;
        } else if (tag.equals("job")) {
            isJob = true;
        } else if (tag.equals("tel")) {
            isTel = true;
        }
    }

    //태그와 태그사이의 컨텐츠를 만났을 때
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch, start, length);
        log.debug(content);

        if (isName) member.setName(content);
        if (isAge) member.setAge(Integer.parseInt(content));
        if (isJob) member.setJob(content);
        if (isTel) member.setTel(content);

    }

    //종료 태그를 만났을 때
    @Override
    public void endElement(String uri, String localName, String tag) throws SAXException {
        log.debug("</" + tag + ">");
        if (tag.equals("member")) {
            memberList.add(member);
        } else if (tag.equals("name")) {
            isName = false;
        } else if (tag.equals("age")) {
            isAge = false;
        } else if (tag.equals("job")) {
            isJob = false;
        } else if (tag.equals("tel")) {
            isTel = false;
        }
    }

    //문서가 끝날 때
    @Override
    public void endDocument() throws SAXException {
        log.debug("xml문서 parsing후 담겨진 회원 수는 "+memberList.size());
    }
}
