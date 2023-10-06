package com.spoons.sehaehae.admin.dao;

import com.spoons.sehaehae.admin.dto.CouponDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CouponMapper {
    List<CouponDTO> selectCouponList();
}
