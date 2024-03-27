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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

        return "user/board/userNotice";
    }
    @GetMapping("/userNoticeView")
    public String getNoticeView(@RequestParam Long no, Model model){

        NoticeDTO noticeDetail = boardService.selectNoticeDetail(no);



        model.addAttribute("notice", noticeDetail);

        return "user/board/userNoticeView";
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
//    public String getReview(@RequestParam(defaultValue = "1") int page,
//                            @RequestParam(required = false) String searchCondition,
//                            @RequestParam(required = false) String searchValue,
//                            Model model) {
//
//        Map<String, String> searchMap = new HashMap<>();
//        searchMap.put("searchCondition", searchCondition);
//        searchMap.put("searchValue", searchValue);
//
//        Map<String, Object> reviewListAndPaging = boardService.selectReviewList(searchMap,page);
//        model.addAttribute("paging",reviewListAndPaging.get("paging"));
//        model.addAttribute("reviewList", reviewListAndPaging.get("reviewList"));
//
//
//        return "/user/board/userReview";
//    }

    @GetMapping("/userReview")
    public String getReview(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(required = false) String searchCondition,
                            @RequestParam(required = false) String searchValue,
                            Model model) {

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);

        Map<String, Object> reviewListAndPaging = boardService.selectReviewList(searchMap, page);

        List<ReviewDTO> reviewList = (List<ReviewDTO>) reviewListAndPaging.get("reviewList");

        // 각 게시글에 대한 댓글 수를 가져오고 모델에 추가
        for (ReviewDTO review : reviewList) {
            int commentCount = boardService.getCommentCountForReview(review.getReviewNo());
            review.setCommentCount(commentCount);
        }

        model.addAttribute("paging", reviewListAndPaging.get("paging"));
        model.addAttribute("reviewList", reviewList);

        return "user/board/userReview";
    }

    @GetMapping("/userReviewView")
    public String getReviewView(@RequestParam Long no, Model model){

        ReviewDTO reviewView = boardService.selectReviewView(no);

        // 댓글 수를 가져옴
        int commentCount = boardService.getCommentCountForReview(no);

        model.addAttribute("review", reviewView);
        model.addAttribute("commentCount", commentCount);
        return "user/board/userReviewView";
    }



    /* 댓글 */

    @PostMapping("/userReviewView/registReply")
    public ResponseEntity<String> registReply(@RequestBody ReplyDTO registReply,
                                              @AuthenticationPrincipal MemberDTO member) {


        registReply.setWriter(member);

        boardService.registReply(registReply);

        return ResponseEntity.ok("댓글 등록 완료");
    }

//    @GetMapping("/userReviewView/loadReply")
//    public ResponseEntity<List<ReplyDTO>> loadReply(ReplyDTO loadReply, ReviewDTO review) {
//
//        log.info("-------test");
//        log.info("loadReply refBoardNo : {}", review.getReviewNo());
//        List<ReplyDTO> replyList = boardService.loadReply(loadReply, review);
//        log.info("loadReply replyList : {}", replyList);
//
//
//        return ResponseEntity.ok(replyList);
//    }

    @GetMapping("/userReviewView/loadReply")
    public ResponseEntity<List<ReplyDTO>> loadReply(@RequestParam("no") Long reviewNo,ReplyDTO loadReply ) {
        log.info("-------test");
        log.info("loadReply refBoardNo : {}", reviewNo);
        List<ReplyDTO> replyList = boardService.loadReply(reviewNo, loadReply);
        log.info("loadReply replyList : {}", replyList);

        return ResponseEntity.ok(replyList);
    }
    @PostMapping("/userReviewView/removeReply")
    public ResponseEntity<String> removeReply(@RequestBody ReplyDTO removeReply) {

        boardService.removeReply(removeReply);

        return ResponseEntity.ok("댓글 삭제 완료");
    }
}
