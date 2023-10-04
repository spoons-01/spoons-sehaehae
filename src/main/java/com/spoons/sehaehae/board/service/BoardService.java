package com.spoons.sehaehae.board.service;

import com.spoons.sehaehae.board.dao.BoardMapper;
import com.spoons.sehaehae.common.paging.Pagenation;
import com.spoons.sehaehae.common.paging.SelectCriteria;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class BoardService {

    private final BoardMapper boardMapper;

    public BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }


    public Map<String, Objects> selectNoticeList(Map<String, String> searchMap, int page) {

        /* 1. 전체 게시글 수 확인 ( 검색어가 있는 경우 포함) => 페이징 처리를 위해 */
        int totalCount = boardMapper.selectTotalCount(searchMap);
        log.info("noticeList totalCount : {}", totalCount);

        /* 2. 페이징 처리와 연관 된 값을 계산하여 SelectCriteris 타입의 객체에 담는다. */
        int limit = 10;         // 한 페이지에 보여줄 게시물의 수
        int buttonAmount = 5;   // 한 번에 보여질 페이징 버튼의 수
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(page, totalCount, limit, buttonAmount, searchMap);
        log.info("noticeList selectCriteria : {}", selectCriteria);

        return null;
    }
}
