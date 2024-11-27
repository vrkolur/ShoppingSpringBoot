package com.varun.shopping.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {

    private Integer cartItemId;

    private Integer quantity;

    private BigDecimal unitPrice;

    private ProductDto product;

}
