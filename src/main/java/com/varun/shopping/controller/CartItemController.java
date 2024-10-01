package com.varun.shopping.controller;

import com.varun.shopping.exception.ResourceNotFoundException;
import com.varun.shopping.model.CartItem;
import com.varun.shopping.response.ApiResponse;
import com.varun.shopping.service.cart.ICartItemService;
import com.varun.shopping.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItem")
public class CartItemController {

    private final ICartItemService cartItemService;

    private final ICartService cartService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Integer cartId,
                                                     @RequestParam Integer productId,
                                                     @RequestParam Integer quantity) {
        try {
            if(cartId==-1)
                cartId = cartService.initializeNewCart();
            cartItemService.addCartItem(cartId, productId, quantity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(" ", e.getMessage()));
        }
        return ResponseEntity.ok(new ApiResponse("Item added to cart successfully", cartService.getCartById(cartId)));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteCartItem(@RequestParam Integer cartId, @RequestParam Integer productId) {
        try {
            cartItemService.deleteCartItem(cartId, productId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(" ", e.getMessage()));
        }
        return ResponseEntity.ok(new ApiResponse("Item removed from cart successfully", null));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateCartItem(@RequestParam Integer cartId, @RequestParam Integer productId, @RequestParam Integer quantity) {
        try {
            cartItemService.updateCartItem(cartId, productId, quantity);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(" ", e.getMessage()));
        }
        return ResponseEntity.ok(new ApiResponse("Item updated successfully", null));
    }
}
