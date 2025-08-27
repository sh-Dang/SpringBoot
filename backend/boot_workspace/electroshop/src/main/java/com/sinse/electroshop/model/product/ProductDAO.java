package com.sinse.electroshop.model.product;

import com.sinse.electroshop.domain.Product;

import java.util.List;

public interface ProductDAO {
    public List<Product> getProductList();
    public Product findProductById(int product_id);

    public void registerProduct(Product product);
}
