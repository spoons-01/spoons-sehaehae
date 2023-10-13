package com.spoons.sehaehae.board.service;

import com.spoons.sehaehae.board.dao.BoardMapper;
import com.spoons.sehaehae.board.dto.AttachmentDTO;
import com.spoons.sehaehae.board.dto.NoticeDTO;
import com.spoons.sehaehae.board.dto.QnaDTO;
import com.spoons.sehaehae.board.dto.ReviewDTO;
import com.spoons.sehaehae.common.paging.Pagenation;
import com.spoons.sehaehae.common.paging.SelectCriteria;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@Transactional
public class BoardService {

    private final BoardMapper boardMapper;

    public BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }


    /* 공지사항 */
    public Map<String, Object> selectNoticeList(Map<String, String> searchMap, int page) {

        /* 1. 전체 게시글 수 확인 ( 검색어가 있는 경우 포함) => 페이징 처리를 위해 */
        int totalCount = boardMapper.selectTotalCount(searchMap);
        log.info("noticeList totalCount : {}", totalCount);

        /* 2. 페이징 처리와 연관 된 값을 계산하여 SelectCriteris 타입의 객체에 담는다. */
        int limit = 10;         // 한 페이지에 보여줄 게시물의 수
        int buttonAmount = 5;   // 한 번에 보여질 페이징 버튼의 수
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(page, totalCount, limit, buttonAmount, searchMap);
        log.info("noticeList selectCriteria : {}", selectCriteria);

        /* 3. 요청 페이지와 검색 기준에 맞는 게시글을 조회해온다. */
        List<NoticeDTO> noticeList = boardMapper.selectNoticeList(selectCriteria);
        log.info("noticeList : {}", noticeList);

        Map<String, Object> noticeListAndPaging = new HashMap<>();
        noticeListAndPaging.put("paging", selectCriteria);
        noticeListAndPaging.put("noticeList", noticeList);

        return noticeListAndPaging;
    }

    public NoticeDTO selectNoticeDetail(Long no) {

        /* 조회수 증가 로직 호출 */
        boardMapper.incrementNoticeCount(no);

        /* 게시글 상세 내용 조회 후 리턴 */
        return boardMapper.selectNoticeDetail(no);
    }

    @Transactional
    public void registNotice(NoticeDTO notice) {

        boardMapper.insertNotice(notice);
    }

    @Transactional
    public void deleteNotice(Integer id) {
        boardMapper.deleteNotice(id);
    }


    public void modifyNotice(NoticeDTO modifynotice) {

        boardMapper.updateNotice(modifynotice);
    }

    /* 자주하는 질문 */

    public Map<String, Object> selectQnaList(Map<String, String> searchMap, int page) {

        /* 1. 전체 게시글 수 확인 ( 검색어가 있는 경우 포함) => 페이징 처리를 위해 */
        int totalCount = boardMapper.qnaTotalCount(searchMap);

        /* 2. 페이징 처리와 연관 된 값을 계산하여 SelectCriteris 타입의 객체에 담는다. */
        int limit = 10;         // 한 페이지에 보여줄 게시물의 수
        int buttonAmount = 5;   // 한 번에 보여질 페이징 버튼의 수
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(page, totalCount, limit, buttonAmount, searchMap);

        /* 3. 요청 페이지와 검색 기준에 맞는 게시글을 조회해온다. */
        List<QnaDTO> qnaList = boardMapper.selectQnaList(selectCriteria);

        Map<String, Object> qnaListAndPaging = new HashMap<>();
        qnaListAndPaging.put("paging", selectCriteria);
        qnaListAndPaging.put("qnaList", qnaList);

        return qnaListAndPaging;
    }

    public QnaDTO selectQnaView(Long no) {

        /* 게시글 상세 내용 조회 후 리턴 */
        return boardMapper.selectQnaView(no);
    }

    public void registQna(QnaDTO qna) {

        boardMapper.insertQna(qna);
    }

    public void deleteQna(Integer id) {

        boardMapper.deleteQna(id);
    }

    public void modifyQna(QnaDTO modifyQna) {

        boardMapper.updateQna(modifyQna);
    }


    public void registReview(ReviewDTO review, AttachmentDTO attachment) {

        boardMapper.insertReview(review);

       // 삽입한 후기글의 reviewNo를 가져옴
        Long reviewNo = review.getReviewNo();

        attachment.setReviewNo(reviewNo);
        boardMapper.insertAttachment(attachment);

    }

    public Map<String, Object> selectReviewList(Map<String, String> searchMap, int page) {

        /* 1. 전체 게시글 수 확인 ( 검색어가 있는 경우 포함) => 페이징 처리를 위해 */
        int totalCount = boardMapper.reviewTotalCount(searchMap);

        /* 2. 페이징 처리와 연관 된 값을 계산하여 SelectCriteris 타입의 객체에 담는다. */
        int limit = 10;         // 한 페이지에 보여줄 게시물의 수
        int buttonAmount = 5;   // 한 번에 보여질 페이징 버튼의 수
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(page, totalCount, limit, buttonAmount, searchMap);

        /* 3. 요청 페이지와 검색 기준에 맞는 게시글을 조회해온다. */
        List<ReviewDTO> reviewList = boardMapper.selectReviewList(selectCriteria);

        Map<String, Object> reviewListAndPaging = new HashMap<>();
        reviewListAndPaging.put("paging", selectCriteria);
        reviewListAndPaging.put("qnaList", reviewList);

        return reviewListAndPaging;
    }
}
