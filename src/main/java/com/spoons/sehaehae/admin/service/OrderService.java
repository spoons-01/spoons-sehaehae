package com.spoons.sehaehae.admin.service;


import com.spoons.sehaehae.admin.dao.SearchMapper;
import com.spoons.sehaehae.admin.dto.OrderDTO;
import com.spoons.sehaehae.common.paging.Pagenation;
import com.spoons.sehaehae.common.paging.SelectCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Slf4j
public class OrderService {

    private final SearchMapper searchMapper;

    public OrderService(SearchMapper searchMapper){this.searchMapper = searchMapper;}

    /*---------------------------------------------전체리스트----------------------------------------------*/
    public Map<String, Object> selectSerchList(Map<String, String> searchMap, int page) {

        int totalCount = searchMapper.selectTotalCount(searchMap);  // searchMap 검색어 기준으로 했을때 총 게시글 수가 몇개 있나요?

        log.info("totalCount {}",  totalCount );
        int limit = 10; //한 페이지에 보여줄 게시물의 수
        int buttonAmount = 5; // 한 번에 보여질 페이징 버튼의 수
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(page, totalCount, limit, buttonAmount, searchMap);
        log.info("{}", selectCriteria);
        /* 요청 페이지와 검색 기준에 맞는 게시글 조회 */
        List<OrderDTO> searchList = searchMapper.selectSearchList(selectCriteria);

        Map<String, Object> searchListAndPaging = new HashMap<>();
        searchListAndPaging.put("paging", selectCriteria);
        searchListAndPaging.put("searchList", searchList);

        return searchListAndPaging;
    }
    /*---------------------------------------------결제완료----------------------------------------------*/

    public Map<String, Object> selectSearchPaymentList(Map<String, String> searchMap, int page) {

        int totalCount = searchMapper.selectTotalCount(searchMap);

        int limit = 10; //한 페이지에 보여줄 게시물의 수
        int buttonAmount = 5; // 한 번에 보여질 페이징 버튼의 수
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(page, totalCount, limit, buttonAmount, searchMap);

        /* 요청 페이지와 검색 기준에 맞는 게시글 조회 */
        List<OrderDTO> searchPaymentList = searchMapper.selectSearchPaymentList(selectCriteria);

        Map<String, Object> searchListAndPaging = new HashMap<>();
        searchListAndPaging.put("paging", selectCriteria);
        searchListAndPaging.put("searchPaymentList", searchPaymentList);

        return searchListAndPaging;
    }


    public boolean updateOrderStatus(Long[] orderId, String newStatus) {

        try {
            Map<String, Object> updateStatus = new HashMap<>();
            updateStatus.put("orderId", Arrays.asList(orderId));
            updateStatus.put("newStatus", newStatus);

            int affectedRows = searchMapper.updateOrderStatus(updateStatus);

            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /*---------------------------------------------수거완료----------------------------------------------*/
    public Map<String, Object> selectSearchCollectionList(Map<String, String> searchMap, int page) {
        int totalCount = searchMapper.selectTotalCount(searchMap);

        int limit = 10; //한 페이지에 보여줄 게시물의 수
        int buttonAmount = 5; // 한 번에 보여질 페이징 버튼의 수
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(page, totalCount, limit, buttonAmount, searchMap);

        /* 요청 페이지와 검색 기준에 맞는 게시글 조회 */
        List<OrderDTO> searchCollectionList = searchMapper.selectSearchCollectionList(selectCriteria);

        Map<String, Object> searchListAndPaging = new HashMap<>();
        searchListAndPaging.put("paging", selectCriteria);
        searchListAndPaging.put("searchCollectionList", searchCollectionList);

        return searchListAndPaging;
    }

    /*---------------------------------------------세탁완료----------------------------------------------*/

    public Map<String, Object> selectSearchLaundryList(Map<String, String> searchMap, int page) {
        int totalCount = searchMapper.selectTotalCount(searchMap);

        int limit = 10; //한 페이지에 보여줄 게시물의 수
        int buttonAmount = 5; // 한 번에 보여질 페이징 버튼의 수
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(page, totalCount, limit, buttonAmount, searchMap);

        /* 요청 페이지와 검색 기준에 맞는 게시글 조회 */
        List<OrderDTO> searchLaundryList = searchMapper.selectSearchLaundryList(selectCriteria);

        Map<String, Object> searchListAndPaging = new HashMap<>();
        searchListAndPaging.put("paging", selectCriteria);
        searchListAndPaging.put("searchLaundryList", searchLaundryList);

        return searchListAndPaging;

    }

    /*---------------------------------------------배송준비----------------------------------------------*/
    public Map<String, Object> selectSearchPreparingList(Map<String, String> searchMap, int page) {
        int totalCount = searchMapper.selectTotalCount(searchMap);

        int limit = 10; //한 페이지에 보여줄 게시물의 수
        int buttonAmount = 5; // 한 번에 보여질 페이징 버튼의 수
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(page, totalCount, limit, buttonAmount, searchMap);

        /* 요청 페이지와 검색 기준에 맞는 게시글 조회 */
        List<OrderDTO> searchPreparingList = searchMapper.selectSearchPreparingList(selectCriteria);

        Map<String, Object> searchListAndPaging = new HashMap<>();
        searchListAndPaging.put("paging", selectCriteria);
        searchListAndPaging.put("searchPreparingList", searchPreparingList);

        return searchListAndPaging;

    }

    /*---------------------------------------------배송중----------------------------------------------*/

    public Map<String, Object> selectSearchDeliveryList(Map<String, String> searchMap, int page) {

        int totalCount = searchMapper.selectTotalCount(searchMap);

        int limit = 10; //한 페이지에 보여줄 게시물의 수
        int buttonAmount = 5; // 한 번에 보여질 페이징 버튼의 수
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(page, totalCount, limit, buttonAmount, searchMap);

        /* 요청 페이지와 검색 기준에 맞는 게시글 조회 */
        List<OrderDTO> searchDeliveryList = searchMapper.selectSearchDeliveryList(selectCriteria);

        Map<String, Object> searchListAndPaging = new HashMap<>();
        searchListAndPaging.put("paging", selectCriteria);
        searchListAndPaging.put("searchDeliveryList", searchDeliveryList);

        return searchListAndPaging;
    }

    /*---------------------------------------------구매확정----------------------------------------------*/

    public Map<String, Object> selectSearchConfirmedList(Map<String, String> searchMap, int page) {
        int totalCount = searchMapper.selectTotalCount(searchMap);

        int limit = 10; //한 페이지에 보여줄 게시물의 수
        int buttonAmount = 5; // 한 번에 보여질 페이징 버튼의 수
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(page, totalCount, limit, buttonAmount, searchMap);

        /* 요청 페이지와 검색 기준에 맞는 게시글 조회 */
        List<OrderDTO> searchConfirmedList = searchMapper.selectSearchConfirmedList(selectCriteria);

        Map<String, Object> searchListAndPaging = new HashMap<>();
        searchListAndPaging.put("paging", selectCriteria);
        searchListAndPaging.put("searchConfirmedList", searchConfirmedList);

        return searchListAndPaging;
    }
}
