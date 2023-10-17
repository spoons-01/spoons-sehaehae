package com.spoons.sehaehae.board.dto;

import com.spoons.sehaehae.admin.dto.OrderDTO;
import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.member.dto.MyOrderDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter @Setter @ToString
public class ReviewDTO {

    private Long reviewNo;
    private String title;
    private String body;
    private int count;
    private Date createdDate;
    private Date modifiedDate;
    private Long memberNo;
    private MemberDTO writer;
    private String status;
    private Date deleteDate;
    private Long orderCode;
    private OrderDTO myOrders;
    private int rating;
    private List<ReplyDTO> replyList;
    private AttachmentDTO attachment;

}
