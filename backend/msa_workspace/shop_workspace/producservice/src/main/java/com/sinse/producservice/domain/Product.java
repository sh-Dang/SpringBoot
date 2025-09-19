package com.sinse.producservice.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name="product")
@Entity
@Data
public class Product {
    @Id
    // insert 후에 자동으로 pk값 채워 넣어줌
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="product_id")
    private int productId;

    @Column(name="product_name")
    private String productName;

    @Column(name="brand")
    private String brand;

    @Column(name="price")
    private int price;

    @Column(name="discount")
    private int discount;

    @Column(name="detail")
    private String detail;

    //mybatis의 경우 association
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "subcategory_id")
    private SubCategory subCategory;

    //mybatis의 경우 collection으로 수집
    @OneToMany(mappedBy = "product", fetch= FetchType.LAZY)
    private List<ProductFile> productFileList;
}
