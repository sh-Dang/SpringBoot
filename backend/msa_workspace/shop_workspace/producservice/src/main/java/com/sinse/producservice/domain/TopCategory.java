package com.sinse.producservice.domain;

import jakarta.persistence.*;
import lombok.Data;

@Table(name="topcategory")
@Entity
@Data
public class TopCategory {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="topcategory_id")
    private String topCategoryId;
    @Column(name="topname")
    private String topName;
}
