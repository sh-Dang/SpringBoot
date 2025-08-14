package com.sinse.busapp.model.bus;

import com.sinse.busapp.domain.Item;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class BusHandler extends DefaultHandler {

    @Getter
    private List<Item> itemList;
    private Item item;

    private boolean isBstopId = false;
    private boolean isBstopName = false;
    private boolean isArsno = false;
    private boolean isGpsx = false;
    private boolean isGpsy = false;
    private boolean isStopType = false;
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void startElement(String uri, String localName, String tag, Attributes attributes) throws SAXException {
        log.debug("<"+tag+">");
        if(tag.equals("items")){
            itemList = new ArrayList<Item>();
        }else if(tag.equals("item")){
            item = new Item();
        }else if(tag.equals("bstopid")){
            isBstopId = true;
        }else if(tag.equals("bstopnm")){
            isBstopName = true;
        }else if(tag.equals("arsno")){
            isArsno = true;
        }else if(tag.equals("gpsx")){
            isGpsx = true;
        }else if(tag.equals("gpsy")){
            isGpsy = true;
        }else if(tag.equals("stoptype")){
            isStopType = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch, start, length);
        if(isBstopId)item.setBstopid(Integer.parseInt(content));
        if(isBstopName)item.setBstopnm(content);
        if(isArsno)item.setArsno(content);
        if(isGpsx)item.setGpsx(Double.valueOf(content));
        if(isGpsy)item.setGpsy(Double.valueOf(content));
        if(isStopType)item.setStoptype(content);
    }

    @Override
    public void endElement(String uri, String localName, String tag) throws SAXException {
        log.debug("</"+tag+">");
        if(tag.equals("item")){
            itemList.add(item);
        }else if(tag.equals("bstopid")){
            isBstopId = false;
        }else if(tag.equals("bstopnm")){
            isBstopName = false;
        }else if(tag.equals("arsno")){
            isArsno = false;
        }else if(tag.equals("gpsx")){
            isGpsx = false;
        }else if(tag.equals("gpsy")){
            isGpsy = false;
        }else if(tag.equals("stoptype")){
            isStopType = false;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}
