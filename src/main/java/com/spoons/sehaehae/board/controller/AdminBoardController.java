package com.spoons.sehaehae.board.controller;

import com.spoons.sehaehae.board.dto.NoticeDTO;
import com.spoons.sehaehae.board.dto.QnaDTO;
import com.spoons.sehaehae.board.service.BoardService;
import com.spoons.sehaehae.member.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin/board")
public class AdminBoardController {

    private final BoardService boardService;

    public AdminBoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    /* 공지사항 */
    @GetMapping("/adminNotice")
    public String getNotice(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(required = false) String searchCondition,
                            @RequestParam(required = false) String searchValue,
                            Model model) {

        log.info("noticeList page : {}", page);
        log.info("noticeList searchCondition : {}", searchCondition);
        log.info("noticeList searchValue : {}", searchValue);

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);

        Map<String, Object> noticeListAndPaging = boardService.selectNoticeList(searchMap, page);
        model.addAttribute("paging", noticeListAndPaging.get("paging"));
        model.addAttribute("noticeList", noticeListAndPaging.get("noticeList"));

        return "admin/board/adminNoticeList";
    }

    @GetMapping("/adminNoticeView")
    public String getNoticeView(@RequestParam Long no, Model model){

        NoticeDTO noticeDetail = boardService.selectNoticeDetail(no);
        log.info("noticeDetail : {}", noticeDetail);
        model.addAttribute("notice", noticeDetail);

        return "admin/board/adminNoticeView";
    }

    @GetMapping("/adminNoticeRegist")
    public String getRegistPage(Model model) {
        model.addAttribute("notice", new NoticeDTO());
        return "admin/board/adminNoticeRegist";
    }

    @PostMapping("/adminNoticeRegist")
    public String registNotice(@ModelAttribute NoticeDTO notice, @AuthenticationPrincipal MemberDTO member) {

        notice.setWriter(member);
        log.info("registNotice notice : {}", notice);

        boardService.registNotice(notice);

        return "redirect:/admin/board/adminNotice";
    }

//    @GetMapping("/adminNoticeUpdate")
//    public String getUpdatePage() {
//
//        return "admin/board/adminNoticeUpdate";
//    }
//
//    @RequestMapping("/adminNoticeUpdate")
//    public String updateNotice(NoticeDTO notice, @AuthenticationPrincipal MemberDTO member ) throws Exception {
//
//        boardService.updateNotice(notice);
//
//        return "redirect:/admin/board/adminNotice";
//    }

    @GetMapping("/update")
    public String modifyPage(@RequestParam Long no,
                               Model model) {

        NoticeDTO noticeDetail = boardService.selectNoticeDetail(no);
        model.addAttribute("notice", noticeDetail);

        return "admin/board/adminNoticeUpdate";
    }

    @PostMapping("/update")
    public String modifyNotice(@RequestParam("no") Long no,
                               @ModelAttribute("modifynotice") NoticeDTO modifynotice ) {


        boardService.modifyNotice(modifynotice);

        return "redirect:/admin/board/adminNotice";
    }

    @GetMapping("/delete")
    public String deleteNotice(Integer id){

        boardService.deleteNotice(id);

        return "redirect:/admin/board/adminNotice";
    }
/* 자주하는 질문 ---------- */
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

    @GetMapping("/adminQnaView")
    public String getQnaView(@RequestParam Long no, Model model){

        QnaDTO qnaView = boardService.selectQnaView(no);
        model.addAttribute("qna", qnaView);

        return "admin/board/adminQnaView";
    }

    @PostMapping("/adminQna")
    public String registQna(QnaDTO qna, @AuthenticationPrincipal MemberDTO member) {

        qna.setWriter(member);

        boardService.registQna(qna);

        return "redirect:/admin/board/adminQna";
    }

    @PostMapping("/updateQna")
    public String modifyQna(@RequestParam("no") Long no,
                               @ModelAttribute("modifyQna") QnaDTO modifyQna) {


        boardService.modifyQna(modifyQna);

        return "redirect:/admin/board/adminQna";
    }

    @GetMapping("/deleteQna")
    public String deleteQna(Integer id){

        boardService.deleteQna(id);

        return "redirect:/admin/board/adminQna";
    }
}
