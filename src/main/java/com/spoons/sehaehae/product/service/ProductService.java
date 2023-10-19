package com.spoons.sehaehae.product.service;

import com.spoons.sehaehae.admin.dto.CpBoxDTO;
import com.spoons.sehaehae.admin.dto.OrderDTO;
import com.spoons.sehaehae.common.paging.Pagenation;
import com.spoons.sehaehae.common.paging.SelectCriteria;
import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.member.dto.MemberLevelDTO;
import com.spoons.sehaehae.product.dao.ProductMapper;
import com.spoons.sehaehae.product.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    public int addCart(CartDTO cart) {

        return productMapper.addCart(cart);
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

    public void addOrder(OrderDTO order,List<OrderProductDTO> orderProducts) {

        for(int i = 0; i < orderProducts.size();i++){
            productMapper.addOrderProduct(orderProducts.get(i));
        }

        productMapper.registOrder(order);
    }

    public void modifyProduct(ProductDTO product) {
        productMapper.modifyProduct(product);
    }


    public void deleteProduct(Map<String,List<Integer>> productMap) {
        productMapper.deleteProduct(productMap);
    }

    public List<CpBoxDTO> selectCoupon(int memberId) {

       return productMapper.selectCoupon(memberId);
    }

    public void deletePremium(Map<String, Object> map) {

        productMapper.deletePremium(map);

    }

    public void deleteEco(Map<String, Object> map) {

        productMapper.deleteEco(map);
    }

    public void addOption(Map<String, Object> addoption) {
        if (addoption.get("option").equals("eco")){
            productMapper.addOption(addoption);
        }else{
            productMapper.addPremium(addoption);
        }
    }

    public Map<String, Object> selectProduct2(Map<String, String> searchMap, int page) {

        int totalCount =  productMapper.selectProductCount(searchMap);
        int limit = 12;
        int buttonAmount= 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(page,totalCount,limit,buttonAmount,searchMap);
        List<ProductDTO> productList = productMapper.selectProduct2(selectCriteria);
        Map<String, Object> productListAndPaging = new HashMap<>();
        productListAndPaging.put("paging",selectCriteria);
        productListAndPaging.put("boardList",productList);

        return productListAndPaging;
    }

    public void updateInfo(Map<String, Object> map) {
        productMapper.updatePoint(map);
        productMapper.updateCoupon(map);
        productMapper.updatePt(map);
        productMapper.deleteCartList(map);
    }


    public PointDTO selectPoint(int memberCode) {
        return productMapper.selectPoint(memberCode);
    }

    public String selectMemberLevel(int memberNo) {

       MemberLevelDTO level = productMapper.selectMemberLevel(memberNo);
       return level.getMembershipCode();
    }

    public void deleteCategory(List<Integer> codeList) {
        productMapper.deleteCategory(codeList);
    }
}
