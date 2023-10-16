package com.spoons.sehaehae.coupon.service;

import com.spoons.sehaehae.admin.dao.CouponMapper;
import com.spoons.sehaehae.admin.dto.CouponDTO;
import com.spoons.sehaehae.admin.dto.CpBoxDTO;
import com.spoons.sehaehae.member.dto.MemberDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCouponService {
    private final CouponMapper couponMapper;
    public UserCouponService(CouponMapper couponMapper){this.couponMapper=couponMapper;}

    public List<CouponDTO> selectCouponList() {return couponMapper.selectCouponList();
    }


    public void userInsertCoupon(CpBoxDTO cpBox, MemberDTO member,CouponDTO coupon) {couponMapper.userInsertCoupon(cpBox, member, coupon);
    }
}
