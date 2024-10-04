package com.varun.shopping.dto;

import com.varun.shopping.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDto {

    private Integer id;

    private Integer userId;

    private LocalDate orderDate;

    private BigDecimal totalAmount;

    private OrderStatus orderStatus;

    private List<OrderItemDto> items;

}
