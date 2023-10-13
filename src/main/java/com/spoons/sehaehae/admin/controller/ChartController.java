package com.spoons.sehaehae.admin.controller;

import com.spoons.sehaehae.admin.service.AdMemberService;
import com.spoons.sehaehae.admin.service.ChartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/chart")
public class ChartController {
    private final ChartService chartService;
    public ChartController(ChartService chartService){this.chartService = chartService;}
    @GetMapping("/list")
    public String genderChart(Model model) {
        int fNum =chartService.getfNum();
        int mNum =chartService.getmNum();

        model.addAttribute("fNum", fNum);
        model.addAttribute("mNum", mNum);

        return "/admin/chart/memberChart";
    }

}