package com.sinse.bookapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
public class BookController {
    private final ObjectMapper mapper;
    private String serviceKey="Z6ICGnUIfZtfFB81Jzn2wQx4h%2Fq3Bf38pKyTOgy%2F623OQQCc3NbKiQcYEntLqp8SfbT8rmcvjn%2Bp3v%2FOKUYoHA%3D%3D";

    public  BookController(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    @GetMapping("/books")
    public String getBookList(String book_name) throws IOException {

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6260000/BookLoanBestService/getBookLoanBest"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("resultType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*JSON방식으로 호출 시 파라미터 resultType=json 입력*/
        urlBuilder.append("&" + URLEncoder.encode(book_name,"UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*표제*/
        urlBuilder.append("&" + URLEncoder.encode("author","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*저자명*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
        return  sb.toString();
    }
}
