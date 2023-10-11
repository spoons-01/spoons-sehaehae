package com.spoons.sehaehae.board.controller;

import com.spoons.sehaehae.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/board")
public class AdminQnaController {

    private final BoardService boardService;

    public AdminQnaController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/adminQna")
    public String getQna(@RequestParam(defaultValue = "1")int page,
                         @RequestParam(required = false) String searchCondition,
                         @RequestParam(required = false)String searchValue,
                         Model model){

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);

    Map<String, Object> boardListAndPaging = boardService.selectQnaList(searchMap, page);
    model.addAttribute("paging",boardListAndPaging.get("paging"));
    model.addAttribute("qnaList", boardListAndPaging.get("qnaList"));

        return "/admin/board/adminQnaList";
    }
}
