package com.spoons.sehaehae.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter @ToString
public class MyOrderDTO {
    private int memberNo;
    private String orderCode;
    private Date orderDate;
    private String orderStatus;
    private Date progressDate;
}
