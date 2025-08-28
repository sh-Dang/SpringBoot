package com.sinse.electroshop.model.product;

import com.sinse.electroshop.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductDAO productDAO;

    @Override
    public List<Product> selectByStoreId(int storeId) {
        return productDAO.selectByStoreId(storeId);
    }

    //모든 productList 반환
    @Override
    public List<Product> getProductList() {
        //DAO에게 일시킴
        return productDAO.getProductList();
    }

    //productDetail 반환
    @Override
    public Product getProductById(int productId) {
        return productDAO.findProductById(productId);
    }

    @Override
    public void registerProduct(Product product) {
        productDAO.registerProduct(product);
    }


}
