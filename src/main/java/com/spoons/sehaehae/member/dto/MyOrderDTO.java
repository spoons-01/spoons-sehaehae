package com.spoons.sehaehae.member.dto;

import com.spoons.sehaehae.product.dto.OrderProductDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter @Setter @ToString
public class MyOrderDTO {
    private int memberNo;
    private String name;
    private String phone;
    private String orderCode;
    private Date orderDate;
    private String orderRequest;
    private int orderPrice;
    private int orderDiscount;
    private int orderTotalPrice;
    private Long inputZipCode;
    private String inputAddress;
    private String inputAddress2;
    private Integer useCoupon;
    private String orderStatus;
    private int usePoint;
    private String collectionDate;
    private String deliveryDate;
    private Date progressDate;
    private String reviewStatus;
    private String image;
    private int reward;

    private int code; // 프로덕트 코드
    private String productName; // 프로덕트 이름
    private int price; // 프로덕트 가격

}
