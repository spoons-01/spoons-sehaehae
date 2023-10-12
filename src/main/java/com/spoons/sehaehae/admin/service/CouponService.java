package com.spoons.sehaehae.admin.service;

import com.spoons.sehaehae.admin.dao.CouponMapper;
import com.spoons.sehaehae.admin.dto.CouponDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CouponService {
    private final CouponMapper couponMapper;
    public CouponService(CouponMapper couponMapper){this.couponMapper=couponMapper;}
    public List<CouponDTO> selectCouponList() {
        return couponMapper.selectCouponList();
    }

    public void insertCoupon(CouponDTO coupon) {
        couponMapper.insertCoupon(coupon);
    }

    public void removeCoupon(CouponDTO removeCoupon) {
        couponMapper.deleteCoupon(removeCoupon);
    }

    public void updateCoupon(CouponDTO coupon) {
        couponMapper.updateCoupon(coupon);
    }
}
