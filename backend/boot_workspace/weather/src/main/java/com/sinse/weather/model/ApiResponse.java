package com.sinse.weather.model;

import lombok.Data;
import java.util.List;

@Data
public class ApiResponse {
    private Response response;

    @Data
    public static class Response {
        private Header header;
        private Body body;
    }

    @Data
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Data
    public static class Body {
        private int pageNo;
        private String dataType;
        private int totalCount;
        private Items items;
        private int numOfRows;
    }

    @Data
    public static class Items {
        private List<Item> item; // **배열로 변경**
    }

    @Data
    public static class Item {
        private String baseDate;
        private String baseTime;
        private String category;
        private String nx;
        private String ny;
        private String obsrValue;
    }
}
