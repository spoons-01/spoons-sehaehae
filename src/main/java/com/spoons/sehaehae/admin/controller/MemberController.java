package com.spoons.sehaehae.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
    @GetMapping("/member")
    public String MemberList(){
        return "/admin/member/memberList";
    }
}
