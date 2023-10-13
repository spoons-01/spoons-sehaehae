package com.spoons.sehaehae.product.dto;


import lombok.*;

import java.util.Date;
import java.util.List;

@Getter@Setter@ToString@NoArgsConstructor@AllArgsConstructor
public class ProductDTO {
    private int code;
    private String name;
    private CategoryDTO category;
    private int price;
    private int premiumPrice;
    private char useableStatus;
    private int ecoPrice;
    private Date registDate;
    private Date modifyDate;
    private Date deleteDate;
    private char deleteStatus;
    private String photo;
    private List<OrderProductDTO> list;
}
