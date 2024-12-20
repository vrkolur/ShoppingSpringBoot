package com.varun.shopping.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {

    private Integer id;

    private String productName;

    private int quantity;

    private BigDecimal unitPrice;

}
