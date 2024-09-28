package com.varun.shopping.service.cart;

import com.varun.shopping.model.CartItem;
import org.springframework.http.ResponseEntity;

public interface ICartItemService {

    ResponseEntity<String> addCartItem(Integer cartId, Integer productId, Integer quantity);

    ResponseEntity<String> updateCartItem(Integer cartId, Integer cartItemId, Integer quantity);

    ResponseEntity<String> deleteCartItem(Integer cartId, Integer cartItemId);

}
