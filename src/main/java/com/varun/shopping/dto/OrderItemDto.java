package com.varun.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemDto {

    private Integer id;

    private String productName;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

}
