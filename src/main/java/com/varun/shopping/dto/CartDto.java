package com.varun.shopping.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDto {

    private Integer cartId;

    private Set<CartItemDto> cartItemDtoSet;

    private BigDecimal totalAmount;

}
