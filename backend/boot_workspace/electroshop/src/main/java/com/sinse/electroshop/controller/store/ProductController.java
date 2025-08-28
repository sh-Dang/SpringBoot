package com.sinse.electroshop.controller.store;

import com.sinse.electroshop.domain.Product;
import com.sinse.electroshop.domain.Store;
import com.sinse.electroshop.model.product.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/product")
    @ResponseBody
    public List<Product> getProductList(Model model) {
        //서비스에게 일 시킴
        return productService.getProductList();
    }

    @GetMapping("/product/detail")//RequestParam메서드 설정 해보기
    public String getProductDetail(@RequestParam(name="product_id", defaultValue = "0", required = false) int productId, Model model) {
        Product product = productService.getProductById(productId);
        log.debug("불러와진 프로덕트 정보는============================" + product.toString());
        model.addAttribute("product", product);
        return "store/product/detail";
    }

    @GetMapping("/user/product/detail")//RequestParam메서드 설정 해보기
    public String getUserProductDetail(@RequestParam(name="productId", defaultValue = "0", required = false) int productId, Model model) {
        Product product = productService.getProductById(productId);
        log.debug("불러와진 프로덕트 정보는=========|||||||||||===================" + product.toString());
        model.addAttribute("userProduct", product);
        return "electro/product";
    }


    @GetMapping("/product/listByStore")
    public String getProductListByStore(Model model,
                                        @RequestParam(name="storeId", required = false) Integer store_id,
                                        HttpSession httpSession) {
        if(store_id == null || store_id == 0) {  // 파라미터가 없으면 세션에서 가져오기
            Store store = (Store) httpSession.getAttribute("store");
            store_id = store.getStoreId();
        }

        List<Product> productList = productService.selectByStoreId(store_id);
        model.addAttribute("productListByStore", productList);

        return "store/product/storeproduct";
    }

}
