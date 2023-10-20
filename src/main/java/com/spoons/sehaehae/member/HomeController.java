package com.spoons.sehaehae.member;

import com.spoons.sehaehae.board.dto.NoticeDTO;
import com.spoons.sehaehae.board.dto.ReplyDTO;
import com.spoons.sehaehae.board.dto.ReviewDTO;
import com.spoons.sehaehae.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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

//    @GetMapping("/userReviewView")
//    public String homeReview(@RequestParam("no") Long no,
//                             Model model) {
//
//        log.info("no: {}", no);
//        ReviewDTO reviewList = boardService.recentlyReview(no);
//
//        log.info("reviewList: {}", reviewList);
//
//        model.addAttribute("reviewList", reviewList);
//        model.addAttribute("writerNickname", reviewList.getWriter().getNickname());
//        model.addAttribute("rating", reviewList.getRating());
//
//
//        return "user/main/main";
//    }

//    @GetMapping("/userReviewView")
//    public ResponseEntity<List<ReviewDTO>> loadReply(@RequestParam("no") Long reviewNo, ReviewDTO review ) {
//        log.info("-------test");
//        log.info("loadReply refBoardNo : {}", reviewNo);
//
//        List<ReviewDTO> reviewList = boardService.recentlyReview(reviewNo, review);
//        log.info("loadReply replyList : {}", reviewList);
//
//        return ResponseEntity.ok(reviewList);
//    }
}