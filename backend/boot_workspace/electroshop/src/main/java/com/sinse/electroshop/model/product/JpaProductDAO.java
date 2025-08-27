package com.sinse.electroshop.model.product;

import com.sinse.electroshop.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaProductDAO implements ProductDAO {
    private final ProductJpaRepository productJpaRepository;

    @Override
    public List<Product> getProductList() {
        //직접 일 함 => 여기서는 productJpaRepository Interface에 시킴
        return productJpaRepository.findAll();
    }

    @Override
    public Product findProductById(int product_id) {
        return productJpaRepository.findById(product_id);
    }

    @Override
    public void registerProduct(Product product) {
        productJpaRepository.save(product);
    }


}
