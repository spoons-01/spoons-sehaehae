package com.spoons.sehaehae.admin.dao;

import com.spoons.sehaehae.admin.dto.OrderDTO;
import com.spoons.sehaehae.admin.dto.RefundDTO;
import com.spoons.sehaehae.common.paging.SelectCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SearchMapper {

    /*페이징*/
    int selectTotalCount(Map<String, String> searchMap);  //페이징
    int selectRefundTotalCount(Map<String, String> searchMap);
    int selectCalculateTotalCount(Map<String, String> searchMap);

    /*주문관리*/
    List<OrderDTO> selectSearchList(SelectCriteria selectCriteria);  //전체 주문리스트
    List<OrderDTO> selectSearchPaymentList(SelectCriteria selectCriteria);  //결제완료
    List<OrderDTO> selectSearchCollectionList(SelectCriteria selectCriteria);  //수거완료
    List<OrderDTO> selectSearchLaundryList(SelectCriteria selectCriteria);    //세탁완료
    List<OrderDTO> selectSearchPreparingList(SelectCriteria selectCriteria);  //배송준비
    List<OrderDTO> selectSearchDeliveryList(SelectCriteria selectCriteria);   //배송중
    List<OrderDTO> selectSearchConfirmedList(SelectCriteria selectCriteria);   // 구매확정

    /*환불*/
    List<RefundDTO> selectSearchRefundList(SelectCriteria selectCriteria);

    /* 업데이트 */
    int updateOrderStatus(Map<String, Object> updateStatus);  //이게 이제 체크박스 체크한 애들 버튼 누를시에 주문상태 바뀌는 역할
    int updateRefundStatus(Map<String, Object> updateStatus);

    /* 주문 상세 페이지 */
    OrderDTO getOrderDetailList(String clickedOrderCode);
    List<OrderDTO> getOrderProductDetailList(String clickedOrderCode);
    OrderDTO getPaymentDetailsList(String clickedOrderCode);

    /* 정산관리 */
    List<OrderDTO> selectCalculateList(SelectCriteria selectCriteria);
}