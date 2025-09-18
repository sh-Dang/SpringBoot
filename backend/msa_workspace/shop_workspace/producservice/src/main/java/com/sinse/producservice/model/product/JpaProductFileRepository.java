package com.sinse.producservice.model.product;

import com.sinse.producservice.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product, Integer> {

}
