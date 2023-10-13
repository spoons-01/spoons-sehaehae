package com.spoons.sehaehae.product.service;

import com.spoons.sehaehae.admin.dto.CouponDTO;
import com.spoons.sehaehae.admin.dto.OrderDTO;
import com.spoons.sehaehae.common.paging.Pagenation;
import com.spoons.sehaehae.common.paging.SelectCriteria;
import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.product.dao.ProductMapper;
import com.spoons.sehaehae.product.dto.*;
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

    public void updateCartList(Map<String,Object> updateMap) {
        productMapper.updateCartList(updateMap);
    }

    public List<ProductDTO> selectAllproduct(Map<String, Object> searchMap) {

        return productMapper.selectAllProduct(searchMap);
    }

    public void deleteCart(Map<String,Object> product) {

        productMapper.deleteCart(product);
    }

    public void addOrder(OrderDTO order) {

        productMapper.registOrder(order);
    }

    public void modifyProduct(ProductDTO product) {
        productMapper.modifyProduct(product);
    }


    public void deleteProduct(Map<String,List<Integer>> productMap) {
        productMapper.deleteProduct(productMap);
    }

    public List<ProductDTO> selectAllproductAdmin() {
        return productMapper.selectAllproductAdmin();
    }

    public List<CouponDTO> selectCoupon(int memberId) {

       return productMapper.selectCoupon(memberId);
    }

    public Map<String, Object> selectAllproduct1(int page) {
        int totalCount = productMapper.selectProductCount();
        int limit = 10;
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(page,limit,buttonAmount, totalCount);
        System.out.println(totalCount);
        return null;
    }

    public void deletePremium(Map<String, Object> map) {

        productMapper.deletePremium(map);

    }

    public void deleteEco(Map<String, Object> map) {

        productMapper.deleteEco(map);
    }
}
