package com.sinse.producservice.model.product;

import com.sinse.producservice.domain.Product;
import com.sinse.producservice.domain.ProductFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final JpaProductRepository jpaProductRepository;//상품등록에 필요한 Jpa
    private final UploadManager uploadManager;//이미지 저장에 필요한 UploadManager
    private final JpaProductFileRepository jpaProductFileRepository;//상품이미지 등록에 필요한 Jpa

    public ProductServiceImpl(JpaProductRepository jpaProductRepository, UploadManager uploadManager, JpaProductFileRepository jpaProductFileRepository) {
        this.jpaProductRepository = jpaProductRepository;
        this.uploadManager = uploadManager;
        this.jpaProductFileRepository = jpaProductFileRepository;
    }

    @Override
    public List<Product> selectAll() {
        return jpaProductRepository.findAllWithFiles();
    }

    @Override
    public Product select(int productId) {
        return null;
    }

    @Override
    public void update(Product product, List<MultipartFile> files) {

    }

    @Override
    public void delete(int productId) {

    }

    @Override
    public void save(Product product, List<MultipartFile> files) { 
        /*======================================================
        * 상품 등록 상품을 등록해야 pk가 반환되므로
        * pk가 있어야 디렉토리 생성, product_file에 사용
        ======================================================*/
        Product savedProduct = jpaProductRepository.save(product);
        int productId = savedProduct.getProductId();
        log.debug("상품등록 인서트 후 반환된 product_id는 ======{}", productId);

        // 1. 파일 저장하고 저장된 정보 받아오기
        List<ProductFile> productFileList = uploadManager.saveFile(files, productId);

        // 2. product_id를 productFile에 할당
        for(ProductFile productFile : productFileList){
            productFile.setProduct(savedProduct);
        }

        // 3. productFile 테이블에 저장
        jpaProductFileRepository.saveAll(productFileList);
        log.debug("상품 이미지 등록 완료");
    }
}
