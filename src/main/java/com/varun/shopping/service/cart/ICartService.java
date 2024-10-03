package com.varun.shopping.service.cart;

import com.varun.shopping.model.Cart;

import java.math.BigDecimal;

public interface ICartService {


    Cart getCartById(Integer id);

    Cart getCartByUserId(Integer userId);

    void clearCart(Integer id);

    BigDecimal getTotalPrice(Integer id);

    Integer initializeNewCart();
}
