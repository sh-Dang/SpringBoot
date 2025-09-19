package com.sinse.producservice.model.product;

import com.sinse.producservice.domain.Product;
import com.sinse.producservice.domain.ProductFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductFileRepository extends JpaRepository<ProductFile, Integer> {

}
