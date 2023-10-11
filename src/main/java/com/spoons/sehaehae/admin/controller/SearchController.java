package com.spoons.sehaehae.admin.controller;

import lombok.extern.slf4j.Slf4j;


import com.spoons.sehaehae.admin.dto.OrderDTO;
import com.spoons.sehaehae.admin.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@Slf4j
@RequestMapping("/orderManagement")
public class SearchController {
    private final OrderService orderService;

    public SearchController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public String selectSearchList(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(required = false) String searchCondition, //검색조건
                                   @RequestParam(required = false) String searchValue,
//                                   @RequestParam(required = false) String startDate,
//                                   @RequestParam(required = false) String endDate,
                                   Model model) { //검색결과

        Map<String, String> searchMap = new HashMap<>();  //검색 조건과 검색결과를 묶는다
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);
//        searchMap.put("startDate", startDate); // 추가
//        searchMap.put("endDate", endDate);     // 추가

        Map<String, Object> searchListAndPaging = orderService.selectSerchList(searchMap, page);  //list 뿐만 아니라 페이징 처리도 같이 하기 위해서 map사용
        model.addAttribute("paging", searchListAndPaging.get("paging"));
        model.addAttribute("searchList", searchListAndPaging.get("searchList"));

        return "admin/orderManagement/orderList";
    }


    @GetMapping("/complete-payment")
    public String selectPaymentList(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(required = false) String searchCondition,
                                    @RequestParam(required = false) String searchValue, Model model) {
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);

        Map<String, Object> searchListAndPaging = orderService.selectSearchPaymentList(searchMap, page);
        model.addAttribute("paging", searchListAndPaging.get("paging"));
        model.addAttribute("searchPaymentList", searchListAndPaging.get("searchPaymentList"));


        return "admin/orderManagement/complete-payment";
    }

    @PostMapping("/update-order-status")
    public ResponseEntity<String> updateOrderStatus(@RequestParam(name = "orderId[]") Long[] orderId) {
        log.info("{}", Arrays.toString(orderId));
        String newStatus = "수거완료"; // 이 부분은 고정된 값으로 설정하거나 요구사항에 따라 동적으로 처리해야 합니다.

        boolean update = orderService.updateOrderStatus(orderId, newStatus);

        log.info("update: {}", update);

        if (update) {
            return ResponseEntity.ok("ssuccess");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("주문 상태 업데이트에 실패했습니다.");
        }
    }




    @GetMapping("/collection-completed")
    public String selectcollectionList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(required = false) String searchCondition,
                                       @RequestParam(required = false) String searchValue, Model model) {

        return "admin/orderManagement/collection-completed";
    }

    @GetMapping("/laundry-complete")
    public String selectlaundryList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(required = false) String searchCondition,
                                       @RequestParam(required = false) String searchValue, Model model) {

        return "admin/orderManagement/laundry-complete";
    }

    @GetMapping("/preparing-delivery")
    public String selectpreparingList(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(required = false) String searchCondition,
                                    @RequestParam(required = false) String searchValue, Model model) {

        return "admin/orderManagement/preparing-delivery";
    }

    @GetMapping("/delivery")
    public String selectdeliveryList(@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(required = false) String searchCondition,
                                      @RequestParam(required = false) String searchValue, Model model) {

        return "admin/orderManagement/delivery";
    }

    @GetMapping("/order-confirmed")
    public String selectconfirmedList(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(required = false) String searchCondition,
                                     @RequestParam(required = false) String searchValue, Model model) {

        return "admin/orderManagement/order-confirmed";
    }


}
