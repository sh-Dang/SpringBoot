package com.sinse.producservice.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductDTO {
    private int ProductId;
    private String ProductName;
    private String brand;
    private int price;
    private int discount;
    private String detail;
    private SubCategoryDTO subCategoryDTO;
    private List<MultipartFile> files;
}
