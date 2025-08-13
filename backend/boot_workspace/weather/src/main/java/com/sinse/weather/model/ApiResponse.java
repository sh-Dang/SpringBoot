package com.sinse.weather.model;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    private Items items;
    @Data
    public static class Items{
        private List<Item> item;
        @Data
        public static class Item{
            private String obsrValue;
            private String baseDate;
            private String baseTime;
            private String category;
            private String nx;
            private String ny;
        }
    }
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
