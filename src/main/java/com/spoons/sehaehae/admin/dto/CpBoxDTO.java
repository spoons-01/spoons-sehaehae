package com.spoons.sehaehae.admin.dto;

import com.spoons.sehaehae.member.dto.MemberDTO;
import lombok.*;

@Getter@Setter@ToString@NoArgsConstructor@AllArgsConstructor
public class CpBoxDTO {
    private CouponDTO code;
    private MemberDTO member;
    private String useStatus;
}
