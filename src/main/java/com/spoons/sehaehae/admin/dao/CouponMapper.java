package com.spoons.sehaehae.admin.dao;

import com.spoons.sehaehae.admin.dto.CouponDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CouponMapper {
    CouponDTO selectCouponList();
}
