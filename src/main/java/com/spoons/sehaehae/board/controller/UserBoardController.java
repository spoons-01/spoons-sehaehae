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

    @GetMapping("/regist")
    public String getRegistPage() {

        return "/user/board/userReviewRegist";
    }

    @PostMapping("/regist")
    public String registReview(ReviewDTO review, MultipartFile attachImage,
                               @AuthenticationPrincipal MemberDTO member) {

        log.info("review request : {}", review);
        log.info("attachImage request : {}", attachImage);

        String fileUploadDir = IMAGE_DIR + "original";
        String thumbnailDir = IMAGE_DIR + "thumbnail";

        File dir1 = new File(fileUploadDir);
        File dir2 = new File(thumbnailDir);

        /* 디렉토리가 없을 경우 생성한다. */
        if(!dir1.exists() ) {
            dir1.mkdirs();
            dir2.mkdirs();
        }

        /* 업로드 파일에 대한 정보를 담을 리스트 */
        AttachmentDTO attachment = new AttachmentDTO();

        try {
                /* 첨부파일이 실제로 존재하는 경우에만 로직 수행 */
                    if(attachImage.getSize() > 0) {

                        String originalFileName = attachImage.getOriginalFilename();
                        log.info("originalFileName : {}", originalFileName);

                        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
                        String savedName = UUID.randomUUID() + ext;
                        log.info("saveName : {}", savedName);

                        Long size = attachImage.getSize();

                        /* 서버의 설정 디렉토리에 파일 저장하기 */

                        attachImage.transferTo(new File(fileUploadDir + "/" + savedName));

                        /* DB에 저장할 파일의 정보 처리 */
//                        AttachmentDTO fileInfo = new AttachmentDTO();
                        attachment.setName(originalFileName);
                        attachment.setSaveName(savedName);
                        attachment.setRoute("/uplode/original/");
                        attachment.setExtension(ext);
                        attachment.setSize(size);
                        log.info("fileInfo : {}", attachment);
                        attachment.setEx("TITLE"); // 대표사진
//
                            /* 대표 사진에 대한 썸네일을 가공해서 저장한다. */
                            Thumbnails.of(fileUploadDir + "/" + savedName).size(150,150)
                                    .toFile(thumbnailDir + "/thumbnail_" + savedName);
                        attachment.setThumbnail("/upload/thumbnail/thumbnail_" + savedName);

//                        attachmentList.add(fileInfo);
                    }

            }catch (IOException e) {
                throw new RuntimeException(e);
        }

        log.info("fileInfo : {}", attachment);

        review.setAttachment(attachment);
        review.setWriter(member);

        boardService.registReview(review, attachment);

        return "redirect:/user/board/userReview";
    }

}
