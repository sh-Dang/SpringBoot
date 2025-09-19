package com.sinse.producservice.dto;

import lombok.Data;

@Data
public class ProductFileDTO {
    private int productFileId;
    private String fileName;
    private String originalName;
    private String contentType;
    private String filePath;
    private long fileSize;
}
