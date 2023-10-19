package com.spoons.sehaehae.product.dao;

import com.spoons.sehaehae.admin.dto.CpBoxDTO;
import com.spoons.sehaehae.admin.dto.OrderDTO;
import com.spoons.sehaehae.common.paging.SelectCriteria;
import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.member.dto.MemberLevelDTO;
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
    int addCart(CartDTO cart);

    List<CartDTO> cartList(int i);

    MemberDTO selectMember(int memberCode);

    void updateCartList(Map<String,Object> updateMap);

    List<ProductDTO> selectAllProduct(Map<String, Object> searchMap);

    void deleteCart(Map<String,Object> product);

    void registOrder(OrderDTO order);

    void modifyProduct(ProductDTO product);

    void deleteProduct(Map<String,List<Integer>> productMap);

    List<ProductDTO> selectAllproductAdmin();

    List<CpBoxDTO> selectCoupon(int memberId);

    void deletePremium(Map<String, Object> map);

    void deleteEco(Map<String, Object> map);

    void addOption(Map<String, Object> addoption);

    void addPremium(Map<String, Object> addoption);

    void addOrderProduct(OrderProductDTO orderProductDTO);

    int selectProductCount(Map<String, String> searchMap);

    List<ProductDTO> selectProduct2(SelectCriteria selectCriteria);

    void updatePoint(Map<String, Object> map);

    void updateCoupon(Map<String, Object> map);

    PointDTO selectPoint(int memberCode);

    MemberLevelDTO selectMemberLevel(int memberNo);

    void deleteCategory(List<Integer> codeList);

    void updatePt(Map<String, Object> map);

    void deleteCartList(Map<String, Object> map);

    boolean checkProduct(CartDTO cart);
}
