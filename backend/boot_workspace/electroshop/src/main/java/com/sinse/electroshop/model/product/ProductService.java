package com.sinse.electroshop.model.product;

import com.sinse.electroshop.domain.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getProductList();
    public Product getProductById(int product_id);

    public void registerProduct(Product product);
}
