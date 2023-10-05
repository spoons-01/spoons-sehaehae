package com.spoons.sehaehae.admin.service;

import com.spoons.sehaehae.admin.dao.CouponMapper;
import com.spoons.sehaehae.admin.dto.CouponDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CouponService {
    private final CouponMapper couponMapper;
    public CouponService(CouponMapper couponMapper){this.couponMapper=couponMapper;}
    public CouponDTO selectCouponList() {
        return couponMapper.selectCouponList();
    }
}
