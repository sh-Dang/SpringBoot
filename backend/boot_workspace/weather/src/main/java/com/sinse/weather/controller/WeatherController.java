package com.sinse.weather.controller;

import com.sinse.weather.model.ApiResponse;
import com.sinse.weather.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class WeatherController {
    private WeatherService weatherService;
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public String getWeather() throws IOException {
        return weatherService.getWeather();
    }
}
