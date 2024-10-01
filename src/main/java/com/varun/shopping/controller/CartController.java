package com.varun.shopping.controller;

import com.varun.shopping.model.Cart;
import com.varun.shopping.response.ApiResponse;
import com.varun.shopping.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cart")
public class CartController {

    private final ICartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCartById(@PathVariable Integer id) {
        Cart cart = null;
        try {
            cart = cartService.getCartById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.ok(new ApiResponse("Cart fetched successfully", cart));
    }

    @DeleteMapping("/clear/{id}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Integer id) {
        Cart cart = null;
        try {
            cart = cartService.getCartById(id);
            cartService.clearCart(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.ok(new ApiResponse("Cart cleared successfully", cart));
    }

    @GetMapping("/total/{cartId}")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Integer cartId) {
        BigDecimal totalAmount = null;
        try {
            totalAmount = cartService.getTotalPrice(cartId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.ok(new ApiResponse("Cart total amount fetched successfully", totalAmount));
    }


}
