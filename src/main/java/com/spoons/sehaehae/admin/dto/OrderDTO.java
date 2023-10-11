package com.spoons.sehaehae.admin.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
@Getter@Setter@ToString
public class OrderDTO {
 private Long code;
 private Date orderDate;
 private String orderRequest;
 private int orderPrice;
 private int orderDiscount;
 private int orderTotalPrice;
 private Long memberCode;
 private MemberDTO member;
 private Long inputZipCode;
 private Long inputAddress;
 private Long InputAddress2;
 private String orderStatus;
 private int usePoint;
 private Date collectionDate;
 private Date deliveryDate;
}
