package com.spoons.sehaehae.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter@Setter@ToString@NoArgsConstructor
public class CouponDTO {
    private int code;
    private String name;
    private Date duration;
    private int rate;
}
