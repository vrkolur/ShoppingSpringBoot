package com.varun.shopping.service.cart;

import com.varun.shopping.dto.CartDto;
import com.varun.shopping.model.Cart;
import com.varun.shopping.model.User;

import java.math.BigDecimal;

public interface ICartService {


    Cart getCartById(Integer id);

    Cart getCartByUserId(Integer userId);

    void clearCart(Integer id);

    BigDecimal getTotalPrice(Integer id);

    Cart initializeNewCart(User user);

    CartDto mapCartToCartDto(Cart cart);
}
