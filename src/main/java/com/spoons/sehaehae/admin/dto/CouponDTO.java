package com.spoons.sehaehae.admin.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
@Getter@Setter@ToString
public class CouponDTO {
    private Long cpCode;
    private String cpName;
    private Long cpRate;
    private Date cpSdate;
    private Date cpEdate;

}
