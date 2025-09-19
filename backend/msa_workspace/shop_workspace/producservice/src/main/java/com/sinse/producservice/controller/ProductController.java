package com.sinse.producservice.controller;

import com.sinse.producservice.domain.Product;
import com.sinse.producservice.domain.SubCategory;
import com.sinse.producservice.dto.ProductDTO;
import com.sinse.producservice.dto.ProductFileDTO;
import com.sinse.producservice.dto.SubCategoryDTO;
import com.sinse.producservice.model.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequestMapping("/productapp")
@RestController
@Slf4j
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 둘 다 JSON이 날아감(status 값이 조정 가능)
    // 어떤 error가 날아오는지 불확실 한 상황에서는 List 보다는 ResponseEntity를 사용한 로그 세분화가 유의미.
    @GetMapping("/products")
    public ResponseEntity<?> products() {
        List<Product> productList = productService.selectAll();
        List<ProductDTO> productDTOList = productList.stream()
                .map(
                        product -> {
                            ProductDTO productDTO = new ProductDTO();
                            productDTO.setProductId(product.getProductId());
                            productDTO.setProductName(product.getProductName());
                            productDTO.setBrand(product.getBrand());
                            productDTO.setPrice(product.getPrice());
                            productDTO.setDiscount(product.getDiscount());
                            productDTO.setDetail(product.getDetail());

                            //SubCategory 옮기기
                            SubCategoryDTO subCategoryDTO = new SubCategoryDTO();
                            if (product.getSubCategory() != null) {
                                subCategoryDTO.setSubCategoryId(product.getSubCategory().getSubcategoryId());
                                subCategoryDTO.setSubName(product.getSubCategory().getSubName());
                            }
                            productDTO.setSubCategoryDTO(subCategoryDTO);

                            //ProductFile도 옮기기 List<ProductFileDTO>에 담기
                            if (product.getProductFileList() != null) {
                                List<ProductFileDTO> productFileDTOList = product.getProductFileList().stream()
                                        .map(pf -> {
                                                    ProductFileDTO productFileDTO = new ProductFileDTO();
                                                    productFileDTO.setProductFileId(pf.getProductFileId());
                                                    productFileDTO.setFileName(pf.getFileName());
                                                    productFileDTO.setOriginalName(pf.getOriginalName());
                                                    productFileDTO.setContentType(pf.getContentType());
                                                    productFileDTO.setFilePath(pf.getFilePath()); // filePath 추가
                                                    productFileDTO.setFileSize(pf.getFileSize());
                                                    return productFileDTO;
                                                }
                                        ).toList();
                                productDTO.setProductFileDTOList(productFileDTOList);
                            }
                            return productDTO;
                        }
                ).toList();

        return ResponseEntity.ok(Map.of("result", productDTOList)); // 반환값을 productDTOList로 수정
    }

    // 무조건 status200
    @GetMapping("/productList")
    public List<String> productList() {
        return List.of("apple", "banana", "kiwi");
    }

    @PostMapping(value = "/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(@ModelAttribute ProductDTO productDTO, @RequestPart List<MultipartFile> files) {
        log.debug("등록요청 들어옴");
        log.debug("넘겨받은 이미지 수는 " + files.size());
        log.debug("넘겨받은 서브카테고리야~ " + productDTO.getSubCategoryDTO().getSubCategoryId());
        log.debug("넘겨받은 상품명은" + productDTO.getProductName());
        log.debug("넘겨받은 브랜드" + productDTO.getBrand());
        log.debug("넘겨받은 가격은" + productDTO.getPrice());
        log.debug("넘겨받은 할인가는" + productDTO.getDiscount());
        log.debug("넘겨받은 상세설명은" + productDTO.getDetail());

        //서비스에게 일 시킬것이야
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setBrand(productDTO.getBrand());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        product.setDetail(productDTO.getDetail());

        SubCategory subCategory = new SubCategory();
        subCategory.setSubcategoryId(productDTO.getSubCategoryDTO().getSubCategoryId());
        product.setSubCategory(subCategory);


        productService.save(product, files);
        return ResponseEntity.ok(Map.of("result", "업로드 성공"));
    }


}
