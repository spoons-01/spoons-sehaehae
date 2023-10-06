package com.spoons.sehaehae.product.dao;

import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.product.dto.CartDTO;
import com.spoons.sehaehae.product.dto.CategoryDTO;
import com.spoons.sehaehae.product.dto.ProductDTO;
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

    List<ProductDTO> selectAllProduct();

    void deleteCart(Map<String,Object> product);
}
