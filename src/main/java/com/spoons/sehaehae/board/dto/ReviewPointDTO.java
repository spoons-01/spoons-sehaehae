package com.spoons.sehaehae.board.dto;

import com.spoons.sehaehae.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewPointDTO {
    private int ptCode;
    private int memberNo;
    private MemberDTO writer;
    private int point;
    private String orderCode;
    private Long reviewNo;
    private ReviewDTO review;
}
