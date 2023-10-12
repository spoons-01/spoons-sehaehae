package com.spoons.sehaehae.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CartDTO {
    private int amount;
    private ProductDTO product;
    private int member;
    private String useEco;
    private String usePremium;
}
