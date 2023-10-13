package com.spoons.sehaehae.admin.controller;

import lombok.extern.slf4j.Slf4j;



import com.spoons.sehaehae.admin.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
@Controller
@Slf4j

/*---------------------------------------------주문리스트----------------------------------------------*/
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
                                   @RequestParam(required = false) String startDate,
                                   @RequestParam(required = false) String endDate,
                                   Model model) { //검색결과

        Map<String, String> searchMap = new HashMap<>();  //검색 조건과 검색결과를 묶는다
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);
        searchMap.put("startDate", startDate);
        searchMap.put("endDate", endDate);

        Map<String, Object> searchListAndPaging = orderService.selectSerchList(searchMap, page);  //list 뿐만 아니라 페이징 처리도 같이 하기 위해서 map사용
        model.addAttribute("paging", searchListAndPaging.get("paging"));
        model.addAttribute("searchList", searchListAndPaging.get("searchList"));

        return "admin/orderManagement/orderList";
    }

    /*---------------------------------------------결제완료----------------------------------------------*/
    @GetMapping("/complete-payment")
    public String selectPaymentList(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(required = false) String searchCondition,
                                    @RequestParam(required = false) String searchValue,
                                    @RequestParam(required = false) String startDate,
                                    @RequestParam(required = false) String endDate,
                                    Model model) {

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);
        searchMap.put("startDate", startDate);
        searchMap.put("endDate", endDate);

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
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("주문 상태 업데이트에 실패했습니다.");
        }
    }


    /*---------------------------------------------수거완료----------------------------------------------*/

    @GetMapping("/collection-completed")
    public String selectcollectionList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(required = false) String searchCondition,
                                       @RequestParam(required = false) String searchValue,
                                       @RequestParam(required = false) String startDate,
                                       @RequestParam(required = false) String endDate,
                                       Model model) {

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);
        searchMap.put("startDate", startDate);
        searchMap.put("endDate", endDate);

        Map<String, Object> searchListAndPaging = orderService.selectSearchCollectionList(searchMap, page);
        model.addAttribute("paging", searchListAndPaging.get("paging"));
        model.addAttribute("searchCollectionList", searchListAndPaging.get("searchCollectionList"));

        return "admin/orderManagement/collection-completed";
    }


    @PostMapping("/update-order-status2")
    public ResponseEntity<String> updateOrderStatus2(@RequestParam(name = "orderId[]") Long[] orderId) {
        log.info("{}", Arrays.toString(orderId));
        String newStatus = "세탁완료"; // 이 부분은 고정된 값으로 설정하거나 요구사항에 따라 동적으로 처리해야 합니다.

        boolean update = orderService.updateOrderStatus(orderId, newStatus);

        log.info("update: {}", update);

        if (update) {
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("주문 상태 업데이트에 실패했습니다.");
        }
    }

    /*---------------------------------------------세탁완료----------------------------------------------*/

    @GetMapping("/laundry-complete")
    public String selectlaundryList(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(required = false) String searchCondition,
                                       @RequestParam(required = false) String searchValue,
                                    @RequestParam(required = false) String startDate,
                                    @RequestParam(required = false) String endDate,
                                    Model model) {

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);
        searchMap.put("startDate", startDate);
        searchMap.put("endDate", endDate);

        Map<String, Object> searchListAndPaging = orderService.selectSearchLaundryList(searchMap, page);
        model.addAttribute("paging", searchListAndPaging.get("paging"));
        model.addAttribute("searchLaundryList", searchListAndPaging.get("searchLaundryList"));

        return "admin/orderManagement/laundry-complete";
    }

    @PostMapping("/update-order-status3")
    public ResponseEntity<String> updateOrderStatus3(@RequestParam(name = "orderId[]") Long[] orderId) {
        log.info("{}", Arrays.toString(orderId));
        String newStatus = "배송준비"; // 이 부분은 고정된 값으로 설정하거나 요구사항에 따라 동적으로 처리해야 합니다.

        boolean update = orderService.updateOrderStatus(orderId, newStatus);

        log.info("update: {}", update);

        if (update) {
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("주문 상태 업데이트에 실패했습니다.");
        }
    }

    /*---------------------------------------------배송준비----------------------------------------------*/

    @GetMapping("/preparing-delivery")
    public String selectpreparingList(@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(required = false) String searchCondition,
                                      @RequestParam(required = false) String searchValue,
                                      @RequestParam(required = false) String startDate,
                                      @RequestParam(required = false) String endDate,
                                      Model model) {

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);
        searchMap.put("startDate", startDate);
        searchMap.put("endDate", endDate);

        Map<String, Object> searchListAndPaging = orderService.selectSearchPreparingList(searchMap, page);
        model.addAttribute("paging", searchListAndPaging.get("paging"));
        model.addAttribute("searchPreparingList", searchListAndPaging.get("searchPreparingList"));

        return "admin/orderManagement/preparing-delivery";
    }

    @PostMapping("/update-order-status4")
    public ResponseEntity<String> updateOrderStatus4(@RequestParam(name = "orderId[]") Long[] orderId) {
        log.info("{}", Arrays.toString(orderId));
        String newStatus = "배송중"; // 이 부분은 고정된 값으로 설정하거나 요구사항에 따라 동적으로 처리해야 합니다.

        boolean update = orderService.updateOrderStatus(orderId, newStatus);

        log.info("update: {}", update);

        if (update) {
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("주문 상태 업데이트에 실패했습니다.");
        }
    }


    /*---------------------------------------------배송중----------------------------------------------*/

    @GetMapping("/delivery")
    public String selectdeliveryList(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(required = false) String searchCondition,
                                     @RequestParam(required = false) String searchValue,
                                     @RequestParam(required = false) String startDate,
                                     @RequestParam(required = false) String endDate,
                                     Model model) {

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);
        searchMap.put("startDate", startDate);
        searchMap.put("endDate", endDate);

        Map<String, Object> searchListAndPaging = orderService.selectSearchDeliveryList(searchMap, page);
        model.addAttribute("paging", searchListAndPaging.get("paging"));
        model.addAttribute("searchDeliveryList", searchListAndPaging.get("searchDeliveryList"));

        return "admin/orderManagement/delivery";
    }

    @PostMapping("/update-order-status5")
    public ResponseEntity<String> updateOrderStatus5(@RequestParam(name = "orderId[]") Long[] orderId) {
        log.info("{}", Arrays.toString(orderId));
        String newStatus = "구매확정"; // 이 부분은 고정된 값으로 설정하거나 요구사항에 따라 동적으로 처리해야 합니다.

        boolean update = orderService.updateOrderStatus(orderId, newStatus);

        log.info("update: {}", update);

        if (update) {
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("주문 상태 업데이트에 실패했습니다.");
        }
    }

    /*---------------------------------------------구매확정----------------------------------------------*/
    @GetMapping("/order-confirmed")
    public String selectconfirmedList(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(required = false) String searchCondition,
                                     @RequestParam(required = false) String searchValue,
                                      @RequestParam(required = false) String startDate,
                                      @RequestParam(required = false) String endDate,
                                      Model model) {

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);
        searchMap.put("startDate", startDate);
        searchMap.put("endDate", endDate);

        Map<String, Object> searchListAndPaging = orderService.selectSearchConfirmedList(searchMap, page);
        model.addAttribute("paging", searchListAndPaging.get("paging"));
        model.addAttribute("searchConfirmedList", searchListAndPaging.get("searchConfirmedList"));


        return "admin/orderManagement/order-confirmed";
    }


}
