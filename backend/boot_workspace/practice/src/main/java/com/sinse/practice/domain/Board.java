package com.sinse.practice.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int board_id;
    private String title;
    private String writer;
    private String content;
    @Column(name = "regdate", insertable = false, updatable = false)
    private String regdate;
    private int hit;
}
