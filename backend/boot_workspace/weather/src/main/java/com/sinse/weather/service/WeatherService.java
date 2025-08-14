package com.sinse.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinse.weather.model.ApiResponse;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class WeatherService {
    private String serviceKey = "Z6ICGnUIfZtfFB81Jzn2wQx4h%2Fq3Bf38pKyTOgy%2F623OQQCc3NbKiQcYEntLqp8SfbT8rmcvjn%2Bp3v%2FOKUYoHA%3D%3D";

//    public String getWeather() throws IOException {
//        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/
//        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey); /*Service Key*/
//        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
//        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
//        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("XML", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
//        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode("20250813", "UTF-8")); /*‘21년 6월 28일 발표*/
//        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode("0600", "UTF-8")); /*06시 발표(정시단위) */
//        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode("55", "UTF-8")); /*예보지점의 X 좌표값*/
//        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/
//        URL url = new URL(urlBuilder.toString());
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Content-type", "application/json");
//
//        System.out.println("Response code: " + conn.getResponseCode());
//        System.out.println(conn.getResponseMessage());
//        BufferedReader rd;
//        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        } else {
//            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//        }
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = rd.readLine()) != null) {
//            sb.append(line);
//        }
//
//        rd.close();
//        conn.disconnect();
//
//        System.out.println(sb);
//
//        String xml = sb.toString();
//
//// XML → JSONObject
//        JSONObject jsonObject = XML.toJSONObject(xml);
//
//// JSONObject → 문자열(JSON)
//        String jsonString = jsonObject.toString();
//
//// JSON 문자열 → Map (Jackson)
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> map = mapper.readValue(jsonString, Map.class);
//
//        System.out.println("JSON 변환 결과: " + jsonString);
//        System.out.println("MAP 변환 결과: " + map);
//
//        return jsonString;
//    }
@GetMapping("/")
public ApiResponse getWeather() throws IOException,  InterruptedException {
    String baseUrl="http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";

//        if(store_name==null)store_name="";

    //파라미터 설정
    String url = baseUrl + "?" +
            "servicekey=" + serviceKey +
            "&currentPage=" + URLEncoder.encode("1", StandardCharsets.UTF_8) +
            "&perPage=" + URLEncoder.encode("20", StandardCharsets.UTF_8) +
            "&CNPNM=" + URLEncoder.encode("지선생쌈촌", StandardCharsets.UTF_8);
    //HttpUrlConnection보다 개선
    HttpClient client = HttpClient.newHttpClient();

    //요청 정보 객체
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .GET()
            .build();

    //open API서버에 요청 시도
    HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());

    /*
     * String 자체를 클라이언트에게 전송 해 버리면 클라이언트가 문자열을 json으로 파싱해야 함
     * Open API의 String 결과물을 자바의 클래스로 매핑 시키되, jackson을 이용하여 자동화 시키자.
     * 클라이언트에게 응답정보로 사용할 수 있을 뿐 아니라, 객체화 되어있기 때문에 서버에서도
     * 활용이 가능해진다.
     */
    ObjectMapper objectMapper = new ObjectMapper();
    ApiResponse apiResponse= objectMapper.readValue(response.body(), ApiResponse.class);

    return apiResponse;
}
}
