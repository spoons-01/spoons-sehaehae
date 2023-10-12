package com.spoons.sehaehae.admin.dto;


import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.product.dto.OrderProductDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter @Setter @ToString
public class OrderDTO {
    private String code;
    private Date orderDate;
    private String orderRequest;
    private int orderPrice;
    private int orderDiscount;
    private int orderTotalPrice;
    private Long memberCode;
    private int member;
    private Long inputZipCode;
    private Long inputAddress;
    private Long InputAddress2;
    private String orderStatus;
    private int usePoint;
    private Date collectionDate;
    private Date deliveryDate;
    private String image;
    private List<OrderProductDTO> list;
}
