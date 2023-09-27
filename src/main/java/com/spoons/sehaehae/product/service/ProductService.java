package com.spoons.sehaehae.product.service;

import com.spoons.sehaehae.product.dao.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    ProductMapper productMapper;
    @Autowired
    ProductService(ProductMapper productMapper){
        this.productMapper = productMapper;
    }
    public void registCategory(String categoryName) {
        productMapper.registCategory(categoryName);


    }
}
