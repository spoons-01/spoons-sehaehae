package com.spoons.sehaehae.board.controller;

import com.spoons.sehaehae.board.dto.BoardCategoryDTO;
import com.spoons.sehaehae.board.dto.NoticeDTO;
import com.spoons.sehaehae.board.dto.QnaDTO;
import com.spoons.sehaehae.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Controller
@RequestMapping("/user/board")
public class UserBoardController {

    private final BoardService boardService;

    public UserBoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    List<BoardCategoryDTO> categoryList = new ArrayList<>();
    List<QnaDTO> qnaList = new ArrayList<>();

    @GetMapping("/userNotice")
    public String getNotice(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(required = false) String searchCondition,
                            @RequestParam(required = false) String searchValue,
                            Model model) {


        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);

        Map<String, Object> noticeListAndPaging = boardService.selectNoticeList(searchMap, page);
        model.addAttribute("paging", noticeListAndPaging.get("paging"));
        model.addAttribute("noticeList", noticeListAndPaging.get("noticeList"));

        return "/user/board/userNotice";
    }
    @GetMapping("/userNoticeView")
    public String getNoticeView(@RequestParam Long no, Model model){

        NoticeDTO noticeDetail = boardService.selectNoticeDetail(no);

        model.addAttribute("notice", noticeDetail);

        return "/user/board/userNoticeView";
    }

    /* 자주하는 질문 */
    @GetMapping("/userQna")
    public String getQna(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(required = false) String searchCondition,
                         @RequestParam(required = false) String searchValue,
                         Model model
                       ) {

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);

        Map<String, Object> boardListAndPaging = boardService.selectQnaList(searchMap, page);
        model.addAttribute("paging",boardListAndPaging.get("paging"));
        model.addAttribute("qnaList", boardListAndPaging.get("qnaList"));

        return "user/board/userQna";
    }

    /* 후기게시판 */

//    @GetMapping("/userReview")
//    public String getReview() {
//
//        return "user/board/userReview";
//    }
//
//    @GetMapping("/userReview")
//    public String getRegistReview() {
//
//        return "user/board/userReview";
//    }

}
