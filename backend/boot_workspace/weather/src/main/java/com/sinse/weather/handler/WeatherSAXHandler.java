package com.sinse.weather.handler;

import com.sinse.weather.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@Slf4j
@Component
public class WeatherSAXHandler extends DefaultHandler {
    private ApiResponse apiResponse;
    private ApiResponse.Items.Item item;
    private boolean isObsrValue=false;
    private boolean isBaseDate=false;
    private boolean isNx=false;
    private boolean isNy=false;
    private boolean isCategory=false;
    private boolean isBaseTime=false;

    @Override
    public void startDocument() throws SAXException {
        log.debug("문서시작");
        apiResponse = new ApiResponse();
    }

    @Override
    public void startElement(String uri, String localName, String tag, Attributes attributes) throws SAXException {
        log.debug("<"+tag+">");
        if(tag.equals("item")){
            item=new ApiResponse.Items.Item();
        }else if(tag.equals("base_date")){
            isBaseDate=true;
        }else if(tag.equals("base_time")){
            isBaseTime=true;
        }else if(tag.equals("nx")){
            isNx=true;
        }else if(tag.equals("ny")){
            isNy=true;
        }else if(tag.equals("category")){
            isCategory=true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value=new String(ch,start,length);

        if(isObsrValue)item.setObsrValue(value);
        if(isBaseDate)item.setBaseDate(value);
        if(isNx)item.setNx(value);
        if(isNy)item.setNy(value);
        if(isCategory)item.setCategory(value);
        if(isBaseTime)item.setBaseTime(value);

        log.debug("태그안의 내용");
    }

    @Override
    public void endElement(String uri, String localName, String tag) throws SAXException {
        log.debug("</"+tag+">");
        if (tag.equals("item")) {
        }
    }

    @Override
    public void endDocument() throws SAXException {
        log.debug("거의 다왔다.");
    }
}
