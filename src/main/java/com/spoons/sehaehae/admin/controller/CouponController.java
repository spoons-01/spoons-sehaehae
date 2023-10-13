package com.spoons.sehaehae.admin.controller;

import com.spoons.sehaehae.admin.dto.CouponDTO;
import com.spoons.sehaehae.admin.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "/admin/coupon/couponList";
    }

    @PostMapping("/insert")
    public String insertCoupon(CouponDTO coupon){
        couponService.insertCoupon(coupon);
        return "redirect:/coupon/list";
    }

    @PostMapping("/update")
    public String updateCoupon(CouponDTO coupon){
        couponService.updateCoupon(coupon);
        return "redirect:/coupon/list";
    }

    @PostMapping("/remove")
    public String removeCoupon(CouponDTO removeCoupon){
        couponService.removeCoupon(removeCoupon);
        return "redirect:/coupon/list";
    }


}
