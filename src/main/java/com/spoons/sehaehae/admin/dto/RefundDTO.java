package com.spoons.sehaehae.admin.dto;

import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.product.dto.OrderProductDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@ToString
public class RefundDTO {
    private String refundCode;
    private String orderCode;
    private OrderDTO order;
    private Date refundReceiptDate;
    private Date refundCompleteDate;
    private String processStatus;
    private int totalRefundAmount;
    private int usedPoint;
    private int usedCupon;
    private int refundShippingFee;
    private String  refundReason;

}
