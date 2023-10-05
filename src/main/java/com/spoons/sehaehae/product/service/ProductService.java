package com.spoons.sehaehae.product.service;

import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.product.dao.ProductMapper;
import com.spoons.sehaehae.product.dto.CartDTO;
import com.spoons.sehaehae.product.dto.CategoryDTO;
import com.spoons.sehaehae.product.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public void registProduct(ProductDTO product) {
        productMapper.registProduct(product);
    }


    public List<ProductDTO> selectProduct(Map<String, String> searchMap) {
        return productMapper.selectProduct(searchMap);
    }

    public List<CategoryDTO> selectCategory() {
        return  productMapper.selectCategory();
    }

    public ProductDTO selectProductByCode(int code) {
        return productMapper.selectProductByCode(code);
    }

    public void addCart(CartDTO cart) {
        productMapper.addCart(cart);
    }



    public List<CartDTO> cartList(int i) {
        return  productMapper.cartList(i);
    }

    public MemberDTO selectMember(int memberCode) {

        return productMapper.selectMember(memberCode);
    }

    public void updateCartList(int body) {
        productMapper.updateCartList(body);
    }

    public List<ProductDTO> selectAllproduct() {

        return productMapper.selectAllProduct();
    }

    public void deleteCart(Map<String,Object> product) {

        productMapper.deleteCart(product);
    }
}
