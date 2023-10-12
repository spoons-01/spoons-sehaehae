package com.spoons.sehaehae.board.dto;

import com.spoons.sehaehae.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter @ToString
public class QnaDTO {

    private Long no;
    private Long categoryNo;
    private BoardCategoryDTO category;
    private Long adminNo;
    private MemberDTO writer;
    private String title;
    private String body;
    private Date createdDate;
    private String status;
    private Date modifiedDate;
    private Date deleteDate;
}
