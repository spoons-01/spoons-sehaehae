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
    public String home(Model model) {

        // 페이징 처리를 하지 않고 전체 리뷰 리스트를 가져옴
        List<ReviewDTO> reviewList = boardService.recentlyReview();

        // 모델에 리뷰 리스트 추가
        model.addAttribute("reviewList", reviewList);

        return "user/main/main";
    }

}