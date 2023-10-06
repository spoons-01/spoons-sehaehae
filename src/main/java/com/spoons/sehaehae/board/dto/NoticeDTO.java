package com.spoons.sehaehae.board.dto;

import com.spoons.sehaehae.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter @ToString
public class NoticeDTO {

    private Long no;
    private String title;
    private String body;
    private Date createdDate;
    private int count;
    private Long noticeWriterNo;
    private MemberDTO writer;
    private String status;
    private Date deleteDate;
    private Date modifiedDate;


}
