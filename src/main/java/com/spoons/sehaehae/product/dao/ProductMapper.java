package com.spoons.sehaehae.product.dao;

import com.spoons.sehaehae.admin.dto.CouponDTO;
import com.spoons.sehaehae.admin.dto.OrderDTO;
import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.product.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Mapper
@Transactional
public interface ProductMapper {
    void registCategory(String categoryName);

    void registProduct(ProductDTO product);

    List<ProductDTO> selectProduct(Map<String, String> searchMap);

    List<CategoryDTO> selectCategory();

    ProductDTO selectProductByCode(int code);
    void addCart(CartDTO cart);

    List<CartDTO> cartList(int i);

    MemberDTO selectMember(int memberCode);

    void updateCartList(Map<String,Object> updateMap);

    List<ProductDTO> selectAllProduct(Map<String, Object> searchMap);

    void deleteCart(Map<String,Object> product);

    void registOrder(OrderDTO order);

    void modifyProduct(ProductDTO product);

    void deleteProduct(Map<String,List<Integer>> productMap);

    List<ProductDTO> selectAllproductAdmin();

    List<CouponDTO> selectCoupon(int memberId);

    int selectProductCount();

    void deletePremium(Map<String, Object> map);

    void deleteEco(Map<String, Object> map);

    void addOption(Map<String, Object> addoption);

    void addPremium(Map<String, Object> addoption);
}
