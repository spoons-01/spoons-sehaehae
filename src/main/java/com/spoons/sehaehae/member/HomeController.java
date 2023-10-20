package com.spoons.sehaehae.member;

import com.spoons.sehaehae.board.dto.ReviewDTO;
import com.spoons.sehaehae.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class HomeController {

    private final BoardService boardService;

    public HomeController(BoardService boardService) {
        this.boardService = boardService;
    }
    @GetMapping("/")
    public String home() {

        return "user/main/main";
    }

//    @GetMapping("/")
//    public String home(
//                       @RequestParam(defaultValue = "1") int page,
//                       @RequestParam(required = false) String searchCondition,
//                       @RequestParam(required = false) String searchValue,
//                       Model model) {
//
//
//
//        Map<String, String> searchMap = new HashMap<>();
//        searchMap.put("searchCondition", searchCondition);
//        searchMap.put("searchValue", searchValue);
//
//        Map<String, Object> reviewListAndPaging = boardService.selectReviewList(searchMap, page);
//
//        List<ReviewDTO> reviewList = (List<ReviewDTO>) reviewListAndPaging.get("reviewList");
//
//
//        model.addAttribute("paging", reviewListAndPaging.get("paging"));
//
//        model.addAttribute("reviewList", reviewList);
//
//        return "user/main/main";
//    }
}