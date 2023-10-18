package com.spoons.sehaehae.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter @ToString
public class MyRefundDTO {
    private int refundCode;
    private String orderCode;
    private Date refundReceiptDate;
    private String processStatus;
    private String refundReason;
}
