package com.sinse.apiapp.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="board")
public class Board {
    @Id
    @GeneratedValue
    @Column(name="board_id")
    private int boardId;

    private String title;
    private String writer;
    private String content;
    @Column(name="regdate", insertable=false, updatable=false
    ,columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private String regDate;
    private int hit;
}
