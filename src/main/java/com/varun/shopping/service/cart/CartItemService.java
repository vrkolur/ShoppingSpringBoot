package com.varun.shopping.service.cart;

import com.varun.shopping.exception.ResourceNotFoundException;
import com.varun.shopping.model.Cart;
import com.varun.shopping.model.CartItem;
import com.varun.shopping.model.Product;
import com.varun.shopping.repository.CartItemRepository;
import com.varun.shopping.repository.CartRepository;
import com.varun.shopping.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;

    private final IProductService productService;

    private final ICartService cartService;

    private final CartRepository cartRepository;

    /**
     * Adds a product to the shopping cart or updates the quantity if the product already exists.
     *
     * @param cartId    The ID of the shopping cart where the item is to be added or updated.
     * @param productId The ID of the product to be added or updated in the cart.
     * @param quantity  The quantity of the product to be added to the cart.
     */
    @Override
    public void addCartItem(Integer cartId, Integer productId, Integer quantity) {
        Cart cart = cartService.getCartById(cartId);
        Product product = productService.getProductById(productId);

        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newCartItem = new CartItem();
                    newCartItem.setCart(cart);
                    newCartItem.setProduct(product);
                    newCartItem.setUnitPrice(product.getUnitPrice());
                    newCartItem.setQuantity(0);
                    return newCartItem;
                });

        // If the product is already in the cart then add the quantity else update the quantity
        cartItem.setQuantity(cartItem.getQuantity() == 0 ? quantity : cartItem.getQuantity() + quantity);

        cartItem.setTotalPrice();
        cart.addCartItem(cartItem);

        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }


    @Override
    public void updateCartItem(Integer cartId, Integer productId, Integer quantity) {
        Cart cart = cartService.getCartById(cartId);
        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        cartItem.setQuantity(quantity);
        cartItem.setUnitPrice(cartItem.getProduct().getUnitPrice());
        cartItem.setTotalPrice();
        cartRepository.save(cart);
    }

    @Override
    public void deleteCartItem(Integer cartId, Integer productId) {
        Cart cart = cartService.getCartById(cartId);
        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + productId));
        cart.removeCartItem(cartItem);
        cart.updateTotalAmount();
        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);
    }
}
