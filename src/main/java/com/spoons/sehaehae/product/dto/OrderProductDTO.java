package com.spoons.sehaehae.product.dto;

import com.spoons.sehaehae.admin.dto.OrderDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter@Setter@ToString@NoArgsConstructor
public class OrderProductDTO {
    private ProductDTO product;
    private OrderDTO order;
    private String orderCode;
    private int productCode;
    private int amount;
    private char useEco;
    private char usePremium;
}



