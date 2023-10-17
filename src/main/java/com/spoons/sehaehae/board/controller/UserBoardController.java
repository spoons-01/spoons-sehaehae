package com.spoons.sehaehae.board.controller;

import com.spoons.sehaehae.board.dto.*;
import com.spoons.sehaehae.board.service.BoardService;
import com.spoons.sehaehae.member.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/user/board")
public class UserBoardController {

    private final BoardService boardService;

    public UserBoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @Value("${image.image-dir}")
    private String IMAGE_DIR;

    @Autowired
    private ResourceLoader resourceLoader;


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

    @GetMapping("/userReview")
    public String getReview(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(required = false) String searchCondition,
                            @RequestParam(required = false) String searchValue,
                            Model model) {

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);

        Map<String, Object> reviewListAndPaging = boardService.selectReviewList(searchMap,page);
        model.addAttribute("paging",reviewListAndPaging.get("paging"));
        model.addAttribute("reviewList", reviewListAndPaging.get("reviewList"));

        return "/user/board/userReview";
    }


    @GetMapping("/userReviewView")
    public String getReviewView(@RequestParam Long no, Model model){

        ReviewDTO reviewView = boardService.selectReviewView(no);

        model.addAttribute("review", reviewView);

        return "/user/board/userReviewView";
    }
}
