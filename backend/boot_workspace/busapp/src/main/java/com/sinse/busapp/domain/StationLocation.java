package com.sinse.busapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class StationLocation {
    private Double gpsy;//위도
    private Double gpsx;//경도

    public  StationLocation(Double gpsy,Double gpsx) {
        this.gpsy=gpsy;
        this.gpsx=gpsx;
    }
}
