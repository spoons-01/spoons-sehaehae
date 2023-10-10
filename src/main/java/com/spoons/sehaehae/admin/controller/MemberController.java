package com.spoons.sehaehae.admin.controller;

import com.spoons.sehaehae.admin.dto.CouponDTO;
import com.spoons.sehaehae.admin.service.CouponService;
import com.spoons.sehaehae.admin.service.MemberService;
import com.spoons.sehaehae.member.dto.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    public MemberController(MemberService memberService){this.memberService=memberService;}
    @GetMapping("/list")
    public String MemberList(Model model){

        List<MemberDTO> memberList = memberService.selectMemberList();
        model.addAttribute("member", memberList);


        return "/admin/member/memberList";
    }
}
