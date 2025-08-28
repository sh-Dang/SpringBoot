package com.sinse.electroshop.model.product;

import com.sinse.electroshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Product, Integer> {
    public List<Product> findAll();
    /// Store의 매핑법 기억하기. ??모르겠다 이건
    public List<Product> findByStore_storeId(int storeId);
    public Product findById(int productId);

}
