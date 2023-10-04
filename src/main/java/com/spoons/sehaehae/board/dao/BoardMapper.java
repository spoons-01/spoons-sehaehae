package com.spoons.sehaehae.board.dao;

import com.spoons.sehaehae.board.dto.NoticeDTO;
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
}
