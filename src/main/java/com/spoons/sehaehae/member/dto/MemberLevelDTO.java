package com.spoons.sehaehae.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MemberLevelDTO {

    private int memberNo;

    private String membershipCode;

    private MemberShipDTO membership;


}
