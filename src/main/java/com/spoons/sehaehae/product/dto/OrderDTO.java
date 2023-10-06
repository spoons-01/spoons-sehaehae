package com.spoons.sehaehae.product.dto;

import com.spoons.sehaehae.member.dto.MemberDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter @ToString @NoArgsConstructor
public class OrderDTO {
    private String code;
    private Date date;
    private String request; //O
    private int price; //o
    private int discount;
    private int totalPrice;
    private int member;
    private String address; //O
    private int coupon;
    private int usePoint; //o
    private String orderStatus;
    private String collectionDate; //o
    private String deliveryDate; //o
    private String image;  //O
}
