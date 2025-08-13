package com.sinse.movie.model.movie;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

@Slf4j
@Component
public class MovieHandler extends DefaultHandler {
    @Getter
    //Movie를 담아둘 배열객체와 그 배열을 불러와서 사용할 수 있는 Getter메서드 까지 정의
    private List<Movie> movieList;

    private Movie movie;

    @Override
    public void startDocument() throws SAXException {
        log.debug("문서시작");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        log.debug("태그열림");
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        log.debug("태그 안의 내용");
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        log.debug("태그 닫힘");
    }

    @Override
    public void endDocument() throws SAXException {
        log.debug("문서 수행완료 후 닫힘");
    }

}
