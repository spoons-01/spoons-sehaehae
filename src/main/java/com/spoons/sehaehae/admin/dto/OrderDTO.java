package com.spoons.sehaehae.admin.dto;


import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.product.dto.OrderProductDTO;
import com.spoons.sehaehae.product.dto.ProductDTO;
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
    private Long inputZipCode;
    private String inputAddress;
    private String InputAddress2;
    private Integer useCoupon;
    private String orderStatus;
    private int usePoint;
    private String collectionDate;
    private String deliveryDate;
    private Date progressDate;
    private String reviewStatus;
    private String image;
    private int reward;
    private List<OrderProductDTO> list;
}
