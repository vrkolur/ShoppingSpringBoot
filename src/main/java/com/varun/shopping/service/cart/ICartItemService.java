package com.varun.shopping.service.cart;

public interface ICartItemService {

    void addCartItem(Integer cartId, Integer productId, Integer quantity);

    void updateCartItem(Integer cartId, Integer productId, Integer quantity);

    void deleteCartItem(Integer cartId, Integer productId);

}
