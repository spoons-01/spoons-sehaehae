package com.spoons.sehaehae.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CouponController {
    @GetMapping("/coupon")
    public String coupn(){
        return "/admin/coupon/coupon";
    }
}
