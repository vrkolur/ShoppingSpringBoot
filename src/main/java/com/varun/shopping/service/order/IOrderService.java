package com.varun.shopping.service.order;

import com.varun.shopping.model.Order;

public interface IOrderService {

    Order placeOrder(Integer userId);

    Order getOrderById(Integer id);

}
