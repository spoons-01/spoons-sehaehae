package com.spoons.sehaehae.board.dto;

import com.spoons.sehaehae.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter @Getter @ToString
public class ReplyDTO {
    private Long no;
    private Long reviewNo;
//    private ReviewDTO review;
    private String body;
    private Date replyDate;
    private String status;
    private Date deleteDate;
    private Date modifiedDate;
    private int memberNo;
    private MemberDTO writer;
}
