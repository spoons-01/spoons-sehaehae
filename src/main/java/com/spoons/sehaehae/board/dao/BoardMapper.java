package com.spoons.sehaehae.board.dao;

import com.spoons.sehaehae.board.dto.ReviewPointDTO;
import com.spoons.sehaehae.board.dto.*;
import com.spoons.sehaehae.common.paging.SelectCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {
    int selectTotalCount(Map<String, String> searchMap);

    List<NoticeDTO> selectNoticeList(SelectCriteria selectCriteria);

    void incrementNoticeCount(Long no);

    NoticeDTO selectNoticeDetail(Long no);

    void insertNotice(NoticeDTO notice);

    void deleteNotice(Integer id);

    int updateNotice(NoticeDTO updatedNotice);

    int qnaTotalCount(Map<String, String> searchMap);

    List<QnaDTO> selectQnaList(SelectCriteria selectCriteria);

    QnaDTO selectQnaView(Long no);

    void insertQna(QnaDTO qna);

    void deleteQna(Integer id);

    void updateQna(QnaDTO modifyQna);


    void insertReview(ReviewDTO review);

    void insertAttachment(AttachmentDTO attachment);

    int reviewTotalCount(Map<String, String> searchMap);

    List<ReviewDTO> selectReviewList(SelectCriteria selectCriteria);

    ReviewDTO selectReviewView(Long no);

    void incrementReviewCount(Long no);

    void deleteReview(Integer id);

    void updateOrderReviewStatus(String orderCode);

    void insertPoint(ReviewPointDTO point);

    void insertReply(ReplyDTO registReply);

    List<ReplyDTO> selectReplyList(Long reviewNo, ReplyDTO loadReply);

    void deleteReply(ReplyDTO removeReply);

    int getCommentCountForReview(Long reviewNo);
}
