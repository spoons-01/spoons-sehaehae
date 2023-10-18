package com.spoons.sehaehae.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MyOrderProductDTO {
    private int productCode;
    private String orderCode;
    private int orderAmount;
    private String usePremium;
    private String useEco;
    private String productName;
    private int productPrice;
}
