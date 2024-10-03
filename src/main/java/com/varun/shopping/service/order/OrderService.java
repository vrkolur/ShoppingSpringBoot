package com.varun.shopping.service.order;

import com.varun.shopping.enums.OrderStatus;
import com.varun.shopping.exception.ResourceNotFoundException;
import com.varun.shopping.model.Cart;
import com.varun.shopping.model.Order;
import com.varun.shopping.model.OrderItem;
import com.varun.shopping.model.Product;
import com.varun.shopping.repository.OrderRepository;
import com.varun.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    @Override
    public Order placeOrder(Integer userId) {
        return null;
    }

    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    // PRIVATE METHODS

    private BigDecimal calculateTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice(),
                    cartItem.getTotalPrice()
            );
        }).toList();
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        //Set the user here
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }


}
