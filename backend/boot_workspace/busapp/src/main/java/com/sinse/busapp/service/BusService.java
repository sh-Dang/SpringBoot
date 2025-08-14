package com.sinse.busapp.service;

import com.sinse.busapp.domain.Item;
import com.sinse.busapp.domain.StationLocation;

import java.io.IOException;
import java.util.List;

public interface BusService {
    public List<StationLocation> getStation() throws IOException;

    List<Item> parse(String data) throws Exception;
}
