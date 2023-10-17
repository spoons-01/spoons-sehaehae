package com.spoons.sehaehae.admin.dto;

import com.spoons.sehaehae.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Member;

@Getter@Setter@ToString
public class CpBoxDTO {
    private CouponDTO code;
    private MemberDTO member;
    private Long cpCode;
    private int memberCode;
    private String useStatus;
}
