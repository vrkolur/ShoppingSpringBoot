package com.varun.shopping.service.order;

import com.varun.shopping.model.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Integer userId);

    Order getOrderById(Integer id);

    List<Order> getUserOrders(Integer userId);
}
