package com.spoons.sehaehae.admin.controller;

import com.spoons.sehaehae.admin.dto.CouponDTO;
import com.spoons.sehaehae.admin.service.CouponService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;
    public CouponController(CouponService couponService){this.couponService=couponService;}
    @GetMapping("/list")
    public String getCouponList(Model model){

        List<CouponDTO> couponList = couponService.selectCouponList();
        model.addAttribute("coupon", couponList);

        return "/admin/coupon/couponlist";
    }
    @PostMapping("/insert")
    public String registCoupon(CouponDTO coupon){

        couponService.registCoupon(coupon);

        return "redirect:/admin/coupon/couponlist";
    }
}
