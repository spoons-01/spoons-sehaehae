package com.spoons.sehaehae.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter@Setter@ToString@NoArgsConstructor
public class OrderProductDTO {
    private int orderCode;
    private int productCode;
    private int amount;
}

