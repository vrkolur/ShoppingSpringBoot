package com.varun.shopping.service.cart;

import com.varun.shopping.dto.CartDto;
import com.varun.shopping.exception.ResourceNotFoundException;
import com.varun.shopping.model.Cart;
import com.varun.shopping.model.User;
import com.varun.shopping.repository.CartItemRepository;
import com.varun.shopping.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final AtomicInteger cartIdGenerator = new AtomicInteger(0);

    private final ModelMapper modelMapper;

    @Override
    public Cart getCartById(Integer id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + id));
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartByUserId(Integer userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) return cart;
        else throw new ResourceNotFoundException("Cart not found with userId: " + userId);
    }

    @Override
    @Transactional
    public void clearCart(Integer id) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + id));
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);
        ResponseEntity.ok("Cart cleared successfully");
    }

    @Override
    public BigDecimal getTotalPrice(Integer id) {
        Cart cart = getCartById(id);
        return cart.getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User user) {
        return Optional.ofNullable(cartRepository.findByUserId(user.getId()))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    @Override
    public CartDto mapCartToCartDto(Cart cart) {
        return modelMapper.map(cart, CartDto.class);
    }
}
