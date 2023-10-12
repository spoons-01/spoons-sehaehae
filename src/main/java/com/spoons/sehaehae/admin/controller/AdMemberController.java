package com.spoons.sehaehae.admin.controller;

import com.spoons.sehaehae.admin.service.AdMemberService;
import com.spoons.sehaehae.member.dto.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/member")
public class AdMemberController {
    private final AdMemberService adMemberService;
    public AdMemberController(AdMemberService adMemberService){this.adMemberService = adMemberService;}
    @GetMapping("/list")
    public String MemberList(Model model){

        List<MemberDTO> memberList = adMemberService.selectMemberList();
        model.addAttribute("member", memberList);


        return "/admin/member/memberList";
    }

    @GetMapping("/message")
    public String MemberMessage(){
        return "admin/member/memberMessage";
    }
}
