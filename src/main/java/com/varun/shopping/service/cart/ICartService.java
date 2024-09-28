package com.varun.shopping.service.cart;

import com.varun.shopping.model.Cart;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface ICartService {


    Cart getCartById(Integer id);

    ResponseEntity<String> clearCart(Integer id);

    BigDecimal getTotalAmount(Integer id);

}
