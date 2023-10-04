package com.spoons.sehaehae.board.controller;

import com.spoons.sehaehae.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/admin/board")
public class AdminNoticeCotroller {

    public final BoardService boardService;

    public AdminNoticeCotroller(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/adminNotice")
    public String getNotice(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(required = false) String searchCondition,
                            @RequestParam(required = false) String searchValue) {

        log.info("noticeList page : {}", page);
        log.info("noticeList searchCondition : {}", searchCondition);
        log.info("noticeList searchValue : {}", searchValue);

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);

        Map<String, Objects> noticeListAndPaging = boardService.selectNoticeList(searchMap, page);

        return "admin/board/adminNoticeList";
    }
}
