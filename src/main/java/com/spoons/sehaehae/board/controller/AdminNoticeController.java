package com.spoons.sehaehae.board.controller;

import com.spoons.sehaehae.board.dto.NoticeDTO;
import com.spoons.sehaehae.board.service.BoardService;
import com.spoons.sehaehae.member.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin/board")
public class AdminNoticeController {

    public final BoardService boardService;

    public AdminNoticeController(BoardService boardService) {
        this.boardService = boardService;
    }

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
    public String getRegistPage() {

        return "admin/board/adminNoticeRegist";
    }

    @PostMapping("/adminNoticeRegist")
    public String registNotice(NoticeDTO notice, @AuthenticationPrincipal MemberDTO member) {

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


//    @PostMapping("/update")
//    public String modifyNotice(@PathVariable("no") Long no,
//                               NoticeDTO updatedNotice) throws Exception {
//
//        NoticeDTO noticeTemp = boardService.selectNoticeDetail(no);
////        boardService.modifyNotice(updatedNotice);
//
//        noticeTemp.setTitle(updatedNotice.getTitle());
//        noticeTemp.setBody(updatedNotice.getBody());
//
//        boardService.registNotice(noticeTemp);
//        return "redirect:/admin/board/adminNotice";
//    }


//    @PostMapping("/modify")
//    public String noticeUpdate(Long no, NoticeDTO notice) throws Exception {
//
//        NoticeDTO noticeTemp = boardService.selectNoticeDetail(no);
//
//        noticeTemp.setTitle(notice.getTitle());
//        noticeTemp.setBody(notice.getBody());
//
//        boardService.registNotice(noticeTemp);
////        boardService.updateNotice(notice);
//        return "redirect:/admin/board/adminNotice";
//    }




    @GetMapping("/delete")
    public String deleteNotice(Integer id){

        boardService.deleteNotice(id);

        return "redirect:/admin/board/adminNotice";
    }


}
