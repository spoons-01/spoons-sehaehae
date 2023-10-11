package com.spoons.sehaehae.admin.dao;

import com.spoons.sehaehae.admin.dto.OrderDTO;
import com.spoons.sehaehae.common.paging.SelectCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SearchMapper {

    int selectTotalCount(Map<String, String> searchMap);

    List<OrderDTO> selectSearchList(SelectCriteria selectCriteria);

    List<OrderDTO> selectSearchPaymentList(SelectCriteria selectCriteria);

    int updateOrderStatus(Map<String, Object> updateStatus);
}
