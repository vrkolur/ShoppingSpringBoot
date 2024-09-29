package com.varun.shopping.service.cart;

import com.varun.shopping.exception.ResourceNotFoundException;
import com.varun.shopping.model.Cart;
import com.varun.shopping.repository.CartItemRepository;
import com.varun.shopping.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final AtomicInteger cartIdGenerator = new AtomicInteger(0);

    @Override
    public Cart getCartById(Integer id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + id));
        BigDecimal totalAmount = getTotalAmount(cart.getId());
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Integer id) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + id));
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);
        ResponseEntity.ok("Cart cleared successfully");
    }

    @Override
    public BigDecimal getTotalAmount(Integer id) {
        Cart cart = getCartById(id);
        return cart.getCartItems().stream().map(cartItem -> {
            BigDecimal unitPrice = cartItem.getUnitPrice();
            if (unitPrice == null) {
                return BigDecimal.ZERO;
            }
            return unitPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    @Override
    public Integer initializeNewCart() {
        Cart cart = new Cart();
        cart.setId(cartIdGenerator.incrementAndGet());
        cartRepository.save(cart);
        return cart.getId();
    }
}
