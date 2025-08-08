package com.sinse.restapp.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 PK를 넣어달라는 의미
    private int board_id;
    private String title;
    private String writer;
    private String content;
    @Column(name = "regdate", insertable = false, updatable = false)
    private String regdate;
    private int hit;
}
