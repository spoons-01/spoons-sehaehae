package com.spoons.sehaehae.admin.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MemberLevelDTO {

    private int memberNo;

    private String membershipCode;

    private MemberShipDTO membership;


}
