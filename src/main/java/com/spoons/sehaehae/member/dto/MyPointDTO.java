package com.spoons.sehaehae.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MyPointDTO {
    private int ptCode;
    private int memberNo;
    private int point;
    private String orderCode;
    private Long reviewNo;
}
