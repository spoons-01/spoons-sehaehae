package com.spoons.sehaehae.admin.controller;

import com.spoons.sehaehae.admin.dto.CouponDTO;
import com.spoons.sehaehae.admin.service.CouponService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CouponController {
    private final CouponService couponService;
    public CouponController(CouponService couponService){this.couponService=couponService;}
    @GetMapping("/coupon")
    public String getCouponList(Model model){

        List<CouponDTO> couponList = couponService.selectCouponList();
        model.addAttribute("coupon", couponList);

        return "/admin/coupon/coupon";
    }
}
