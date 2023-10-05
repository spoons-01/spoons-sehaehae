package com.spoons.sehaehae.member.dto;


import lombok.*;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class MemberDTO {
    private int no;
    private String name;
    private int phone;
    private String address;
    private int point;

}
