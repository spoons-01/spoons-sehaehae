package com.spoons.sehaehae.coupon;

import com.spoons.sehaehae.admin.dto.CouponDTO;
import com.spoons.sehaehae.admin.dto.CpBoxDTO;
import com.spoons.sehaehae.coupon.service.UserCouponService;
import com.spoons.sehaehae.member.dto.MemberDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/coupon")
public class UserCouponController {
    private final UserCouponService userCouponService;
    public UserCouponController(UserCouponService userCouponService){this.userCouponService=userCouponService;}

    @GetMapping("/list")
    public String UserCouponList(Model model){
        List<CouponDTO> couponList = userCouponService.selectCouponList();
        model.addAttribute("coupon", couponList);
        return "/user/coupon/userCouponList";
    }

    @PostMapping("/insert")
    public String insertCoupon(CpBoxDTO cpBox, @AuthenticationPrincipal MemberDTO member,CouponDTO coupon ) {
        cpBox.setMember(member);
        cpBox.setCode(coupon);
        userCouponService.userInsertCoupon(cpBox, member, coupon);
        return "redirect:/user/coupon/list";
    }
}
