package com.sinse.busapp.controller;

import com.sinse.busapp.domain.Item;
import com.sinse.busapp.domain.StationLocation;
import com.sinse.busapp.service.BusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class RestBusController {

    private BusService busService;

    public RestBusController(BusService busService) {
        this.busService = busService;
    }
    @GetMapping("/bus/location")
    public List<StationLocation> getStation() throws IOException {
        return busService.getStation();
    }
    @GetMapping("/buses")
    public List<Item> parse(String data) throws Exception {
        return busService.parse(data);
    }
}
