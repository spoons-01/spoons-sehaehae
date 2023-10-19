package com.spoons.sehaehae.member.dto;

import com.spoons.sehaehae.product.dto.OrderProductDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter @Setter @ToString
public class MyPointDTO {
    private int ptCode;
    private int memberNo;
    private int point;
    private String orderCode;
    private Long reviewNo;
//
//    @Getter
//    @Setter
//    @ToString
//    @NoArgsConstructor
//    public static class MyOrderDTO {
//        private String orderCode;
//        private Date orderDate;
//        private String orderRequest;
//        private int orderPrice;
//        private int orderDiscount;
//        private int orderTotalPrice;
//        private int memberNo;
//        private MemberDTO member;
//        private Long inputZipCode;
//        private String inputAddress;
//        private String InputAddress2;
//        private Integer useCoupon;
//        private String orderStatus;
//        private int usePoint;
//        private String collectionDate;
//        private String deliveryDate;
//        private Date progressDate;
//        private String reviewStatus;
//        private String image;
//        private int reward;
//        private List<OrderProductDTO> list;
//    }
}
