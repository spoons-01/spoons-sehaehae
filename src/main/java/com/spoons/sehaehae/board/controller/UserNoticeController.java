package com.spoons.sehaehae.board.controller;

import com.spoons.sehaehae.board.dto.NoticeDTO;
import com.spoons.sehaehae.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Controller
@RequestMapping("/user/board")
public class UserNoticeController {

    public final BoardService boardService;

    public UserNoticeController(BoardService boardService) {
        this.boardService = boardService;
    }

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

}
