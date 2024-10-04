package com.varun.shopping.service.order;

import com.varun.shopping.dto.OrderDto;
import com.varun.shopping.model.Order;

import java.util.List;

public interface IOrderService {

    OrderDto placeOrder(Integer userId);

    OrderDto getOrderById(Integer id);

    List<OrderDto> getUserOrders(Integer userId);
}
