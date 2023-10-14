package com.spoons.sehaehae.admin.dto;


import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.product.dto.OrderProductDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter @Setter @ToString @NoArgsConstructor
public class OrderDTO {
    private String code;
    private Date orderDate;
    private String orderRequest;
    private int orderPrice;
    private int orderDiscount;
    private int orderTotalPrice;
    private Long memberCode;
    private MemberDTO member;
    private String inputZipCode;
    private String inputAddress;
    private String InputAddress2;
    private String orderStatus;
    private int usePoint;
    private int useCoupon;
    private String collectionDate;
    private String deliveryDate;
    private String image;
    private List<OrderProductDTO> list;
}
