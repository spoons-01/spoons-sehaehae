package com.spoons.sehaehae.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/board")
public class UserNoticeCotroller {

    @GetMapping("/userNotice")
    public String getNotice() {

        return "/user/board/userNotice";
    }

    @GetMapping("/userNoticeDetails")
    public String getBoardDetail(){


        return "/user/board/userNoticeDetails";
    }
}
