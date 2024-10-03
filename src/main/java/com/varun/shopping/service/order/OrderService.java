package com.varun.shopping.service.order;

import com.varun.shopping.enums.OrderStatus;
import com.varun.shopping.exception.ResourceNotFoundException;
import com.varun.shopping.model.Cart;
import com.varun.shopping.model.Order;
import com.varun.shopping.model.OrderItem;
import com.varun.shopping.model.Product;
import com.varun.shopping.repository.OrderRepository;
import com.varun.shopping.repository.ProductRepository;
import com.varun.shopping.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final CartService cartService;


    @Override
    public Order placeOrder(Integer userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = prepareOrder(cart);
        return saveOrderWithItems(order, cart);
    }


    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Override
    public List<Order> getUserOrders(Integer userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        if (!orders.isEmpty()) return orders;
        else throw new ResourceNotFoundException("Orders not found with userId: " + userId);
    }

    // PRIVATE METHODS

    private Order prepareOrder(Cart cart) {
        Order order = createOrder(cart); // Assuming createOrder already initializes basic fields of the order
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList)); // Add items to order
        order.setTotalAmount(calculateTotalPrice(orderItemList)); // Set total price
        return order;
    }


    // This is used to create the order.
    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private Order saveOrderWithItems(Order order, Cart cart) {
        // Save the order and clear the cart afterward, ensuring consistency
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId()); // Clear cart after order is placed
        return savedOrder;
    }

    private BigDecimal calculateTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(order, product, cartItem.getQuantity(), cartItem.getUnitPrice(), cartItem.getTotalPrice());
        }).toList();
    }


}
