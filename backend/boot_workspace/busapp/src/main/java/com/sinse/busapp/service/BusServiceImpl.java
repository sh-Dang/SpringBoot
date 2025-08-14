package com.sinse.busapp.service;

import com.sinse.busapp.domain.Item;
import com.sinse.busapp.domain.StationLocation;
import com.sinse.busapp.model.bus.BusHandler;
import com.sinse.busapp.model.bus.BusParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BusServiceImpl implements BusService {

    private BusParser busParser;
    private BusHandler busHandler;
    public BusServiceImpl(BusParser busParser, BusHandler busHandler) {
        this.busParser = busParser;
        this.busHandler = busHandler;
    }

    public List<StationLocation> getStation() throws IOException {
        List<Item> station = busHandler.getItemList();
        List<StationLocation> stationLocationList = new ArrayList<>();
        log.debug("");
        log.debug(station.toString());
        for(int i = 0; i < station.size(); i++) {
            Item item = station.get(i);
            log.debug(String.valueOf(item.getGpsx()));
            log.debug(String.valueOf(item.getGpsy()));
            stationLocationList.add(new StationLocation(item.getGpsx(), item.getGpsy()));
        }
        log.debug(stationLocationList.toString());
        return stationLocationList;
    }

    @Override
    public List<Item> parse(String data) throws Exception{
        return busParser.parse(data);
    }
}
