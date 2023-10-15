package com.spoons.sehaehae.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter @ToString
public class MyCouponDTO {
    private Long cpCode;
    private int memberNo;
    private Date getDate;
    private String useStatus;
    private Date useDate;
    private String cpName;
    private Long cpRate;
    private Date cpEdate;
}
