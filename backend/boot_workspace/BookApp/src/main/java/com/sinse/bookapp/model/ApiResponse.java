package com.sinse.bookapp.model;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    private Body body;
    private Header header;

    @Data
    public static class Header{
        private String resultMsg;
        private String resultCode;
    }
    @Data
    public static class Body{
        private int totalCount;
        private Items items;
        private int pageNo;
        private int numOfRows;

        @Data
        public static class Items{
            private List<Item> item;
            @Data
            public static class Item{
                private int rank;
                private String title;
                private String author;
                private String publisher;
                private String shelf_loc_name;
                private int cnt;
                private int publish_year;
                private String lib_name;
                private String image;
            }
        }
    }
}
