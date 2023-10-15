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

        int first =chartService.getFirst();
        int second =chartService.getSecond();
        int third =chartService.getThird();
        int fourth =chartService.getFourth();
        int fifth =chartService.getFifth();

        model.addAttribute("fNum", fNum);
        model.addAttribute("mNum", mNum);

        model.addAttribute("first", first);
        model.addAttribute("second", second);
        model.addAttribute("third", third);
        model.addAttribute("fourth", fourth);
        model.addAttribute("fifth", fifth);

        return "/admin/chart/memberChart";
    }

}