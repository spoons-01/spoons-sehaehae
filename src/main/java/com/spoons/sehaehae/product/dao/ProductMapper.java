package com.spoons.sehaehae.product.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
    void registCategory(String categoryName);
}
