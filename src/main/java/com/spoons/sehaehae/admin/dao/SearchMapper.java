package com.spoons.sehaehae.admin.dao;

import com.spoons.sehaehae.admin.dto.OrderDTO;
import com.spoons.sehaehae.common.paging.SelectCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SearchMapper {

    int selectTotalCount(Map<String, String> searchMap);

    List<OrderDTO> selectSearchList(SelectCriteria selectCriteria);  //전체 주문리스트

    List<OrderDTO> selectSearchPaymentList(SelectCriteria selectCriteria);  //결제완료

    int updateOrderStatus(Map<String, Object> updateStatus);  //이게 이제 체크박스 체크한 애들 버튼 누를시에 주문상태 바뀌는 역할

    List<OrderDTO> selectSearchCollectionList(SelectCriteria selectCriteria);  //수거완료

    List<OrderDTO> selectSearchLaundryList(SelectCriteria selectCriteria);    //세탁완료

    List<OrderDTO> selectSearchPreparingList(SelectCriteria selectCriteria);

    List<OrderDTO> selectSearchDeliveryList(SelectCriteria selectCriteria);

    List<OrderDTO> selectSearchConfirmedList(SelectCriteria selectCriteria);
}
