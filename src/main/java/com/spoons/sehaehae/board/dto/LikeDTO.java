package com.spoons.sehaehae.board.dto;

import com.spoons.sehaehae.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class LikeDTO {
    private Long memberNo;
    private MemberDTO member;
    private Long rivewNo;
    private ReviewDTO rivew;
}
